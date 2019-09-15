package com.calculator.abstractions;

public abstract class CalculatorItem<T> {
    public static final String OPERATOR = "operator";
    public static final String NUMBER = "number";
    public static final String BRACKET = "bracket";
    public static final String MESSAGE = "message";

    public abstract T getItem();

    public abstract String getType();
}






