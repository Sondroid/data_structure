import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;


public class BigInteger
{
    private byte[] byteArr;
    private boolean sign;
    private int idxOfTail;
    
    public static final String QUIT_COMMAND = "quit";

    // Constructors
    public BigInteger(String s)
    {
        byteArr = s.getBytes();
        sign = true;
        idxOfTail = byteArr.length - 1;
    }

    public BigInteger(String s, char c)
    {
        switch(c){
            case '+': sign = true; break;
            case '-': sign = false; break;
        }
        byteArr = s.getBytes();
        idxOfTail = byteArr.length - 1;
    }

    public BigInteger(int i)
    {
        byteArr = new byte[i];
        sign = true;
        idxOfTail = byteArr.length - 1;
    }

    // BinInteger to String
    // @Override
    public static String toString(BigInteger bigInt)
    {
        String str = new String(bigInt.byteArr);
        if(!bigInt.sign){
            str = "-" + str;
        }
        return str;
    }

    private static boolean absBiggerThan(BigInteger num1, BigInteger num2){
        if(num1.idxOfTail > num2.idxOfTail){
            return true;
        }
        else if(num1.idxOfTail < num2.idxOfTail){
            return false;
        }
        else {
            for(int i = 0; i <= num1.idxOfTail; i++){
                if(num1.byteArr[i] > num2.byteArr[i]){
                    return true;
                }
                else if(num1.byteArr[i] < num2.byteArr[i]){
                    return false;
                }
            }
            return true;
            // meaning not strictly bigger
        }
    }
    
    private static BigInteger toggle(BigInteger num){
        BigInteger toggled = new BigInteger(num.idxOfTail + 1);
        toggled.byteArr = num.byteArr;
        toggled.sign = !num.sign;
        return toggled;
    }

    public static BigInteger removeLeadingZeros(BigInteger num){
        // include empty
        if(num.byteArr[0] != 48 & num.byteArr[0] != 0){
            return num;
        }
        else{
            int countZero = 0;
            for(int i = 0; i <= num.idxOfTail; i++){
                if(num.byteArr[i] == 48 | num.byteArr[i] == 0){
                    countZero += 1;
                }
                else{
                    break;
                }
            }
            BigInteger removed = new BigInteger(num.idxOfTail + 1 - countZero);
            for(int k=0; k <= removed.idxOfTail; k++){
                removed.byteArr[removed.idxOfTail - k] = num.byteArr[num.idxOfTail - k];
            }
            return removed;
        }
    }

    public static BigInteger shiftLeft(BigInteger num, int i){
        // input has no leading zeros
        
        if(i == 0) return num;

        BigInteger result = new BigInteger(num.idxOfTail + 1 + i);

        for(int j=0; j <= num.idxOfTail ; j++){
            result.byteArr[j] = num.byteArr[j];
        }

        for(int k=0; k < i; k++){
            result.byteArr[result.idxOfTail - k] = 48;
        }

        return result;
    }

    public static BigInteger absAdd(BigInteger num1, BigInteger num2){
        byte[] operand1 = num1.byteArr;
        byte[] operand2 = num2.byteArr;
        int length1 = num1.idxOfTail + 1;
        int length2 = num2.idxOfTail + 1;
        int biggerLength = Math.max(length1, length2);
        int carry = 0;
        int temp;

        BigInteger result = new BigInteger(biggerLength + 1);

        for(int i = 0; i < biggerLength; i++){
            if(num1.idxOfTail - i >= 0 & num2.idxOfTail - i >= 0){
                temp = operand1[num1.idxOfTail - i] + operand2[num2.idxOfTail - i] + carry - 96;
                carry = temp / 10;
                temp = temp % 10;
                result.byteArr[result.idxOfTail - i] = (byte) (temp + 48);
            }
            else if(num1.idxOfTail - i < 0){
                temp = operand2[num2.idxOfTail - i] + carry - 48;
                carry = temp / 10;
                temp = temp % 10;
                result.byteArr[result.idxOfTail - i] = (byte) (temp + 48);
            }
            else{
                temp = operand1[num1.idxOfTail - i] + carry - 48;
                carry = temp / 10;
                temp = temp % 10;
                result.byteArr[result.idxOfTail - i] = (byte) (temp + 48);
            }
            
        }
        if(carry != 0){
            result.byteArr[0] = (byte) (carry + 48);
        }

        return removeLeadingZeros(result);
    }
    
    private static boolean isEqual(BigInteger num1, BigInteger num2){
        
        if(num1.idxOfTail != num2.idxOfTail){
            return false;
        }
        else{
            for(int i=0; i <= num1.idxOfTail; i++){
                if(num1.byteArr[i] != num2.byteArr[i]){
                    return false;
                }
            }
            return true;
        }
    }    

    public static BigInteger absSubtract(BigInteger num1, BigInteger num2){
        
        if(isEqual(num1, num2)){
            BigInteger zeroResult = new BigInteger(1);
            zeroResult.byteArr[zeroResult.idxOfTail] = 48;
            return zeroResult;
        }

        if(!absBiggerThan(num1, num2)){
            return toggle(absSubtract(num2, num1));
        }

        byte[] operand1 = num1.byteArr;
        byte[] operand2 = num2.byteArr;
        int length = num1.idxOfTail + 1;
        int carry = 0;
        int temp;

        BigInteger result = new BigInteger(length);

        for(int i = 0; i < length; i++){
            if(num2.idxOfTail - i >= 0){
                temp = operand1[num1.idxOfTail - i] - operand2[num2.idxOfTail - i] + carry;
                if(temp < 0){
                    carry = -1;
                    temp += 10;
                }
                else{
                    carry = 0;
                }
                result.byteArr[result.idxOfTail - i] = (byte) (temp + 48);
            }
            else{
                temp = operand1[num1.idxOfTail - i] + carry;
                carry = 0;
                result.byteArr[result.idxOfTail - i] = (byte) temp;
            }

        }

        return removeLeadingZeros(result);
    }

    public static BigInteger absMultiply(BigInteger num1, BigInteger num2){
        byte[] operand1 = num1.byteArr;
        byte[] operand2 = num2.byteArr;
        int length1 = num1.idxOfTail + 1;
        int length2 = num2.idxOfTail + 1;
        int lengthSum = length1 + length2;
        int carry = 0;
        int temp;
        BigInteger tempInt;

        BigInteger result = new BigInteger(1);
        result.byteArr[result.idxOfTail] = 48;
        if(num1.idxOfTail == 0 & operand1[0] == 48) return result;
        if(num2.idxOfTail == 0 & operand2[0] == 48) return result;


        for(int i = 0; i < length2; i++){

            tempInt = new BigInteger(lengthSum);

            for(int j = 0; j < length1; j++){
                temp = (operand1[num1.idxOfTail - j] - 48) * (operand2[num2.idxOfTail - i] - 48) + carry;
                carry = temp / 10;
                temp = temp % 10;
                tempInt.byteArr[tempInt.idxOfTail - j] = (byte) (temp + 48);
                if(j == length1 - 1){
                    tempInt.byteArr[tempInt.idxOfTail - j - 1] = (byte) (carry + 48);
                    carry = 0;
                }
            }
            result = add(result,shiftLeft(removeLeadingZeros(tempInt), i));
        }

        return removeLeadingZeros(result);
    }

    public static BigInteger add(BigInteger num1, BigInteger num2){
        if(num1.sign & num2.sign){
            return absAdd(num1, num2);
        }
        else if(num1.sign & !num2.sign){
            return subtract(num1, toggle(num2));
        }
        else if(!num1.sign & num2.sign){
            return subtract(num2, toggle(num1));
        }
        else{
            return toggle(absAdd(num1, num2));
        }
    }
    public static BigInteger subtract(BigInteger num1, BigInteger num2){
        if(num1.sign & num2.sign){
            return absSubtract(num1, num2);
        }
        else if(num1.sign & !num2.sign){
            return absAdd(num1, num2);
        }
        else if(!num1.sign & num2.sign){
            return toggle(absAdd(num1, num2));
        }
        else{
            return absSubtract(num2, num1);
        }
    }
    public static BigInteger multiply(BigInteger num1, BigInteger num2){
        if(num1.sign ^ num2.sign){
            return(toggle(absMultiply(num1, num2)));
        }
        else{
            return(absMultiply(num1, num2));
        }
    }
    
    public static BigInteger evaluate(String input)
    {   
        BigInteger result = null;
        
        // parse input
        String trimedInput = input.replace(" ","");
        String[] parsedInput;
        BigInteger num1;
        BigInteger num2;
        int idxOfOperator;

        if(trimedInput.startsWith("+") | trimedInput.startsWith("-")){
            // if input starts with sign
            char temp1 = trimedInput.charAt(0);
            parsedInput = trimedInput.substring(1).split("[\\+|\\-|\\*]",2);
            num1 = new BigInteger(parsedInput[0], temp1);
            idxOfOperator = parsedInput[0].length() + 1;
        }
        else{
            parsedInput = trimedInput.split("[\\+|\\-|\\*]", 2);
            num1 = new BigInteger(parsedInput[0]);
            idxOfOperator = parsedInput[0].length();
        }

        if(parsedInput[1].startsWith("+") | parsedInput[1].startsWith("-")){
            char temp2 = parsedInput[1].charAt(0);
            parsedInput[1] = parsedInput[1].substring(1);
            num2 = new BigInteger(parsedInput[1], temp2);
        }
        else{
            num2 = new BigInteger(parsedInput[1]);
        }

        // do operation
        char operator = trimedInput.charAt(idxOfOperator);
        switch(operator){
            case '+': result = add(num1, num2); break;
            case '-': result = subtract(num1, num2); break;
            case '*': result = multiply(num1, num2); break;
            default: System.out.println("Cannot find operater");
        }
        return result;
    }

    public static void main(String[] args) throws IOException
    {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(isr);

        boolean done = false;
        while(!done){
            String input = reader.readLine();
            done = processInput(input);
        }
    }

    static boolean processInput(String input)
    {
        boolean quit = isQuitCmd(input);

        if(quit)
        {
            return true;
        }
        else
        {                
            BigInteger result = evaluate(input);
            System.out.println(toString(result));
            return false;
        }
    }

    static boolean isQuitCmd(String input)
    {
        return input.equalsIgnoreCase(QUIT_COMMAND);
    }
}