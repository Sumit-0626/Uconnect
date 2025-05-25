//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import model.User;

public class LandingPageUI extends JFrame {
    public LandingPageUI(User user) {
        this.setTitle("Uconnect - Dashboard");
        this.setSize(500, 400);
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo((Component)null);
        this.setLayout(new GridLayout(4, 2, 10, 10));
        JLabel welcomeLabel = new JLabel("Welcome, " + user.getFullName(), 0);
        welcomeLabel.setFont(new Font("Arial", 1, 16));
        this.add(welcomeLabel);
        JButton profileBtn = new JButton("View Profile");
        profileBtn.addActionListener((e) -> (new ProfilePageUI(user)).setVisible(true));
        this.add(profileBtn);
        JButton timelineBtn = new JButton("View Timeline");
        timelineBtn.addActionListener((e) -> (new TimelineUI(user)).setVisible(true));
        this.add(timelineBtn);
        JButton uploadPhotoBtn = new JButton("Upload Photo");
        uploadPhotoBtn.addActionListener((e) -> (new UploadPhotoUI(user)).setVisible(true));
        this.add(uploadPhotoBtn);
        JButton uploadBlogBtn = new JButton("Upload Blog");
        uploadBlogBtn.addActionListener((e) -> (new UploadBlogUI(user)).setVisible(true));
        this.add(uploadBlogBtn);
        JButton findFriendsBtn = new JButton("Find Friends");
        findFriendsBtn.addActionListener((e) -> (new FindFriendsUI(user)).setVisible(true));
        this.add(findFriendsBtn);
        JButton friendRequestsBtn = new JButton("Friend Requests");
        friendRequestsBtn.addActionListener((e) -> (new FriendRequestsUI(user)).setVisible(true));
        this.add(friendRequestsBtn);
        JButton chatHubBtn = new JButton("Chat with Friends");
        chatHubBtn.addActionListener((e) -> (new ChatHubUI(user)).setVisible(true));
        this.add(chatHubBtn);
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener((e) -> {
            this.dispose();
            (new LoginUI()).setVisible(true);
        });
        this.add(logoutBtn);
    }
}
