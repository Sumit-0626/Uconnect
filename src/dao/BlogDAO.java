//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Blog;
import util.MyJDBC;

public class BlogDAO {
    public BlogDAO() {
    }

    public boolean uploadBlog(Blog blog) {
        String sql = "INSERT INTO blogs (user_id, title, content, timestamp) VALUES (?, ?, ?, ?)";

        try {
            boolean var5;
            try (
                    Connection conn = MyJDBC.getConnection();
                    PreparedStatement stmt = conn.prepareStatement(sql);
            ) {
                stmt.setInt(1, blog.getUserId());
                stmt.setString(2, blog.getTitle());
                stmt.setString(3, blog.getContent());
                stmt.setTimestamp(4, blog.getTimestamp());
                var5 = stmt.executeUpdate() > 0;
            }

            return var5;
        } catch (SQLException e) {
            System.out.println("uploadBlog error: " + e.getMessage());
            return false;
        }
    }

    public List<Blog> getAllBlogs() {
        List<Blog> blogs = new ArrayList();
        String sql = "SELECT * FROM blogs ORDER BY timestamp DESC";

        try (
                Connection conn = MyJDBC.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
        ) {
            while(rs.next()) {
                Blog blog = new Blog();
                blog.setBlogId(rs.getInt("blog_id"));
                blog.setUserId(rs.getInt("user_id"));
                blog.setTitle(rs.getString("title"));
                blog.setContent(rs.getString("content"));
                blog.setTimestamp(rs.getTimestamp("timestamp"));
                blogs.add(blog);
            }
        } catch (SQLException e) {
            System.out.println("getAllBlogs error: " + e.getMessage());
        }

        return blogs;
    }

    public List<Blog> getBlogsByUser(int userId) {
        List<Blog> blogs = new ArrayList();
        String sql = "SELECT * FROM blogs WHERE user_id = ? ORDER BY timestamp DESC";

        try (
                Connection conn = MyJDBC.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Blog blog = new Blog();
                blog.setBlogId(rs.getInt("blog_id"));
                blog.setUserId(rs.getInt("user_id"));
                blog.setTitle(rs.getString("title"));
                blog.setContent(rs.getString("content"));
                blog.setTimestamp(rs.getTimestamp("timestamp"));
                blogs.add(blog);
            }
        } catch (SQLException e) {
            System.out.println("getBlogsByUser error: " + e.getMessage());
        }

        return blogs;
    }

    public boolean deleteBlog(int blogId) {
        String sql = "DELETE FROM blogs WHERE blog_id = ?";

        try {
            boolean var5;
            try (
                    Connection conn = MyJDBC.getConnection();
                    PreparedStatement stmt = conn.prepareStatement(sql);
            ) {
                stmt.setInt(1, blogId);
                var5 = stmt.executeUpdate() > 0;
            }

            return var5;
        } catch (SQLException e) {
            System.out.println("deleteBlog error: " + e.getMessage());
            return false;
        }
    }

    public boolean updateBlog(Blog blog) {
        String sql = "UPDATE blogs SET title = ?, content = ? WHERE blog_id = ?";

        try {
            boolean var5;
            try (
                    Connection conn = MyJDBC.getConnection();
                    PreparedStatement stmt = conn.prepareStatement(sql);
            ) {
                stmt.setString(1, blog.getTitle());
                stmt.setString(2, blog.getContent());
                stmt.setInt(3, blog.getBlogId());
                var5 = stmt.executeUpdate() > 0;
            }

            return var5;
        } catch (SQLException e) {
            System.out.println("updateBlog error: " + e.getMessage());
            return false;
        }
    }
}
