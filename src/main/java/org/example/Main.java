package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(null);

        JLabel label1 = new JLabel("Введите V0:");
        label1.setBounds(50, 30, 100, 30);
        frame.add(label1);

        JTextField textField1 = new JTextField();
        textField1.setBounds(150, 30, 200, 30);
        frame.add(textField1);

        JLabel label2 = new JLabel("Введите R:");
        label2.setBounds(50, 70, 100, 30);
        frame.add(label2);

        JTextField textField2 = new JTextField();
        textField2.setBounds(150, 70, 200, 30);
        frame.add(textField2);

        JLabel label3 = new JLabel("Введите a:");
        label3.setBounds(50, 110, 100, 30);
        frame.add(label3);

        JTextField textField3 = new JTextField();
        textField3.setBounds(150, 110, 200, 30);
        frame.add(textField3);

        JButton buttonCalculate = new JButton("Вычислить");
        buttonCalculate.setBounds(150, 150, 200, 30);
        frame.add(buttonCalculate);

        JLabel labelResult = new JLabel("");
        labelResult.setBounds(50, 200, 300, 30);
        frame.add(labelResult);

        // Создаем экземпляр сервиса бизнес-логики
        CalculatorService service = new CalculatorService();

        buttonCalculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double first, second, third, result;

                try {
                    first = Double.parseDouble(textField1.getText());
                    second = Double.parseDouble(textField2.getText());
                    third = Double.parseDouble(textField3.getText());
                } catch (NumberFormatException ex) {
                    boolean isLimitReached = service.incrementErrorsAndCheckLimit();
                    if (isLimitReached) {
                        labelResult.setText("Две неудачные попытки. Пожалуйста, проверьте ввод.");
                    } else {
                        labelResult.setText("Введите корректные числа.");
                    }
                    return;
                }

                service.resetErrorCounter();

                try {
                    result = service.calculate(first, second, third);
                    labelResult.setText("Результат: " + String.format("%.2f", result));
                } catch (Exception ex) {
                    labelResult.setText("Ошибка вычислений: " + ex.getMessage());
                }
            }
        });

        frame.setVisible(true);
    }
}