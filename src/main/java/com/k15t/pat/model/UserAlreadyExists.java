package com.k15t.pat.model;

import lombok.Builder;
import lombok.Data;

/**
 * Simple POJO to hold the response to the client in case of user already exists
 *
 * @author Julian Vasa
 */
@Data
@Builder
public class UserAlreadyExists {
    private String code;
    private String description;
}
