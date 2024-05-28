package org.suzyBarbie.services;

import org.junit.jupiter.api.Test;
import org.suzyBarbie.models.User;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserServiceTest {
    private final UserService userService = new UserService();


    @Test
    public void testTransferFunds(){
        Long senderId = 2L;
        Long recipientId = 4L;
        BigDecimal amount = new BigDecimal(500);
        String response = userService.transferFunds(senderId, recipientId, amount);
        assertNotNull(response);
    }
}
