package com.xudod.gen_code.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbUtil {

    /**
     * 获取数据库连接
     *
     * @return
     */
    public static Connection getConnection(String dbUrl, String dbUser, String dbPw) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
        } catch (SQLException e) {
        	System.out.println("get connection failure" + e);
        }
        return conn;
    }

    /**
     * 关闭数据库连接
     * @param conn
     */
    public static void closeConnection(Connection conn) {
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
            	System.out.println("close connection failure" + e);
            }
        }
    }
    
    /**
     * 关闭数据库连接
     * @param conn
     */
    public static void closeStatement(Statement stat) {
        if(stat != null) {
            try {
            	stat.close();
            } catch (SQLException e) {
            	System.out.println("close statement failure" + e);
            }
        }
    }
    
    /**
	 * 生成数据库连接用databaseUrl
	 * @param genCodeInfo
	 */
    public static String createDatabaseUrl(String dbIp, String dbPort, String dbName) {
    	String databaseUrl = "jdbc:mysql://" + dbIp + ":" + dbPort + "/"
    			+ dbName + "?serverTimezone=GMT%2B8&amp&useSSL=false&amp&nullCatalogMeansCurrent=true&amp&allowPublicKeyRetrieval=true";
		return databaseUrl;
	}
    
}
