package com.redmart.spreadsheet.calculator;

public abstract class Element {

    private String element;

    public Element(String element) {
        this.element = element;
    }

    public String getElement() {
        return this.element;
    }
}
