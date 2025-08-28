package com.example.prattparsergui;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

/*
Class ParserExecute: Splits up the input expression into tokens then executes the pratt parser.
- Contains the method isValidExpression that returns a string "valid" if expression is valid or
- an appropriate error message string.
 */

public class ParserExecute {

    private final PrattParser prattParser;

    private ParsingResult parsingResult;

    public ParserExecute(){
        prattParser = new PrattParser();
    }

    /*
    Executes the pratt parser on the given expression and returns result as a string.
     */
    public ArrayList<String> getParsingResult(String expression) {
        // Tokenize the expression.
        ArrayList<String> tokens = getTokens(expression);
        parsingResult = prattParser.pratt(0, tokens, "");

        return prattParser.getResultList();
    }

    public int getResultValue() {
        return parsingResult.getLeftValue();
    }

    public String getResultString() {
        return "Result String: " + parsingResult.getLeft() + ", Result Value: " + parsingResult.getLeftValue();
    }

    public void clearStatusList() {
        prattParser.clearStatus();
    }

    public ArrayList<ParsingStatus> getStatusList() {
        return prattParser.getStatusList();
    }

    /*
    Function checks if input expressions is a valid expression.
    - if the brackets are valid
    - if there is only numbers
    - if the operator and operand pair is correct
    for each error a specific error message is returned.
     */
    public String isValidExpression(String input) {
        input = input.replaceAll("\\s+","");
        // check for validity of brackets.
        // if brackets are correct, remove all brackets from input string.
        if (!isBracketsValid(input).equals("valid")) {
            return isBracketsValid(input);
        } else {
            input = removeBrackets(input);
        }
        // check validity of input string.
        boolean hasOperator = false;
        String previousS = "";
        for (int i = 0; i < input.length(); i++) {
            String s = String.valueOf(input.charAt(i));
            if (!isNumber(s)) {

                if ((i == 0) && isOperator(s)) {
                    return "invalid expression, expression cannot start with operator, try again";
                } else if (isOperator(s) && isOperator(previousS)) {
                    return "invalid expression, operators are written incorrectly, try again";
                } else if ((i == input.length() - 1) && isOperator(s)) {
                    return "invalid expression, operators must follow with a number, try again";
                } else if (isOperator(s)) {
                    hasOperator = true;
                    previousS = s;
                } else {
                    return "invalid expression, must contain only numbers and valid operators, try again";
                }
            } else {
                previousS = s;
            }
        }
        if (!hasOperator) {
            return "invalid expression, must contain operators, try again";
        }
        return "valid";
    }
    private String removeBrackets(String input) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            String s = String.valueOf(input.charAt(i));
            if(!isBracket(s)) {
                result.append(s);
            }
        }
        return result.toString();
    }

    private boolean isBracket(String s) {
        return switch (s) {
            case "(", ")" -> true;
            default -> false;
        };
    }

    /*
    Checks is the brackets are correctly placed for the given input.
    */
    private String isBracketsValid(String input) {
        Stack<String> brackets = new Stack<>();
        for (int i = 0; i < input.length(); i++) {
            String s = String.valueOf(input.charAt(i));
            if(s.equals("(")) {
                brackets.push(s);
            }else if(s.equals(")") && brackets.isEmpty()) {
                return "invalid expression, missing '(', try again";
            }

            if(!brackets.isEmpty()) {
                if(s.equals(")") && brackets.peek().equals("(")) {
                    brackets.pop();
                } else if(brackets.peek().equals("(") && (i == input.length() - 1)) {
                    return "invalid expression, missing ')', try again";
                }
            }

        }
        if (!brackets.isEmpty()) {
            return "invalid expression, missing ')', try again";
        }
        return "valid";

    }

    private boolean isOperator(String s) {
        return switch (s) {
            case "-", "+", "/", "*" -> true;
            default -> false;
        };
    }

    /*
    Takes an expression and splits them into brackets, operators and operands and stores them in an ArrayList.
    The ArrayList is then returned.
    */
    private ArrayList<String> getTokens(String expression) {
        expression = expression.replaceAll("\\s+","");
        ArrayList<String> expLst = new ArrayList<>();
        String element = "";
        for (int i = 0; i < expression.length(); i++) {
            String s = String.valueOf(expression.charAt(i));
            if (isNumber(s) && isNumber(element)) {
                element += s;
            } else {
                if (!Objects.equals(element, "")) {
                    expLst.add(element);
                }
                element = s;
            }
        }
        expLst.add(element);
        return expLst;
    }
    private boolean isNumber(String str) {
        if (Objects.equals(str, "")) {
            return false;
        }
        try {
            int i = Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
