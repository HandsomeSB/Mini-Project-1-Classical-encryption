package edu.grinnell.csc207.main;

import java.io.PrintWriter;
import edu.grinnell.csc207.util.CipherUtils;

public class AllCaesar {
  public static void main(String[] args) {
    if(!(args.length == 2)){
      System.err.println("Incorrect number of parameters");
      System.exit(2);
    }

    if(!(args[0].equals("encode") || args[0].equals("decode"))){
        System.err.println("Invalid option: " + args[0] + " Valid options are \"encode\" or \"decode\"");
        System.exit(1);
    }

    if(!args[1].toLowerCase().equals(args[1])){
      System.err.println("String contains characters other than lowercase letters.");
      System.exit(1);
    }

    PrintWriter pen = new PrintWriter(System.out, true);

    if(args[0].equals("decode")){
      for (char ch = 'a'; ch <= 'z'; ch++) {
        pen.printf("n = %c: %s\n", ch, CipherUtils.caesarDecrypt(args[1], ch));
      }
    }else{
      for (char ch = 'a'; ch <= 'z'; ch++) {
        pen.printf("n = %c: %s\n", ch, CipherUtils.caesarEncrypt(args[1], ch));
      }
    }

    pen.close();
  }
}
