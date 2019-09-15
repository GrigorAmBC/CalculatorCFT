package com.calculator.abstractions;

import java.util.List;

public abstract class ErrorHandler {
    protected String error;

    public abstract boolean check(List<CalculatorItem> items);

    public String getError() {
        return error;
    }

    protected void setError(String error) {
        this.error = error;
    }
}
