import java.util.ArrayDeque;
import java.util.HashMap;

/** 
 *  Class to interpret and compute the result of arithmetic expressions 
 *  in INFIX format. Contains method that transform infix format to postfix
 *  format. Calling the main with correct arithmetic expressions generates
 *  result, otherwise error messages are printed.
 *  @author Wantong Wu
 *  @version Fall 2022
 *  Reference: 
 *        - https://en.wikipedia.org/w/index.php?title=
 *          Shunting-yard_algorithm&oldid=572362024
 */
public class Calculate {
  /** Take in an arrayDeque with infix expression info and transform it to
   *  postfix format.
   *  @param tokens arrayDeque that contains infix expression information
   *  @return ArrayDeque<Object> arrayDeque that contains postfix expression
   */
  public static ArrayDeque<Object> infixToPostfix(ArrayDeque<Object> tokens) {
    // HashMap to create operator - precedence relation
    HashMap<Character, Integer> precedence = new HashMap<>();
    precedence.put('^', 4);
    precedence.put('/', 3);
    precedence.put('*', 3);
    precedence.put('+', 2);
    precedence.put('-', 2);
    precedence.put('(', 0);
    
    // The queue that will include Postfix expression
    ArrayDeque<Object> queue = new ArrayDeque<>();
    
    // Character stack for temporary operator stay
    ArrayDeque<Character> stack = new ArrayDeque<>();

    // Go through the original token List
    while (!tokens.isEmpty()) {
      Object curToken = tokens.removeFirst();
      
      // Check the data type of token
      // If string, invalid
      if (curToken instanceof String) {
        System.out.println("Invalid expression input");
        System.exit(-1);

      // If number, store in the queue
      } else if (curToken instanceof Double) {
        Double number = (Double) curToken;
        queue.addLast(number);
        
      // If char, check the precedence and recalculate the stack
      } else if (curToken instanceof Character) {
        try {
          Character operator = (Character) curToken;
          if (operator.equals('(')) {
            stack.addFirst(operator);
            continue;
          } if (operator.equals(')')) {
            try {
              while (!stack.peekFirst().equals('(')) {
                queue.addLast(stack.removeFirst());
              }
              stack.removeFirst();
            } catch (Exception e) {
              System.out.println("Mismatched parentheses");
              System.exit(-1);
            }
            continue;
          }
          while (!stack.isEmpty()) {
            Character opLast = stack.peekFirst();
            if ((!operator.equals('^') &&
                 precedence.get(operator).intValue() == 
                 precedence.get(opLast).intValue()) ||
                 (precedence.get(operator).intValue() < 
                 precedence.get(opLast).intValue())) {
              queue.addLast(stack.removeFirst());
            } else {
              break;
            }
          }
          stack.addFirst(operator);
        } catch (Exception e) {
          System.out.println("Invalid expression input");
          System.exit(-1);
        }
      }  
    }

    // No tokens left in original list
    while (!stack.isEmpty()) {
      if (stack.peekFirst().equals('(') || stack.peekFirst().equals(')')) {
        System.out.println("Mismatched parentheses");
        System.exit(-1);
      }
      queue.addLast(stack.removeFirst());
    }
    return queue;
  }

  /** Calculate expressions in infix format
   *  @param args string expression to be calculated
   */
  public static void main(String[] args) {
    // If no arguments passed, print instructions
    if (args.length == 0) {
      System.err.println("Usage:  java Calculate <expr>");
    } else {
      // Parse the input and store info in an ArrayDeque
      ArrayDeque<Object> tokens = Tokenizer.readTokens(args[0]);

      // Transform the infix to postfix
      ArrayDeque<Object> postfixEx = infixToPostfix(tokens);

      // Calculate the result
      Postfix.calculatePostfix(postfixEx);
    }
  }

}