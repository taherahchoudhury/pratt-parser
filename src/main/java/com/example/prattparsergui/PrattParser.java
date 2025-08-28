package com.example.prattparsergui;

import java.util.ArrayList;

public class PrattParser {

    private final ArrayList<ParsingStatus> statusList;
    private final ArrayList<String> resultList;
    private ArrayList<Operator> operators;

    public PrattParser(){
        statusList = new ArrayList<>();
        resultList = new ArrayList<>();
        setOperators();
    }
    private void setOperators() {
        operators = new ArrayList<>();

        operators.add(new Operator(1,"+"));
        operators.add(new Operator(1,"-"));
        operators.add(new Operator(2,"*"));
        operators.add(new Operator(2,"/"));
    }

    public ArrayList<ParsingStatus> getStatusList() {
        return statusList;
    }

    public ArrayList<String> getResultList() {
        return resultList;
    }

    public void clearStatus() {
        statusList.clear();
    }

    /*
    This method takes in 3 parameters.
    1. limit: This is the current precedence (initially starts at 0)
    2. tokens: This is the expression broken into tokens (brackets, operators and operands)
    3. indentString: This is a string used to display the steps in a readable format.
    Parses the tokenized input expression and calculates the result.
     */
    public ParsingResult pratt(int limit, ArrayList<String> tokens, String indentString) {
        resultList.add(indentString + "Entered pratt, tokens = " + display(tokens) + "\n");

        if (tokens.isEmpty()) {
            return new ParsingResult("", tokens);
        } else {
            statusList.add(new ParsingStatus(tokens, tokens.get(0), ""));
            resultList.add(indentString + "Current Token: " + tokens.get(0) + "\n");

            if (tokens.get(0).equals("(")) {
                resultList.add(indentString + "'(' is ignored\n");
                ArrayList<String> remaining = getTail(tokens);
                ParsingResult parsingResult = pratt(0, remaining, indentString + "  ");
                return ploop(limit, parsingResult.getLeft(), getTail(parsingResult.getRest()), parsingResult.getLeftValue(), indentString + "  ");
            } else {
                int firstToken = Integer.parseInt(tokens.get(0));
                return ploop(limit, tokens.get(0), getTail(tokens), firstToken, indentString + "  " );
            }
        }
    }

    private String display(ArrayList<String> tokens) {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < tokens.size(); i++) {
            if (i != tokens.size() - 1) {
                result.append("'").append(tokens.get(i)).append("', ");
            } else {
                result.append("'").append(tokens.get(i)).append("']");
            }
        }
        return result.toString();
    }

    // returns the tail of a given list
    private ArrayList<String> getTail(ArrayList<String> elements) {
        if (elements.size() != 1) {
            elements.remove(0);
            return elements;
        }
        return elements;
    }

    /*
     Updates the current values of the parsed expression.
     Returns the final answer to the parsed expression as well as the parsed expression.
     */
    public ParsingResult ploop(int limit, String left, ArrayList<String> rest, int leftValue, String indentString) {
        resultList.add(indentString + "Beginning ploop\n");

        if(rest.isEmpty()) {
            return new ParsingResult(left, rest, leftValue);
        }

        resultList.add(indentString + "Checking head of rest of the tokens \n");

        // Check if the first element in rest is an operator
        Operator currentOperator = getCurrentOperator(rest.get(0));
        if(currentOperator != null) {
            resultList.add(indentString + "Current Operator = " + currentOperator.getOperator() + "\n");

            statusList.add(new ParsingStatus(rest, rest.get(0), currentOperator.getOperator()));

            int precedence = currentOperator.getPrecedence();
            if(precedence <= limit) {
                resultList.add(indentString + "Returning the left value: " + left + "\n");
                return new ParsingResult(left, rest, leftValue);
            } else {
                ParsingResult prattResult = pratt(precedence, getTail(rest), indentString + "  ");

                String right = prattResult.getLeft();
                int rightValue = prattResult.getLeftValue();
                ArrayList<String> tokensAfter = prattResult.getRest();

                resultList.add(indentString + "Right: " + right + "\n");

                ParsingResult ploopResult = ploop(limit, "<" + left + currentOperator.getOperator() + right + ">", tokensAfter, currentOperator.calculate(leftValue, rightValue), indentString + "  ");

                String returnStr = ploopResult.getLeft();
                ArrayList<String> returnList = ploopResult.getRest();

                resultList.add(indentString + "Returning: " + returnStr + "; " + displayList(returnList) + "\n");

                resultList.add(indentString + "End of ploop\n");

                return ploopResult;
            }
        } else {
            resultList.add(indentString + "End of ploop\n");
            return new ParsingResult(left, rest, leftValue);
        }

    }

    private String displayList(ArrayList<String> list) {
        StringBuilder str = new StringBuilder();
        for (String s: list) {
            str.append(s);
        }
        return str.toString();
    }

    private Operator getCurrentOperator(String op) {
        for (Operator o: operators) {
            if(o.getOperator().equals(op)) {
                return o;
            }
        }
        return null;
    }
}
