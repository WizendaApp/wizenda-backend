package ao.wizenda.backend.configs;

import ao.wizenda.backend.security.SecurityUserDetailsService;
import ao.wizenda.backend.security.filter.JWTAuthenticationFilter;
import ao.wizenda.backend.services.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
public class SecurityConfig {

  private static void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>
                                    .AuthorizationManagerRequestMatcherRegistry registry) {
    registry.requestMatchers(
            "/v3/api-docs/**",    // Allow access to OpenAPI docs
            "/swagger-ui/**",     // Allow access to Swagger UI
            "/swagger-ui.html",
            "/api/v1/auth/**"
        ).permitAll()
        .requestMatchers("/error").permitAll()
        .requestMatchers("/actuator/**").permitAll()
        .requestMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
        .requestMatchers(HttpMethod.POST, "/api/v1/users/verify").permitAll()
        .anyRequest().authenticated();
  }

  private static void customize(SessionManagementConfigurer<HttpSecurity> configurer) {
    configurer.sessionCreationPolicy(STATELESS);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthService authService) {
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(SecurityConfig::customize)
        .formLogin(AbstractHttpConfigurer::disable)
        .addFilterBefore(new JWTAuthenticationFilter(authService), UsernamePasswordAuthenticationFilter.class)
        .sessionManagement(SecurityConfig::customize);

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http,
                                                     SecurityUserDetailsService userDetailsService) {
    var builder = http.getSharedObject(AuthenticationManagerBuilder.class);

    builder.userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder());

    return builder.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
  }
}
