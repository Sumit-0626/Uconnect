//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ui;

import dao.CommentDAO;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import model.Comment;
import model.User;

public class CommentUI extends JFrame {
    private final User user;
    private final int postId;
    private final String postType;
    private CommentDAO commentDAO;
    private DefaultListModel<String> commentListModel;
    private JList<String> commentList;
    private JTextArea commentTextArea;

    public CommentUI(User user, int postId, String postType) {
        this.user = user;
        this.postId = postId;
        this.postType = postType;

        try {
            this.commentDAO = new CommentDAO();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database connection error: " + e.getMessage());
            return;
        }

        this.setTitle("Comments - " + postType);
        this.setSize(500, 500);
        this.setLocationRelativeTo((Component)null);
        this.setDefaultCloseOperation(2);
        this.setLayout(new BorderLayout(10, 10));
        JPanel topPanel = new JPanel(new FlowLayout(0));
        JButton backButton = new JButton("⬅ Back");
        backButton.addActionListener((ex) -> this.dispose());
        topPanel.add(backButton);
        this.add(topPanel, "North");
        this.commentListModel = new DefaultListModel();
        this.commentList = new JList(this.commentListModel);
        this.add(new JScrollPane(this.commentList), "Center");
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        this.commentTextArea = new JTextArea(3, 30);
        bottomPanel.add(new JScrollPane(this.commentTextArea), "Center");
        JButton postButton = new JButton("Post Comment");
        postButton.addActionListener(this::handlePostComment);
        bottomPanel.add(postButton, "East");
        this.add(bottomPanel, "South");
        this.loadComments();
    }

    private void loadComments() {
        try {
            List<Comment> comments = this.commentDAO.getComments(this.postId, this.postType);
            this.commentListModel.clear();

            for(Comment c : comments) {
                DefaultListModel var10000 = this.commentListModel;
                String var10001 = c.getFullName();
                var10000.addElement(var10001 + ": " + c.getCommentText());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load comments: " + e.getMessage());
        }

    }

    private void handlePostComment(ActionEvent e) {
        String comment = this.commentTextArea.getText().trim();
        if (comment.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a comment.");
        } else {
            try {
                boolean success = this.commentDAO.addComment(this.user.getUserId(), this.postId, this.postType, comment);
                if (success) {
                    this.commentTextArea.setText("");
                    this.loadComments();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to post comment.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error posting comment: " + ex.getMessage());
            }

        }
    }
}
