package com.redmart.spreadsheet.calculator;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class CellTest {

    @Test
    public void testParseContent() {
        Cell cell = new Cell("A1", "A2");
        cell.parseContent();
        List<ReferenceElement> references = cell.getReferences();
        assertEquals(references.size(), 1);
        assertEquals(references.get(0).getElement(), "A2");

        cell = new Cell("A2", "4 5 *");
        cell.parseContent();
        LinkedList<Element> list = cell.getListOfElements();
        assertEquals(3, list.size());
        assertEquals(list.get(0).getElement(), "4");
        assertEquals(list.get(1).getElement(), "5");
        assertEquals(list.get(2).getElement(), "*");

        cell = new Cell("B1", "A1 B2 / 2 +");
        cell.parseContent();
        list = cell.getListOfElements();
        assertEquals(5, list.size());
        assertEquals(list.get(0).getElement(), "A1");
        assertEquals(list.get(1).getElement(), "B2");
        assertEquals(list.get(2).getElement(), "/");
        assertEquals(list.get(3).getElement(), "2");
        assertEquals(list.get(4).getElement(), "+");
        references = cell.getReferences();
        assertEquals(references.size(), 2);
        assertEquals(references.get(0).getElement(), "A1");
        assertEquals(references.get(1).getElement(), "B2");
    }
}
