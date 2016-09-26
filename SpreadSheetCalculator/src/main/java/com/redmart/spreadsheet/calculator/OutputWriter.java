package com.redmart.spreadsheet.calculator;

import java.util.LinkedHashMap;

public class OutputWriter {

    public void printOutput(int width, int height, LinkedHashMap<String, Double> evaluatedElements) {
        System.out.println(width + " " + height);
        for(String cellName: evaluatedElements.keySet()) {
            System.out.println(String.format("%.5f", evaluatedElements.get(cellName)));
        }
    }
}
