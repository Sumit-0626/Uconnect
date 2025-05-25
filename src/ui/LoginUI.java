//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ui;

import dao.UserDAO;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import model.User;

public class LoginUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginUI() {
        this.setTitle("Uconnect - Login");
        this.setDefaultCloseOperation(3);
        this.setSize(400, 300);
        this.setLocationRelativeTo((Component)null);
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = 2;
        JLabel title = new JLabel("Welcome to Uconnect");
        title.setFont(new Font("SansSerif", 1, 18));
        title.setHorizontalAlignment(0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(title, gbc);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Username:"), gbc);
        this.usernameField = new JTextField();
        gbc.gridx = 1;
        mainPanel.add(this.usernameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Password:"), gbc);
        this.passwordField = new JPasswordField();
        gbc.gridx = 1;
        mainPanel.add(this.passwordField, gbc);
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(this::handleLogin);
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(loginButton, gbc);
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener((e) -> {
            this.dispose();
            (new RegisterUI()).setVisible(true);
        });
        gbc.gridx = 1;
        mainPanel.add(registerButton, gbc);
        this.add(mainPanel);
    }

    private void handleLogin(ActionEvent e) {
        String username = this.usernameField.getText().trim();
        String password = (new String(this.passwordField.getPassword())).trim();
        if (!username.isEmpty() && !password.isEmpty()) {
            UserDAO dao = new UserDAO();

            try {
                User user = dao.loginUser(username, password);
                if (user != null) {
                    JOptionPane.showMessageDialog(this, "Login successful. Welcome, " + user.getFullName() + "!");
                    this.dispose();
                    (new LandingPageUI(user)).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error", 0);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", 0);
            }

        } else {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.", "Warning", 2);
        }
    }
}
