package ao.wizenda.backend.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

import static ao.wizenda.backend.filters.SessionFilter.SessionContext.SESSION;

public class SessionFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
                                  @NonNull HttpServletResponse response,
                                  @NonNull FilterChain filterChain) throws ServletException, IOException {
    var session = UUID.randomUUID().toString();

    ScopedValue.where(SESSION, session).run(() -> {
      try {
        filterChain.doFilter(request, response);
      } catch (IOException | ServletException e) {
        throw new RuntimeException(e);
      }
    });

  }

  public static class SessionContext {
    public static final ScopedValue<String> SESSION = ScopedValue.newInstance();
  }
}
