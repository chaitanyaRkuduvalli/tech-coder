package com.redmart.spreadsheet.calculator;

import com.redmart.spreadsheet.calculator.enums.Operators;
import com.redmart.spreadsheet.calculator.exceptions.CircularDependencyException;

import java.util.*;

public class WorkBook {

    private LinkedList<Cell> tList = new LinkedList<Cell>();
    private int width;
    private int height;
    private Map<String, HashSet<Cell>> dependenciesMap = new HashMap<String, HashSet<Cell>>();
    private LinkedHashMap<String, Double> evaluatedElements = new LinkedHashMap<String, Double>();

    public void readInput(Scanner scanner) {
        width = scanner.nextInt();
        height = scanner.nextInt();

        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                String cellNum = Character.toString((char) (65 + i)) + (j + 1);
                evaluatedElements.put(cellNum, null);
                String cellContent = scanner.nextLine().trim().toUpperCase();
                Cell cell = new Cell(cellNum, cellContent);
                cell.parseContent();
                if(cell.getReferences().size() > 0) {
                    addDependencies(cell);
                } else {
                    tList.add(cell);
                }
            }
        }
    }

    private void addDependencies(Cell cell) {
        List<ReferenceElement> references = cell.getReferences();
        for(ReferenceElement ref: references) {
            HashSet<Cell> set = dependenciesMap.get(ref.getElement());
            if(set == null) {
                set = new HashSet<Cell>();
            }
            set.add(cell);
            dependenciesMap.put(ref.getElement(), set);
        }
    }

    public void calculate() throws CircularDependencyException, IllegalArgumentException {
        int count = 0;

        while(!tList.isEmpty()) {
            Cell cell = tList.remove(0);
            evaluateCell(cell);
            count++;
            removeDependentEdges(cell);
        }
        if(count != width * height) {
            throw new CircularDependencyException("Circular dependency exists");
        }
    }

    private void removeDependentEdges(Cell cell) {
        if (dependenciesMap.containsKey(cell.getCellNum())) {
            HashSet<Cell> dependents = dependenciesMap.get(cell.getCellNum());
            if (!dependents.isEmpty()) {
                for (Cell depCell : dependents) {
                    depCell.setUnresolvedReferences(depCell.getUnresolvedReferences() - 1);
                    if (depCell.getUnresolvedReferences() == 0)
                        tList.add(depCell);
                }
            }
        }
    }

    private Double evaluateCell(Cell cell) throws CircularDependencyException, IllegalArgumentException {
        if(cell.isEvaluated()) {
            return cell.getEvaluatedValue();
        }

        List<Element> elementList = cell.getListOfElements();
        Stack<Double> evaluationStack = new Stack<Double>();
        for(Element elem: elementList) {
            if(elem instanceof ValueElement) {
                evaluationStack.push(((ValueElement) elem).parse());
            } else if(elem instanceof OperatorElement) {
                Operators.getOperator(elem.getElement()).apply(evaluationStack);
            } else if (elem instanceof ReferenceElement) {
                /*
                 * As per our topological sort algorithm, by the time we hit a reference element, that element should
                 * have been already evaluated. If not, there is a cyclic dependency.
                 */
                Double value = evaluatedElements.get(elem.getElement());
                if(value == null) {
                    throw new CircularDependencyException("Circular Dependency exists");
                }
                evaluationStack.push(value);
            } else {
                throw new RuntimeException("Invalid element exception: " + elem.getElement());
            }
        }
        cell.setEvaluatedValue(evaluationStack.pop());
        cell.setIsEvaluated(true);
        evaluatedElements.put(cell.getCellNum(), cell.getEvaluatedValue());
        return cell.getEvaluatedValue();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public LinkedHashMap<String, Double> getEvaluatedElements() {
        return evaluatedElements;
    }

    /*
     * Package level scope for these setter methods only for the purpose of unit testing.
     */

    void setWidth(int width) {
        this.width = width;
    }

    void setHeight(int height) {
        this.height = height;
    }

    void setEvaluatedElements(LinkedHashMap<String, Double> evaluatedElements) {
        this.evaluatedElements = evaluatedElements;
    }

    void setDependenciesMap(Map<String, HashSet<Cell>> dependenciesMap) {
        this.dependenciesMap = dependenciesMap;
    }

    void settList(LinkedList<Cell> tList) {
        this.tList = tList;
    }
}
