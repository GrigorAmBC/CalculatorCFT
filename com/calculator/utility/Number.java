package com.calculator.utility;

import com.calculator.abstractions.CalculatorItem;

public class Number extends CalculatorItem<Long> {
  private Long number;

  public Number(Long number) {
    this.number = number;
  }

  @Override
  public Long getItem() {
    return number;
  }

  @Override
  public String getType() {
    return CalculatorItem.NUMBER;
  }

  @Override
  public String toString() {
    return String.valueOf(number);
  }
}