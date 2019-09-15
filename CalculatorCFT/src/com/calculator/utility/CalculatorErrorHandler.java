package com.calculator.utility;

import com.calculator.abstractions.CalculatorItem;
import com.calculator.abstractions.ErrorHandler;

import java.util.List;


public class CalculatorErrorHandler extends ErrorHandler {

    public static final String SYNTAX_ERROR = "Syntax error";
    public static final String ZERO_DIVISION_ERROR = "Cannot divide by zero";

    @Override
    public boolean check(List<CalculatorItem> items) {
        boolean syntaxError = checkForSyntaxError(items),
                zeroDivisionError = checkForZeroDivision(items);

        if (syntaxError)
            setError(SYNTAX_ERROR);
        else if (zeroDivisionError)
            setError(ZERO_DIVISION_ERROR);

        return (syntaxError || zeroDivisionError);
    }


    private boolean checkForSyntaxError(List<CalculatorItem> items) {
        boolean numB = false, operB = false, synError = false;
        int lBrack = 0, rBrack = 0;
        for (CalculatorItem item : items) {
            if (item.getType().equals(CalculatorItem.NUMBER)) {
                numB = true;
                operB = false;
            } else if (item.getType().equals(CalculatorItem.OPERATOR) && numB) {
                numB = false;
                operB = true;
            } else if (item.toString().equals("(")
                    && !numB) {
                operB = false;
                lBrack++;
            } else if (item.toString().equals(")") && numB && lBrack > 0)
                rBrack++;
            else
                synError = true;
        }

        if (lBrack != rBrack || operB)
            synError = true;
        return synError;
    }

    private boolean checkForZeroDivision(List<CalculatorItem> items) {//maybe make it final??
        for (int i = 0; i < items.size() - 1; i++) {
            if (items.get(i).toString().equals("÷") &&
                    items.get(i + 1).toString().equals("0"))
                return true;
        }

        return false;
    }
}
