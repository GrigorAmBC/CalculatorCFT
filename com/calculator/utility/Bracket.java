package com.calculator.utility;

import com.calculator.abstractions.CalculatorItem;

public class Bracket extends CalculatorItem<String> {
  private String bracket;

  public Bracket(String bracket) {
    this.bracket = bracket;
  }

  @Override
  public String getItem() {
    return bracket;
  }

  @Override
  public String getType() {
    return CalculatorItem.BRACKET;
  }

  @Override
  public String toString() {
    return bracket;
  }
}