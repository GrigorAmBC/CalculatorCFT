package com.calculator.abstractions;

public interface Observable {
    void subscribe(Observer observer);
    void dispose(Observer observer);
    void onChanged();
    void onResultChanged();
}