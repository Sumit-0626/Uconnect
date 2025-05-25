//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ui;

import dao.PostDAO;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.Post;
import model.User;

public class EditPhotoUI extends JFrame {
    private final JTextField captionField;
    private final JLabel imagePreview;
    private String imagePath;
    private final User user;
    private final Post post;

    public EditPhotoUI(User user, Post post) {
        this.user = user;
        this.post = post;
        this.setTitle("Edit Photo Post - Uconnect");
        this.setSize(500, 400);
        this.setDefaultCloseOperation(2);
        this.setLocationRelativeTo((Component)null);
        this.setLayout(new BorderLayout(10, 10));
        JPanel topPanel = new JPanel(new FlowLayout(0));
        JButton backBtn = new JButton("⬅ Back");
        backBtn.addActionListener((e) -> {
            this.dispose();
            (new ProfilePageUI(user)).setVisible(true);
        });
        topPanel.add(backBtn);
        this.add(topPanel, "North");
        JPanel formPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        this.captionField = new JTextField(post.getCaption());
        formPanel.add(new JLabel("Caption:"));
        formPanel.add(this.captionField);
        this.imagePreview = new JLabel("No image selected", 0);
        this.imagePreview.setPreferredSize(new Dimension(200, 150));
        this.imagePath = post.getImagePath();
        if (this.imagePath != null && (new File(this.imagePath)).exists()) {
            ImageIcon icon = new ImageIcon(this.imagePath);
            Image scaled = icon.getImage().getScaledInstance(300, 200, 4);
            this.imagePreview.setIcon(new ImageIcon(scaled));
            this.imagePreview.setText((String)null);
        }

        formPanel.add(this.imagePreview);
        JButton chooseImageBtn = new JButton("Change Image");
        chooseImageBtn.addActionListener((e) -> this.chooseImage());
        formPanel.add(chooseImageBtn);
        JButton saveBtn = new JButton("Save Changes");
        saveBtn.addActionListener((e) -> this.saveChanges());
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(saveBtn);
        this.add(formPanel, "Center");
        this.add(bottomPanel, "South");
    }

    private void chooseImage() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if (result == 0) {
            File file = chooser.getSelectedFile();
            this.imagePath = file.getAbsolutePath();
            ImageIcon icon = new ImageIcon(this.imagePath);
            Image scaled = icon.getImage().getScaledInstance(300, 200, 4);
            this.imagePreview.setIcon(new ImageIcon(scaled));
            this.imagePreview.setText((String)null);
        }

    }

    private void saveChanges() {
        this.post.setCaption(this.captionField.getText().trim());
        this.post.setImagePath(this.imagePath);
        this.post.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));

        try {
            PostDAO dao = new PostDAO();
            boolean success = dao.updatePost(this.post);
            if (success) {
                JOptionPane.showMessageDialog(this, "Post updated successfully.");
                this.dispose();
                (new ProfilePageUI(this.user)).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update post.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating post: " + e.getMessage());
        }

    }
}
