//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ui;

import dao.PostDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import model.Post;
import model.User;

public class ProfilePageUI extends JFrame {
    private final User user;

    public ProfilePageUI(User user) {
        this.user = user;
        this.setTitle("Uconnect - Profile Page");
        this.setSize(700, 800);
        this.setLocationRelativeTo((Component)null);
        this.setDefaultCloseOperation(2);
        this.setLayout(new BorderLayout());
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, 1));
        profilePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        if (user.getProfilePicPath() != null && !user.getProfilePicPath().isEmpty()) {
            File imageFile = new File(user.getProfilePicPath());
            if (imageFile.exists()) {
                ImageIcon profileIcon = new ImageIcon(imageFile.getAbsolutePath());
                Image scaledImage = profileIcon.getImage().getScaledInstance(100, 100, 4);
                JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
                profilePanel.add(imageLabel);
            } else {
                profilePanel.add(new JLabel("Profile picture not found"));
            }
        }

        profilePanel.add(new JLabel("Name: " + user.getFullName()));
        profilePanel.add(new JLabel("Username: " + user.getUsername()));
        profilePanel.add(new JLabel("Email: " + user.getEmail()));
        profilePanel.add(new JLabel("University: " + user.getUniversity()));
        profilePanel.add(new JLabel("Course: " + user.getCourse()));
        profilePanel.add(new JLabel("Semester: " + user.getSemester()));
        this.add(profilePanel, "North");
        JPanel postsPanel = new JPanel();
        postsPanel.setLayout(new BoxLayout(postsPanel, 1));
        postsPanel.setBorder(BorderFactory.createTitledBorder("Your Posts"));

        try {
            PostDAO dao = new PostDAO();

            for(Post post : dao.getPostsByUser(user.getUserId())) {
                JPanel postCard = new JPanel();
                postCard.setLayout(new BoxLayout(postCard, 1));
                postCard.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), BorderFactory.createLineBorder(Color.GRAY)));
                postCard.setBackground(Color.WHITE);
                String type = post.getType() != null ? post.getType().toUpperCase() : "UNKNOWN";
                JLabel typeLabel = new JLabel("Type: " + type);
                typeLabel.setFont(new Font("Arial", 1, 14));
                postCard.add(typeLabel);
                if ("BLOG".equalsIgnoreCase(post.getType())) {
                    postCard.add(new JLabel("Title: " + post.getTitle()));
                    JTextArea contentArea = new JTextArea(post.getCaption());
                    contentArea.setLineWrap(true);
                    contentArea.setWrapStyleWord(true);
                    contentArea.setEditable(false);
                    postCard.add(contentArea);
                } else if ("PHOTO".equalsIgnoreCase(post.getType())) {
                    postCard.add(new JLabel("Caption: " + post.getCaption()));
                    if (post.getImagePath() != null && !post.getImagePath().isEmpty()) {
                        File photoFile = new File(post.getImagePath());
                        if (photoFile.exists()) {
                            ImageIcon photoIcon = new ImageIcon(photoFile.getAbsolutePath());
                            Image scaledPhoto = photoIcon.getImage().getScaledInstance(400, 300, 4);
                            postCard.add(new JLabel(new ImageIcon(scaledPhoto)));
                        } else {
                            postCard.add(new JLabel("(Photo not found)"));
                        }
                    }
                }

                postsPanel.add(Box.createVerticalStrut(10));
                postsPanel.add(postCard);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading posts: " + e.getMessage(), "Error", 0);
        }

        JScrollPane scrollPane = new JScrollPane(postsPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        this.add(scrollPane, "Center");
    }
}
