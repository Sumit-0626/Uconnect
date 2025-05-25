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
import model.Post;
import util.MyJDBC;

public class PostDAO {
    private final Connection conn;

    public PostDAO() {
        try {
            this.conn = MyJDBC.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }

    public boolean uploadPost(Post post) {
        String sql = "INSERT INTO posts (user_id, caption, image_path, timestamp) VALUES (?, ?, ?, ?)";

        try {
            boolean var4;
            try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
                stmt.setInt(1, post.getUserId());
                stmt.setString(2, post.getCaption());
                stmt.setString(3, post.getImagePath());
                stmt.setTimestamp(4, post.getTimestamp());
                var4 = stmt.executeUpdate() > 0;
            }

            return var4;
        } catch (SQLException e) {
            System.err.println("Upload post error: " + e.getMessage());
            return false;
        }
    }

    public boolean updatePost(Post post) {
        String sql = "UPDATE posts SET caption = ?, image_path = ?, timestamp = ? WHERE post_id = ?";

        try {
            boolean var4;
            try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
                stmt.setString(1, post.getCaption());
                stmt.setString(2, post.getImagePath());
                stmt.setTimestamp(3, post.getTimestamp());
                stmt.setInt(4, post.getPostId());
                var4 = stmt.executeUpdate() > 0;
            }

            return var4;
        } catch (SQLException e) {
            System.err.println("Update post error: " + e.getMessage());
            return false;
        }
    }

    public List<Post> getPostsByUser(int userId) {
        List<Post> posts = new ArrayList();
        String sql = "SELECT * FROM posts WHERE user_id = ? ORDER BY timestamp DESC";

        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Post post = new Post();
                post.setPostId(rs.getInt("post_id"));
                post.setUserId(rs.getInt("user_id"));
                post.setCaption(rs.getString("caption"));
                post.setImagePath(rs.getString("image_path"));
                post.setTimestamp(rs.getTimestamp("timestamp"));
                post.setType("photo");
                posts.add(post);
            }
        } catch (SQLException e) {
            System.err.println("Get posts by user error: " + e.getMessage());
        }

        return posts;
    }

    public List<Post> getPostsForTimeline(int userId) {
        List<Post> posts = new ArrayList();
        String sql = "    SELECT 'blog' AS type, NULL AS image_path, b.title, b.content AS caption, b.timestamp, u.full_name\n    FROM blogs b\n    JOIN users u ON b.user_id = u.user_id\n    WHERE b.user_id = ? OR b.user_id IN (\n        SELECT friend_id FROM friendships WHERE user_id = ? AND status = 'accepted'\n        UNION\n        SELECT user_id FROM friendships WHERE friend_id = ? AND status = 'accepted'\n    )\n    UNION ALL\n    SELECT 'photo', p.image_path, NULL, p.caption, p.timestamp, u.full_name\n    FROM posts p\n    JOIN users u ON p.user_id = u.user_id\n    WHERE p.user_id = ? OR p.user_id IN (\n        SELECT friend_id FROM friendships WHERE user_id = ? AND status = 'accepted'\n        UNION\n        SELECT user_id FROM friendships WHERE friend_id = ? AND status = 'accepted'\n    )\n    ORDER BY timestamp DESC\n";

        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            for(int i = 1; i <= 6; ++i) {
                stmt.setInt(i, userId);
            }

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Post post = new Post();
                post.setType(rs.getString("type"));
                post.setImagePath(rs.getString("image_path"));
                post.setTitle(rs.getString("title"));
                post.setCaption(rs.getString("caption"));
                post.setTimestamp(rs.getTimestamp("timestamp"));
                post.setFullName(rs.getString("full_name"));
                posts.add(post);
            }
        } catch (SQLException e) {
            System.err.println("Get timeline posts error: " + e.getMessage());
        }

        return posts;
    }
}
