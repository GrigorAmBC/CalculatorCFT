package com.calculator.utility;

import com.calculator.abstractions.CalculatorItem;

public class Operator extends CalculatorItem<Character> {
  private Character operator;
  private int coef;

  int getCoefficient() {
    return coef;
  }

  public Operator(Character operator, int coef) {
    this.operator = operator;
    this.coef = coef;
  }

  public Operator(Character operator) {
    this.operator = operator;
    this.coef = 0;
  }

  @Override
  public Character getItem() {
    return operator;
  }

  @Override
  public String getType() {
    return CalculatorItem.OPERATOR;
  }

  @Override
  public String toString() {
    return String.valueOf(operator);
  }
}