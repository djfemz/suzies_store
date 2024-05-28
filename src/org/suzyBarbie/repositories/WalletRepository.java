package org.suzyBarbie.repositories;

import org.suzyBarbie.exception.UserUpdateFailedException;
import org.suzyBarbie.models.User;
import org.suzyBarbie.models.Wallet;
import org.suzyBarbie.repositories.db.DatabaseConnectionManager;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


@SuppressWarnings(value = {"all"})
public class WalletRepository {

    public Wallet save(Wallet wallet){
        try(Connection connection = DatabaseConnectionManager.getInstance().getConnection()){
            var databaseManager = DatabaseConnectionManager.getInstance();
            String sql = "INSERT into wallets (id, balance) VALUES (?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            Long id = databaseManager.generateId("wallets");
            wallet.setId(id);
            statement.setLong(1, wallet.getId());
            statement.setBigDecimal(2, wallet.getBalance());
            statement.executeUpdate();
            return wallet;
        }catch (SQLException exception){
            throw new RuntimeException(exception.getMessage());
        }
    }

    public Optional<Wallet> findById(Connection connection, Long id) {
        try{
            String sql = "SELECT * from wallets where id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            Long walletId = resultSet.getLong("id");
            BigDecimal balance = resultSet.getBigDecimal("balance");
            Wallet wallet = new Wallet();
            wallet.setId(walletId);
            wallet.setBalance(balance);
            return Optional.of(wallet);
        }catch (SQLException exception){
            System.err.println("error: "+exception.getMessage());
            return Optional.empty();
        }

    }


    public Wallet updateWallet(Connection connection, Long walletId, BigDecimal balance){
        try{
            String sql = "UPDATE wallets SET balance=? WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBigDecimal(1, balance);
            statement.setLong(2, walletId);
            statement.executeUpdate();
            return findById(connection, walletId).orElseThrow();
        }catch (SQLException exception){
            throw new UserUpdateFailedException(exception.getMessage());
        }
    }

}
