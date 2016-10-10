package com.example.security.transfer;

import lombok.Data;

/**
 * Simple placeholder for info extracted from the JWT
 *
 * Created by sungmen999 on 10/10/2016 AD.
 */

@Data
public class JwtUserDto {
    private Long id;
    private String username;
    private String role;
}
