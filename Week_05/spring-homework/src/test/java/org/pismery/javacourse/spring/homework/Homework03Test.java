package org.pismery.javacourse.spring.homework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Homework03Test {

    @BeforeEach
    private void beforeEach() {
        Homework03 homework03 = new Homework03();
        try (Connection connection = Homework03.ConnectionManager.getConnectionFromDriver()) {
            String dropTable = "drop table if exists t;";
            homework03.execute(connection, dropTable);

            String createTable = "CREATE TABLE t (" +
                    "`id` int(11) NOT NULL AUTO_INCREMENT," +
                    "`c` int(11) DEFAULT NULL," +
                    "`d` int(11) DEFAULT NULL," +
                    "PRIMARY KEY (`id`)" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8";

            homework03.execute(connection, createTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void jdbcWithStatement() {
        System.out.println("jdbcWithStatement: ");
        Homework03 homework03 = new Homework03();

        try (Connection connection = Homework03.ConnectionManager.getConnectionFromDriver()) {
            LocalDateTime now = LocalDateTime.now();

            System.out.println("insert: ");
            for (int i = 1; i < 100; i++) {
                homework03.execute(connection, String.format("insert into t(id,c,d) values (%s,%s,%s)", i, i, i));
            }
            homework03.executeQuery(connection, "select id,c,d from t limit 10;");

            System.out.println("update: ");
            for (int i = 1; i < 100; i++) {
                homework03.execute(connection, String.format("update t set d = %s where id = %s", i + 1, i));
            }
            homework03.executeQuery(connection, "select id,c,d from t limit 10;");

            System.out.println("delete: ");
            for (int i = 1; i < 100; i++) {
                homework03.execute(connection, String.format("delete from t where id = %s", i));
            }
            homework03.executeQuery(connection, "select id,c,d from t limit 10;");

            System.out.println("JDBC Statement cause time: " + Duration.between(now, LocalDateTime.now()).toMillis() + " ms");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void jdbcWithPrepareStatement() {
        System.out.println("jdbcWithPrepareStatement: ");
        Homework03 homework03 = new Homework03();

        try (Connection connection = Homework03.ConnectionManager.getConnectionFromDriver()) {
            LocalDateTime now = LocalDateTime.now();

            System.out.println("insert: ");
            for (int i = 1; i < 100; i++) {
                homework03.execute(connection, "insert into t(id,c,d) values (?,?,?)", i, i, i);
            }
            homework03.executeQuery(connection, "select id,c,d from t limit 10;");

            System.out.println("update: ");
            for (int i = 1; i < 100; i++) {
                homework03.execute(connection, "update t set d = ? where id = ?", i + 1, i);
            }
            homework03.executeQuery(connection, "select id,c,d from t limit 10;");

            System.out.println("delete: ");

            for (int i = 1; i < 100; i++) {
                homework03.execute(connection, "delete from t where id = ?", i);
            }
            homework03.executeQuery(connection, "select id,c,d from t limit 10;");

            System.out.println("JDBC PrepareStatement cause time: " + Duration.between(now, LocalDateTime.now()).toMillis() + " ms");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void jdbcWithBatch() {
        System.out.println("jdbcWithBatch: ");
        Homework03 homework03 = new Homework03();

        try (Connection connection = Homework03.ConnectionManager.getConnectionFromDriver()) {
            LocalDateTime now = LocalDateTime.now();

            System.out.println("insert: ");
            List<List<Object>> insertDataList = IntStream.range(1, 100)
                    .mapToObj(i -> Arrays.asList((Object) i, i, i))
                    .collect(Collectors.toList());
            homework03.executeBatch(connection, "insert into t(id,c,d) values (?,?,?)", insertDataList);
            homework03.executeQuery(connection, "select id,c,d from t limit 10;");

            System.out.println("update: ");
            List<List<Object>> updateDataList = IntStream.range(1, 100)
                    .mapToObj(i -> Arrays.asList((Object) (i + 1), i))
                    .collect(Collectors.toList());
            homework03.executeBatch(connection, "update t set d = ? where id = ?", updateDataList);
            homework03.executeQuery(connection, "select id,c,d from t limit 10;");

            System.out.println("delete: ");
            List<List<Object>> deleteDataList = IntStream.range(1, 100)
                    .mapToObj(i -> Collections.singletonList((Object) i))
                    .collect(Collectors.toList());
            homework03.executeBatch(connection, "delete from t where id = ?", deleteDataList);
            homework03.executeQuery(connection, "select id,c,d from t limit 10;");

            System.out.println("JDBC Batch cause time: " + Duration.between(now, LocalDateTime.now()).toMillis() + " ms");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void jdbcWithHikari() {
        System.out.println("jdbcWithHikari: ");
        Homework03 homework03 = new Homework03();

        try (Connection connection = Homework03.ConnectionManager.getConnectionFromPool()) {
            LocalDateTime now = LocalDateTime.now();

            System.out.println("insert: ");
            List<List<Object>> insertDataList = IntStream.range(1, 100)
                    .mapToObj(i -> Arrays.asList((Object) i, i, i))
                    .collect(Collectors.toList());
            homework03.executeBatch(connection, "insert into t(id,c,d) values (?,?,?)", insertDataList);
            homework03.executeQuery(connection, "select id,c,d from t limit 10;");

            System.out.println("update: ");
            List<List<Object>> updateDataList = IntStream.range(1, 100)
                    .mapToObj(i -> Arrays.asList((Object) (i + 1), i))
                    .collect(Collectors.toList());
            homework03.executeBatch(connection, "update t set d = ? where id = ?", updateDataList);
            homework03.executeQuery(connection, "select id,c,d from t limit 10;");

            System.out.println("delete: ");
            List<List<Object>> deleteDataList = IntStream.range(1, 100)
                    .mapToObj(i -> Collections.singletonList((Object) i))
                    .collect(Collectors.toList());
            homework03.executeBatch(connection, "delete from t where id = ?", deleteDataList);
            homework03.executeQuery(connection, "select id,c,d from t limit 10;");

            System.out.println("JDBC Hikari cause time: " + Duration.between(now, LocalDateTime.now()).toMillis() + " ms");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}