package edu.grinnell.csc207.util;

/** Has static methods for encrypting and decryping saesar and vigenere ciphers. */
public class CipherUtils {
  /**
   * Mod that always return positive e.g. -2 / 26 will return 24
   *
   * @param n First number
   * @param m Second number
   * @return The remainder
   */
  private static int mod(int n, int m) {
    return ((n % m) + m) % m;
  } // mod

  private static char charShift(char ch, int n) {
    final int numChars = 26;
    final int base = (int) 'a';
    return (char) (mod((int) ch + n - base, numChars) + base);
  } // charShift

  private static int letter2int(char letter) {
    return (int) letter - (int) 'a';
  } // letter2int

  private static char int2letter(int i) {
    return (char) (i + (int) 'a');
  } // int2letter

  /**
   * Encrypts a string with the caesar cipher. Alphabetical number of the letter determines the
   * shift
   *
   * @param str Plain text
   * @param letter cipher key
   * @return Cipher text
   */
  public static String caesarEncrypt(String str, char letter) {
    StringBuilder cipher = new StringBuilder(str.length());
    for (char ch : str.toCharArray()) {
      cipher.append(charShift(ch, letter2int(letter)));
    } // for

    return cipher.toString();
  } // caesarEncrypt

  /**
   * Decrypt a string with the caesar cipher. Alphabetical number of the letter determines the shift
   *
   * @param str Cipher text
   * @param letter cipher key
   * @return Plain text
   */
  public static String caesarDecrypt(String str, char letter) {
    StringBuilder cipher = new StringBuilder(str.length());
    for (char ch : str.toCharArray()) {
      cipher.append(charShift(ch, -1 * letter2int(letter)));
    } // for

    return cipher.toString();
  } // caesarDecrypt

  /**
   * Encrypt a string with the vignere cipher.
   *
   * @param str Plain text
   * @param key cipher key
   * @return Cipher text
   */
  public static String vigenereEncrypt(String str, String key) {
    StringBuilder cipher = new StringBuilder(str.length());

    for (int i = 0; i < str.length(); i++) {
      cipher.append(charShift(str.charAt(i), letter2int(key.charAt(i % key.length()))));
    } // for

    return cipher.toString();
  } // vigenereEncrypt

  /**
   * Decrypt a string with the vignere cipher.
   *
   * @param str Cipher text
   * @param key cipher key
   * @return Plain text
   */
  public static String vigenereDecrypt(String str, String key) {
    StringBuilder cipher = new StringBuilder(str.length());

    for (int i = 0; i < str.length(); i++) {
      cipher.append(charShift(str.charAt(i), -1 * letter2int(key.charAt(i % key.length()))));
    } // for

    return cipher.toString();
  } // vigenereDecrypt
} // CipherUtils
