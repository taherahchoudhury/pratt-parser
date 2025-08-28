package com.example.prattparsergui;

import java.util.ArrayList;

public class ParsingResult {
    private final String left;
    private final ArrayList<String> rest;
    private int leftValue;

    public ParsingResult(String left, ArrayList<String> rest, int leftValue) {
        this.left = left;
        this.rest = rest;
        this.leftValue = leftValue;
    }
    public ParsingResult(String left, ArrayList<String> rest) {
        this.left = left;
        this.rest = rest;
    }

    public String getLeft(){
        return left;
    }

    public ArrayList<String> getRest(){
        return rest;
    }

    public int getLeftValue(){
        return leftValue;
    }
}
