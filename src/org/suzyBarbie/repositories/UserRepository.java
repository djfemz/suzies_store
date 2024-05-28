package org.suzyBarbie.repositories;

import org.suzyBarbie.exception.UserUpdateFailedException;
import org.suzyBarbie.models.User;
import org.suzyBarbie.repositories.db.DatabaseConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings(value = {"all"})
public class UserRepository {




    public User saveUser(User user){
        String sql = "insert into users (id, wallet_id) values (?,?)";
        DatabaseConnectionManager databaseConnectionManager = DatabaseConnectionManager.getInstance();
        try(Connection connection = databaseConnectionManager.getConnection()){

            var preparedStatement = connection.prepareStatement(sql);
            Long id = databaseConnectionManager.generateId("users");
            preparedStatement.setLong(1, id);
            preparedStatement.setObject(2, user.getWalletId());
            preparedStatement.execute();
            return getUserBy(connection, id);
        }catch (SQLException exception){
            System.err.println("Error: "+exception.getMessage());
            throw new RuntimeException("Failed to connect to database");
        }
    }



    private User getUserBy(Connection connection, Long id){
        String sql = "select * from users where id=?";
        try{
            var preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Long userId = resultSet.getLong(1);
            Long walletId = resultSet.getLong(2);
            User user = new User();
            user.setId(userId);
            user.setWalletId(walletId);
            return user;
        }catch (SQLException exception){
            return null;
        }
    }


    public User updateUser(Connection connection, Long userId, Long walletId){
        try{
            String sql = "UPDATE users SET wallet_id=? WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, walletId);
            statement.setLong(2, userId);
            statement.executeUpdate();
            return getUserBy(connection, userId);
        }catch (SQLException exception){
            throw new UserUpdateFailedException(exception.getMessage());
        }
    }


    public Optional<User> findById(Connection connection, Long id) {
        User user =  getUserBy(connection, id);
        if (user!=null) return Optional.of(user);
        return Optional.empty();
    }

    public void deleteById(Long id) {
        try(Connection connection = DatabaseConnectionManager.getInstance().getConnection()){
            String sql = "DELETE FROM users WHERE id=?";
            var statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.executeUpdate();
        }catch (SQLException exception){
            throw new RuntimeException("Failed to delete user");
        }
    }

    public List<User> findAll() {

        try(Connection connection = DatabaseConnectionManager.getInstance().getConnection()){
            String sql = "SELECT * FROM users";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            return extractUsersFrom(resultSet);
        }catch (SQLException exception){
            return null;
        }
    }

    private List<User> extractUsersFrom(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();
        while (resultSet.next()){
            Long id = resultSet.getLong("id");
            Long walletId = resultSet.getLong("wallet_id");
            User user = new User();
            user.setId(id);
            user.setWalletId(walletId);
            users.add(user);
        }
        return users;
    }
}
