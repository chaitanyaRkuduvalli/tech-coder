package com.redmart.spreadsheet.calculator.enums;

import java.util.regex.Pattern;

public enum Patterns {
    VALUE("[+-]?\\d+"),
    REFERENCE("([a-zA-Z]+)(\\d+)"),
    OPERATOR("[*/+-]");

    private final String pattern;
    public static Pattern valuePattern;
    public static Pattern referencePattern;
    public static Pattern operatorPattern;

    static {
        valuePattern = Pattern.compile("[+-]?\\d+");
        referencePattern = Pattern.compile("([a-zA-Z]+)(\\d+)");
        operatorPattern = Pattern.compile("[*/+-]");
    }

    private Patterns(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return this.pattern;
    }
}
