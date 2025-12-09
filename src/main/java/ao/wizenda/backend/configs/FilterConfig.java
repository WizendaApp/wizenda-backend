package ao.wizenda.backend.configs;

import ao.wizenda.backend.filters.LanguageFilter;
import ao.wizenda.backend.filters.SessionFilter;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
  @Bean
  public FilterRegistrationBean<@NonNull SessionFilter> sessionFilter() {
    FilterRegistrationBean<@NonNull SessionFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new SessionFilter()); // Or autowire if needed
    registrationBean.addUrlPatterns("/api/*"); // Apply filter to specific URL patterns
    registrationBean.setOrder(1); // Set order if multiple filters are present
    return registrationBean;
  }

  @Bean
  public FilterRegistrationBean<@NonNull LanguageFilter> langFilter() {
    FilterRegistrationBean<@NonNull LanguageFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new LanguageFilter()); // Or autowire if needed
    registrationBean.addUrlPatterns("/api/*"); // Apply filter to specific URL patterns
    registrationBean.setOrder(2); // Set order if multiple filters are present
    return registrationBean;
  }
}
