package com.bugboard26.repository;

import com.bugboard26.model.Issue;
import com.bugboard26.utils.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
public class IssueRepository {
    private static final Logger logger = LoggerFactory.getLogger(IssueRepository.class);
    public List<Issue> findAll(){
        List<Issue> issues = new ArrayList<>();
        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM issues");
                ResultSet rs = stmt.executeQuery()
        ) {
            while(rs.next()){
                Issue issue = new Issue();

                issue.setId(rs.getInt("id"));
                issue.setTitle(rs.getString("title"));
                issue.setDescription(rs.getString("description"));
                issue.setPriority(rs.getString("priority"));
                issue.setStatus(rs.getString("status"));
                issue.setCreatedAt(rs.getString("created_at"));
                issue.setCreatedBy(rs.getString("created_by"));
                issue.setAssignedTo(rs.getString("assigned_to"));

                issues.add(issue);
            }

        }catch(Exception e){
            logger.error("Errore in insert", e);
        }

        return issues;
    }
    public int save(Issue issue){

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO issues(title,description,type, priority,status,created_by,created_at) VALUES(?,?,?,?,?,?,NOW())", Statement.RETURN_GENERATED_KEYS);
        ) {



            stmt.setString(1,issue.getTitle());
            stmt.setString(2,issue.getDescription());
            stmt.setString(3,issue.getType());
            stmt.setString(4,issue.getPriority());
            stmt.setString(5,issue.getStatus());
            stmt.setString(6,issue.getCreatedBy());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();

            if(rs.next()){
                return rs.getInt(1);
            }

        }catch(Exception e){
            logger.error("Errore in insert", e);
            return -1;
        }
        return -1;
    }

    public List<Issue> findFiltered(
            String status,
            String type,
            String priority,
            String orderBy
    ) {

        List<Issue> issues = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM issues WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (status != null && !status.isEmpty()) {
            sql.append(" AND status = ?");
            params.add(status);
        }

        if (type != null && !type.isEmpty()) {
            sql.append(" AND type = ?");
            params.add(type);
        }

        if (priority != null && !priority.isEmpty()) {
            sql.append(" AND priority = ?");
            params.add(priority);
        }

        if (orderBy != null && !orderBy.isEmpty()) {
            sql.append(" ORDER BY ").append(orderBy);
        }

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql.toString())
        ) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    Issue issue = new Issue();

                    issue.setId(rs.getInt("id"));
                    issue.setTitle(rs.getString("title"));
                    issue.setDescription(rs.getString("description"));
                    issue.setPriority(rs.getString("priority"));
                    issue.setStatus(rs.getString("status"));
                    issue.setType(rs.getString("type"));
                    issue.setCreatedBy(rs.getString("created_by"));
                    issue.setAssignedTo(rs.getString("assigned_to"));
                    issue.setCreatedAt(
                            DBUtil.getStringFromTimestamp(rs.getTimestamp("created_at"))
                    );

                    issues.add(issue);
                }
            }

        } catch (Exception e) {
            logger.error("Errore in select", e);
        }

        return issues;
    }

    public Issue findById(int id){

        Issue issue = null;

        String sql = "SELECT id, title, type, priority, status, created_by,"+
               "assigned_to, created_at, description FROM issues WHERE id = ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if(rs.next()){

                    issue = new Issue();

                    issue.setId(rs.getInt("id"));
                    issue.setTitle(rs.getString("title"));
                    issue.setType(rs.getString("type"));
                    issue.setPriority(rs.getString("priority"));
                    issue.setStatus(rs.getString("status"));
                    issue.setCreatedBy(rs.getString("created_by"));
                    issue.setAssignedTo(rs.getString("assigned_to"));

                    // meglio usare Timestamp → String (coerente col resto del progetto)
                    issue.setCreatedAt(
                            DBUtil.getStringFromTimestamp(rs.getTimestamp("created_at"))
                    );

                    issue.setDescription(rs.getString("description"));
                }
            }

        } catch(Exception e){
            logger.error("Errore in select", e);
        }

        return issue;
    }

    public void update(Issue issue){

        String sql = "UPDATE issues SET title = ?, type = ?, priority = ?, status = ?,"+
            "description = ?, assigned_to = ?, updated_at = NOW() WHERE id = ?";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, issue.getTitle());
            stmt.setString(2, issue.getType());
            stmt.setString(3, issue.getPriority());
            stmt.setString(4, issue.getStatus());
            stmt.setString(5, issue.getDescription());
            stmt.setString(6, issue.getAssignedTo());
            stmt.setInt(7, issue.getId());

        } catch(Exception e){
            logger.error("Errore in update", e);
        }

    }

    // STATISTICHE

    public int countOpenIssues(){

        String sql = "SELECT COUNT(*) FROM issues WHERE status != 'done' and type = 'BUG'";

        try(
                Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ){

            if(rs.next()){
                return rs.getInt(1);
            }

        }catch(SQLException e){
            logger.error("Errore in select count", e);
        }

        return 0;
    }

    public List<Map<String, Object>> countByUser(){

        List<Map<String, Object>> list = new ArrayList<>();

        String sql = " SELECT assigned_to, COUNT(*) as total FROM issues " +
            " WHERE assigned_to IS NOT NULL and type = 'BUG' and status != 'done' " +
            " GROUP BY assigned_to";

        try(
                Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ){

            while(rs.next()){

                Map<String, Object> row = new HashMap<>();

                row.put("assignedTo", rs.getString("assigned_to"));
                row.put("total", rs.getInt("total"));

                list.add(row);
            }

        }catch(SQLException e){
            logger.error("Errore in select count user", e);
        }

        return list;
    }

    public double avgResolution(){

        String sql = "SELECT AVG(EXTRACT(EPOCH FROM (updated_at - created_at))) as avg_seconds " +
            " FROM issues WHERE status = 'done' and type = 'BUG' ";

        try(
                Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ){

            if(rs.next()){
                return rs.getDouble("avg_seconds");
            }

        }catch(SQLException e){
            logger.error("Errore in media risoluzione", e);
        }

        return 0;
    }

    public List<Map<String, Object>> avgResolutionByUser(){

        List<Map<String, Object>> list = new ArrayList<>();

        String sql = "SELECT assigned_to,AVG(EXTRACT(EPOCH FROM (updated_at - created_at))) as avg_seconds"+
            " FROM issues WHERE status = 'done' and type = 'BUG' GROUP BY assigned_to";

        try(
                Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ){

            while(rs.next()){

                Map<String, Object> row = new HashMap<>();

                row.put("assignedTo", rs.getString("assigned_to"));
                row.put("avg", rs.getDouble("avg_seconds"));

                list.add(row);
            }

        }catch(SQLException e){
            logger.error("Errore in media risoluzione per utente", e);
        }

        return list;
    }

}