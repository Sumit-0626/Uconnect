//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ui;

import dao.MessageDAO;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Timestamp;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import model.Message;
import model.User;

public class ChatUI extends JFrame {
    private final User currentUser;
    private final User friend;
    private JTextArea chatArea;
    private JTextField messageField;
    private Timer refreshTimer;

    public ChatUI(User currentUser, User friend) {
        this.currentUser = currentUser;
        this.friend = friend;
        this.setTitle("Chat with " + friend.getFullName());
        this.setSize(400, 500);
        this.setDefaultCloseOperation(2);
        this.setLocationRelativeTo((Component)null);
        this.setLayout(new BorderLayout(10, 10));
        this.chatArea = new JTextArea();
        this.chatArea.setEditable(false);
        this.chatArea.setLineWrap(true);
        this.add(new JScrollPane(this.chatArea), "Center");
        this.messageField = new JTextField();
        JButton sendButton = new JButton("Send");
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(this.messageField, "Center");
        inputPanel.add(sendButton, "East");
        JButton backButton = new JButton("⬅ Back");
        backButton.addActionListener((e) -> {
            this.dispose();
            (new ChatHubUI(currentUser)).setVisible(true);
        });
        JPanel topPanel = new JPanel(new FlowLayout(0));
        topPanel.add(backButton);
        this.add(topPanel, "North");
        this.add(inputPanel, "South");
        sendButton.addActionListener((e) -> this.sendMessage());
        this.messageField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    ChatUI.this.sendMessage();
                }

            }
        });
        this.refreshTimer = new Timer();
        this.refreshTimer.schedule(new TimerTask() {
            public void run() {
                ChatUI.this.loadMessages();
            }
        }, 0L, 1500L);
    }

    private void sendMessage() {
        String content = this.messageField.getText().trim();
        if (!content.isEmpty()) {
            MessageDAO dao = new MessageDAO();
            Message msg = new Message();
            msg.setSenderId(this.currentUser.getUserId());
            msg.setReceiverId(this.friend.getUserId());
            msg.setContent(content);
            msg.setTimestamp(new Timestamp(System.currentTimeMillis()));
            msg.setRead(false);
            boolean sent = dao.sendMessage(msg);
            if (sent) {
                this.messageField.setText("");
                this.loadMessages();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to send message.");
            }

        }
    }

    private void loadMessages() {
        MessageDAO dao = new MessageDAO();
        List<Message> messages = dao.getMessages(this.currentUser.getUserId(), this.friend.getUserId());
        StringBuilder sb = new StringBuilder();

        for(Message msg : messages) {
            String sender = msg.getSenderId() == this.currentUser.getUserId() ? "Me" : this.friend.getFullName();
            sb.append("[").append(sender).append(" - ").append(msg.getTimestamp()).append("]:\n");
            sb.append(msg.getContent()).append("\n\n");
        }

        this.chatArea.setText(sb.toString());
        this.chatArea.setCaretPosition(this.chatArea.getDocument().getLength());
        dao.markMessagesAsRead(this.friend.getUserId(), this.currentUser.getUserId());
    }
}
