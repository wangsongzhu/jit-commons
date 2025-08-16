package com.nonisystems.jit.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements Serializable {

    private String id;
    private String email;
    private String password;
    private String passwordHash;
    private Timestamp signupDate;
    private byte verified;
    private Timestamp lastLogin;
}
