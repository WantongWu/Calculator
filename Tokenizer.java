import java.util.Scanner;
import java.io.StringReader;
import java.util.ArrayDeque;

/** 
 *  Read in the arguments into a queue.
 *  @author Nick Howe
 *  @version 26 September 2013
 */
public class Tokenizer {
  /** Pattern that matches on words */
  public static final String WORD = "[a-zA-Z]*\\b";

  /** Pattern that matches on arithmetic symbols */
  public static final String SYMBOL = "[^\\w]";

  /** 
   *  Converts the input string to a queue of tokens 
   *  @param input  the string to convert
   *  @return  the queue of tokens
   */
  public static ArrayDeque<Object> readTokens(String input) {
    ArrayDeque<Object> queue = new ArrayDeque<>(); // Combine different types    
    Scanner scanner = new Scanner(new StringReader(input));
    // Below is a complicated regular expression that will split the
    // input string at various boundaries.
    scanner.useDelimiter
      ("(\\s+"                            // whitespace
      +"|(?<=[a-zA-Z])(?=[^a-zA-Z])"      // word->non-word
      +"|(?<=[^a-zA-Z])(?=[a-zA-Z])"      // non-word->word
      +"|(?<=[^0-9\\056])(?=[0-9\\056])"  // non-number->number
      +"|(?<=[0-9\\056])(?=[^0-9\\056])"  // number->non-number
      +"|(?<=[^\\w])(?=[^\\w]))");        // symbol->symbol
    
    while (scanner.hasNext()) {
      if (scanner.hasNextDouble()) {
        double d = scanner.nextDouble();
        queue.add(d);
      } else if (scanner.hasNext(WORD)) {
        String s = scanner.next(WORD);
        queue.add(s);
      } else if (scanner.hasNext(SYMBOL)) {
        char c = scanner.next(SYMBOL).charAt(0);
        queue.add(c);
      } else {
        System.out.println(scanner.next());
      }
    }
    scanner.close();    
    return queue;
  }
}
