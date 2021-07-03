package com.madv.jdbcsample;
/*
* Головной класс
*
* */
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Log4j2
public class JDBCExample {
    private static Connection connection;
    static String USER = "postgres";
    static String PASSWORD = "root";
    static String URL = "jdbc:postgresql://127.0.0.1:5432/test";

    public static Connection getConn() {
        if ( connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                if (connection != null) {
                    log.info("Пользователь: {} присоеденился к базе данных: {}", USER, URL);
                } else {
                    log.info("Пользователь: {} не смог присоеденится к базе данных: {}", USER, URL);

                }
            } catch (SQLException throwables) {
                log.info("SQL State:{} Message: {}");
                throwables.printStackTrace();
            } catch (Exception throwables) {
                throwables.printStackTrace();
            }
        }
        return connection ;
    }

    public static void main(String[] args) {
        log.info("Ура заработало.");
        Connection connection = JDBCExample.getConn();
        JdbcStatement jdbcStatement = new JdbcStatement(connection);
        JdbcPreStatement jdbcPreStatement = new JdbcPreStatement(connection);
        TransactionExample transactionExample = new TransactionExample(connection);

        try {
            jdbcStatement.batchUpdate();
            jdbcPreStatement.batchUpdate();
            log.info("Следующая ошибка должна иметь меcто.");
            transactionExample.run();

        } catch (SQLException e) {
            log.error("SQL State:{} Message: {}");
            e.printStackTrace();
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }


    }

    private static void jdbcPreStatemtnt() {
    }


}