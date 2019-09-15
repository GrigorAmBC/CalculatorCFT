package com.calculator.abstractions;

import com.calculator.utility.Message;

import java.util.List;

public abstract class Computer {
    protected ErrorHandler errorHandler;

    public abstract Message compute(List<CalculatorItem> items);

    public void setErrorHandler(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }
}
