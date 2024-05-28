package org.suzyBarbie.repositories.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


@SuppressWarnings({"all"})
public class DatabaseConnectionManager {

    private Connection connection;
    private DatabaseConnectionManager(){}

    private static final class SINGLETON{
        private static final DatabaseConnectionManager databaseConnectionManager =
                new DatabaseConnectionManager();
    }


    public static DatabaseConnectionManager getInstance(){
        return SINGLETON.databaseConnectionManager;
    }

    public Long generateId(String tableName) {
        try(Connection connection = DatabaseConnectionManager.getInstance().getConnection()){
            String sql = "SELECT max(id) FROM "+tableName;
            var statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            Long lastIdGenerated = resultSet.getLong(1);
            return lastIdGenerated + 1;
        }catch (SQLException exception){
            throw new RuntimeException(exception.getMessage());
        }
    }


    public Connection getConnection() {
        if (connection!=null) return connection;
        String url ="jdbc:postgresql://localhost:5432/suzies_store"; // TODO: mysql--> jdbc:mysql://localhost:3306,
        String username ="postgres";
        String password ="postgres";
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
