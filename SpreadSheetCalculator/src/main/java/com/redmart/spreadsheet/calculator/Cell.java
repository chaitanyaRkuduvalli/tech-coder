package com.redmart.spreadsheet.calculator;

import com.redmart.spreadsheet.calculator.enums.Patterns;

import java.util.LinkedList;

public class Cell {

    private String cellNum;
    private String contents;
    private LinkedList<ReferenceElement> references;
    private boolean isEvaluated;
    private Double evaluatedValue;
    private int unresolvedReferences;
    private LinkedList<Element> listOfElements;

    public Cell(String cellNum, String contents) {
        this.cellNum = cellNum;
        this.contents = contents;
        references = new LinkedList<ReferenceElement>();
        listOfElements = new LinkedList<Element>();
    }

    public String getCellNum() {
        return cellNum;
    }

    public LinkedList<ReferenceElement> getReferences() {
        return references;
    }

    public boolean isEvaluated() {
        return isEvaluated;
    }

    public void setIsEvaluated(boolean isEvaluated) {
        this.isEvaluated = isEvaluated;
    }

    public int getUnresolvedReferences() {
        return unresolvedReferences;
    }

    public void setUnresolvedReferences(int unresolvedReferences) {
        this.unresolvedReferences = unresolvedReferences;
    }

    public Double getEvaluatedValue() {
        return evaluatedValue;
    }

    public void setEvaluatedValue(Double evaluatedValue) {
        this.evaluatedValue = evaluatedValue;
    }

    public LinkedList<Element> getListOfElements() {
        return listOfElements;
    }

    // Default scope for the purpose of unit testing.
    void setListOfElements(LinkedList<Element> listOfElements) {
        this.listOfElements = listOfElements;
    }

    public void parseContent() {
        String[] tokens = contents.split("\\s+");
        for(String token: tokens) {
            if(Patterns.valuePattern.matcher(token).matches()) {
                listOfElements.add(new ValueElement(token));
            } else if(Patterns.operatorPattern.matcher(token).matches()) {
                listOfElements.add(new OperatorElement(token));
            } else if(Patterns.referencePattern.matcher(token).matches()) {
                ReferenceElement refElement = new ReferenceElement(token);
                references.add(refElement);
                listOfElements.add(refElement);
                unresolvedReferences++;
            } else {
                throw new RuntimeException("Invalid token found: " + token);
            }
        }
    }
}
