package org.pismery.javacourse.spring.homework;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.List;

/**
 * Homework for JDBC operation, Operation see {@link org.pismery.javacourse.spring.homework.Homework03Test}
 */
public class Homework03 {

    static class ConnectionManager {
        private static final String JDBC_URL = "jdbc:mysql://localhost:3306/test?serverTimezone=GMT";
        private static final String USER_NAME = "root";
        private static final String PWD = "";
        private static final HikariDataSource ds;

        static {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(JDBC_URL); //数据源
            config.setUsername(USER_NAME); //用户名
            config.setPassword(PWD); //密码
            config.addDataSourceProperty("cachePrepStmts", "true"); //是否自定义配置，为true时下面两个参数才生效
            config.addDataSourceProperty("prepStmtCacheSize", "250"); //连接池大小默认25，官方推荐250-500
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048"); //单条语句最大长度默认256，官方推荐2048
            config.addDataSourceProperty("useServerPrepStmts", "true"); //新版本MySQL支持服务器端准备，开启能够得到显著性能提升
            config.addDataSourceProperty("useLocalSessionState", "true");
            config.addDataSourceProperty("useLocalTransactionState", "true");
            config.addDataSourceProperty("rewriteBatchedStatements", "true");
            config.addDataSourceProperty("cacheResultSetMetadata", "true");
            config.addDataSourceProperty("cacheServerConfiguration", "true");
            config.addDataSourceProperty("elideSetAutoCommits", "true");
            config.addDataSourceProperty("maintainTimeStats", "false");

            ds = new HikariDataSource(config);
        }


        static Connection getConnectionFromDriver() {
            try {
                return DriverManager.getConnection(JDBC_URL, USER_NAME, PWD);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        static Connection getConnectionFromPool() {
            try {
                return ds.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void executeQuery(Connection conn, String sql) {
        try (Statement statement = conn.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                printResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int execute(Connection conn, String sql) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            return statement.executeUpdate(sql);
        }
    }

    public void executeQuery(Connection conn, String sql, Object... args) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                printResultSet(resultSet);
            }
        }
    }

    public int execute(Connection conn, String sql, Object... args) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            for (int i = 1; i <= args.length; i++) {
                statement.setObject(i, args[i - 1]);
            }

            return statement.executeUpdate();
        }
    }

    public int[] executeBatch(Connection conn, String sql, List<List<Object>> argsList) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            for (List<Object> objects : argsList) {
                for (int i = 0; i < objects.size(); i++) {
                    statement.setObject(i + 1, objects.get(i));
                }
                statement.addBatch();
            }

            return statement.executeBatch();
        }
    }

    private void printResultSet(ResultSet resultSet) throws SQLException {
        boolean empty = true;
        while (resultSet.next()) {
            empty = false;
            System.out.println(String.format("{id:%s, c:%s, d:%s}",
                    resultSet.getInt("id"),
                    resultSet.getString("c"),
                    resultSet.getString("d")
            ));
        }

        if (empty) {
            System.out.println("select nothing...");
        }
    }
}
