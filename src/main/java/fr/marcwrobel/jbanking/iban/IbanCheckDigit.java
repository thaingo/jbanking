package fr.marcwrobel.jbanking.iban;

/**
 * Provide ISO 7064 Mod 97,10 IBAN check digit calculation and validation.
 *
 * <p>This class is implementing the singleton pattern by being an enumeration. Algorithm is based
 * on the work in <a
 * href="http://svn.apache.org/viewvc/commons/proper/validator/trunk/src/main/java/org/apache/commons/validator/routines/checkdigit/IBANCheckDigit.java?view=co">Apache
 * commons validator project</a>.
 *
 * @author Marc Wrobel
 * @see <a
 *     href="http://wikipedia.org/wiki/International_Bank_Account_Number">http://wikipedia.org/wiki/International_Bank_Account_Number</a>
 * @see <a
 *     href="http://svn.apache.org/viewvc/commons/proper/validator/trunk/src/main/java/org/apache/commons/validator/routines/checkdigit/IBANCheckDigit.java?view=co">IBANCheckDigit</a>
 * @since 1.0
 */
public enum IbanCheckDigit {
  INSTANCE;

  private static final int BBAN_INDEX = 4;

  private static final long CHECK_DIGITS_MAX = 999999999;
  private static final long CHECK_DIGITS_MODULUS = 97;
  private static final int CHECK_DIGITS_REMAINDER = 1;

  /**
   * Validate the given IBAN check digit.
   *
   * @param iban a non null string.
   * @return {@code true} if the given IBAN check digit is valid, {@code false} otherwise.
   * @throws IllegalArgumentException if the given IBAN is null or if its size is not at least four
   *     characters.
   */
  public boolean validate(String iban) {
    return modulus(iban) == CHECK_DIGITS_REMAINDER;
  }

  /**
   * Calculate the given IBAN check digit. For a valid calculation the given IBAN its characters
   * have to be alphanumeric ([a-zA-Z0-9]) and check digit characters have to be set to zero.
   *
   * @param iban a non null string
   * @return the given IBAN check digit.
   */
  public String calculate(String iban) {
    int modulusResult = modulus(iban);
    int charValue = (98 - modulusResult);
    String checkDigit = Integer.toString(charValue);
    return (charValue > 9 ? checkDigit : "0" + checkDigit);
  }

  private int modulus(String iban) {
    if (iban == null) {
      throw new IllegalArgumentException("the iban argument cannot be null");
    }
    if (iban.length() <= BBAN_INDEX) {
      throw new IllegalArgumentException(
          "the iban argument size must be grater than " + BBAN_INDEX);
    }

    String reformattedIban = iban.substring(BBAN_INDEX) + iban.substring(0, BBAN_INDEX);
    long total = 0;
    for (int i = 0; i < reformattedIban.length(); i++) {
      int charValue = Character.getNumericValue(reformattedIban.charAt(i));
      total = (charValue > 9 ? total * 100 : total * 10) + charValue;
      if (total > CHECK_DIGITS_MAX) {
        total = total % CHECK_DIGITS_MODULUS;
      }
    }

    return (int) (total % CHECK_DIGITS_MODULUS);
  }
}
