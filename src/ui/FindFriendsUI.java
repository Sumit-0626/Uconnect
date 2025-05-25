//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ui;

import dao.FriendDAO;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import model.User;

public class FindFriendsUI extends JFrame {
    private final User currentUser;
    private final JPanel resultPanel;
    private final JTextField searchField;

    public FindFriendsUI(User currentUser) {
        this.currentUser = currentUser;
        this.setTitle("Find Friends");
        this.setSize(400, 500);
        this.setLocationRelativeTo((Component)null);
        this.setLayout(new BorderLayout());
        JPanel searchPanel = new JPanel(new BorderLayout());
        this.searchField = new JTextField();
        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener((e) -> this.showResults(this.searchField.getText().trim()));
        searchPanel.add(new JLabel("Search by name or username:"), "North");
        searchPanel.add(this.searchField, "Center");
        searchPanel.add(searchBtn, "East");
        this.resultPanel = new JPanel();
        this.resultPanel.setLayout(new BoxLayout(this.resultPanel, 1));
        this.add(searchPanel, "North");
        this.add(new JScrollPane(this.resultPanel), "Center");
        this.setDefaultCloseOperation(2);
    }

    private void showResults(String keyword) {
        this.resultPanel.removeAll();
        FriendDAO friendDAO = new FriendDAO();

        for(User u : friendDAO.searchUsers(keyword, this.currentUser.getUserId())) {
            JPanel userPanel = new JPanel(new BorderLayout());
            String var10002 = u.getFullName();
            JLabel nameLabel = new JLabel(var10002 + " (" + u.getEmail() + ")");
            JButton requestBtn = new JButton();
            String status = friendDAO.getFriendshipStatus(this.currentUser.getUserId(), u.getUserId());
            if (status.equals("pending")) {
                requestBtn.setText("Request pending");
                requestBtn.setEnabled(false);
            } else if (status.equals("accepted")) {
                requestBtn.setText("Already friends");
                requestBtn.setEnabled(false);
            } else {
                requestBtn.setText("Send Friend Request");
                requestBtn.addActionListener((e) -> {
                    boolean sent = friendDAO.sendRequest(this.currentUser.getUserId(), u.getUserId());
                    if (sent) {
                        JOptionPane.showMessageDialog(this, "Friend request sent.");
                        requestBtn.setText("Request pending");
                        requestBtn.setEnabled(false);
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to send friend request.");
                    }

                });
            }

            userPanel.add(nameLabel, "Center");
            userPanel.add(requestBtn, "East");
            this.resultPanel.add(userPanel);
        }

        this.resultPanel.revalidate();
        this.resultPanel.repaint();
    }
}
