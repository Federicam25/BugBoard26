package com.bugboard26.repository;

import com.bugboard26.model.User;
import com.bugboard26.utils.DBUtil;
import com.bugboard26.utils.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);
    public Map<String,String> login(String email, String password) {

        String sql = "SELECT email, password_hash, role FROM users WHERE email=?";
        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String hash = rs.getString("password_hash");
                String role = rs.getString("role");
                String username = rs.getString("email");
                if (PasswordUtil.verifyPassword(password, hash)) {
                    Map<String, String> result = new HashMap<>();
                    result.put("username", username);
                    result.put("role", role);
                    return result;
                }
                return new HashMap<>();
            }
        }catch (SQLException e){
            logger.error("Credenziali non valide", e);
        }
        return new HashMap<>();
    }

    public int save(User user) {

        String sql = "INSERT INTO users(email,password_hash,role) VALUES(?,?,?)";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());

            ResultSet rs = stmt.getGeneratedKeys();

            if(rs.next()){
                return rs.getInt(1);
            }

        }catch (SQLException e){
            logger.error("Errore nella crezione di un utente", e);
            return -1;
        }
        return -1;
    }

    public List<String> findAllUsers() {

        List<String> users = new ArrayList<>();

        String sql = "SELECT u.email, COUNT(i.id) AS open_issues\n" +
                " FROM users u" +
                " LEFT JOIN issues i " +
                "  ON u.email = i.assigned_to " +
                "  AND i.status != 'done'" +
                " GROUP BY u.email" +
                " ORDER BY open_issues ASC;";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {
                users.add(rs.getString("email"));
            }

        }catch (SQLException e){
            logger.error("Errore in select users", e);
        }

        return users;
    }

}