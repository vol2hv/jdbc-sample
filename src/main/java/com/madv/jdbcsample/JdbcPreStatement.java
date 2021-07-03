package com.madv.jdbcsample;

import com.madv.jdbcsample.model.Employee;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;

// TODO: 29.06.2021 все длинные стринги в Enum
@Log4j2
public class JdbcPreStatement {
    private Connection connection;

    public JdbcPreStatement(Connection connection) {
        this.connection = connection;
    }

//    String sql = "SELECT * FROM EMPLOYEE";

    public void tableCreate() throws SQLException {
        Statement statement = connection.createStatement();
        PreparedStatement preStatement = connection.prepareStatement(SqlQuery.SQL_CREATE.getText());
        preStatement.execute();
    }

    public void tableDrop() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SQL_DROP.getText());
        preparedStatement.execute();
    }

    public int rowInsert() throws SQLException {
        Statement statement = connection.createStatement();
        PreparedStatement preparedStatement =
                connection.prepareStatement(SqlQuery.SQL_INSERT.getText());
        preparedStatement.setString(1, "ivanoff");
        preparedStatement.setBigDecimal(2, new BigDecimal(799.88));
        preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
        int row = preparedStatement.executeUpdate();
        return row;
    }

    public Employee rowSelect() throws SQLException {
        PreparedStatement preparedStatement =
                connection.prepareStatement(SqlQuery.SQL_SELECT.getText());
        Statement statement = connection.createStatement();
        ResultSet resultSet = preparedStatement.executeQuery();

        Employee employee = new Employee();
        while (resultSet.next()) {

            long id = resultSet.getLong("ID");
            String name = resultSet.getString("NAME");
            BigDecimal salary = resultSet.getBigDecimal("SALARY");
            Timestamp createdDate = resultSet.getTimestamp("CREATED_DATE");
            employee.setId(id);
            employee.setName(name);
            employee.setSalary(salary);
            // Timestamp -> LocalDateTime
            employee.setCreatedDate(createdDate.toLocalDateTime());
        }
        return employee;
    }

    public int rowUpdate() throws SQLException {
        PreparedStatement preparedStatement =
                connection.prepareStatement(SqlQuery.SQL_UPDATE.getText());
        Statement statement = connection.createStatement();
        preparedStatement.setBigDecimal(1, new BigDecimal(999.99));
        preparedStatement.setString(2, "ivanoff");
        int row = preparedStatement.executeUpdate();
        return row;
    }

    public int rowDelete() throws SQLException {
        PreparedStatement preparedStatement =
                connection.prepareStatement(SqlQuery.SQL_DELETE.getText());
        Statement statement = connection.createStatement();
        preparedStatement.setString(1, "ivanoff");
        int row = preparedStatement.executeUpdate();
        return row;
    }

    public void batchUpdate() throws SQLException {
        Statement statement = connection.createStatement();
        PreparedStatement psCreate = connection.prepareStatement(SqlQuery.SQL_CREATE.getText());
        PreparedStatement psDrop = connection.prepareStatement(SqlQuery.SQL_DROP.getText());

        PreparedStatement psInsert = connection.prepareStatement(SqlQuery.SQL_INSERT.getText());
        PreparedStatement psUpdate = connection.prepareStatement(SqlQuery.SQL_UPDATE.getText());
        connection.setAutoCommit(false);
        psDrop.execute();
        psCreate.execute();

        psInsert.setString(1, "ivanoff");
        psInsert.setBigDecimal(2, new BigDecimal(10));
        psInsert.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
        psInsert.addBatch();
        psInsert.setString(1, "petrov");
        psInsert.setBigDecimal(2, new BigDecimal(20));
        psInsert.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
        psInsert.addBatch();
        psInsert.setString(1, "sidorov");
        psInsert.setBigDecimal(2, new BigDecimal(30));
        psInsert.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
        psInsert.addBatch();
        int[] rows = psInsert.executeBatch();

        psUpdate.setBigDecimal(1, new BigDecimal(999.99));
        psUpdate.setString(2, "ivanoff");
        psUpdate.addBatch();

        psUpdate.setBigDecimal(1, new BigDecimal(888.88));
        psUpdate.setString(2, "sidorov");
        psUpdate.addBatch();

        int[] rows2 = psUpdate.executeBatch();

        connection.commit();
        }

// ----------------------------------------------------------------------
    private static String generateInsert(String name, BigDecimal salary) {

        return "INSERT INTO EMPLOYEE (NAME, SALARY, CREATED_DATE) " +
                "VALUES ('" + name + "','" + salary + "','" + LocalDateTime.now() + "')";

    }

    private static String updateSalaryByName(String name, BigDecimal salary) {

        return "UPDATE EMPLOYEE SET SALARY='" + salary + "' WHERE NAME='" + name + "'";

    }

    private static String deleteByName(String name) {

        return "DELETE FROM EMPLOYEE WHERE NAME='" + name + "'";

    }


}
