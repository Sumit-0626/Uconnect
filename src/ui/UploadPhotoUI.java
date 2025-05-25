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

public class UploadPhotoUI extends JFrame {
    private JTextField captionField;
    private JLabel imagePreview;
    private String imagePath;
    private final User user;

    public UploadPhotoUI(User user) {
        this.user = user;
        this.setTitle("Upload Photo Post - Uconnect");
        this.setSize(500, 400);
        this.setDefaultCloseOperation(2);
        this.setLocationRelativeTo((Component)null);
        this.setLayout(new BorderLayout(10, 10));
        JPanel topPanel = new JPanel(new FlowLayout(0));
        JButton backBtn = new JButton("⬅ Back");
        backBtn.addActionListener((e) -> {
            this.dispose();
            (new LandingPageUI(user)).setVisible(true);
        });
        topPanel.add(backBtn);
        this.add(topPanel, "North");
        JPanel formPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        this.captionField = new JTextField();
        formPanel.add(new JLabel("Caption:"));
        formPanel.add(this.captionField);
        this.imagePreview = new JLabel("No image selected", 0);
        this.imagePreview.setPreferredSize(new Dimension(200, 150));
        formPanel.add(this.imagePreview);
        JButton browseBtn = new JButton("Choose Image");
        browseBtn.addActionListener((e) -> this.chooseImage());
        formPanel.add(browseBtn);
        JButton uploadBtn = new JButton("Upload");
        uploadBtn.addActionListener((e) -> this.uploadPhoto());
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(uploadBtn);
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

    private void uploadPhoto() {
        String caption = this.captionField.getText().trim();
        if (!caption.isEmpty() && this.imagePath != null) {
            Post post = new Post();
            post.setUserId(this.user.getUserId());
            post.setCaption(caption);
            post.setImagePath(this.imagePath);
            post.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
            boolean success = false;

            try {
                PostDAO dao = new PostDAO();
                success = dao.uploadPost(post);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", 0);
                e.printStackTrace();
                return;
            }

            if (success) {
                JOptionPane.showMessageDialog(this, "Photo uploaded successfully!");
                this.dispose();
                (new LandingPageUI(this.user)).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Upload failed!");
            }

        } else {
            JOptionPane.showMessageDialog(this, "Caption and image are required.");
        }
    }
}
