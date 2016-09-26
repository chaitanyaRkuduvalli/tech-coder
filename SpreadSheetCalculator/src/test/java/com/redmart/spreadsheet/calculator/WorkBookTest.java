package com.redmart.spreadsheet.calculator;

import com.redmart.spreadsheet.calculator.exceptions.CircularDependencyException;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class WorkBookTest {

    @Test
    public void testEvaluateCell() {

        WorkBook workBook = new WorkBook();

        workBook.setWidth(2);
        workBook.setHeight(1);
        LinkedList<Element> l1 = new LinkedList<Element>();
        l1.add(new ReferenceElement("A2"));
        LinkedList<Element> l2 = new LinkedList<Element>();
        l2.add(new ValueElement("4"));
        l2.add(new ValueElement("5"));
        l2.add(new OperatorElement("*"));

        LinkedHashMap<String, Double> evaluatedElements = new LinkedHashMap<String, Double>();
        evaluatedElements.put("A1", null);
        evaluatedElements.put("A2", null);
        workBook.setEvaluatedElements(evaluatedElements);

        Map<String, HashSet<Cell>> dependenciesMap = new HashMap<String, HashSet<Cell>>();
        HashSet<Cell> set = new HashSet<Cell>();
        Cell a1Cell = new Cell("A1", "A2");
        a1Cell.setListOfElements(l1);
        a1Cell.setUnresolvedReferences(1);
        set.add(a1Cell);
        dependenciesMap.put("A2",set);
        workBook.setDependenciesMap(dependenciesMap);

        Cell a2Cell = new Cell("A2", "4 5 *");
        a2Cell.setListOfElements(l2);
        LinkedList<Cell> list = new LinkedList<Cell>();
        list.add(a2Cell);
        workBook.settList(list);

        try {
            workBook.calculate();
        } catch (CircularDependencyException e) {
            fail("Unit test for workbook calculate failed");
        }

        assertEquals("20.0", a2Cell.getEvaluatedValue().toString());
        assertEquals("20.0", a1Cell.getEvaluatedValue().toString());
    }
}
