package ao.wiza.backend.utils;

import java.util.Random;

public final class RandomUtils {
  private static final Random random = new Random();
  private RandomUtils() {}

  public static String randomCode(int length) {
    if (length < 0) {
      throw new IllegalArgumentException("Length must be greater than zero");
    }

    var builder = new StringBuilder();

    for (var i = 0; i < length; i++) {
      builder.append(random.nextInt(0, 9));
    }

    return builder.toString();
  }
}
