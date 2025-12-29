package ao.wizenda.backend.utils.validations.adult;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.jspecify.annotations.Nullable;

import java.time.LocalDate;
import java.time.Period;

import static java.time.LocalDate.now;

public class IsAdultValidator implements ConstraintValidator<IsAdult, @Nullable LocalDate> {
  @Override
  public boolean isValid(@Nullable LocalDate value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }

    return Period.between(value, now()).getYears() >= 18;
  }
}
