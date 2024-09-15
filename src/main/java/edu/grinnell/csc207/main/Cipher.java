package edu.grinnell.csc207.main;

import edu.grinnell.csc207.util.CipherUtils;
import java.io.PrintWriter;

/**
 * Main class. Serves as entry point to the program
 */
public class Cipher {

  /**
   * Check if the string contains only lower case letters.
   * @param str The string to check
   * @return whether the string contains only lower case characters
   */
  public static boolean isAllLower(String str) {
    for (int i = 0; i < str.length(); i++) {
      if (!(str.charAt(i) >= 'a' && str.charAt(i) <= 'z')) {
        return false;
      } // if character not lower case
    } // for
    return true;
  } // isAllLower

  /**
   * Main function of Cipher.java. Entry point for program
   * @param args command line arguements must contain -encode/-decode -caesar/-vigenere text1 text2
   */
  public static void main(String[] args) {
    final int argLength = 4;
    if (!(args.length == argLength)) {
      System.err.println("Error: Expected 4 parameters, received " + args.length);
      System.exit(2);
    } // parameter number

    String action = null;
    String option = null;
    int textIndex = 0;
    String[] text = new String[2];

    for (String s : args) {
      if (s.equals("-decode") || s.equals("-encode")) {
        if (action != null) {
          text[textIndex] = s;
          textIndex++;
        } // if not action
        action = s;
      } // if not decode or encode

      if (s.equals("-caesar") || s.equals("-vigenere")) {
        if (option != null) {
          text[textIndex] = s;
          textIndex++;
        } // if no option
        option = s;
      } // if no caesar or vigenere

      if ((s.length() == 0 || s.charAt(0) != '-') && textIndex < 2) {
        text[textIndex] = s;
        textIndex++;
      } // parse text input
    } // parse args

    if (action == null) {
      System.err.println(
          "Error: No valid action specified.  Legal values are '-encode' and '-decode'");
      System.exit(1);
    } // no action

    if (option == null) {
      System.err.println(
          "Error: No valid option specified.  Legal values are '-caesar' and '-vigenere'");
      System.exit(1);
    } // no option

    for (String s : text) {
      if (!isAllLower(s)) {
        System.err.println("Error: strings must be only lowercase letters");
        System.exit(1);
      } // check lower case
    } // for all inputs

    PrintWriter pen = new PrintWriter(System.out, true);

    if (option.equals("-caesar")) {
      if (text[1].length() != 1) {
        System.err.println("Error: Caesar ciphers require a one-character key");
        System.exit(1);
      } // if caesar key more than one key

      if (action.equals("-decode")) {
        pen.println(CipherUtils.caesarDecrypt(text[0], text[1].charAt(0)));
      } else {
        pen.println(CipherUtils.caesarEncrypt(text[0], text[1].charAt(0)));
      } // encode decode control flow
    } // caesar cipher

    if (option.equals("-vigenere")) {
      if (text[1].length() == 0) {
        System.err.println("Error: Empty keys are not permitted");
        System.exit(1);
      } // if key is empty

      if (action.equals("-decode")) {
        pen.println(CipherUtils.vigenereDecrypt(text[0], text[1]));
      } else {
        pen.println(CipherUtils.vigenereEncrypt(text[0], text[1]));
      } // encode decode control flow
    } // vignere cipher

    pen.close();
  } // main
} // Cipher
