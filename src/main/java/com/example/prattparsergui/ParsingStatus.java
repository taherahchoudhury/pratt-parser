package com.example.prattparsergui;

import java.util.ArrayList;

public class ParsingStatus {

    private final String tokens;

    private final String token;

    private final String operator;

    public ParsingStatus(ArrayList<String> list, String token, String operator) {
        tokens = convertToString(list);
        this.token = token;
        this.operator = operator;
    }

    private String convertToString(ArrayList<String> list) {
        StringBuilder tokensStr = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                tokensStr.append(list.get(i)).append("]");
            } else {
                tokensStr.append(list.get(i)).append(", ");
            }
        }
        return tokensStr.toString();
    }

    public final String getTokens() {
        return tokens;
    }

    public final String getToken() {
        return token;
    }

    public final String getOperator() {
        return operator;
    }

}
