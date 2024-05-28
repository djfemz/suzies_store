package org.suzyBarbie.repositories;

import org.junit.jupiter.api.Test;
import org.suzyBarbie.models.User;
import org.suzyBarbie.repositories.db.DatabaseConnectionManager;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    private final UserRepository userRepository = new UserRepository();

    @Test
    void saveUserTest() {
        User user = new User();
        User savedUser = userRepository.saveUser(user);
        assertNotNull(savedUser);
    }


    @Test
    public void testUpdateUser(){
        Long userId = 2L;
        Long walletId = 200L;
        Connection connection = DatabaseConnectionManager.getInstance().getConnection();
        User user = userRepository.updateUser(connection,userId, walletId);
        assertNotNull(user);
        assertEquals(200L, user.getWalletId());
    }


    @Test
    public void testFindUserById(){
        Connection connection = DatabaseConnectionManager.getInstance().getConnection();
        User user = userRepository.findById(connection, 2L).orElseThrow();
        assertNotNull(user);
        assertEquals(2L, user.getId());
    }


    @Test
    public void testFindAllUsers(){
        List<User> users = userRepository.findAll();
        System.out.println("users: "+users);
        assertNotNull(users);
        assertEquals(4, users.size());
    }

    @Test
    public void testDeleteUser(){
        Connection connection = DatabaseConnectionManager.getInstance().getConnection();
        userRepository.deleteById(1L);
        Optional<User> user = userRepository.findById(connection,1L);
        assertTrue(user.isEmpty());
    }



}