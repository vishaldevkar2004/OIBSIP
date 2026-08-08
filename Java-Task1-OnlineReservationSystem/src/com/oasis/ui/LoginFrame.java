package com.oasis.ui;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.oasis.dao.LoginDAO;

public class LoginFrame extends JFrame {

    private JLabel lblTitle;
    private JLabel lblUsername;
    private JLabel lblPassword;

    private JTextField txtUsername;
    private JPasswordField txtPassword;

    private JButton btnLogin;
    private JButton btnClear;

    public LoginFrame() {

        setTitle("Online Reservation System");
        setSize(500, 350);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Title
        lblTitle = new JLabel("ONLINE RESERVATION SYSTEM");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setBounds(65, 20, 350, 30);
        add(lblTitle);

        // Username
        lblUsername = new JLabel("Username");
        lblUsername.setBounds(70, 90, 100, 25);
        add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(180, 90, 180, 25);
        add(txtUsername);

        // Password
        lblPassword = new JLabel("Password");
        lblPassword.setBounds(70, 140, 100, 25);
        add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(180, 140, 180, 25);
        add(txtPassword);

        // Login Button
        btnLogin = new JButton("Login");
        btnLogin.setBounds(110, 220, 100, 35);
        add(btnLogin);

        // Clear Button
        btnClear = new JButton("Clear");
        btnClear.setBounds(260, 220, 100, 35);
        add(btnClear);

        // Login Button Action
        btnLogin.addActionListener(e -> login());

        // Clear Button Action
        btnClear.addActionListener(e -> {
            txtUsername.setText("");
            txtPassword.setText("");
            txtUsername.requestFocus();
        });

        setVisible(true);
    }

    private void login() {

        String username = txtUsername.getText().trim();
        String password = String.valueOf(txtPassword.getPassword());

        // Validation
        if (username.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Username and Password are required!",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE);

            return;
        }

        LoginDAO dao = new LoginDAO();

        if (dao.validateUser(username, password)) {

        	JOptionPane.showMessageDialog(this, "Login Successful!");

        	new ReservationFrame();

        	dispose();

            // Reservation Screen 

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Username or Password!",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

}