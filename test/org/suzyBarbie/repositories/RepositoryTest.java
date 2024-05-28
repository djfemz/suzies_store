package org.suzyBarbie.repositories;

import org.junit.jupiter.api.Test;
import org.suzyBarbie.repositories.UserRepository;
import org.suzyBarbie.repositories.db.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RepositoryTest {

    @Test
    public void testDatabaseConnection(){
        try(Connection connection = DatabaseConnectionManager.getInstance().getConnection()){
            assertNotNull(connection);
            System.out.println("connection--> "+connection);
        }catch (SQLException ex){
            assertNull(ex);
            ex.printStackTrace();
        }
    }
}
