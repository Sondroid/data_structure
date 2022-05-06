import java.io.*;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CalculatorTest
{

	private static int priority(String operator){
		if(operator.compareTo("+") == 0) return 0;
		if(operator.compareTo("-") == 0) return 0;
		if(operator.compareTo("*") == 0) return 1;
		if(operator.compareTo("/") == 0) return 1;
		if(operator.compareTo("%") == 0) return 1;
		if(operator.compareTo("~") == 0) return 2;
		if(operator.compareTo("^") == 0) return 3;
		if(operator.compareTo("(") == 0) return -1;
		return -1;
	}

	private static String toPostfix(String infix){
		
		Stack<String> operatorStack = new Stack<>();
		String postfix = "";
		// for check unary "-"
		boolean isPrevInt = false;
		String operator;

		Pattern pattern = Pattern.compile("\\D|\\d+");
		Matcher matcher = pattern.matcher(infix);

        while(matcher.find()){
			// if number, immediately concat
            if(matcher.group().matches("\\d+")){
				postfix = postfix.concat(matcher.group());
				postfix = postfix.concat(" ");
				isPrevInt = true;
			}
			else{
				// check unary "-"
				if(matcher.group().compareTo("-") == 0 && !isPrevInt){
					operator = "~";
				}
				else{
					operator = matcher.group();
					// "(", ")" does not affect isPrevInt
					if(operator.compareTo("(") != 0 && operator.compareTo(")") != 0) isPrevInt= false;
				}

				if(operatorStack.size() == 0){
					operatorStack.push(operator);
				}
				// if operator "(", push
				else if(operator.compareTo("(") == 0){
					operatorStack.push(operator);
				}
				// if operator ")", pop and concat until pop "("
				else if(operator.compareTo(")") == 0){
					while(operatorStack.peek().compareTo("(") != 0){
						postfix = postfix.concat(operatorStack.pop());
						postfix = postfix.concat(" ");
					}
					// pop "("
					operatorStack.pop();
				}
				// if operator otherwise, push operator only when priority of peek is lower.
				// if priority of peek is higher or equal, pop and concat until it become lower. Then push operator.
				// allow right-associative "~", "^" to cumulate exceptionally.
				else{
					if(priority(operator) > priority(operatorStack.peek()) || (operator.compareTo("~") == 0 && operatorStack.peek().compareTo("~") == 0) || (operator.compareTo("^") == 0 && operatorStack.peek().compareTo("^") == 0 )){
						operatorStack.push(operator);
					}
					else{
						while(operatorStack.size() != 0 && priority(operator) <= priority(operatorStack.peek())){
							postfix = postfix.concat(operatorStack.pop());
							postfix = postfix.concat(" ");
						}
						operatorStack.push(operator);
					}
				}
			}
        }
		while(operatorStack.size() > 0){
			postfix = postfix.concat(operatorStack.pop());
			postfix = postfix.concat(" ");
		}
		postfix = postfix.trim();
		return postfix;
	}
	
	private static long evaluate(String postfix){
		
		Stack<Long> operandStack = new Stack<>();

		String[] postfixSplit = postfix.split(" ");
		for(String element: postfixSplit){
			if(element.matches("\\d+")){
				operandStack.push(Long.parseLong(element));
			}
			else{
				switch(element){
					case "+": add(operandStack); break;
					case "-": subtract(operandStack); break;
					case "*": multiply(operandStack); break;
					case "/": quotient(operandStack); break;
					case "%": remainder(operandStack); break;
					case "~": negative(operandStack); break;
					case "^": power(operandStack); break;
					default: break;
				}
			}
		}

		return operandStack.pop();
	}

	private static void add(Stack<Long> operandStack){
		long operand1 = operandStack.pop();
		long operand2 = operandStack.pop();
		operandStack.push(operand1 + operand2);
	}
	private static void subtract(Stack<Long> operandStack){
		long operand1 = operandStack.pop();
		long operand2 = operandStack.pop();
		operandStack.push(operand2 - operand1);
	}
	private static void multiply(Stack<Long> operandStack){
		long operand1 = operandStack.pop();
		long operand2 = operandStack.pop();
		operandStack.push(operand1 * operand2);
	}
	private static void quotient(Stack<Long> operandStack){
		long operand1 = operandStack.pop();
		long operand2 = operandStack.pop();
		operandStack.push(operand2 / operand1);
	}
	private static void remainder(Stack<Long> operandStack){
		long operand1 = operandStack.pop();
		long operand2 = operandStack.pop();
		operandStack.push(operand2 % operand1);
	}
	private static void negative(Stack<Long> operandStack){
		long operand = operandStack.pop();
		operandStack.push(-operand);

	}
	private static void power(Stack<Long> operandStack){
		long operand1 = operandStack.pop();
		long operand2 = operandStack.pop();
		
		if(operand2 == 0 && operand1 < 0){
			throw new ArithmeticException("0 ** negative number");
		}

		operandStack.push((long) Math.pow(operand2, operand1));
	}

	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true)
		{
			try
			{
				String input = br.readLine();
				if (input.compareTo("q") == 0)
					break;

				command(input);
			}
			catch (Exception e)
			{
				// System.out.println(e.getMessage());
				System.out.println("ERROR");
			}
		}
	}

	private static void command(String input)
	{
		if(input.matches(".*\\d+\\s+\\d+.*")){
			throw new RuntimeException("Invalid Expression");
		};

		String noWhiteSpaceInput = input.replace(" ", "");
		noWhiteSpaceInput = noWhiteSpaceInput.replace("\t", "");

		if(!isValidExp(noWhiteSpaceInput)){
			throw new RuntimeException("Invalid Expression");
		}

		String postfixExp = toPostfix(noWhiteSpaceInput);
		long result = evaluate(postfixExp);

		System.out.println(postfixExp);
		System.out.println(result);
	}

	private static boolean isValidExp(String input){

		// System.out.println(input);
		
		if(input.matches("\\d+")) return true;

		if(input.startsWith("-")){
			String sub = input.substring(1);
			if(sub.length() == 0) return false;
		
			return isValidExp(sub);
		}

		if(input.startsWith("(") && input.endsWith(")")){
			String sub = input.substring(1, input.length()-1);
			if(sub.length() == 0) return false;
			
			// if first opened ( closed, cannot make (E) -> E
			int parenthesisLevel = 0;
			boolean parenthesisCheck = true;
			for(int i = 0; i < sub.length(); i++){
				if(sub.charAt(i) == '(') parenthesisLevel++;
				if(sub.charAt(i) == ')') parenthesisLevel--;
				if(parenthesisLevel < 0){
					parenthesisCheck = false;
					break;
				}
			}
			if(parenthesisCheck) return isValidExp(sub);
		
		}

		int parenthesisLevel = 0;

		for(int i = 0; i < input.length(); i++){
			switch(input.charAt(i)){
				case '(': parenthesisLevel++; break;
				case ')': parenthesisLevel--; break;
				case '+':
				case '-':
				case '*':
				case '/':
				case '%':
				case '^':
					if(parenthesisLevel == 0){
						String sub1 = input.substring(0,i);
						String sub2 = input.substring(i+1);
						if(sub1.length() == 0 || sub2.length() == 0) return false;
						return isValidExp(sub1) && isValidExp(sub2);
					}
					break;
				default: continue;
			}
		}

		return false;
	}
}

