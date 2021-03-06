package com.zhong.sqlite;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/12/10 14:06
 */
public class CreateDB {

    public static void createNewDatabase(String fileName) {

        String url = "jdbc:sqlite:" + fileName;

        try {
            Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        createNewDatabase("D:/software/sqlite/create-db.db");
    }
}


