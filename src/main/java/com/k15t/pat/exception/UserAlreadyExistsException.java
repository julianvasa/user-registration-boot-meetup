package com.k15t.pat.exception;

/**
 * Exception thrown when an existing user exists in the local DB with the provided name
 *
 * @author Julian Vasa
 */
public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String username){
        super("User already exists with name: "+username);
    }
}
