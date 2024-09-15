package edu.grinnell.csc207.main;

import edu.grinnell.csc207.util.CipherUtils;
import java.io.PrintWriter;

/**
 * Contains the main function that test the caesar cipher.
 */
public class AllCaesar {
  /**
   * Entry point for AllCaesar. Accepts and string and outputs all caesar combinations
   * @param args
   */
  public static void main(String[] args) {
    if (!(args.length == 2)) {
      System.err.println("Incorrect number of parameters");
      System.exit(2);
    } // incorrect number of parameters

    if (!(args[0].equals("encode") || args[0].equals("decode"))) {
      System.err.println(
          "Invalid option: " + args[0] + " Valid options are \"encode\" or \"decode\"");
      System.exit(1);
    } // input not encode or decode

    if (!args[1].toLowerCase().equals(args[1])) {
      System.err.println("String contains characters other than lowercase letters.");
      System.exit(1);
    } // string other than lowercase letters

    PrintWriter pen = new PrintWriter(System.out, true);

    if (args[0].equals("decode")) {
      for (char ch = 'a'; ch <= 'z'; ch++) {
        pen.printf("n = %c: %s\n", ch, CipherUtils.caesarDecrypt(args[1], ch));
      } // test all decode letters
    } else {
      for (char ch = 'a'; ch <= 'z'; ch++) {
        pen.printf("n = %c: %s\n", ch, CipherUtils.caesarEncrypt(args[1], ch));
      } // test all encode letters
    } // encode decode control flow

    pen.close();
  } // encode decode control flow
} // AllCaesar
