package com.calculator.utility;

import com.calculator.abstractions.CalculatorItem;
import com.calculator.abstractions.Computer;

import java.util.ArrayList;
import java.util.List;


public class CalculatorComputer extends Computer {
    private List<CalculatorItem> polishNotation = new ArrayList<>();

    public CalculatorComputer() {
        this.errorHandler = new CalculatorErrorHandler();
    }

    private void setup() {
        polishNotation.clear();
        polishNotation = new ArrayList<>();
    }

    public Message compute(List<CalculatorItem> items) {
        setup();
        if (errorHandler.check(items))
            return new Message(errorHandler.getError());

        createPolishNotation(items);
        return getResult();
    }

    private int getOperatorPriority(Operator operator) {//priority of an operand
        Character c = operator.getItem();
        if (c == '+' || c == '-')
            return 1;
        else if (c == '*' || c == '÷')
            return 2;
        else
            return 0;
    }

    private void addOperators(List<Operator> operators) {
        while (!operators.isEmpty()) {
            Operator operator = operators.get(operators.size() - 1);
            polishNotation.add(operator);
            operators.remove(operators.size() - 1);
        }
    }

    private void addOperator(List<Operator> operators, Operator curOperator, int priorCoef) {//processing operands
        final int curPrior = priorCoef + getOperatorPriority(curOperator);

        while (!operators.isEmpty()) {
            Operator operator = operators.get(operators.size() - 1);
            if (getOperatorPriority(operator) + operator.getCoefficient() >= curPrior) {
                polishNotation.add(operator);
                operators.remove(operators.size() - 1);
            } else
                break;
        }

        operators.add(new Operator(curOperator.getItem(), priorCoef));
    }

    private void createPolishNotation(List<CalculatorItem> items) {
        int coefficient = 0;
        List<Operator> operators = new ArrayList<>();

        for (CalculatorItem item : items) {
            switch (item.getType()) {
                case CalculatorItem.NUMBER:
                    polishNotation.add(item);
                    break;
                case CalculatorItem.OPERATOR:
                    addOperator(operators, (Operator) item, coefficient);
                    break;
                case CalculatorItem.BRACKET:
                    if (item.getItem().equals("(")) {
                        coefficient += 2;
                    } else if (item.getItem().equals(")")) {
                        coefficient -= 2;
                    }
                    break;
            }
        }
        addOperators(operators);
    }

    private Message getResult() {
        long num1, num2, result = 0;
        char curOperator;
        List<CalculatorItem> temp = new ArrayList<>();

        for (CalculatorItem item : polishNotation) {

            switch (item.getType()) {
                case CalculatorItem.NUMBER:
                    temp.add(item);
                    break;
                case CalculatorItem.OPERATOR:
                    num1 = ((com.calculator.utility.Number) temp.get(temp.size() - 1)).getItem();
                    num2 = ((com.calculator.utility.Number) temp.get(temp.size() - 2)).getItem();
                    curOperator = ((Operator) item).getItem();

                    if (curOperator == '*')
                        result = num2 * num1;
                    else if (curOperator == '÷' && num1 != 0)
                        result = num2 / num1;
                    else if (curOperator == '÷') {
                        return new Message(CalculatorErrorHandler.ZERO_DIVISION_ERROR);
                    } else if (curOperator == '+')
                        result = num2 + num1;
                    else if (curOperator == '-')
                        result = num2 - num1;
                    temp.remove(temp.size() - 1);
                    temp.remove(temp.size() - 1);
                    temp.add(new com.calculator.utility.Number(result));
                    break;
            }
        }

        if (temp.isEmpty())
            return new Message(CalculatorErrorHandler.SYNTAX_ERROR);

        return new Message(temp.get(temp.size() - 1).toString());
    }
}
