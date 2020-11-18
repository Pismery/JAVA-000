package org.pismery.javacourse.spring.homework;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * Homework for JDBC operation
 */
public class Homework03 {

    public static final String JDBC_URL = "jdbc:mysql://localhost/test?serverTimezone=GMT";
    public static final String USER_NAME = "root";
    public static final String PWD = "123123";

    public static void main(String[] args) {
        System.out.println("jdbcCRUD");
        jdbcCRUD();

        System.out.println("jdbcCRUDWithPrepareStatement");
        jdbcCRUDWithPrepareStatement();

        System.out.println("jdbcCRUDWithBatch");
        jdbcCRUDWithBatch();

        System.out.println("jdbcCRUDWithHikari");
        jdbcCRUDWithHikari();
    }

    private static void jdbcCRUDWithHikari() {
        HikariDataSource ds = configHikariDataSource();

        try (Connection connection = ds.getConnection()) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Init");
            String deleteSQL = "delete from t";
            //execute(connection, deleteSQL);
            //executeQuery(connection,"select * from t");
            int[] totalRows = null;
            String insertSQL = "insert into t(id,c,d) values (?,?,?)";
            totalRows = executeBatch(connection, insertSQL
                    ,i -> i
                    ,i -> i
                    ,i -> i
            );
            System.out.println("Insert totalRows:" + IntStream.of(totalRows).sum());

            String updateSQL = "update t set d = ? where id = ?";
            totalRows = executeBatch(connection, updateSQL
                    ,i -> i + 1
                    ,i -> i
            );
            System.out.println("Update totalRows:" + IntStream.of(totalRows).sum());

            System.out.println("Delete SQL:"+execute(connection, deleteSQL));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static HikariDataSource configHikariDataSource() {
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

        return new HikariDataSource(config);
    }

    private static void jdbcCRUDWithBatch() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER_NAME, PWD)) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Init");
            String deleteSQL = "delete from t";
            execute(connection, deleteSQL);
            executeQuery(connection,"select * from t");

            String insertSQL = "insert into t(id,c,d) values (?,?,?)";
            int[] totalRows = executeBatch(connection, insertSQL
                    ,i -> i
                    ,i -> i
                    ,i -> i
            );
            System.out.println("Insert totalRows:" + IntStream.of(totalRows).sum());

            String updateSQL = "update t set d = ? where id = ?";
            totalRows = executeBatch(connection, updateSQL
                    ,i -> i + 1
                    ,i -> i
            );
            System.out.println("Update totalRows:" + IntStream.of(totalRows).sum());

            System.out.println("Delete SQL:"+execute(connection, deleteSQL));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void jdbcCRUDWithPrepareStatement() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER_NAME, PWD)) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String selectById = "select id,c,d from t where id = ?";
            String deleteSQL = "delete from t where id = ?";
            String insertSQL = "insert into t(id,c,d) values (?,?,?)";
            String updateSQL = "update t set c = ? where id = ?";

            System.out.println("Init");
            execute(connection, deleteSQL, 1);
            executeQuery(connection, selectById, 1);

            System.out.print("Insert SQL: ");
            System.out.println(execute(connection, insertSQL, 1, 1, 1));

            System.out.print("Update SQL: ");
            System.out.println(execute(connection, updateSQL, 2, 1));

            System.out.print("Delete SQL: ");
            System.out.println(execute(connection, deleteSQL, 1));

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void jdbcCRUD() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER_NAME, PWD);
             Statement statement = connection.createStatement()
        ) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String selectAll = "select id,c,d from t;";
            String insertSQL = "insert into t(id,c,d) values (1,1,1)";
            String updateSQL = "update t set c = 2 where id = 1";
            String deleteSQL = "delete from t where id = 1";

            System.out.println("Init");
            statement.executeUpdate(deleteSQL);
            executeQuery(statement, selectAll);

            System.out.print("Insert SQL: ");
            System.out.println(statement.executeUpdate(insertSQL));

            System.out.print("Update SQL: ");
            System.out.println(statement.executeUpdate(updateSQL));

            System.out.print("Delete SQL: ");
            System.out.println(statement.executeUpdate(deleteSQL));

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void executeQuery(Statement statement, String sql) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                System.out.println(String.format("{id:%s, c:%s, d:%s}", resultSet.getInt("id"),
                        resultSet.getString("c"),
                        resultSet.getString("d")
                ));
            }
        }
    }

    private static void executeQuery(Connection conn, String sql, Object... args) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            for (int i = 1; i <= args.length; i++) {
                statement.setObject(i, args[i - 1]);
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    System.out.println(String.format("{id:%s, c:%s, d:%s}",
                            resultSet.getInt("id"),
                            resultSet.getString("c"),
                            resultSet.getString("d")
                    ));
                }
            }
        }
    }

    private static int execute(Connection conn, String sql, Object... args) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            for (int i = 1; i <= args.length; i++) {
                statement.setObject(i, args[i - 1]);
            }

            return statement.executeUpdate();
        }
    }

    @SafeVarargs
    private static int[] executeBatch(Connection conn, String sql, Function<Integer, Object>... functions) {
        int[] totalRows = null;
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            for (int i = 1; i < 100; i++) {
                for (int j = 0; j < functions.length; j++) {
                    statement.setObject(j + 1, functions[j].apply(i));
                }
                statement.addBatch();
            }

            totalRows = statement.executeBatch();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return totalRows;
    }
}
