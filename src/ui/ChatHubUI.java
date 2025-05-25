//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ui;

import dao.FriendDAO;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import model.User;

public class ChatHubUI extends JFrame {
    private final User currentUser;

    public ChatHubUI(User currentUser) {
        this.currentUser = currentUser;
        this.setTitle("Chat Hub");
        this.setDefaultCloseOperation(2);
        this.setSize(400, 600);
        this.setLocationRelativeTo((Component)null);
        JLabel titleLabel = new JLabel("Friends List", 0);
        titleLabel.setFont(new Font("Arial", 1, 18));
        this.add(titleLabel, "North");
        JPanel friendsPanel = new JPanel();
        friendsPanel.setLayout(new BoxLayout(friendsPanel, 1));

        try {
            FriendDAO dao = new FriendDAO();
            List<User> friends = dao.getFriends(currentUser.getUserId());
            if (friends.isEmpty()) {
                friendsPanel.add(new JLabel("You have no friends yet."));
            } else {
                for(User friend : friends) {
                    JPanel row = new JPanel(new BorderLayout());
                    JLabel nameLabel = new JLabel(friend.getFullName());
                    row.add(nameLabel, "Center");
                    JButton chatBtn = new JButton("Chat");
                    chatBtn.addActionListener((ex) -> (new ChatUI(currentUser, friend)).setVisible(true));
                    row.add(chatBtn, "East");
                    friendsPanel.add(row);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(friendsPanel);
        this.add(scrollPane, "Center");
    }
}
