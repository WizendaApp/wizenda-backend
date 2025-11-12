package ao.wiza.backend.utils;

import org.springframework.context.MessageSource;

import java.util.Locale;

public record TranslationUtils(MessageSource messageSource) {
  public String translate(String message) {
    return translate(message, "pt");
  }

  public String translate(String message, String lang, Object... values) {
    return messageSource.getMessage(message, values, Locale.of(lang));
  }
}
