package ao.wizenda.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class TokenProvider {

  @Value("${security.jwt.secret-key}")
  private String secret;

  @Value("${security.jwt.issuer}")
  private String issuer;

  @Value("${security.jwt.token-expiry-time-in-seconds}")
  private Long tokenExpiryTimeInSeconds;

  @Value("${security.jwt.refresh-expiry-time-in-seconds}")
  private long refreshExpiryTimeInSeconds;


  public String createToken(Authentication authentication) {
    String username = authentication.getName();
    List<String> authorities = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .map(authority -> authority.replace("ROLE_", ""))
        .toList();
    return Jwts.
        builder()
        .issuer(issuer)
        .subject(username)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + (tokenExpiryTimeInSeconds * 1000)))
        .claim("username", username)
        .claim("authorities", authorities)
        .signWith(getSecretKey()).compact();
  }

  public String createRefreshToken(Authentication authentication) {
    String username = authentication.getName();
    return Jwts.
        builder()
        .issuer(issuer)
        .subject(username)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + (refreshExpiryTimeInSeconds * 1000)))
        .signWith(getSecretKey()).compact();
  }

  @SuppressWarnings("unchecked")
  public Authentication getAuthentication(String token) {
    Claims payload = parseClaimsFromToken(token);
    String username = payload.getSubject();
    var authorities = (ArrayList<String>) payload.get("authorities", ArrayList.class);
    var grantedAuthorities = ofNullable(authorities).orElse(new ArrayList<>())
        .stream().map(role -> "ROLE_" + role).map(SimpleGrantedAuthority::new).toList();
    return new UsernamePasswordAuthenticationToken(username, "", grantedAuthorities);
  }

  public boolean validateToken(String token) {
    try {
      parseClaimsFromToken(token);
      return true;
    } catch (JwtException e) {
      return false;
    }
  }

  private SecretKey getSecretKey() {
    return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
  }

  private Claims parseClaimsFromToken(String token) {
    return Jwts.parser()
        .verifyWith(getSecretKey())
        .build().parseSignedClaims(token).getPayload();
  }
}