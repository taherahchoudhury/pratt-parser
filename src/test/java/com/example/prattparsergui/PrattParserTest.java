package com.example.prattparsergui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrattParserTest {

    PrattParser prattParser;

    @BeforeEach
    void setUp() {
        prattParser = new PrattParser();
    }

    @Test
    void testValidSingleBrackets(){
        ArrayList<String> tokens = getTokens("(10+10)");
        ParsingResult parsingResult = prattParser.pratt(0, tokens, "");

        assertEquals(parsingResult.getLeftValue(), 20);
    }

    @Test
    void testValidDoubleBrackets(){
        ArrayList<String> tokens = getTokens("((10+10))");
        ParsingResult parsingResult = prattParser.pratt(0, tokens, "");

        assertEquals(parsingResult.getLeftValue(), 20);
    }

    @Test
    void testValidAddition(){
        ArrayList<String> tokens = getTokens("10+10");
        ParsingResult parsingResult = prattParser.pratt(0, tokens, "");

        assertEquals(parsingResult.getLeftValue(), 20);
    }

    @Test
    void testValidSubtraction(){
        ArrayList<String> tokens = getTokens("10-2");
        ParsingResult parsingResult = prattParser.pratt(0, tokens, "");

        assertEquals(parsingResult.getLeftValue(), 8);
    }

    @Test
    void testValidMultiplication(){
        ArrayList<String> tokens = getTokens("2*3");
        ParsingResult parsingResult = prattParser.pratt(0, tokens, "");

        assertEquals(parsingResult.getLeftValue(), 6);
    }

    @Test
    void testValidDivision(){
        ArrayList<String> tokens = getTokens("20/5");
        ParsingResult parsingResult = prattParser.pratt(0, tokens, "");

        assertEquals(parsingResult.getLeftValue(), 4);
    }

    // methods for testing the PrattParser class
    private ArrayList<String> getTokens(String expression) {
        expression = expression.replaceAll("\\s+","");
        ArrayList<String> expLst = new ArrayList<>();
        String element = "";
        System.out.println(expression.length());
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