package com.example.prattparsergui;

public final class Operator {
    private final int precedence;
    private final String operator;

    public Operator(int precedence, String operator) {
        this.precedence = precedence;
        this.operator = operator;
    }

    public int calculate(int x, int y) {
        return switch (operator) {
            case "-" -> x - y;
            case "+" -> x + y;
            case "/" -> x / y;
            case "*" -> x * y;
            default -> 0;
        };
    }

    public int getPrecedence() {
        return precedence;
    }

    public String getOperator() {
        return operator;
    }

}
