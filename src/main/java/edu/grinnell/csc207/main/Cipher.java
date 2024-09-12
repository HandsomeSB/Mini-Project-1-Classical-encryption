package edu.grinnell.csc207.main;

import java.io.PrintWriter;

import edu.grinnell.csc207.util.CipherUtils;

public class Cipher {
  //https://www.geeksforgeeks.org/how-to-check-if-a-string-contains-only-lowercase-letters-in-java/
  public static boolean containsOnlyLowercase(String str)  
  { 
      for (char c : str.toCharArray())  
      { 
          // check if the character is not a lowercase letter 
          if (!(c >= 'a' && c <= 'z'))  
          { 
              return false; 
          } 
      } 
      // all characters are lowercase letters 
      return true; 
  } 

  public static void main(String[] args) {
    if(!(args.length == 4)){
      System.err.println("Error: Expected 4 parameters, received " + args.length);
      System.exit(2);
    }

    String action = null;
    String option = null;
    int textIndex = 0;
    String[] text = new String[2];

    for(String s : args){
      if(s.equals("-decode") || s.equals("-encode")){
        if(action != null){
          text[textIndex] = s;
          textIndex++;
        }
        action = s;
      }

      if(s.equals("-caesar") || s.equals("-vigenere")){
        if(option != null){
          text[textIndex] = s;
          textIndex++;
        }
        option = s;
      }

      if((s.length() == 0 || s.charAt(0) != '-') && textIndex < 2){
        text[textIndex] = s;
        textIndex++;
      }

    }

    if(action == null){
      System.err.println("Error: No valid action specified.  Legal values are '-encode' and '-decode'");
      System.exit(1);
    }

    if(option == null){
      System.err.println("Error: No valid option specified.  Legal values are '-caesar' and '-vigenere'");
      System.exit(1);
    }

    for(String s : text){
      if(!containsOnlyLowercase(s)){
        System.err.println("Error: strings must be only lowercase letters");
        System.exit(1);
      }
    }


    PrintWriter pen = new PrintWriter(System.out, true);

    if(option.equals("-caesar")){
      if(text[1].length() != 1){
        System.err.println("Error: Caesar ciphers require a one-character key");
        System.exit(1);
      }

      if(action.equals("-decode")){
        pen.println(CipherUtils.caesarDecrypt(text[0], text[1].charAt(0)));
      }else{
        pen.println(CipherUtils.caesarEncrypt(text[0], text[1].charAt(0)));
      }
    }

    if(option.equals("-vigenere")){
      if(text[1].length() == 0){
        System.err.println("Error: Empty keys are not permitted");
        System.exit(1);
      }

      if(action.equals("-decode")){
        pen.println(CipherUtils.vigenereDecrypt(text[0], text[1]));
      }else{
        pen.println(CipherUtils.vigenereEncrypt(text[0], text[1]));
      }
    }


    pen.close();
  }
}
