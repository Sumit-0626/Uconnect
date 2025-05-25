//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ui;

import dao.UserDAO;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import model.User;

public class RegisterUI extends JFrame {
    private JTextField usernameField;
    private JTextField emailField;
    private JTextField fullNameField;
    private JTextField universityField;
    private JTextField courseField;
    private JTextField semesterField;
    private JPasswordField passwordField;

    public RegisterUI() {
        this.setTitle("Uconnect - Register");
        this.setSize(400, 500);
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo((Component)null);
        this.setLayout(new GridLayout(9, 2, 10, 10));
        this.add(new JLabel("Username:"));
        this.usernameField = new JTextField();
        this.add(this.usernameField);
        this.add(new JLabel("Email:"));
        this.emailField = new JTextField();
        this.add(this.emailField);
        this.add(new JLabel("Password:"));
        this.passwordField = new JPasswordField();
        this.add(this.passwordField);
        this.add(new JLabel("Full Name:"));
        this.fullNameField = new JTextField();
        this.add(this.fullNameField);
        this.add(new JLabel("University:"));
        this.universityField = new JTextField();
        this.add(this.universityField);
        this.add(new JLabel("Course:"));
        this.courseField = new JTextField();
        this.add(this.courseField);
        this.add(new JLabel("Semester (number):"));
        this.semesterField = new JTextField();
        this.add(this.semesterField);
        JButton registerBtn = new JButton("Register");
        registerBtn.addActionListener((e) -> this.handleRegister());
        this.add(registerBtn);
        JButton loginBtn = new JButton("Back to Login");
        loginBtn.addActionListener((e) -> {
            this.dispose();
            (new LoginUI()).setVisible(true);
        });
        this.add(loginBtn);
    }

    private void handleRegister() {
        String username = this.usernameField.getText().trim();
        String email = this.emailField.getText().trim();
        String password = (new String(this.passwordField.getPassword())).trim();
        String fullName = this.fullNameField.getText().trim();
        String university = this.universityField.getText().trim();
        String course = this.courseField.getText().trim();
        String semesterText = this.semesterField.getText().trim();
        if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !fullName.isEmpty() && !university.isEmpty() && !course.isEmpty() && !semesterText.isEmpty()) {
            int semester;
            try {
                semester = Integer.parseInt(semesterText);
            } catch (NumberFormatException var12) {
                JOptionPane.showMessageDialog(this, "Semester must be a number!", "Error", 0);
                return;
            }

            UserDAO dao = new UserDAO();
            if (dao.userExists(username, email)) {
                JOptionPane.showMessageDialog(this, "Username or Email already exists!", "Error", 0);
            } else {
                User user = new User(username, email, password, fullName);
                user.setUniversity(university);
                user.setCourse(course);
                user.setSemester(semester);
                user.setProfilePicPath("assets/default.png");
                boolean success = dao.registerUser(user);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Registration successful!");
                    this.dispose();
                    (new LoginUI()).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Registration failed!", "Error", 0);
                }

            }
        } else {
            JOptionPane.showMessageDialog(this, "Please complete all fields!", "Error", 0);
        }
    }
}
