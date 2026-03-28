package com.bugboard26.repository;

import com.bugboard26.model.IssueHistory;
import com.bugboard26.utils.DBUtil;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class IssueHistoryRepository {
    private static final Logger logger = LoggerFactory.getLogger(IssueHistoryRepository.class);

    public void saveHistory(
            int issueId,
            String field,
            String oldValue,
            String newValue,
            String username
    ){

        String sql = "INSERT INTO issue_history (issue_id, field_name, old_value, new_value, changed_by)"+
            " VALUES (?, ?, ?, ?, ?)";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, issueId);
            stmt.setString(2, field);
            stmt.setString(3, oldValue);
            stmt.setString(4, newValue);
            stmt.setString(5, username);

            stmt.executeUpdate();

        } catch(Exception e){
            logger.error("Errore in insert", e);
        }

    }

    public List<IssueHistory> findByIssueId(int issueId){

        List<IssueHistory> list = new ArrayList<>();

        String sql = "SELECT issue_id, field_name, old_value, new_value, changed_by, changed_at"+
            " FROM issue_history WHERE issue_id = ? ORDER BY changed_at DESC";

        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, issueId);

            try (ResultSet rs = stmt.executeQuery()) {

                while(rs.next()){

                    IssueHistory h = new IssueHistory();

                    h.setIssueId(rs.getInt("issue_id"));
                    h.setFieldName(rs.getString("field_name"));
                    h.setOldValue(rs.getString("old_value"));
                    h.setNewValue(rs.getString("new_value"));
                    h.setChangedBy(rs.getString("changed_by"));
                    h.setChangedAt(
                            DBUtil.getStringFromTimestampHours(rs.getTimestamp("changed_at"))
                    );

                    list.add(h);
                }
            }

        } catch(Exception e){
            logger.error("Errore in select", e);
        }

        return list;
    }
}