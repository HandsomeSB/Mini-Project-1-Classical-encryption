package edu.grinnell.csc207.util;

public class CipherUtils {
  /**
   * Mod that always return positive
   * e.g. -2 / 26 will return 24
   * @param n First number
   * @param m Second number
   * @return The remainder
   */
  private static int mod(int n, int m){
    return ((n % m) + m) % m; 
  }

  private static char charShift(char ch, int n){
    final int base = (int) 'a' ;
    return (char) (mod((int) ch + n - base, 26) + base);
  }

  private static int letter2int(char letter) {
    return (int) letter - (int) 'a'; 
  }

  private static char int2letter(int i) {
    return (char) (i + (int) 'a'); 
  }

  public static String caesarEncrypt(String str, char letter) {
    StringBuilder cipher = new StringBuilder(str.length());
    for(char ch : str.toCharArray()){
      cipher.append(charShift(ch, letter2int(letter)));
    }

    return cipher.toString();
  }

  public static String caesarDecrypt(String str, char letter) {
    StringBuilder cipher = new StringBuilder(str.length());
    for(char ch : str.toCharArray()){
      cipher.append(charShift(ch, -1 * letter2int(letter)));
    }

    return cipher.toString();
  }

  public static String vigenereEncrypt(String str, String key) {
    StringBuilder cipher = new StringBuilder(str.length());

    for(int i = 0; i < str.length(); i++){
      cipher.append(charShift(str.charAt(i), letter2int(key.charAt(i % key.length()))));
    }

    return cipher.toString(); 
  }

  public static String vigenereDecrypt(String str, String key) {
    StringBuilder cipher = new StringBuilder(str.length());

    for(int i = 0; i < str.length(); i++){
      cipher.append(charShift(str.charAt(i), -1 * letter2int(key.charAt(i % key.length()))));
    }

    return cipher.toString(); 
  }
}
