package ao.wiza.backend.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

import static ao.wiza.backend.filters.LanguageFilter.LanguageContext.LANG;

public class LanguageFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
                                  @NonNull HttpServletResponse response,
                                  @NonNull FilterChain filterChain) throws ServletException, IOException {


    var lang = Optional.ofNullable(request.getHeader("Accept-Language")).orElse("pt");

    ScopedValue.where(LANG, lang).run(() -> {
      try {
        filterChain.doFilter(request, response);
      } catch (IOException | ServletException e) {
        throw new RuntimeException(e);
      }
    });

  }

  public static class LanguageContext {
    public static final ScopedValue<String> LANG = ScopedValue.newInstance();
  }
}
