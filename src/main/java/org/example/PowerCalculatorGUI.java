package org.example;

import javax.accessibility.Accessible;
import javax.accessibility.AccessibleContext;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * PowerCalculatorGUI - User interface for the power calculator.
 *
 * This class provides a modern, accessible graphical user interface for the power calculator
 * with proper UI design principles and full accessibility support.
 *
 * @author Your Name
 * @version 1.0.0
 */
public class PowerCalculatorGUI extends JFrame implements Accessible {
    private static final long serialVersionUID = 1L;

    // Colour scheme
    private static final Color PRIMARY_COLOR = new Color(52, 152, 219);
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color ERROR_COLOR   = new Color(231, 76, 60);
    private static final Color WARNING_COLOR = new Color(243, 156, 18);
    private static final Color BACKGROUND_COLOR = new Color(248, 249, 250);

    // GUI components
    private JTextField baseField;
    private JTextField exponentField;
    private JLabel     resultLabel;
    private JTextArea  errorArea;
    private JButton    calculateButton;
    private JButton    clearButton;
    private JPanel     mainPanel;

    public PowerCalculatorGUI() {
        initializeFrame();
        setupAccessibility();
        createLayout();
        setupEventHandlers();
        applyStyling();
        pack();              // size window to preferred sizes of components
        centerWindow();
    }

    // ------------------------------------------------------------
    // Frame & accessibility helpers
    // ------------------------------------------------------------
    private void initializeFrame() {
        setTitle("Power Calculator - X^Y");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());         // a single root layout – mainPanel goes CENTER
        setMinimumSize(new Dimension(500, 400));
    }

    private void setupAccessibility() {
        AccessibleContext ctx = getAccessibleContext();
        ctx.setAccessibleName("Power Calculator Window");
        ctx.setAccessibleDescription("Calculator for x raised to the power of y (x^y)");
    }


    // ------------------------------------------------------------
    // Layout
    // ------------------------------------------------------------
    private void createLayout() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(BACKGROUND_COLOR);

        // Header
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel);
        mainPanel.add(Box.createVerticalStrut(15));

        // Input
        JPanel inputPanel = createInputPanel();
        mainPanel.add(inputPanel);
        mainPanel.add(Box.createVerticalStrut(15));

        // Results
        JPanel resultPanel = createResultPanel();
        mainPanel.add(resultPanel);

        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel title    = new JLabel("Power Calculator", SwingConstants.CENTER);
        title.setFont(new Font("SanSerif", Font.BOLD, 24));
        title.setForeground(PRIMARY_COLOR);

        JLabel subtitle = new JLabel("Calculate x raised to the power of y (x^y)", SwingConstants.CENTER);
        subtitle.setFont(new Font("SanSerif", Font.PLAIN, 14));
        subtitle.setForeground(Color.GRAY);

        header.add(title, BorderLayout.NORTH);
        header.add(subtitle, BorderLayout.SOUTH);
        return header;
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                "Input Values",
                0, 0,
                new Font("SanSerif", Font.BOLD, 14),
                PRIMARY_COLOR));
        panel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridy  = 0;

        // Base label & field
        JLabel baseLabel = new JLabel("Base (x):");
        baseLabel.setFont(new Font("SanSerif", Font.BOLD, 14));
        baseField = new JTextField(12);
        baseField.setFont(new Font("SanSerif", Font.PLAIN, 14));
        baseLabel.setLabelFor(baseField);

        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(baseLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(baseField, gbc);

        // Exponent label & field – next row
        JLabel expLabel = new JLabel("Exponent (y):");
        expLabel.setFont(new Font("SanSerif", Font.BOLD, 14));
        exponentField = new JTextField(12);
        exponentField.setFont(new Font("SanSerif", Font.PLAIN, 14));
        expLabel.setLabelFor(exponentField);

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(expLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(exponentField, gbc);

        // Buttons (row 2 spanning two columns)
        createButtons();
        JPanel buttonRow = new JPanel();
        buttonRow.setOpaque(false);
        buttonRow.add(calculateButton);
        buttonRow.add(Box.createHorizontalStrut(10));
        buttonRow.add(clearButton);

        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(buttonRow, gbc);

        // Accessibility descriptions
        setupTextFieldAccessibility(baseField, "Base input field", "Enter the base number for the power calculation");
        setupTextFieldAccessibility(exponentField, "Exponent input field", "Enter the exponent for the power calculation");

        return panel;
    }

    private JPanel createResultPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(SUCCESS_COLOR, 2),
                "Results",
                0, 0,
                new Font("SanSerif", Font.BOLD, 14),
                SUCCESS_COLOR));
        panel.setOpaque(false);

        resultLabel = new JLabel("Enter values and click Calculate", SwingConstants.CENTER);
        resultLabel.setFont(new Font("SanSerif", Font.BOLD, 16));
        resultLabel.setForeground(Color.GRAY);
        resultLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        errorArea = new JTextArea(3, 40);
        errorArea.setLineWrap(true);
        errorArea.setWrapStyleWord(true);
        errorArea.setEditable(false);
        errorArea.setFont(new Font("SanSerif", Font.PLAIN, 12));
        errorArea.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        errorArea.setBackground(new Color(250, 250, 250));
        JScrollPane scroll = new JScrollPane(errorArea);
        scroll.setPreferredSize(new Dimension(450, 80));

        panel.add(resultLabel, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        setupLabelAccessibility(resultLabel, "Result display", "Displays the calculated result");
        setupTextAreaAccessibility(errorArea, "Message area", "Displays error messages and helpful information");

        return panel;
    }

    private void createButtons() {
        calculateButton = new JButton("Calculate");
        clearButton     = new JButton("Clear");

        Dimension btnSize = new Dimension(120, 40);
        calculateButton.setPreferredSize(btnSize);
        clearButton.setPreferredSize(btnSize);

        calculateButton.setFont(new Font("SanSerif", Font.BOLD, 15));
        clearButton.setFont(new Font("SanSerif", Font.BOLD, 15));

        setupButtonAccessibility(calculateButton, "Calculate button", "Click to calculate x raised to the power of y");
        setupButtonAccessibility(clearButton,   "Clear button",     "Click to clear all inputs and results");
    }

    private void applyStyling() {
        getContentPane().setBackground(BACKGROUND_COLOR);

        calculateButton.setBackground(SUCCESS_COLOR);
        calculateButton.setForeground(Color.BLACK);
        calculateButton.setFocusPainted(false);
        clearButton.setBackground(PRIMARY_COLOR);
        clearButton.setForeground(Color.BLACK);
        clearButton.setFocusPainted(false);

        baseField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 2, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        exponentField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 2, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
    }



    /**
     * Sets up event handlers for better user experience.
     */
    private void setupEventHandlers() {
        // Calculate button action
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculatePower();
            }
        });

        // Clear button action
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearAll();
            }
        });

        // Enter key support for both fields
        KeyAdapter enterKeyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    calculatePower();
                }
            }
        };

        baseField.addKeyListener(enterKeyAdapter);
        exponentField.addKeyListener(enterKeyAdapter);

        // Focus listeners for better UX
        baseField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                baseField.selectAll();
            }
        });

        exponentField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                exponentField.selectAll();
            }
        });
    }


    /**
     * Sets up accessibility for text fields.
     *
     * @param textField the text field to configure
     * @param name the accessible name
     * @param description the accessible description
     */
    private void setupTextFieldAccessibility(JTextField textField, String name, String description) {
        textField.getAccessibleContext().setAccessibleName(name);
        textField.getAccessibleContext().setAccessibleDescription(description);
    }

    /**
     * Sets up accessibility for buttons.
     *
     * @param button the button to configure
     * @param name the accessible name
     * @param description the accessible description
     */
    private void setupButtonAccessibility(JButton button, String name, String description) {
        button.getAccessibleContext().setAccessibleName(name);
        button.getAccessibleContext().setAccessibleDescription(description);
    }

    /**
     * Sets up accessibility for labels.
     *
     * @param label the label to configure
     * @param name the accessible name
     * @param description the accessible description
     */
    private void setupLabelAccessibility(JLabel label, String name, String description) {
        label.getAccessibleContext().setAccessibleName(name);
        label.getAccessibleContext().setAccessibleDescription(description);
    }

    /**
     * Sets up accessibility for text areas.
     *
     * @param textArea the text area to configure
     * @param name the accessible name
     * @param description the accessible description
     */
    private void setupTextAreaAccessibility(JTextArea textArea, String name, String description) {
        textArea.getAccessibleContext().setAccessibleName(name);
        textArea.getAccessibleContext().setAccessibleDescription(description);
    }

    /**
     * Centers the window on the screen.
     */
    private void centerWindow() {
        setLocationRelativeTo(null);
    }

    /**
     * Clears all inputs and results.
     */
    private void clearAll() {
        baseField.setText("");
        exponentField.setText("");
        resultLabel.setText("Enter values and click Calculate");
        resultLabel.setForeground(Color.GRAY);
        errorArea.setText("");
        baseField.requestFocus();
    }

    /**
     * Calculates the power and updates the GUI with results.
     * This method demonstrates proper error handling and user feedback.
     */
    private void calculatePower() {
        errorArea.setText(""); // Clear previous errors
        resultLabel.setText("Calculating...");
        resultLabel.setForeground(WARNING_COLOR);

        double x;
        double y;

        try {
            String baseText = baseField.getText().trim();
            String exponentText = exponentField.getText().trim();

            if (baseText.isEmpty() || exponentText.isEmpty()) {
                showError("Please enter both base and exponent values.", "Missing Input");
                return;
            }

            x = Double.parseDouble(baseText);
            y = Double.parseDouble(exponentText);
        } catch (NumberFormatException ex) {
            showError("Please enter valid numerical values for both base and exponent.\n" +
                    "Examples: 2.5, -3, 0.5, 1e6", "Invalid Input Format");
            return;
        }

        try {
            double result = PowerCalculatorEngine.compute(x, y);

            if (Double.isNaN(result)) {
                handleNaNResult(x, y);
            } else if (Double.isInfinite(result)) {
                handleInfiniteResult(x, y, result);
            } else {
                showSuccessResult(result);
            }
        } catch (Exception ex) {
            showError("An unexpected error occurred during calculation.\n" +
                    "Please try again with different values.\n" +
                    "Error: " + ex.getMessage(), "Calculation Error");
        }
    }

    /**
     * Handles NaN results with meaningful messages.
     *
     * @param x the base value
     * @param y the exponent value
     */
    private void handleNaNResult(double x, double y) {
        String message;
        String title;

        if (x == 0 && y == 0) {
            message = "The expression 0^0 is mathematically undefined.\n" +
                    "This is because any number raised to the power of 0 equals 1,\n" +
                    "but 0 raised to any power equals 0, creating a contradiction.";
            title = "Mathematical Undefined";
        } else if (x == 0 && y < 0) {
            message = "Raising 0 to a negative power is undefined.\n" +
                    "This would require division by zero, which is not allowed in mathematics.\n" +
                    "Try using a positive exponent or a non-zero base.";
            title = "Division by Zero";
        } else if (x < 0 && (y % 1 != 0)) {
            message = "Negative base with fractional exponent results in complex numbers.\n" +
                    "This calculator only handles real numbers.\n" +
                    "Try using an integer exponent or a positive base.";
            title = "Complex Number Result";
        } else {
            message = "The result of " + x + "^" + y + " is not a real number.\n" +
                    "This may be due to mathematical constraints or overflow.";
            title = "Invalid Result";
        }

        showError(message, title);
    }

    /**
     * Handles infinite results with meaningful messages.
     *
     * @param x the base value
     * @param y the exponent value
     * @param result the infinite result
     */
    private void handleInfiniteResult(double x, double y, double result) {
        String message;
        String title;

        if (x == 0 && y < 0) {
            message = "Raising 0 to a negative power results in infinity.\n" +
                    "This represents division by zero.";
            title = "Infinite Result";
        } else if (result > 0) {
            message = "The result is too large to represent as a finite number.\n" +
                    "Result: Positive Infinity\n" +
                    "This occurs when the calculation exceeds the maximum representable value.";
            title = "Overflow - Positive Infinity";
        } else {
            message = "The result is too large to represent as a finite number.\n" +
                    "Result: Negative Infinity\n" +
                    "This occurs when the calculation exceeds the maximum representable value.";
            title = "Overflow - Negative Infinity";
        }

        showError(message, title);
    }

    /**
     * Shows a successful calculation result.
     *
     * @param result the calculated result
     */
    private void showSuccessResult(double result) {
        String formattedResult;
        if ((Math.abs(result) < 1e-8 && result != 0) || Math.abs(result) > 1e8) {
            formattedResult = String.format("%.8e", result);
        } else {
            formattedResult = String.format("%.8f", result);
        }

        resultLabel.setText("Result: " + formattedResult);
        resultLabel.setForeground(SUCCESS_COLOR);
        errorArea.setText("Calculation completed successfully!");
    }

    /**
     * Shows an error message with proper styling.
     *
     * @param message the error message
     * @param title the error title
     */
    private void showError(String message, String title) {
        resultLabel.setText("Error occurred");
        resultLabel.setForeground(ERROR_COLOR);
        errorArea.setText(title + ":\n" + message);
    }

    /**
     * Main method to launch the GUI application.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PowerCalculatorGUI().setVisible(true);
            }
        });
    }

    @Override
    public AccessibleContext getAccessibleContext() {
        return super.getAccessibleContext();
    }


}