//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Message;
import util.MyJDBC;

public class MessageDAO {
    private Connection conn;

    public MessageDAO() {
        try {
            this.conn = MyJDBC.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean sendMessage(Message msg) {
        String sql = "INSERT INTO messages (sender_id, receiver_id, content, timestamp, is_read) VALUES (?, ?, ?, ?, ?)";

        try {
            boolean var4;
            try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
                stmt.setInt(1, msg.getSenderId());
                stmt.setInt(2, msg.getReceiverId());
                stmt.setString(3, msg.getContent());
                stmt.setTimestamp(4, msg.getTimestamp());
                stmt.setBoolean(5, msg.isRead());
                var4 = stmt.executeUpdate() > 0;
            }

            return var4;
        } catch (SQLException e) {
            System.err.println("Send message error: " + e.getMessage());
            return false;
        }
    }

    public List<Message> getMessages(int userId1, int userId2) {
        List<Message> messages = new ArrayList();
        String sql = "SELECT * FROM messages WHERE (sender_id = ? AND receiver_id = ?) OR (sender_id = ? AND receiver_id = ?) ORDER BY timestamp ASC";

        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setInt(1, userId1);
            stmt.setInt(2, userId2);
            stmt.setInt(3, userId2);
            stmt.setInt(4, userId1);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Message msg = new Message();
                msg.setMessageId(rs.getInt("message_id"));
                msg.setSenderId(rs.getInt("sender_id"));
                msg.setReceiverId(rs.getInt("receiver_id"));
                msg.setContent(rs.getString("content"));
                msg.setTimestamp(rs.getTimestamp("timestamp"));
                msg.setRead(rs.getBoolean("is_read"));
                messages.add(msg);
            }
        } catch (SQLException e) {
            System.err.println("Fetch messages error: " + e.getMessage());
        }

        return messages;
    }

    public void markMessagesAsRead(int senderId, int receiverId) {
        String sql = "UPDATE messages SET is_read = TRUE WHERE sender_id = ? AND receiver_id = ?";

        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setInt(1, senderId);
            stmt.setInt(2, receiverId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Mark as read error: " + e.getMessage());
        }

    }

    public int countUnreadMessages(int senderId, int receiverId) {
        String sql = "SELECT COUNT(*) FROM messages WHERE sender_id = ? AND receiver_id = ? AND is_read = FALSE";

        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setInt(1, senderId);
            stmt.setInt(2, receiverId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Count unread error: " + e.getMessage());
        }

        return 0;
    }
}
