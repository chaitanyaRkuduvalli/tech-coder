package com.redmart.spreadsheet.calculator.enums;

import java.util.Stack;

public enum Operators {
    ADD("+"),
    SUB("-"),
    MUL("*"),
    DIV("/");

    private final String operator;

    private Operators(String op) {
        operator = op;
    }

    private String getOperator() {
        return this.operator;
    }

    public static Operators getOperator(String op) throws IllegalArgumentException {
        for (Operators operator: Operators.values()) {
            if (operator.getOperator().equalsIgnoreCase(op)) {
                return operator;
            }
        }
        throw new IllegalArgumentException(String.format("There is no value with name '%s' in Enum %s", op, Operators.class.getName()));
    }

    public Stack<Double> apply(Stack<Double> rpnStack) throws IllegalArgumentException {
        double op1, op2;
        switch (this) {
            case ADD:
                op1 = rpnStack.pop();
                op2 = rpnStack.pop();
                rpnStack.push(op2 + op1);
                break;
            case SUB:
                op1 = rpnStack.pop();
                op2 = rpnStack.pop();
                rpnStack.push(op2 - op1);
                break;
            case MUL:
                op1 = rpnStack.pop();
                op2 = rpnStack.pop();
                rpnStack.push(op2 * op1);
                break;
            case DIV:
                op1 = rpnStack.pop();
                op2 = rpnStack.pop();
                if (op1 == 0) {
                    throw new IllegalArgumentException("Error: Cannot divide by 0");
                }
                rpnStack.push(op2 / op1);
                break;
        }
        return rpnStack;
    }

}
