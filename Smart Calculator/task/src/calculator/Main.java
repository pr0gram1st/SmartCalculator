package calculator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    static Map < String , String > var = new HashMap<>();
    public static int Prec(char ch) {
        switch (ch) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
        }
        return -1;
    }
    public static String infixToPostfix(String exp) {
        StringBuilder result = new StringBuilder();

        Stack<Character> stack = new Stack<>();
        for (int i = 0; i<exp.length(); ++i) {
            char c = exp.charAt(i);
            if (Character.isLetterOrDigit(c) || c == ' ')
                result.append(c);
            else if (c == '(')
                stack.push(c);
            else if (c == ')') {
                while(!stack.isEmpty() &&
                        stack.peek() != '(')
                    result.append(stack.pop());
                stack.pop();
            }
            else {
                while(!stack.isEmpty() && Prec(c)
                        <= Prec(stack.peek())){
                    result.append(stack.pop());
                }
                stack.push(c);
            }
        }
        while (!stack.isEmpty()){
            if(stack.peek() == '(')
                return "Invalid Expression";
            result.append(stack.pop());
        }
        return result.toString();
    }
    public static boolean can(String s){
        int start = 0;
        while(s.charAt(start) == ' ') {
            start++;
        }
        int minus = ((s.charAt(start) == '-') ? 1 : 0);
        for(int i = start + minus; i < s.length(); ++i){
            if(Character.isDigit(s.charAt(i)) || s.charAt(i) == ' '){
                continue;
            }
            return false;
        }
        return true;
    }
    public static BigDecimal evaluatePostfix(String exp) {
        Stack<BigDecimal> stack = new Stack<>();
        for(int i = 0; i < exp.length(); i++) {
            char c = exp.charAt(i);
            if(c == ' ') {
                continue;
            }
            else if(Character.isDigit(c)) {
                BigDecimal n = BigDecimal.valueOf(0);
                while(Character.isDigit(c)){
                    n = n.multiply(BigDecimal.valueOf(10));
                    n = n.add(BigDecimal.valueOf((c-'0')));
                    i++;
                    c = exp.charAt(i);
                }
                i--;
                stack.push(n);
            }
            else {
                BigDecimal val1 = stack.pop();
                BigDecimal val2 = stack.pop();
                switch(c) {
                    case '+':
                        BigDecimal val3 = val1;
                        val3 = val3.add(val2);
                        stack.push(val3);
                        break;

                    case '-':
                        val3 = val2;
                        val3 = val3.subtract(val1);
                        stack.push(val3);
                        break;

                    case '/':
                        val3 = val2;
                        val3 = val3.divide(val1);
                        stack.push(val3);
                        break;

                    case '*':
                        val3 = val2;
                        val3 = val3.multiply(val1);
                        stack.push(val3);
                        break;
                }
            }
        }
        return stack.pop();
    }
    public static boolean checkBrackets(String s){
        Stack < Character > st = new Stack<>();
        for(int i = 0; i < s.length(); ++i){
            char c = s.charAt(i);
            if(c == '('){
                st.push(c);
            }
            else if(c == ')'){
                if(st.size() == 0){
                    return false;
                }
                if(st.peek() == '('){
                    st.pop();
                }
                else{
                    return false;
                }
            }
        }
        if(st.size() == 0){
            return true;
        }
        return false;
    }
    public static BigDecimal calculate(String exp){
        exp = infixToPostfix(exp);
        BigDecimal ans = evaluatePostfix(exp);
        return ans;
    }
    public static boolean continuousSigns(String s){
        for(int i = 0; i < s.length(); ++i){
            char c = s.charAt(i);
            if(i > 0 && (c == '*' || c == '/') && s.charAt(i - 1) == '-'){
                return false;
            }
            if(i > 0 && (c == '*' || c == '/') && s.charAt(i - 1) == '+'){
                return false;
            }
            if(i > 0 && (c == '*' || c == '/') && s.charAt(i - 1) == '*'){
                return false;
            }
            if(i > 0 && (c == '*' || c == '/') && s.charAt(i - 1) == '/'){
                return false;
            }
            if(i > 0 && (c == '*' || c == '/') && s.charAt(i - 1) == '='){
                return false;
            }
        }
        return true;
    }
    public static boolean containsOnlyLatin(String s){
        for(int i = 0; i < s.length(); ++i){
            char c = s.charAt(i);
            if(c <= 122){
                continue;
            }
            return false;
        }
        return true;
    }
    public static boolean containsLetter(String s){
        for(int i = 0; i < s.length(); ++i){
            if(Character.isLetter(s.charAt(i))){
                return true;
            }
        }
        return false;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(true){
            String s = scanner.nextLine();
            if(s.length() == 0){
                continue;
            }
            if(s.equals("/exit")){
                System.out.println("Bye!");
                break;
            }
            if(s.equals("/help")){
                System.out.println("The program calculates the sum of numbers");
                continue;
            }
            if(s.charAt(0) == '/'){
                System.out.println("Unknown command");
                continue;
            }
            if(!containsOnlyLatin(s)){
                System.out.println("Invalid expression");
                continue;
            }
            if(!checkBrackets(s)){
                System.out.println("Invalid expression");
                continue;
            }
            int last = 0;
            int flag = 0;
            for(int i = 0; i < s.length(); ++i){
                char c = s.charAt(i);
                if(Character.isDigit(c) && last == 1){
                    System.out.println("Invalid expression");
                    flag = 1;
                    break;
                }
                if(c == ' ' && i > 0 && Character.isDigit(s.charAt(i - 1))){
                    last = 1;
                }
                if(c == '+' || c == '-' || c == '*' || c == '/'){
                    last = 0;
                }
            }
            if(flag == 1){
                continue;
            }
            String s1 = s;
            s = "";
            int equalCount = 0;
            for(int i = 0; i < s1.length(); ++i){
                char c = s1.charAt(i);
                if(c != ' '){
                    s += c;
                }
                if(c == '='){
                    equalCount++;
                }
            }
            if(equalCount > 1){
                System.out.println("Invalid assignment");
                continue;
            }

            s1 = s;
            String variables[] = new String[10002];
            String curString = "";
            flag = 0;
            int last_stringg = 0;
            for(int i = 0; i < s1.length(); ++i){
                char c = s1.charAt(i);
                if(Character.isLetter(c) || Character.isDigit(c) || c == '-'){
                    curString += c;
                }
                if(i > 0) {
                    if (Character.isLetter(c) && Character.isDigit(s1.charAt(i - 1))
                            || Character.isDigit(c) && Character.isLetter(s1.charAt(i))) {
                        System.out.println("Invalid assignment");
                        flag = 1;
                        break;
                    }
                }
                if(c == '='){
                    variables[last_stringg] = curString;
                    last_stringg++;
                    curString = "";
                }
            }
            if(curString.length() != 0){
                variables[last_stringg] = curString;
            }
            if(flag == 1){
                continue;
            }
            if(equalCount == 1){
                if(!containsLetter(variables[1])){
                    var.put(variables[0], variables[1]);
                    continue;
                }
                else{
                    if(var.containsKey(variables[1])){
                        var.put(variables[0], var.get(variables[1]));
                        continue;
                    }
                    else{
                        System.out.println("Unknown variable");
                        continue;
                    }
                }
            }
            s = "";
            for(int i = 0; i < s1.length(); ++i){
                char c = s1.charAt(i);
                if(c == '-' || c == '+'){
                    int cur = 0;
                    while(i < s1.length() && (s1.charAt(i) == '-' || s1.charAt(i) == '+')){
                        c = s1.charAt(i);
                        if(c == '-'){
                            cur++;
                        }
                        i++;
                    }
                    i--;
                    if(cur % 2 == 0){
                        s += '+';
                    }
                    else{
                        s += '-';
                    }
                }
                else{
                    s += c;
                }
            }
            if(s.charAt(s.length() - 1) == '+' || s.charAt(s.length() - 1) == '-'
            || s.charAt(s.length() - 1) == '=' || s.charAt(s.length() - 1) == '/'
            || s.charAt(s.length() - 1) == '*' || !continuousSigns(s)){
                System.out.println("Invalid expression");
                continue;
            }
            flag = 0;
            String current_variable = "";
            s1 = "";
            for(int i = 0; i < s.length(); ++i){
                char c = s.charAt(i);
                if(c == '+'){
                    s1 += "+";
                    continue;
                }
                if(c == '-'){
                    s1 += "-";
                    continue;
                }
                if(c == '*'){
                    s1 += " * ";
                    continue;
                }
                if(c == '/'){
                    s1 += " / ";
                    continue;
                }
                if(c == ')'){
                    s1 += " ) ";
                    continue;
                }
                if(c == '('){
                    s1 += " ( ";
                    continue;
                }
                if(Character.isLetter(c)){
                    current_variable += c;
                    if(i == s.length() - 1 || !Character.isLetter(s.charAt(i + 1))){
                        if(var.containsKey(current_variable)) {
                            s1 += var.get(current_variable);
                            current_variable = "";
                        }
                        else{
                            flag = 1;
                        }
                    }
                }
                else {
                    s1 += c;
                }
            }
            s = "";
            for(int i = 0; i < s1.length(); ++i){
                char c = s1.charAt(i);
                if(c == '-' || c == '+'){
                    int current = 0;
                    while(i < s1.length() && (s1.charAt(i) == '-' || s1.charAt(i) == '+')){
                        c = s1.charAt(i);
                        if(c == '-'){
                            current++;
                        }
                        i++;
                    }
                    i--;
                    if(current % 2 == 0){
                        s += " + ";
                    }
                    else{
                        s += " - ";
                    }
                }
                else{
                    s += c;
                }
            }
            if(flag == 1){
                System.out.println("Unknown variable");
                continue;
            }
            if(can(s)){
                for(int i = 0; i < s.length(); ++i){
                    char c = s.charAt(i);
                    if(c != ' '){
                        System.out.print(c);
                    }
                }
                System.out.print('\n');
            }
            else {
                System.out.println(calculate(s));
            }
        }
    }
}