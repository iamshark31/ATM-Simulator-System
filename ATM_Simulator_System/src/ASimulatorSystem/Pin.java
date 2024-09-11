package ASimulatorSystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Pin extends JFrame implements ActionListener {
    
    JPasswordField t1, t2;
    JButton b1, b2;                               
    JLabel l1, l2, l3;
    String pin;
    
    Pin(String pin) {
        this.pin = pin;
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("ASimulatorSystem/icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1000, 1180, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l4 = new JLabel(i3);
        l4.setBounds(0, 0, 960, 1080);
        setLayout(null);

        l1 = new JLabel("CHANGE YOUR PIN");
        l1.setFont(new Font("System", Font.BOLD, 16));
        l1.setForeground(Color.WHITE);
        
        l2 = new JLabel("New PIN:");
        l2.setFont(new Font("System", Font.BOLD, 16));
        l2.setForeground(Color.WHITE);
        
        l3 = new JLabel("Re-Enter New PIN:");
        l3.setFont(new Font("System", Font.BOLD, 16));
        l3.setForeground(Color.WHITE);
        
        t1 = new JPasswordField();
        t1.setFont(new Font("Raleway", Font.BOLD, 25));
        
        t2 = new JPasswordField();
        t2.setFont(new Font("Raleway", Font.BOLD, 25));
        
        b1 = new JButton("CHANGE");
        b2 = new JButton("BACK");
        
        b1.addActionListener(this);
        b2.addActionListener(this);
        
        l1.setBounds(280, 330, 800, 35);
        l4.add(l1);
        
        l2.setBounds(180, 390, 150, 35);
        l4.add(l2);
        
        l3.setBounds(180, 440, 200, 35);
        l4.add(l3);
        
        t1.setBounds(350, 390, 180, 25);
        l4.add(t1);
        
        t2.setBounds(350, 440, 180, 25);
        l4.add(t2);
        
        b1.setBounds(390, 588, 150, 35);
        l4.add(b1);
        
        b2.setBounds(390, 633, 150, 35);
        l4.add(b2);
        
        add(l4);
        setSize(960, 1080);
        setLocation(500, 0);
        setUndecorated(true);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        try {        
            String npin = new String(t1.getPassword());
            String rpin = new String(t2.getPassword());
            
            if (npin.isEmpty() || rpin.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter and re-enter the new PIN");
                return;
            }
            
            if (!npin.equals(rpin)) {
                JOptionPane.showMessageDialog(null, "Entered PIN does not match");
                return;
            }
            
            if (ae.getSource() == b1) {
                Conn c1 = new Conn();
                
                String q1 = "UPDATE bank SET pin = ? WHERE pin = ?";
                String q2 = "UPDATE login SET pin = ? WHERE pin = ?";
                String q3 = "UPDATE signup3 SET pin = ? WHERE pin = ?";

                try (PreparedStatement pst1 = c1.c.prepareStatement(q1);
                     PreparedStatement pst2 = c1.c.prepareStatement(q2);
                     PreparedStatement pst3 = c1.c.prepareStatement(q3)) {
                     
                    pst1.setString(1, rpin);
                    pst1.setString(2, pin);
                    pst1.executeUpdate();
                    
                    pst2.setString(1, rpin);
                    pst2.setString(2, pin);
                    pst2.executeUpdate();
                    
                    pst3.setString(1, rpin);
                    pst3.setString(2, pin);
                    pst3.executeUpdate();

                    JOptionPane.showMessageDialog(null, "PIN changed successfully");
                    setVisible(false);
                    new Transactions(rpin).setVisible(true);
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
                }
                
            } else if (ae.getSource() == b2) {
                new Transactions(pin).setVisible(true);
                setVisible(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred. Please try again.");
        }
    }

    public static void main(String[] args) {
        new Pin("defaultPIN").setVisible(true);
    }
}
