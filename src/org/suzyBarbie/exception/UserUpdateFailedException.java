package org.suzyBarbie.exception;

import org.suzyBarbie.models.User;

public class UserUpdateFailedException extends RuntimeException{

    public UserUpdateFailedException(String message){
        super(message);
    }
}
