package ASimulatorSystem;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

class BalanceEnquiry extends JFrame implements ActionListener {

    JLabel l1;
    JButton b1;
    String pin;

    BalanceEnquiry(String pin) {
        this.pin = pin;

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("ASimulatorSystem/icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1000, 1180, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel backgroundLabel = new JLabel(i3);
        backgroundLabel.setBounds(0, 0, 960, 1080);
        setLayout(null);
        add(backgroundLabel);

        l1 = new JLabel();
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));

        b1 = new JButton("BACK");
        b1.addActionListener(this);

        l1.setBounds(190, 350, 400, 35);
        backgroundLabel.add(l1);

        b1.setBounds(390, 633, 150, 35);
        backgroundLabel.add(b1);

        setSize(960, 1080);
        setUndecorated(true);
        setLocation(500, 0);
        setVisible(true);

        int balance = 0;
        try {
            Conn c1 = new Conn();
            String query = "SELECT * FROM bank WHERE pin = ?";
            try (PreparedStatement pst = c1.con.prepareStatement(query)) {
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
        } catch (SQLException e) {
            e.printStackTrace(); // Print stack trace for debugging
            JOptionPane.showMessageDialog(this, "Error retrieving balance: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        l1.setText("Your Current Account Balance is Rs " + balance);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
        new Transactions(pin).setVisible(true);
    }

    public static void main(String[] args) {
        new BalanceEnquiry("defaultPIN").setVisible(true); // Replace with an actual PIN if needed
    }
}
