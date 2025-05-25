//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ui;

import dao.BlogDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import model.Blog;
import model.User;

public class UploadBlogUI extends JFrame {
    private JTextField titleField;
    private JTextArea contentArea;
    private JButton uploadButton;

    public UploadBlogUI(User user) {
        this.setTitle("Upload Blog - Uconnect");
        this.setSize(600, 500);
        this.setLocationRelativeTo((Component)null);
        this.setDefaultCloseOperation(2);
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        JPanel titlePanel = new JPanel(new BorderLayout(5, 5));
        JLabel titleLabel = new JLabel("Title:");
        this.titleField = new JTextField();
        titlePanel.add(titleLabel, "North");
        titlePanel.add(this.titleField, "Center");
        JPanel contentPanel = new JPanel(new BorderLayout(5, 5));
        JLabel contentLabel = new JLabel("Content:");
        this.contentArea = new JTextArea();
        this.contentArea.setLineWrap(true);
        this.contentArea.setWrapStyleWord(true);
        JScrollPane contentScrollPane = new JScrollPane(this.contentArea);
        contentPanel.add(contentLabel, "North");
        contentPanel.add(contentScrollPane, "Center");
        this.uploadButton = new JButton("Upload Blog");
        this.uploadButton.setBackground(new Color(30, 144, 255));
        this.uploadButton.setForeground(Color.WHITE);
        this.uploadButton.setFocusPainted(false);
        this.uploadButton.setFont(new Font("SansSerif", 1, 14));
        this.uploadButton.addActionListener((e) -> this.handleUpload(user));
        panel.add(titlePanel, "North");
        panel.add(contentPanel, "Center");
        panel.add(this.uploadButton, "South");
        this.add(panel);
    }

    private void handleUpload(User user) {
        String title = this.titleField.getText().trim();
        String content = this.contentArea.getText().trim();
        if (!title.isEmpty() && !content.isEmpty()) {
            Blog blog = new Blog();
            blog.setUserId(user.getUserId());
            blog.setTitle(title);
            blog.setContent(content);
            BlogDAO dao = new BlogDAO();
            if (dao.uploadBlog(blog)) {
                JOptionPane.showMessageDialog(this, "Blog uploaded successfully!");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to upload blog.", "Error", 0);
            }

        } else {
            JOptionPane.showMessageDialog(this, "Please enter title and content.");
        }
    }
}
