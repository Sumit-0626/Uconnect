//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ui;

import dao.FriendDAO;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import model.User;

public class FriendRequestsUI extends JFrame {
    private final User currentUser;
    private final FriendDAO dao;
    private final JPanel requestsPanel;

    public FriendRequestsUI(User currentUser) {
        this.currentUser = currentUser;
        this.dao = new FriendDAO();
        this.setTitle("Friend Requests");
        this.setSize(400, 500);
        this.setLocationRelativeTo((Component)null);
        this.setDefaultCloseOperation(2);
        this.setLayout(new BorderLayout());
        this.requestsPanel = new JPanel();
        this.requestsPanel.setLayout(new BoxLayout(this.requestsPanel, 1));
        this.add(new JScrollPane(this.requestsPanel), "Center");
        this.loadRequests();
    }

    private void loadRequests() {
        this.requestsPanel.removeAll();

        for(User requester : this.dao.getPendingRequests(this.currentUser.getUserId())) {
            JPanel row = new JPanel(new FlowLayout(0));
            String var10003 = requester.getFullName();
            row.add(new JLabel(var10003 + " (@" + requester.getUsername() + ")"));
            JButton acceptBtn = new JButton("Accept");
            acceptBtn.addActionListener((e) -> {
                boolean success = this.dao.respondToRequest(requester.getUserId(), this.currentUser.getUserId(), true);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Friend request accepted.");
                    this.loadRequests();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to accept request.");
                }

            });
            JButton rejectBtn = new JButton("Reject");
            rejectBtn.addActionListener((e) -> {
                boolean success = this.dao.respondToRequest(requester.getUserId(), this.currentUser.getUserId(), false);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Friend request rejected.");
                    this.loadRequests();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to reject request.");
                }

            });
            row.add(acceptBtn);
            row.add(rejectBtn);
            this.requestsPanel.add(row);
        }

        this.requestsPanel.revalidate();
        this.requestsPanel.repaint();
    }
}
