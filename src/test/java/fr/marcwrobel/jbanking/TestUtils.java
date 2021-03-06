package fr.marcwrobel.jbanking;

import org.junit.jupiter.api.Assertions;

/**
 * Some test utilities.
 *
 * @author Marc Wrobel
 */
public class TestUtils {

  public static final String BLANK = " \t\n ";

  public static void shouldHaveThrown(Class<? extends Throwable> exceptionClass) {
    Assertions.fail("Should have thrown " + exceptionClass.getName());
  }
}
