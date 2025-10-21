import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    private String currentOperator;
    private double firstNumber;
    private boolean startNewNumber = true;

    public Calculator() {
        // Frame settings
        setTitle("Calculator");
        setSize(350, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        // Display
        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.BOLD, 30));
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setBackground(Color.WHITE);
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(display, BorderLayout.NORTH);

        // Buttons
        String[] buttons = {
                "C", "±", "%", "/",
                "7", "8", "9", "*",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "0", ".", "="
        };

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setBackground(new Color(230, 230, 250)); // soft lavender background

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 22));
            button.setForeground(Color.BLACK);
            button.setBackground(new Color(245, 245, 245)); // soft button color
            button.setFocusPainted(false);
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.matches("[0-9]") || command.equals(".")) {
            if (startNewNumber) {
                display.setText(command.equals(".") ? "0." : command);
                startNewNumber = false;
            } else {
                if (command.equals(".") && display.getText().contains(".")) return;
                display.setText(display.getText() + command);
            }
        } else if (command.equals("C")) {
            display.setText("0");
            currentOperator = null;
            firstNumber = 0;
            startNewNumber = true;
        } else if (command.equals("±")) {
            display.setText(String.valueOf(-Double.parseDouble(display.getText())));
        } else if (command.equals("%")) {
            display.setText(String.valueOf(Double.parseDouble(display.getText()) / 100));
        } else if (command.equals("=")) {
            calculate(Double.parseDouble(display.getText()));
            currentOperator = null;
        } else { // operators
            if (currentOperator != null) {
                calculate(Double.parseDouble(display.getText()));
            } else {
                firstNumber = Double.parseDouble(display.getText());
            }
            currentOperator = command;
            startNewNumber = true;
        }
    }

    private void calculate(double secondNumber) {
        double result = 0;
        switch (currentOperator) {
            case "+": result = firstNumber + secondNumber; break;
            case "-": result = firstNumber - secondNumber; break;
            case "*": result = firstNumber * secondNumber; break;
            case "/": result = secondNumber != 0 ? firstNumber / secondNumber : 0; break;
        }
        display.setText(String.valueOf(result));
        firstNumber = result;
        startNewNumber = true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Calculator::new);
    }
}