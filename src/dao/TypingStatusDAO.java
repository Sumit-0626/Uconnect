//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.MyJDBC;

public class TypingStatusDAO {
    public TypingStatusDAO() {
    }

    public void updateTypingStatus(int userId, int friendId, boolean isTyping) {
        try (Connection conn = MyJDBC.getConnection()) {
            String check = "SELECT * FROM typing_status WHERE user_id = ? AND friend_id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(check);
            checkStmt.setInt(1, userId);
            checkStmt.setInt(2, friendId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                String update = "UPDATE typing_status SET is_typing = ? WHERE user_id = ? AND friend_id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(update);
                updateStmt.setBoolean(1, isTyping);
                updateStmt.setInt(2, userId);
                updateStmt.setInt(3, friendId);
                updateStmt.executeUpdate();
            } else {
                String insert = "INSERT INTO typing_status (user_id, friend_id, is_typing) VALUES (?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insert);
                insertStmt.setInt(1, userId);
                insertStmt.setInt(2, friendId);
                insertStmt.setBoolean(3, isTyping);
                insertStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean isFriendTyping(int friendId, int userId) {
        try (Connection conn = MyJDBC.getConnection()) {
            String query = "SELECT is_typing FROM typing_status WHERE user_id = ? AND friend_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, friendId);
            stmt.setInt(2, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("is_typing");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
