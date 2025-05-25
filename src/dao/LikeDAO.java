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

public class LikeDAO {
    private final Connection conn = MyJDBC.getConnection();

    public LikeDAO() throws SQLException {
    }

    public boolean likePost(int userId, int postId, String postType) {
        String sql = "INSERT INTO likes (user_id, post_id, post_type) VALUES (?, ?, ?)";

        try {
            boolean var6;
            try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                stmt.setInt(2, postId);
                stmt.setString(3, postType);
                var6 = stmt.executeUpdate() > 0;
            }

            return var6;
        } catch (SQLException e) {
            System.err.println("Like insert error: " + e.getMessage());
            return false;
        }
    }

    public boolean hasLiked(int userId, int postId, String postType) {
        String sql = "SELECT * FROM likes WHERE user_id = ? AND post_id = ? AND post_type = ?";

        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, postId);
            stmt.setString(3, postType);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Check like error: " + e.getMessage());
            return false;
        }
    }

    public int countLikes(int postId, String postType) {
        String sql = "SELECT COUNT(*) FROM likes WHERE post_id = ? AND post_type = ?";

        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setInt(1, postId);
            stmt.setString(2, postType);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Count likes error: " + e.getMessage());
        }

        return 0;
    }
}
