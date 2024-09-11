package ASimulatorSystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
    JLabel welcomeLabel, cardNoLabel, pinLabel;
    JTextField tf1;
    JPasswordField pf2;
    JButton signInButton, clearButton, signUpButton;

    Login() {
        setTitle("AUTOMATED TELLER MACHINE");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("ASimulatorSystem/icons/logo.jpg"));
        Image i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel logoLabel = new JLabel(i3);
        logoLabel.setBounds(70, 10, 100, 100);
        add(logoLabel);

        welcomeLabel = new JLabel("WELCOME TO ATM");
        welcomeLabel.setFont(new Font("Osward", Font.BOLD, 38));
        welcomeLabel.setBounds(200, 40, 450, 40);
        add(welcomeLabel);

        cardNoLabel = new JLabel("Card No:");
        cardNoLabel.setFont(new Font("Raleway", Font.BOLD, 28));
        cardNoLabel.setBounds(125, 150, 375, 30);
        add(cardNoLabel);

        tf1 = new JTextField(15);
        tf1.setBounds(300, 150, 230, 30);
        tf1.setFont(new Font("Arial", Font.BOLD, 14));
        add(tf1);

        pinLabel = new JLabel("PIN:");
        pinLabel.setFont(new Font("Raleway", Font.BOLD, 28));
        pinLabel.setBounds(125, 220, 375, 30);
        add(pinLabel);

        pf2 = new JPasswordField(15);
        pf2.setFont(new Font("Arial", Font.BOLD, 14));
        pf2.setBounds(300, 220, 230, 30);
        add(pf2);

        signInButton = new JButton("SIGN IN");
        signInButton.setBackground(Color.BLACK);
        signInButton.setForeground(Color.WHITE);

        clearButton = new JButton("CLEAR");
        clearButton.setBackground(Color.BLACK);
        clearButton.setForeground(Color.WHITE);

        signUpButton = new JButton("SIGN UP");
        signUpButton.setBackground(Color.BLACK);
        signUpButton.setForeground(Color.WHITE);

        setLayout(null);

        signInButton.setFont(new Font("Arial", Font.BOLD, 14));
        signInButton.setBounds(300, 300, 100, 30);
        add(signInButton);

        clearButton.setFont(new Font("Arial", Font.BOLD, 14));
        clearButton.setBounds(430, 300, 100, 30);
        add(clearButton);

        signUpButton.setFont(new Font("Arial", Font.BOLD, 14));
        signUpButton.setBounds(300, 350, 230, 30);
        add(signUpButton);

        signInButton.addActionListener(this);
        clearButton.addActionListener(this);
        signUpButton.addActionListener(this);

        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Close on exit

        setSize(800, 480);
        setLocation(550, 200);
        setVisible(true);}

    public void actionPerformed(ActionEvent ae) {
        try {
            if (ae.getSource() == signInButton) {
                Conn c1 = new Conn();  // This class should contain the Connection object
                String cardno = tf1.getText();
                String pin = new String(pf2.getPassword());
    
                String q = "select * from login where cardno = ? and pin = ?";
    
                // Use the connection object (c1.c) to create a PreparedStatement
                PreparedStatement ps = c1.c.prepareStatement(q);
                ps.setString(1, cardno);
                ps.setString(2, pin);
    
                ResultSet rs = ps.executeQuery();
    
                if (rs.next()) {
                    setVisible(false);
                    new Transactions(pin).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect Card Number or PIN");
                }
            } else if (ae.getSource() == clearButton) {
                tf1.setText("");
                pf2.setText("");
            } else if (ae.getSource() == signUpButton) {
                setVisible(false);
                new Signup().setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }}
    

    public static void main(String[] args) {
        new Login().setVisible(true);
    }}