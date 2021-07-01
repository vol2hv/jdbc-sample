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

    String sql = "SELECT * FROM EMPLOYEE";

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
        int row = statement.executeUpdate(generateInsert("ivanoff", new BigDecimal(999.80)));
        return row;
    }

    public Employee rowSelect() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        Employee employee = new Employee();
        while (resultSet.next()) {
            employee.setId(resultSet.getLong("ID"));
            employee.setName(resultSet.getString("NAME"));
            employee.setSalary(resultSet.getBigDecimal("SALARY"));
            employee.setCreatedDate(resultSet.getTimestamp("CREATED_DATE").toLocalDateTime());
            log.debug(employee.toString());
        }
        return employee;
    }

    public int rowUpdate() throws SQLException {
        Statement statement = connection.createStatement();
        int row = statement.executeUpdate(updateSalaryByName("ivanoff", new BigDecimal(777)));
        return row;
    }

    public int rowDelete() throws SQLException {
        Statement statement = connection.createStatement();
        int row = statement.executeUpdate(deleteByName("ivanoff"));
        return row;
    }

    public void batchUpdate() throws SQLException {
        Statement statement = connection.createStatement();

            connection.setAutoCommit(false);

            statement.addBatch(SqlQuery.SQL_DROP.getText());
            statement.addBatch(SqlQuery.SQL_CREATE.getText());
            statement.addBatch(generateInsert("ivanoff", new BigDecimal(1000)));
            statement.addBatch(generateInsert("jane", new BigDecimal(3000)));
            statement.addBatch(updateSalaryByName("ivanoff", new BigDecimal(7777)));
            int[] rows = statement.executeBatch();

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
