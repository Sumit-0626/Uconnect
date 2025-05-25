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
import model.Comment;
import util.MyJDBC;

public class CommentDAO {
    private final Connection conn = MyJDBC.getConnection();

    public CommentDAO() throws SQLException {
    }

    public boolean addComment(int userId, int postId, String postType, String commentText) throws SQLException {
        String sql = "INSERT INTO comments (user_id, post_id, post_type, comment, timestamp) VALUES (?, ?, ?, ?, NOW())";

        boolean var7;
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, postId);
            stmt.setString(3, postType);
            stmt.setString(4, commentText);
            var7 = stmt.executeUpdate() > 0;
        }

        return var7;
    }

    public List<Comment> getComments(int postId, String postType) throws SQLException {
        List<Comment> comments = new ArrayList();
        String sql = "    SELECT c.comment, c.timestamp, u.full_name\n    FROM comments c\n    JOIN users u ON c.user_id = u.user_id\n    WHERE c.post_id = ? AND c.post_type = ?\n    ORDER BY c.timestamp ASC\n";

        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setInt(1, postId);
            stmt.setString(2, postType);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Comment c = new Comment();
                c.setCommentText(rs.getString("comment"));
                c.setTimestamp(rs.getTimestamp("timestamp"));
                c.setFullName(rs.getString("full_name"));
                comments.add(c);
            }
        }

        return comments;
    }
}
