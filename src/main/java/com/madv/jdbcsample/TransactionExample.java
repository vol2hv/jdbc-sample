package com.madv.jdbcsample;

/*
* должна выбрасываться ошибка
* org.postgresql.util.PSQLException: No value specified for parameter 1.
* */
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;

public class TransactionExample {
    private Connection connection;

    public TransactionExample(Connection connection) {
        this.connection = connection;
    }
    public void run() throws SQLException {
        Statement statement = connection.createStatement();
        PreparedStatement psInsert = connection.prepareStatement(SqlQuery.SQL_INSERT.getText());
        PreparedStatement psUpdate = connection.prepareStatement(SqlQuery.SQL_UPDATE.getText());

        statement.execute(SqlQuery.SQL_DROP.getText());
        statement.execute(SqlQuery.SQL_CREATE.getText());

        // start transaction block
        connection.setAutoCommit(false);

        // Run list of insert commands
        psInsert.setString(1, "ivanoff");
        psInsert.setBigDecimal(2, new BigDecimal(10));
        psInsert.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
        psInsert.execute();

        psInsert.setString(1, "petrov");
        psInsert.setBigDecimal(2, new BigDecimal(20));
        psInsert.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
        psInsert.execute();

        // Run list of update commands

        // error, test roolback
        // org.postgresql.util.PSQLException: No value specified for parameter 1.
        psUpdate.setBigDecimal(2, new BigDecimal(999.99));
        //psUpdate.setBigDecimal(1, new BigDecimal(999.99));
        psUpdate.setString(2, "ivanoff");
        psUpdate.execute();

        // end transaction block, commit changes
        connection.commit();

        // good practice to set it back to default true
        connection.setAutoCommit(true);




    }

}
