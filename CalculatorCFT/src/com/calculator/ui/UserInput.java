package com.calculator.ui;

import com.calculator.abstractions.CalculatorItem;
import com.calculator.abstractions.Observable;
import com.calculator.utility.CalculatorErrorHandler;

import java.util.ArrayList;
import java.util.List;
import com.calculator.abstractions.*;


public class UserInput implements Observable {

  private CalculatorItem result;
  private List<CalculatorItem> items = new ArrayList<>();
  private List<Observer> observers = new ArrayList<>();
  private boolean isErrorStateActive = false;

  public void setup() {
    items.clear();
    isErrorStateActive = false;
    result = null;
    onChanged();
  }

  public boolean isErrorStateActive() {
    return isErrorStateActive;
  }

  public void append(CalculatorItem item) {
    items.add(item);
    onChanged();
  }

  public CalculatorItem getLastElement() {
    return (items.isEmpty()) ? null :
            items.get(items.size() - 1);
  }

  private void setErrorState(String message) {
    if (message.equals(CalculatorErrorHandler.ZERO_DIVISION_ERROR) ||
            message.equals(CalculatorErrorHandler.SYNTAX_ERROR))
      isErrorStateActive = true;
  }

  public void setResult(CalculatorItem item) {
    setup();
    result = item;
    setErrorState(item.toString());
    onResultChanged();
  }

  public CalculatorItem getResult() {
    return result;
  }


  public List<CalculatorItem> getItems() {
    return items;
  }

  @Override
  public void subscribe(Observer observer) {
    observers.add(observer);
  }

  @Override
  public void dispose(Observer observer) {
    observers.remove(observer);
  }

  @Override
  public void onChanged() {
    for (Observer observer : observers) {
      observer.update();
    }
  }

  @Override
  public void onResultChanged() {
    for (Observer observer : observers) {
      observer.updateResult();
    }
  }
}