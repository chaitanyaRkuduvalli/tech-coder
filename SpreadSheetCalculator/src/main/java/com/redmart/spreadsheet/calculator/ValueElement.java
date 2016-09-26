package com.redmart.spreadsheet.calculator;

public class ValueElement extends Element {

    public ValueElement(String element) {
        super(element);
    }

    public Double parse() {
        return Double.parseDouble(getElement());
    }
}
