package ASimulatorSystem;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.*;

public class FastCash extends JFrame implements ActionListener {

    JLabel l1;
    JButton b1, b2, b3, b4, b5, b6, b7;
    String pin;

    FastCash(String pin) {
        this.pin = pin;
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("ASimulatorSystem/icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1000, 1180, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel backgroundLabel = new JLabel(i3);
        backgroundLabel.setBounds(0, 0, 960, 1080);
        setLayout(null);
        add(backgroundLabel);

        l1 = new JLabel("SELECT WITHDRAWAL AMOUNT");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));

        b1 = new JButton("Rs 100");
        b2 = new JButton("Rs 500");
        b3 = new JButton("Rs 1000");
        b4 = new JButton("Rs 2000");
        b5 = new JButton("Rs 5000");
        b6 = new JButton("Rs 10000");
        b7 = new JButton("BACK");

        l1.setBounds(235, 400, 700, 35);
        backgroundLabel.add(l1);

        b1.setBounds(170, 499, 150, 35);
        backgroundLabel.add(b1);

        b2.setBounds(390, 499, 150, 35);
        backgroundLabel.add(b2);

        b3.setBounds(170, 543, 150, 35);
        backgroundLabel.add(b3);

        b4.setBounds(390, 543, 150, 35);
        backgroundLabel.add(b4);

        b5.setBounds(170, 588, 150, 35);
        backgroundLabel.add(b5);

        b6.setBounds(390, 588, 150, 35);
        backgroundLabel.add(b6);

        b7.setBounds(390, 633, 150, 35);
        backgroundLabel.add(b7);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        b7.addActionListener(this);

        setSize(960, 1080);
        setLocation(500, 0);
        setUndecorated(true);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            String amount = ((JButton) ae.getSource()).getText().substring(3);
            int withdrawalAmount = Integer.parseInt(amount);
            Conn c = new Conn();
            int balance = 0;
            String query = "SELECT * FROM bank WHERE pin = ?";
            try (PreparedStatement pst = c.c.prepareStatement(query)) {
                pst.setString(1, pin);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    if ("Deposit".equals(rs.getString("mode"))) {
                        balance += Integer.parseInt(rs.getString("amount"));
                    } else {
                        balance -= Integer.parseInt(rs.getString("amount"));
                    }
                }
            }

            if (ae.getSource() == b7) {
                this.setVisible(false);
                new Transactions(pin).setVisible(true);
                return;
            }

            if (balance < withdrawalAmount) {
                JOptionPane.showMessageDialog(null, "Insufficient Balance");
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = sdf.format(new java.util.Date());
            String insertQuery = "INSERT INTO bank (pin, date, mode, amount) VALUES (?, ?, 'Withdrawal', ?)";
            try (PreparedStatement pst = c.c.prepareStatement(insertQuery)) {
                pst.setString(1, pin);
                pst.setString(2, date);
                pst.setInt(3, withdrawalAmount);
                pst.executeUpdate();
            }

            JOptionPane.showMessageDialog(null, "Rs. " + amount + " Debited Successfully");
            setVisible(false);
            new Transactions(pin).setVisible(true);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Invalid amount format", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new FastCash("defaultPIN").setVisible(true); // Replace with actual PIN if needed
    }
}
