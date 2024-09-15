package edu.grinnell.csc207;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.grinnell.csc207.main.AllCaesar;
import edu.grinnell.csc207.util.CipherUtils;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/** Tests of the various classes for MP01. */
public class TestMP01 {
  // +--------------+------------------------------------------------
  // | Design notes |
  // +--------------+

  /*
  Since some of the project goals are classes with main methods,
  we have a variety of utility classes that allow us to capture
  System.in and System.out. We then call the main method and check
  the results.
  */

  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /** Blank output for AllCaesar */
  private static final String ALL_CAESAR_EMPTY =
      ""
          .concat("n = a: \n")
          .concat("n = b: \n")
          .concat("n = c: \n")
          .concat("n = d: \n")
          .concat("n = e: \n")
          .concat("n = f: \n")
          .concat("n = g: \n")
          .concat("n = h: \n")
          .concat("n = i: \n")
          .concat("n = j: \n")
          .concat("n = k: \n")
          .concat("n = l: \n")
          .concat("n = m: \n")
          .concat("n = n: \n")
          .concat("n = o: \n")
          .concat("n = p: \n")
          .concat("n = q: \n")
          .concat("n = r: \n")
          .concat("n = s: \n")
          .concat("n = t: \n")
          .concat("n = u: \n")
          .concat("n = v: \n")
          .concat("n = w: \n")
          .concat("n = x: \n")
          .concat("n = y: \n")
          .concat("n = z: \n");

  // +---------------+-----------------------------------------------
  // | Static fields |
  // +---------------+

  /** The original value of System.err. */
  static PrintStream stderr;

  /** The original value of System.out. */
  static PrintStream stdout;

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /** A place to put System.err output. */
  ByteArrayOutputStream errbytes;

  /** A place to put System.out output. */
  ByteArrayOutputStream outbytes;

  /** The replacement System.err. */
  PrintStream newerr = null;

  /** The replacement System.out. */
  PrintStream newout = null;

  // +---------+-----------------------------------------------------
  // | Helpers |
  // +---------+

  /** Redirect System.err and System.out. */
  public void redirectStandardFDs() {
    errbytes = new ByteArrayOutputStream();
    outbytes = new ByteArrayOutputStream();
    newerr = new PrintStream(errbytes);
    newout = new PrintStream(outbytes);
    System.setErr(newerr);
    System.setOut(newout);
  } // redirectStandardFDs

  /** Return to the normal stderr and stdout. */
  public void restoreStandardFDs() {
    if (newerr != null) {
      newerr.close();
      newerr = null;
    } // if (newerr != null)
    if (newout != null) {
      newout.close();
      newout = null;
    } // if (! outclosed)
    System.setErr(stderr);
    System.setOut(stdout);
  } // restoreStandardFDs

  // +--------------------+------------------------------------------
  // | Setup and takedown |
  // +--------------------+

  /** Since we shuffle System.err and System.out, begin by saving them. */
  @BeforeAll
  public static void saveStandardFDs() {
    stderr = System.err;
    stdout = System.out;
  } // saveStdFDs

  /**
   * A test of AllCaesar.
   *
   * @param args The command-line parameters.
   * @param expectedOut The expected output. Set to null if we expect it to fail.
   * @param msg The messsage to display.
   */
  public void allCaesarTest(String args[], String expectedOut, String msg) {
    redirectStandardFDs();
    AllCaesar.main(args);
    restoreStandardFDs();
    String outString = outbytes.toString();
    String errString = errbytes.toString();
    // Test for null
    if (expectedOut == null) {
      assertEquals("", outString, msg);
      assertEquals("Error:", errString.substring(0, 6), msg);
    } else {
      assertEquals(expectedOut, outString, msg);
      assertEquals("", errString, msg);
    } // if/else
  } // allCaesarTest

  // +-------------------------+-------------------------------------
  // | R tests for CipherUtils |
  // +-------------------------+

  /** Ensure that caesarEncrypt returns a non-empty string given non-empty input. */
  @Test
  public void testCaesarEncryptReturnsNonEmptyString() {
    String encode = CipherUtils.caesarEncrypt("abcde", 'a');
    assertTrue(encode != null, "R: caesarEncrypt doesn't return null");
    assertTrue(!encode.equals(""), "R: caesarEncrypt returns non-empty string");
  } // testCaesarEncryptReturnsString()

  /** Ensure that caesarDecrypt returns a non-empty string given non-empty input. */
  @Test
  public void testCaesarDecryptReturnsNonEmptyString() {
    String encode = CipherUtils.caesarDecrypt("abcde", 'a');
    assertTrue(encode != null, "R: caesarDecrypt doesn't return null");
    assertTrue(!encode.equals(""), "R: caesarDecrypt returns non-empty string");
  } // testCaesarEncryptReturnsString()

  // +-------------------------+-------------------------------------
  // | M tests for CipherUtils |
  // +-------------------------+

  /** Ensure that caesarEncrypt returns the right string for a variety of keys. */
  @Test
  public void testCaesarEncrypt() {
    assertEquals(
        "abcdefghijklmnopqrstuvwxyz",
        CipherUtils.caesarEncrypt("abcdefghijklmnopqrstuvwxyz", 'a'),
        "M: caesarEncrypt with 'a'");
    assertEquals(
        "bcdefghijklmnopqrstuvwxyza",
        CipherUtils.caesarEncrypt("abcdefghijklmnopqrstuvwxyz", 'b'),
        "M: caesarEncrypt with 'b'");
    assertEquals(
        "cdefghijklmnopqrstuvwxyzab",
        CipherUtils.caesarEncrypt("abcdefghijklmnopqrstuvwxyz", 'c'),
        "M: caesarEncrypt with 'c'");
    assertEquals(
        "defghijklmnopqrstuvwxyzabc",
        CipherUtils.caesarEncrypt("abcdefghijklmnopqrstuvwxyz", 'd'),
        "M: caesarEncrypt with 'd'");
    assertEquals(
        "uryybjbeyq",
        CipherUtils.caesarEncrypt("helloworld", 'n'),
        "M: caesarEncrypt helloworld with 'n'");
  } // testCaesarEncrypt()

  /** Ensure that caesarDecrypt returns the right string for a variety of keys. */
  @Test
  public void testCaesarDecrypt() {
    assertEquals(
        "abcdefghijklmnopqrstuvwxyz",
        CipherUtils.caesarDecrypt("abcdefghijklmnopqrstuvwxyz", 'a'),
        "M: caesarDecrypt with 'a'");
    assertEquals(
        "zabcdefghijklmnopqrstuvwxy",
        CipherUtils.caesarDecrypt("abcdefghijklmnopqrstuvwxyz", 'b'),
        "M: caesarDecrypt with 'b'");
    assertEquals(
        "yzabcdefghijklmnopqrstuvwx",
        CipherUtils.caesarDecrypt("abcdefghijklmnopqrstuvwxyz", 'c'),
        "M: caesarDecrypt with 'c'");
    assertEquals(
        "xyzabcdefghijklmnopqrstuvw",
        CipherUtils.caesarDecrypt("abcdefghijklmnopqrstuvwxyz", 'd'),
        "M: caesarDecrypt with 'd'");
  } // testCaesarDecrypt()

  // +-------------------------+-------------------------------------
  // | E tests for CipherUtils |
  // +-------------------------+

  /** Ensure that we can successfully encrypt the empty string. */
  @Test
  public void caesarEncryptEmpty() {
    assertEquals("", CipherUtils.caesarEncrypt("", 'c'), "E: caesarEncrypt of the empty string");
  } // caesarEncryptEmpty()

  /** Ensure that we can successfully decrypt the empty string. */
  @Test
  public void caesarDecryptEmpty() {
    assertEquals("", CipherUtils.caesarDecrypt("", 'q'), "E: caesarEncrypt of the empty string");
  } // caesarEncryptEmpty()

  // +-----------------------+---------------------------------------
  // | R tests for AllCaesar |
  // +-----------------------+

  /** Does AllCaesar run when given appropriate parameters? */
  @Test
  public void allCaesarRuns() {
    redirectStandardFDs();
    AllCaesar.main(new String[] {"encode", "abcde"});
    restoreStandardFDs();
    assertTrue(true, "R: The AllCaesar program runs with correct inputs.");
  } // allCaesarRuns()

  // +-----------------------+---------------------------------------
  // | M tests for AllCaesar |
  // +-----------------------+

  /** Does AllCaesar give the correct output for encrypting helloworld? */
  @Test
  public void allCaesarEncryptHelloWorld() {
    allCaesarTest(
        new String[] {"encode", "helloworld"},
        """
        n = a: helloworld
        n = b: ifmmpxpsme
        n = c: jgnnqyqtnf
        n = d: khoorzruog
        n = e: lippsasvph
        n = f: mjqqtbtwqi
        n = g: nkrrucuxrj
        n = h: olssvdvysk
        n = i: pmttwewztl
        n = j: qnuuxfxaum
        n = k: rovvygybvn
        n = l: spwwzhzcwo
        n = m: tqxxaiadxp
        n = n: uryybjbeyq
        n = o: vszzckcfzr
        n = p: wtaadldgas
        n = q: xubbemehbt
        n = r: yvccfnficu
        n = s: zwddgogjdv
        n = t: axeehphkew
        n = u: byffiqilfx
        n = v: czggjrjmgy
        n = w: dahhksknhz
        n = x: ebiiltloia
        n = y: fcjjmumpjb
        n = z: gdkknvnqkc
        """,
        "M: AllCaesar encode helloworld");
  } // allCaesarEncryptHelloWorld()

  /** Does AllCaesar give the correct output for encrypting the alphabet? */
  @Test
  public void allCaesarEncryptAlphabet() {
    allCaesarTest(
        new String[] {"encode", "abcdefghijklmnopqrstuvwxyz"},
        """
        n = a: abcdefghijklmnopqrstuvwxyz
        n = b: bcdefghijklmnopqrstuvwxyza
        n = c: cdefghijklmnopqrstuvwxyzab
        n = d: defghijklmnopqrstuvwxyzabc
        n = e: efghijklmnopqrstuvwxyzabcd
        n = f: fghijklmnopqrstuvwxyzabcde
        n = g: ghijklmnopqrstuvwxyzabcdef
        n = h: hijklmnopqrstuvwxyzabcdefg
        n = i: ijklmnopqrstuvwxyzabcdefgh
        n = j: jklmnopqrstuvwxyzabcdefghi
        n = k: klmnopqrstuvwxyzabcdefghij
        n = l: lmnopqrstuvwxyzabcdefghijk
        n = m: mnopqrstuvwxyzabcdefghijkl
        n = n: nopqrstuvwxyzabcdefghijklm
        n = o: opqrstuvwxyzabcdefghijklmn
        n = p: pqrstuvwxyzabcdefghijklmno
        n = q: qrstuvwxyzabcdefghijklmnop
        n = r: rstuvwxyzabcdefghijklmnopq
        n = s: stuvwxyzabcdefghijklmnopqr
        n = t: tuvwxyzabcdefghijklmnopqrs
        n = u: uvwxyzabcdefghijklmnopqrst
        n = v: vwxyzabcdefghijklmnopqrstu
        n = w: wxyzabcdefghijklmnopqrstuv
        n = x: xyzabcdefghijklmnopqrstuvw
        n = y: yzabcdefghijklmnopqrstuvwx
        n = z: zabcdefghijklmnopqrstuvwxy
        """,
        "M: AllCaesar encode abcdefghijklmnopqrstuvwxyz");
  } // allCaesarEncryptAlphabet()

  /** Does AllCaesar give the correct output for decrypting dahhksknhz? */
  @Test
  public void allCaesarDecryptDahhksknhz() {
    allCaesarTest(
        new String[] {"decode", "dahhksknhz"},
        """
        n = a: dahhksknhz
        n = b: czggjrjmgy
        n = c: byffiqilfx
        n = d: axeehphkew
        n = e: zwddgogjdv
        n = f: yvccfnficu
        n = g: xubbemehbt
        n = h: wtaadldgas
        n = i: vszzckcfzr
        n = j: uryybjbeyq
        n = k: tqxxaiadxp
        n = l: spwwzhzcwo
        n = m: rovvygybvn
        n = n: qnuuxfxaum
        n = o: pmttwewztl
        n = p: olssvdvysk
        n = q: nkrrucuxrj
        n = r: mjqqtbtwqi
        n = s: lippsasvph
        n = t: khoorzruog
        n = u: jgnnqyqtnf
        n = v: ifmmpxpsme
        n = w: helloworld
        n = x: gdkknvnqkc
        n = y: fcjjmumpjb
        n = z: ebiiltloia
        """,
        "M: AllCaesar decode dahhksknhz");
  } // allCaesarDecryptDahhksknhz()

  /** Does AllCaesar give the correct output for decrypting the alphabet? */
  @Test
  public void allCaesarDecryptAlphabet() {
    allCaesarTest(
        new String[] {"decode", "abcdefghijklmnopqrstuvwxyz"},
        """
        n = a: abcdefghijklmnopqrstuvwxyz
        n = b: zabcdefghijklmnopqrstuvwxy
        n = c: yzabcdefghijklmnopqrstuvwx
        n = d: xyzabcdefghijklmnopqrstuvw
        n = e: wxyzabcdefghijklmnopqrstuv
        n = f: vwxyzabcdefghijklmnopqrstu
        n = g: uvwxyzabcdefghijklmnopqrst
        n = h: tuvwxyzabcdefghijklmnopqrs
        n = i: stuvwxyzabcdefghijklmnopqr
        n = j: rstuvwxyzabcdefghijklmnopq
        n = k: qrstuvwxyzabcdefghijklmnop
        n = l: pqrstuvwxyzabcdefghijklmno
        n = m: opqrstuvwxyzabcdefghijklmn
        n = n: nopqrstuvwxyzabcdefghijklm
        n = o: mnopqrstuvwxyzabcdefghijkl
        n = p: lmnopqrstuvwxyzabcdefghijk
        n = q: klmnopqrstuvwxyzabcdefghij
        n = r: jklmnopqrstuvwxyzabcdefghi
        n = s: ijklmnopqrstuvwxyzabcdefgh
        n = t: hijklmnopqrstuvwxyzabcdefg
        n = u: ghijklmnopqrstuvwxyzabcdef
        n = v: fghijklmnopqrstuvwxyzabcde
        n = w: efghijklmnopqrstuvwxyzabcd
        n = x: defghijklmnopqrstuvwxyzabc
        n = y: cdefghijklmnopqrstuvwxyzab
        n = z: bcdefghijklmnopqrstuvwxyza
        """,
        "M: AllCaesar decode abcdefghijklmnopqrstuvwxyz");
  } // allCaesarDecryptAlphabet()

  /** Does AllCaesar issue an error with zero arguments? */
  @Test
  public void allCaesarErrorZeroArguments() {
    allCaesarTest(new String[] {}, null, "M: AllCaesar with zero arguments");
  } // allCaesarErrorZeroArguments()

  /** Does AllCaesar issue an error with one argument? */
  @Test
  public void allCaesarErrorOneArgument() {
    allCaesarTest(new String[] {"encode"}, null, "M: AllCaesar with one argument");
    allCaesarTest(new String[] {"helloworld"}, null, "M: AllCaesar with one argument");
  } // allCaesarErrorOneArgument()

  /** Does AllCaesar issue an error with three arguments? */
  @Test
  public void allCaesarErrorThreeArguments() {
    allCaesarTest(
        new String[] {"encode", "helloworld", "q"}, null, "M: AllCaesar with three arguments");
  } // allCaesarErrorThreeArguments()

  /** Does AllCaesar issue an error with an invalid action? */
  @Test
  public void allCaesarErrorInvalidAction() {
    allCaesarTest(new String[] {"deecode", "helloworld"}, null, "M: AllCaesar with invalid action");
    allCaesarTest(new String[] {"emcode", "helloworld"}, null, "M: AllCaesar with invalid action");
    allCaesarTest(new String[] {"codeen", "helloworld"}, null, "M: AllCaesar with invalid action");
    allCaesarTest(new String[] {"", "helloworld"}, null, "M: AllCaesar with invalid action");
  } // allCaesarErrorInvalidAction

  /** Does AllCaesar issue an error with an invalid input string? */
  public void allCaesarErrorInvalidString() {
    allCaesarTest(
        new String[] {"encode", "Helloworld"},
        null,
        "M: AllCaesar with invalid string (\"HelloWorld\")");
    allCaesarTest(new String[] {"encode", " "}, null, "M: AllCaesar with invalid string (\" \")");
    allCaesarTest(
        new String[] {"encode", "hello world"},
        null,
        "M: AllCaesar with invalid string (\"hello world\")");
    allCaesarTest(
        new String[] {"encode", "h3lloworld"},
        null,
        "M: AllCaesar with invalid string (\"h3lloworld\")");
    allCaesarTest(
        new String[] {"decode", "Helloworld"},
        null,
        "M: AllCaesar with invalid string (\"HelloWorld\")");
    allCaesarTest(new String[] {"decode", " "}, null, "M: AllCaesar with invalid string (\" \")");
    allCaesarTest(
        new String[] {"decode", "hello world"},
        null,
        "M: AllCaesar with invalid string (\"hello world\")");
    allCaesarTest(
        new String[] {"decode", "h3lloworld"},
        null,
        "M: AllCaesar with invalid string (\"h3lloworld\")");
  } // allCaesarErrorInvalidString()

  // +-----------------------+---------------------------------------
  // | E tests for AllCaesar |
  // +-----------------------+

  /** Does AllCaesar give the correct output for encrypting the empty string? */
  @Test
  public void allCaesarEncryptEmptyString() {
    allCaesarTest(new String[] {"encode", ""}, ALL_CAESAR_EMPTY, "M: AllCaesar encode \"\"");
  } // allCaesarEncryptEmptyString()

  /** Does AllCaesar give the correct output for decrypting the empty string? */
  @Test
  public void allCaesarDecryptEmptyString() {
    allCaesarTest(new String[] {"decode", ""}, ALL_CAESAR_EMPTY, "M: AllCaesar decode \"\"");
  } // allCaesarDecryptEmptyString()

  // +--------------------+------------------------------------------
  // | R tests for Cipher |
  // +--------------------+

  // +--------------------+------------------------------------------
  // | M tests for Cipher |
  // +--------------------+

  // +--------------------+------------------------------------------
  // | E tests for Cipher |
  // +--------------------+

} // TestMP01
