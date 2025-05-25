//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import model.User;
import util.MyJDBC;

public class EditBlogUI extends JFrame {
    private final int blogId;
    private final User user;
    private JTextField titleField;
    private JTextArea contentArea;

    public EditBlogUI(User user, int blogId) {
        this.user = user;
        this.blogId = blogId;
        this.setTitle("Edit Blog Post");
        this.setSize(500, 400);
        this.setLocationRelativeTo((Component)null);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(2);
        JPanel formPanel = new JPanel(new BorderLayout(10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.titleField = new JTextField();
        this.contentArea = new JTextArea(10, 30);
        this.contentArea.setLineWrap(true);
        this.contentArea.setWrapStyleWord(true);
        formPanel.add(new JLabel("Title:"), "North");
        formPanel.add(this.titleField, "Center");
        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        centerPanel.add(new JLabel("Content:"), "North");
        centerPanel.add(new JScrollPane(this.contentArea), "Center");
        formPanel.add(centerPanel, "South");
        this.add(formPanel, "Center");
        JButton saveButton = new JButton("\ud83d\udcbe Save Changes");
        saveButton.addActionListener((e) -> this.updateBlog());
        this.add(saveButton, "South");
        this.loadBlog();
    }

    private void loadBlog() {
        try (Connection conn = MyJDBC.getConnection()) {
            String sql = "SELECT title, content FROM blogs WHERE blog_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, this.blogId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                this.titleField.setText(rs.getString("title"));
                this.contentArea.setText(rs.getString("content"));
            } else {
                JOptionPane.showMessageDialog(this, "Blog not found.");
                this.dispose();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load blog: " + e.getMessage());
        }

    }

    private void updateBlog() {
        String newTitle = this.titleField.getText().trim();
        String newContent = this.contentArea.getText().trim();
        if (!newTitle.isEmpty() && !newContent.isEmpty()) {
            try (Connection conn = MyJDBC.getConnection()) {
                String sql = "UPDATE blogs SET title = ?, content = ? WHERE blog_id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, newTitle);
                stmt.setString(2, newContent);
                stmt.setInt(3, this.blogId);
                if (stmt.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(this, "Blog updated successfully.");
                    this.dispose();
                    (new ProfilePageUI(this.user)).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update blog.");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }

        } else {
            JOptionPane.showMessageDialog(this, "Title and content cannot be empty.");
        }
    }
}
