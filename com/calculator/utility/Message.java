package com.calculator.utility;

import com.calculator.abstractions.CalculatorItem;

public class Message extends CalculatorItem {
  private String message;

  public Message(String message) {
    this.message = message;
  }

  @Override
  public Object getItem() {
    return message;
  }

  @Override
  public String getType() {
    return CalculatorItem.MESSAGE;
  }

  @Override
  public String toString() {
    return message;
  }
}