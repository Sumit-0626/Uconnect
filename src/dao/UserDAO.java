//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.User;
import util.MyJDBC;

public class UserDAO {
    private final Connection conn;

    public UserDAO() {
        try {
            this.conn = MyJDBC.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Database connection failed: " + e.getMessage(), e);
        }
    }

    public boolean registerUser(User user) {
        String sql = "INSERT INTO users (username, email, password, full_name, university, course, semester, profile_pic) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            boolean var4;
            try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getPassword());
                stmt.setString(4, user.getFullName());
                stmt.setString(5, user.getUniversity());
                stmt.setString(6, user.getCourse());
                stmt.setInt(7, user.getSemester());
                stmt.setString(8, user.getProfilePic());
                var4 = stmt.executeUpdate() > 0;
            }

            return var4;
        } catch (SQLException e) {
            System.err.println("Registration error: " + e.getMessage());
            return false;
        }
    }

    public boolean userExists(String username, String email) {
        String sql = "SELECT * FROM users WHERE username = ? OR email = ?";

        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("User existence check error: " + e.getMessage());
            return false;
        }
    }

    public User loginUser(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setFullName(rs.getString("full_name"));
                user.setUniversity(rs.getString("university"));
                user.setCourse(rs.getString("course"));
                user.setSemester(rs.getInt("semester"));
                user.setProfilePic(rs.getString("profile_pic"));
                return user;
            }
        }

        return null;
    }

    public boolean updateProfilePic(int userId, String newPath) {
        String sql = "UPDATE users SET profile_pic = ? WHERE user_id = ?";

        try {
            boolean var5;
            try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
                stmt.setString(1, newPath);
                stmt.setInt(2, userId);
                var5 = stmt.executeUpdate() > 0;
            }

            return var5;
        } catch (SQLException e) {
            System.err.println("Update profile picture error: " + e.getMessage());
            return false;
        }
    }
}
