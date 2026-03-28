package com.bugboard26.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class DBUtil {

    static String host = getEnv("DB_HOST", "postgres");
    static String port = getEnv("DB_PORT", "5432");
    static String db   = getEnv("DB_NAME", "bugboard");
    static String user = getEnv("DB_USER", "postgres");
    static String pass = getEnv("DB_PASSWORD", "Aa220890");

    static String url = "jdbc:postgresql://" + host + ":" + port + "/" + db;

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }

    public static String getStringFromTimestamp(Timestamp dateTS){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return dateTS.toLocalDateTime().format(formatter);
    }

    private static String getEnv(String key, String defaultValue) {
        String value = System.getenv(key);
        return (value == null || value.isEmpty()) ? defaultValue : value;
    }

    public static String getStringFromTimestampHours(Timestamp dateTS){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return dateTS.toLocalDateTime().format(formatter);
    }
}
