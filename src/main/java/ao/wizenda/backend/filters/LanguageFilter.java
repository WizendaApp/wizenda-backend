package ao.wizenda.backend.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

import static ao.wizenda.backend.filters.LanguageFilter.LanguageContext.LANG;

public class LanguageFilter extends OncePerRequestFilter {


  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
                                  @NonNull HttpServletResponse response,
                                  @NonNull FilterChain filterChain) {
    var lang = Optional.ofNullable(request.getHeader("Accept-Language"))
        .map(LanguageFilter::transform)
        .orElse("pt")
        .substring(0, 2)
        .trim();

    ScopedValue.where(LANG, lang).run(() -> {
      try {
        filterChain.doFilter(request, response);
      } catch (IOException | ServletException e) {
        throw new RuntimeException(e);
      }
    });

  }

  private static String transform(String s) {
    if (s.length() > 1) {
      return s;
    }

    return s + " ".repeat(3);
  }

  public static class LanguageContext {
    public static final ScopedValue<String> LANG = ScopedValue.newInstance();
  }
}
