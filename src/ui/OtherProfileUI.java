//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ui;

import dao.PostDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import model.Post;
import model.User;

public class OtherProfileUI extends JFrame {
    private final User currentUser;
    private final User otherUser;
    private final PostDAO postDAO;

    public OtherProfileUI(User currentUser, User otherUser) {
        this.currentUser = currentUser;
        this.otherUser = otherUser;
        this.postDAO = new PostDAO();
        this.setTitle("Uconnect - Profile of " + otherUser.getFullName());
        this.setSize(600, 700);
        this.setDefaultCloseOperation(2);
        this.setLocationRelativeTo((Component)null);
        this.setLayout(new BorderLayout(10, 10));
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, 1));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        mainPanel.setBackground(Color.DARK_GRAY);
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, 1));
        infoPanel.setBackground(Color.DARK_GRAY);
        infoPanel.setAlignmentX(0.0F);
        if (otherUser.getProfilePicPath() != null && (new File(otherUser.getProfilePicPath())).exists()) {
            ImageIcon icon = new ImageIcon(otherUser.getProfilePicPath());
            Image scaled = icon.getImage().getScaledInstance(120, 120, 4);
            JLabel imageLabel = new JLabel(new ImageIcon(scaled));
            infoPanel.add(imageLabel);
            infoPanel.add(Box.createVerticalStrut(10));
        }

        infoPanel.add(new JLabel("Name: " + otherUser.getFullName()));
        infoPanel.add(new JLabel("Username: " + otherUser.getUsername()));
        infoPanel.add(new JLabel("Email: " + otherUser.getEmail()));
        infoPanel.add(new JLabel("University: " + otherUser.getUniversity()));
        infoPanel.add(new JLabel("Course: " + otherUser.getCourse()));
        infoPanel.add(new JLabel("Semester: " + otherUser.getSemester()));
        mainPanel.add(infoPanel);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(new JSeparator());
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(new JLabel("User's Posts:"));

        try {
            for(Post post : this.postDAO.getPostsByUser(otherUser.getUserId())) {
                JPanel postPanel = new JPanel();
                postPanel.setLayout(new BoxLayout(postPanel, 1));
                postPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                postPanel.setBackground(Color.WHITE);
                postPanel.setAlignmentX(0.0F);
                postPanel.setMaximumSize(new Dimension(500, Integer.MAX_VALUE));
                String type = post.getType() != null ? post.getType().toUpperCase() : "UNKNOWN";
                postPanel.add(new JLabel("Type: " + type));
                if ("BLOG".equalsIgnoreCase(type)) {
                    postPanel.add(new JLabel("\ud83d\udcdd Title: " + post.getTitle()));
                    JTextArea contentArea = new JTextArea(post.getCaption());
                    contentArea.setLineWrap(true);
                    contentArea.setWrapStyleWord(true);
                    contentArea.setEditable(false);
                    contentArea.setBackground(Color.WHITE);
                    contentArea.setBorder((Border)null);
                    postPanel.add(contentArea);
                } else if ("PHOTO".equalsIgnoreCase(type)) {
                    String var10003 = post.getCaption() != null ? post.getCaption() : "(No caption)";
                    postPanel.add(new JLabel("\ud83d\udcf8 " + var10003));
                    if (post.getImagePath() != null && (new File(post.getImagePath())).exists()) {
                        ImageIcon icon = new ImageIcon(post.getImagePath());
                        Image scaled = icon.getImage().getScaledInstance(400, 300, 4);
                        JLabel imageLabel = new JLabel(new ImageIcon(scaled));
                        postPanel.add(imageLabel);
                    } else {
                        postPanel.add(new JLabel("\ud83d\udcdb Image not found"));
                    }
                }

                String var21 = this.formatTimestamp(post.getTimestamp());
                postPanel.add(new JLabel("⏱ " + var21));
                postPanel.add(Box.createVerticalStrut(10));
                mainPanel.add(postPanel);
                mainPanel.add(Box.createVerticalStrut(15));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading posts: " + e.getMessage());
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        this.add(scrollPane, "Center");
        JButton backBtn = new JButton("← Back");
        backBtn.addActionListener((ex) -> this.dispose());
        JPanel bottomPanel = new JPanel(new FlowLayout(0));
        bottomPanel.add(backBtn);
        this.add(bottomPanel, "South");
    }

    private String formatTimestamp(Timestamp timestamp) {
        return timestamp == null ? "Unknown time" : (new SimpleDateFormat("MMM dd, yyyy hh:mm a")).format(timestamp);
    }
}
