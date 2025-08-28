package com.example.prattparsergui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserExecuteTest {

    ParserExecute parserExecute;

    @BeforeEach
    void setUp() {
        parserExecute = new ParserExecute();
    }

    @Test
    void testErrorMessageForMissingClosedBracket(){
        assertEquals(parserExecute.isValidExpression("(10+10"), "invalid expression, missing ')', try again");
        assertEquals(parserExecute.isValidExpression("((10+10"), "invalid expression, missing ')', try again");
        assertEquals(parserExecute.isValidExpression("((10+10)"), "invalid expression, missing ')', try again");
    }

    @Test
    void testErrorMessageForMissingOpenBracket(){
        assertEquals(parserExecute.isValidExpression("10+10)"), "invalid expression, missing '(', try again");
        assertEquals(parserExecute.isValidExpression("10+10))"), "invalid expression, missing '(', try again");
        assertEquals(parserExecute.isValidExpression("(10+10))"), "invalid expression, missing '(', try again");
    }

    @Test
    void testErrorMessageForOnlyNumbers(){
        assertEquals(parserExecute.isValidExpression("20041339"), "invalid expression, must contain operators, try again");
    }


    @Test
    void testErrorMessageForLetters(){
        assertEquals(parserExecute.isValidExpression("hello world"), "invalid expression, must contain only numbers and valid operators, try again");
    }

    @Test
    void testInvalidExpressionForAddition(){
        assertEquals(parserExecute.isValidExpression("+2"), "invalid expression, expression cannot start with operator, try again");
        assertEquals(parserExecute.isValidExpression("2++2"), "invalid expression, operators are written incorrectly, try again");
    }

    @Test
    void testInvalidExpressionForSubtraction(){
        assertEquals(parserExecute.isValidExpression("-2"), "invalid expression, expression cannot start with operator, try again");
        assertEquals(parserExecute.isValidExpression("2--2"), "invalid expression, operators are written incorrectly, try again");
    }

    @Test
    void testInvalidExpressionForMultiplication(){
        assertEquals(parserExecute.isValidExpression("*2"), "invalid expression, expression cannot start with operator, try again");
        assertEquals(parserExecute.isValidExpression("2**2"), "invalid expression, operators are written incorrectly, try again");
    }

    @Test
    void testInvalidExpressionForDivision(){
        assertEquals(parserExecute.isValidExpression("/2"), "invalid expression, expression cannot start with operator, try again");
        assertEquals(parserExecute.isValidExpression("2//2"), "invalid expression, operators are written incorrectly, try again");
    }
}