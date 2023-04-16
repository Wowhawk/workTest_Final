package org.prog.steps;

import io.cucumber.java.en.Given;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.prog.dto.NameDto;
import org.prog.dto.UserDto;
import org.prog.util.DataHolder;

import java.net.InetAddress;
import java.sql.*;
import java.util.Optional;

public class SQLSteps {

    private final static String FILTER_BY_PARAMETER =
            "select FirstName, LastName from Persons WHERE %s = '%s'";

    private final static String FILTER_BY_PARAMETER_ONE_RECORD =
            "select FirstName, LastName from Persons WHERE %s = '%s' LIMIT 0,1";

    private final static String NEW_USER_SQL =
            "insert into Persons (LastName, FirstName, Title, Gender)" +
                    " VALUES ('%s', '%s', '%s', '%s')";

    @Given("I save person {string} to DB")
    public void savePersonToDB(String alias) {
        try {
            UserDto userDto = (UserDto) DataHolder.getInstance().get(alias);

            String query = String.format(NEW_USER_SQL, userDto.getName().getLast(), userDto.getName().getFirst(),
                    userDto.getName().getTitle(), userDto.getGender());
            executeQuery(query, false);
        } catch (SQLException e) {
            System.err.println("Failed to create user due to SQL Exception!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Given("I print all users in DB with {string} = {string}")
    public void setupConnection(String parameter, String value) throws ClassNotFoundException, SQLException {
        String query = String.format(FILTER_BY_PARAMETER, parameter, value);
        Optional<ResultSet> sqlResults = executeQuery(query, true);
        if (sqlResults.isPresent()) {
            while (sqlResults.get().next()) {
                String name = sqlResults.get().getString(1) + " " + sqlResults.get().getString(2);
                System.out.println(name);
            }
        } else {
            Assert.fail("SQL Execution failed!");
        }
    }

    @SneakyThrows
    private Optional<ResultSet> executeQuery(String query, boolean hasResponse)
            throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con;

        String hostName = InetAddress.getLocalHost().getHostName();
        if ("BEZZUBYAK".equals(hostName)) {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "user", "password");
        } else {
            con = DriverManager.getConnection("jdbc:mysql://mysql:3306/db", "user", "password");
        }


        Statement stmt = con.createStatement();
        if (hasResponse) {
            return Optional.of(stmt.executeQuery(query));
        } else {
            stmt.execute(query);
            return Optional.empty();
        }
    }

    @Given("I retrieve person with {string} = {string} as {string}")
    public void iRetrievePersonFromDB(String dbColumnName, String dbColumnValue, String alias)
            throws SQLException, ClassNotFoundException {
        String query = String.format(FILTER_BY_PARAMETER_ONE_RECORD, dbColumnName, dbColumnValue);
        Optional<ResultSet> sqlResults = executeQuery(query, true);

        UserDto userDto = new UserDto();
        userDto.setName(new NameDto());

        if (sqlResults.isPresent()) {
            while (sqlResults.get().next()) {
                userDto.getName().setFirst(sqlResults.get().getString(1));
                userDto.getName().setLast(sqlResults.get().getString(2));
            }
            DataHolder.getInstance().put(alias, userDto);
        } else {
            Assert.fail("SQL Execution failed!");
        }
    }
}

