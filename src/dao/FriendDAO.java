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
import model.User;
import util.MyJDBC;

public class FriendDAO {
    private final Connection conn;

    public FriendDAO() {
        try {
            this.conn = MyJDBC.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to DB", e);
        }
    }

    public boolean sendRequest(int senderId, int receiverId) {
        String sql = "INSERT INTO friendships (user_id, friend_id, status) VALUES (?, ?, 'pending')";

        try {
            boolean var5;
            try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
                stmt.setInt(1, senderId);
                stmt.setInt(2, receiverId);
                var5 = stmt.executeUpdate() > 0;
            }

            return var5;
        } catch (SQLException e) {
            System.err.println("Send request error: " + e.getMessage());
            return false;
        }
    }

    public boolean respondToRequest(int senderId, int receiverId, boolean accepted) {
        String sql = "UPDATE friendships SET status = ? WHERE user_id = ? AND friend_id = ?";

        try {
            boolean var6;
            try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
                stmt.setString(1, accepted ? "accepted" : "rejected");
                stmt.setInt(2, senderId);
                stmt.setInt(3, receiverId);
                var6 = stmt.executeUpdate() > 0;
            }

            return var6;
        } catch (SQLException e) {
            System.err.println("Respond to request error: " + e.getMessage());
            return false;
        }
    }

    public List<User> getFriends(int userId) {
        List<User> friends = new ArrayList();
        String sql = "    SELECT u.* FROM users u\n    JOIN friendships f ON (\n        (f.user_id = ? AND f.friend_id = u.user_id)\n        OR (f.friend_id = ? AND f.user_id = u.user_id)\n    )\n    WHERE f.status = 'accepted'\n";

        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, userId);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                User friend = new User();
                friend.setUserId(rs.getInt("user_id"));
                friend.setUsername(rs.getString("username"));
                friend.setEmail(rs.getString("email"));
                friend.setFullName(rs.getString("full_name"));
                friend.setUniversity(rs.getString("university"));
                friend.setCourse(rs.getString("course"));
                friend.setSemester(rs.getInt("semester"));
                friend.setProfilePic(rs.getString("profile_pic"));
                friends.add(friend);
            }
        } catch (SQLException e) {
            System.err.println("Get friends error: " + e.getMessage());
        }

        return friends;
    }

    public List<User> getPendingRequests(int userId) {
        List<User> pending = new ArrayList();
        String sql = "    SELECT u.* FROM users u\n    JOIN friendships f ON f.user_id = u.user_id\n    WHERE f.friend_id = ? AND f.status = 'pending'\n";

        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                User requester = new User();
                requester.setUserId(rs.getInt("user_id"));
                requester.setUsername(rs.getString("username"));
                requester.setEmail(rs.getString("email"));
                requester.setFullName(rs.getString("full_name"));
                requester.setUniversity(rs.getString("university"));
                requester.setCourse(rs.getString("course"));
                requester.setSemester(rs.getInt("semester"));
                requester.setProfilePic(rs.getString("profile_pic"));
                pending.add(requester);
            }
        } catch (SQLException e) {
            System.err.println("Get pending requests error: " + e.getMessage());
        }

        return pending;
    }

    public String getFriendshipStatus(int userId, int otherId) {
        String sql = "    SELECT status FROM friendships\n    WHERE (user_id = ? AND friend_id = ?)\n       OR (user_id = ? AND friend_id = ?)\n";

        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, otherId);
            stmt.setInt(3, otherId);
            stmt.setInt(4, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("status");
            }
        } catch (SQLException e) {
            System.err.println("Check friendship status error: " + e.getMessage());
        }

        return "none";
    }

    public List<User> searchUsers(String keyword, int userId) {
        List<User> users = new ArrayList();
        String sql = "    SELECT * FROM users WHERE (username LIKE ? OR full_name LIKE ?)\n    AND user_id != ?\n    AND user_id NOT IN (\n        SELECT friend_id FROM friendships WHERE user_id = ?\n        UNION\n        SELECT user_id FROM friendships WHERE friend_id = ?\n    )\n";

        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            String like = "%" + keyword + "%";
            stmt.setString(1, like);
            stmt.setString(2, like);
            stmt.setInt(3, userId);
            stmt.setInt(4, userId);
            stmt.setInt(5, userId);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setUsername(rs.getString("username"));
                u.setEmail(rs.getString("email"));
                u.setFullName(rs.getString("full_name"));
                u.setUniversity(rs.getString("university"));
                u.setCourse(rs.getString("course"));
                u.setSemester(rs.getInt("semester"));
                u.setProfilePic(rs.getString("profile_pic"));
                users.add(u);
            }
        } catch (SQLException e) {
            System.err.println("Search users error: " + e.getMessage());
        }

        return users;
    }
}
