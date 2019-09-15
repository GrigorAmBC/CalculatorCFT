package com.calculator.ui;

import com.calculator.abstractions.Computer;
import com.calculator.abstractions.Observer;
import com.calculator.utility.*;
import com.calculator.abstractions.CalculatorItem;
import com.calculator.utility.Number;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;

public class CalculatorGUI implements Observer {
  private JButton oneButton;
  private JButton twoButton;
  private JButton threeButton;
  private JButton fourButton;
  private JButton fiveButton;
  private JButton sixButton;
  private JButton sevenButton;
  private JButton eightButton;
  private JButton nineButton;
  private JButton zeroButton;
  private JButton addButton;
  private JButton subtractButton;
  private JButton multiplyButton;
  private JButton resultButton;
  private JButton divideButton;
  private JButton dropButton;
  private JPanel panelMain;
  private JTextPane resultTextPane;
  private JButton rightBracketButton;
  private JButton leftBracketButton;
  private JTextPane inputTextPane;
  private JButton unaryMinusButton;

  private UserInput userInput = new UserInput();
  private Computer computer = new CalculatorComputer();

  public CalculatorGUI() {
    userInput.subscribe(this);

    // numbers
    ActionListener listener = e -> {
      checkErrorState();
      String a = inputTextPane.getText();
      String b = ((JButton) e.getSource()).getText();
      if (a.length() + 1 >= 12)
        return;

      if (a.startsWith("0"))
        inputTextPane.setText(b);
      else
        inputTextPane.setText(a + b);
    };

    oneButton.addActionListener(listener);
    twoButton.addActionListener(listener);
    threeButton.addActionListener(listener);
    fourButton.addActionListener(listener);
    fiveButton.addActionListener(listener);
    sixButton.addActionListener(listener);
    sevenButton.addActionListener(listener);
    eightButton.addActionListener(listener);
    nineButton.addActionListener(listener);
    zeroButton.addActionListener(listener);

    //operations
    ActionListener listener1 = e -> {
      checkErrorState();
      String a = ((JButton) e.getSource()).getText();
      String b = inputTextPane.getText();

      CalculatorItem last = userInput.getLastElement();

      if (last != null && last.getItem().toString().equals(")")) {
        userInput.append(new Operator(a.charAt(0)));
      } else {
        userInput.append(new Number(Long.valueOf(b)));
        userInput.append(new Operator(a.charAt(0)));
      }

      inputTextPane.setText("0");
    };
    addButton.addActionListener(listener1);
    subtractButton.addActionListener(listener1);
    multiplyButton.addActionListener(listener1);
    divideButton.addActionListener(listener1);

    // support symbols
    ActionListener listener2 = e -> {
      checkErrorState();
      String a = ((JButton) e.getSource()).getText();
      String b = inputTextPane.getText();

      CalculatorItem last = userInput.getLastElement();

      if (last != null && (last.getType().equals(CalculatorItem.OPERATOR) ||
              last.toString().equals("(")) &&
              a.equals(")")) {
        userInput.append(new Number(Long.valueOf(b)));
        userInput.append(new Bracket(a));
      } else
        userInput.append(new Bracket(a));


      inputTextPane.setText("0");
    };
    rightBracketButton.addActionListener(listener2);
    leftBracketButton.addActionListener(listener2);

    // reset
    dropButton.addActionListener(e -> {
      checkErrorState();
      userInput.setup();
      inputTextPane.setText("0");
    });

    // result
    resultButton.addActionListener(e -> {
      checkErrorState();
      String a = resultTextPane.getText();
      String b = inputTextPane.getText();

      if (a.isEmpty())
        return;

      CalculatorItem last = userInput.getLastElement();

      if (last != null && last.getType().equals(CalculatorItem.OPERATOR))
        userInput.append(new Number(Long.valueOf(b)));

      userInput.setResult(computer.compute(userInput.getItems()));
    });
    // unary minus
    unaryMinusButton.addActionListener(e -> {
      checkErrorState();
      long a = Long.valueOf(inputTextPane.getText());
      a *= -1;
      inputTextPane.setText(String.valueOf(a));
    });
  }

  private void setup() {
    userInput.setup();
    inputTextPane.setText("0");
  }

  private void checkErrorState() {
    if (userInput.isErrorStateActive())
      setup();
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("Calculator");
    frame.setContentPane(new CalculatorGUI().panelMain);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }


  @Override
  public void update() {
    String a = "";
    List<CalculatorItem> items = userInput.getItems();
    for (CalculatorItem item : items) {
      if (item.getType().equals(CalculatorItem.NUMBER)) {
        if (item.toString().startsWith("-"))
          a += "(" + item + ")";
        else
          a += item;
      } else
        a += item;

    }
    resultTextPane.setText(a);
  }

  public void updateResult() {
    String a = userInput.getResult().toString();
    resultTextPane.setText("");
    inputTextPane.setText(a);
  }
}
