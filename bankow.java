package banko;

import javax.swing.*;             // the code begins with importing packages for Swing SGUI components and event handling
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 

public class bankow extends JFrame { // this extends JFrame it represents the main window of the GUI application 
    private JTextField pinField; // instance variables declared a text field for entering PIN and a login button 
    private JButton loginButton;

    private int balance = 1000; // Initial Balance only for demonstration 

    public bankow() {
        setTitle("Banking Management System"); // Initializes the JFrame by setting its title, default close operation and location on the screen 
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // sets the default close operation for the JFrame 
        setLocationRelativeTo(null); // set the location of the JFrame 

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10)); // creates a JPanel with a border layout and an empty border 
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 

       
        ImageIcon logoIcon = new ImageIcon("5.png"); // logo directory . creates the logo for the GUI 
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel pinLabel = new JLabel("Enter PIN:");
        pinField = new JTextField(); // used to create a login form where the user can enter the PIN and click the login button  
        loginButton = new JButton("Login");

        loginButton.addActionListener(new LoginButtonListener()); // Action listener for the LoginButton 

        panel.add(logoLabel, BorderLayout.NORTH); 
        panel.add(pinLabel, BorderLayout.WEST); // to automatically position the specified regions 
        panel.add(pinField, BorderLayout.CENTER);
        panel.add(loginButton, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    private class LoginButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String pin = pinField.getText();
            if (pin.equals("1234")) { // Pin set 
                dispose(); 
                showTransactionGUI();
            } else {
                JOptionPane.showMessageDialog(bankow.this, "Invalid PIN!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showTransactionGUI() {
        SwingUtilities.invokeLater(new Runnable() { // 
            public void run() {
                new TransactionGUI(balance);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new bankow();
            }
        });
    }
}

class TransactionGUI extends JFrame {
    private JButton withdrawButton;
    private JButton depositButton;
    private JButton balanceButton;  // buttons for the transaction GUI 
    private JButton inquireButton;

    private int balance;

    public TransactionGUI(int initialBalance) {
        this.balance = initialBalance;

        setTitle("Transaction");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        withdrawButton = new JButton("Withdraw");
        depositButton = new JButton("Deposit");
        balanceButton = new JButton("Check Balance");  
        inquireButton = new JButton("Inquire Balance");

        withdrawButton.addActionListener(new WithdrawButtonListener());
        depositButton.addActionListener(new DepositButtonListener()); // action listeners for the buttons 
        balanceButton.addActionListener(new BalanceButtonListener());
        inquireButton.addActionListener(new InquireButtonListener());

        panel.add(withdrawButton);
        panel.add(depositButton);  // adds button to the panel 
        panel.add(balanceButton); 
        panel.add(inquireButton);

        add(panel);
        setVisible(true);
    }

    private class WithdrawButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String amountString = JOptionPane.showInputDialog(TransactionGUI.this, "Enter withdrawal amount:");
            if (amountString != null && !amountString.isEmpty()) {
                int amount = Integer.parseInt(amountString); 
                if (amount <= balance) {
                    balance -= amount;
                    showReceipt("Withdrawal successful!\nAmount: $" + amount);
                } else {
                    JOptionPane.showMessageDialog(TransactionGUI.this, "Insufficient funds for withdrawal!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        private void showReceipt(String receiptText) {
          
            ReceiptGUI receiptGUI = new ReceiptGUI(receiptText);
            receiptGUI.setVisible(true);
        }
    }

    private class DepositButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String amountString = JOptionPane.showInputDialog(TransactionGUI.this, "Enter deposit amount:");
            if (amountString != null && !amountString.isEmpty()) { 
                int amount = Integer.parseInt(amountString);
                balance += amount;
                showReceipt("Deposit successful!\nAmount: $" + amount);
            } else {
                JOptionPane.showMessageDialog(TransactionGUI.this, "Invalid deposit amount!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void showReceipt(String receiptText) {
            
            ReceiptGUI receiptGUI = new ReceiptGUI(receiptText);
            receiptGUI.setVisible(true);
        }
    }

    private class BalanceButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            showReceipt("Current balance: $" + balance);
        }

        private void showReceipt(String receiptText) {
           
            ReceiptGUI receiptGUI = new ReceiptGUI(receiptText);
            receiptGUI.setVisible(true);
        }
    }

    private class InquireButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            JOptionPane.showMessageDialog(TransactionGUI.this, "Inquire Balance Button Clicked", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void showReceipt(String receiptText) {
    
        ReceiptGUI receiptGUI = new ReceiptGUI(receiptText);
        receiptGUI.setVisible(true);
    }
}

class ReceiptGUI extends JFrame {
    public ReceiptGUI(String receiptText) {
        setTitle("Receipt");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JTextArea receiptArea = new JTextArea(receiptText);
        receiptArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(receiptArea);

        add(scrollPane);
        setVisible(true);
    }
}

