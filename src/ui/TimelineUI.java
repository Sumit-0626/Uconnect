//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ui;

import dao.LikeDAO;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Image;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import model.User;
import util.MyJDBC;

public class TimelineUI extends JFrame {
    private final User user;

    public TimelineUI(User user) {
        this.user = user;
        this.setTitle("Uconnectv2 - Timeline Feed");
        this.setSize(800, 800);
        this.setLocationRelativeTo((Component)null);
        this.setDefaultCloseOperation(2);
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, 1));
        contentPanel.setBackground(Color.DARK_GRAY);
        contentPanel.setBorder(new EmptyBorder(10, 30, 10, 30));
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        this.add(scrollPane);

        try (Connection conn = MyJDBC.getConnection()) {
            String sql = "    SELECT 'blog' AS type, b.blog_id AS id, b.title, b.content, NULL AS caption, NULL AS image_path, b.timestamp, u.full_name\n    FROM blogs b\n    JOIN users u ON b.user_id = u.user_id\n    UNION ALL\n    SELECT 'photo', p.post_id, NULL, NULL, p.caption, p.image_path, p.timestamp, u.full_name\n    FROM posts p\n    JOIN users u ON p.user_id = u.user_id\n    ORDER BY timestamp DESC\n";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            LikeDAO likeDAO = new LikeDAO();

            while(rs.next()) {
                String type = rs.getString("type");
                int postId = rs.getInt("id");
                String fullName = rs.getString("full_name");
                String timestamp = this.formatTimestamp(rs.getTimestamp("timestamp"));
                JPanel postPanel = new JPanel();
                postPanel.setLayout(new BoxLayout(postPanel, 1));
                postPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                postPanel.setBackground(Color.WHITE);
                postPanel.setAlignmentX(0.5F);
                postPanel.add(new JLabel("\ud83d\udc64 " + fullName + (type.equals("blog") ? " posted a blog" : " uploaded a photo")));
                postPanel.add(new JLabel("\ud83d\udd52 " + timestamp));
                if (type.equals("blog")) {
                    postPanel.add(new JLabel("\ud83d\udcdd Title: " + rs.getString("title")));
                    JTextArea content = new JTextArea(rs.getString("content"));
                    content.setWrapStyleWord(true);
                    content.setLineWrap(true);
                    content.setEditable(false);
                    content.setBackground(Color.WHITE);
                    postPanel.add(content);
                } else {
                    String caption = rs.getString("caption");
                    postPanel.add(new JLabel("\ud83d\udcf8 " + (caption != null ? caption : "(No caption)")));
                    String imagePath = rs.getString("image_path");
                    if (imagePath != null && (new File(imagePath)).exists()) {
                        ImageIcon icon = new ImageIcon(imagePath);
                        Image scaled = icon.getImage().getScaledInstance(500, 300, 4);
                        postPanel.add(new JLabel(new ImageIcon(scaled)));
                    } else {
                        postPanel.add(new JLabel("(Image not found)"));
                    }
                }

                JPanel interactionPanel = new JPanel(new FlowLayout(0));
                interactionPanel.setBackground(Color.WHITE);
                JButton likeBtn = new JButton("❤️ Like");
                int likeCount = likeDAO.countLikes(postId, type);
                JLabel likeLabel = new JLabel("Likes: " + likeCount);
                if (likeDAO.hasLiked(user.getUserId(), postId, type)) {
                    likeBtn.setText("\ud83d\udc94 Liked");
                }

                likeBtn.addActionListener((ex) -> {
                    if (!likeDAO.hasLiked(user.getUserId(), postId, type)) {
                        likeDAO.likePost(user.getUserId(), postId, type);
                        likeBtn.setText("\ud83d\udc94 Liked");
                        int var10001 = likeDAO.countLikes(postId, type);
                        likeLabel.setText("Likes: " + var10001);
                    }

                });
                JButton commentBtn = new JButton("\ud83d\udcac Comments");
                commentBtn.addActionListener((ex) -> (new CommentUI(user, postId, type)).setVisible(true));
                interactionPanel.add(likeBtn);
                interactionPanel.add(likeLabel);
                interactionPanel.add(commentBtn);
                postPanel.add(Box.createVerticalStrut(10));
                postPanel.add(interactionPanel);
                postPanel.add(Box.createVerticalStrut(20));
                contentPanel.add(postPanel);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load timeline: " + e.getMessage());
        }

    }

    private String formatTimestamp(Timestamp ts) {
        return ts == null ? "Unknown time" : (new SimpleDateFormat("MMM d, yyyy hh:mm a")).format(ts);
    }
}
