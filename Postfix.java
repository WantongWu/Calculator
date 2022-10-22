import java.util.ArrayDeque;

/** 
 *  Class to interpret and compute the result of arithmetic expressions 
 *  in POSTFIX format. Contains method that calculate a postfix expression.
 *  Calling the main with correct arithmetic expressions generates
 *  result, otherwise error messages are printed.
 *  @author Wantong Wu
 *  @version Fall 2022
 *  Reference: 
 *        - https://docs.oracle.com/javase/9/docs/api/java/util/ArrayDeque.html
 */
public class Postfix {
  /** Take in an arrayDeque with Postfix expression info and calculate it
   *  @param deque arrayDeque with Postfix expression
   *  @return double the result of calculation
   */
  public static double calculatePostfix(ArrayDeque<Object> deque) {
    // Create a stack for number
    ArrayDeque<Double> numStack = new ArrayDeque<>();
    while (!deque.isEmpty()) {
      Object curToken = deque.removeFirst();

      // Check the data type of token
      // If string, invalid
      if (curToken instanceof String) {
        System.out.println("Invalid expression input");
        System.exit(-1);
        
      // If char, compute the previous two numbers
      } else if (curToken instanceof Character) {
        try {
          double token1 = numStack.pop();
          double token2 = numStack.pop();
          
          // Addition
          if (curToken.equals('+')) {
            numStack.push(token2 + token1);
          // Subtraction
          } else if (curToken.equals('-')) {
            numStack.push(token2 - token1);
          // Multiplication
          } else if (curToken.equals('*')) {
            numStack.push(token2*token1);
          // Division
          } else if (curToken.equals('/')) {
            numStack.push(token2/token1);
          // Exponentiation
          } else if (curToken.equals('^')) {
            numStack.push(Math.pow(token2, token1));
          }
        } catch (Exception e) {
          System.out.println("Invalid expression input");
          System.exit(-1);
        }
        
      // If number, store in the stack
      } else if (curToken instanceof Double) {
        Double token = (Double) curToken;
        numStack.push(token.doubleValue());
      }
    }
    if (numStack.size() > 1) {
      System.out.println("Invalid expression input");
      System.exit(-1);
    }
    double result = numStack.pop();
    System.out.println("Answer: " + result);
    return result;
  }

  /** Calculate expressions in Postfix format
   *  @param args string expression to be calculated
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      // If no arguments passed, print instructions
      System.err.println("Usage:  java Postfix <expr>");
    } else {
      // Parse the input and store info in an ArrayDeque
      ArrayDeque<Object> queue = Tokenizer.readTokens(args[0]);
      calculatePostfix(queue);
    }
  }

}