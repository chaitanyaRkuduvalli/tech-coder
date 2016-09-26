package com.redmart.spreadsheet.calculator;

import com.redmart.spreadsheet.calculator.exceptions.CircularDependencyException;

import java.util.Scanner;

public class Spreadsheet {

    private WorkBook workBook;
    private Scanner scanner;

    public Spreadsheet() {
        workBook = new WorkBook();
        scanner = new Scanner(System.in);
    }

    public WorkBook getWorkBook() {
        return workBook;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public static void main(String[] args) {
        Spreadsheet spreadsheet = new Spreadsheet();

        WorkBook workBook = spreadsheet.getWorkBook();
        Scanner iscanner = spreadsheet.getScanner();

        try {
            workBook.readInput(iscanner);
            workBook.calculate();
        } catch(RuntimeException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (CircularDependencyException e) {
            System.out.println("Cyclic Dependency exists");
            System.exit(1);
        }

        OutputWriter writer = new OutputWriter();
        writer.printOutput(workBook.getWidth(), workBook.getHeight(), workBook.getEvaluatedElements());
    }
}
