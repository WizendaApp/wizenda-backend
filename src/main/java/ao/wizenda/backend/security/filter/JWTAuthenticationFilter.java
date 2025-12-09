package ao.wizenda.backend.security.filter;

import ao.wizenda.backend.models.User;
import ao.wizenda.backend.services.AuthService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
  private static final String AUTHORIZATION_HEADER = "Authorization";

  private static final String BEARER_PREFIX = "Bearer ";

  private final AuthService authService;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
                                  @NonNull HttpServletResponse response,
                                  @NonNull FilterChain filterChain) throws ServletException, IOException {

    String token = extractBearerToken(request);
    if (token != null && authService.validateToken(token)) {
      Authentication authentication = parseToAuth(authService.getUser(token));
      log.debug("User: {}", authentication);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    filterChain.doFilter(request, response);
  }

  private Authentication parseToAuth(@Nullable User user) {
    if (user == null) return null;

    return new UsernamePasswordAuthenticationToken(
        user.getUsername(),
        "",
        List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
    );
  }

  private String extractBearerToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
      return bearerToken.substring(7);
    }
    return null;
  }
}

