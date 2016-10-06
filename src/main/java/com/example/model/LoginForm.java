package com.example.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by sungmen999 on 10/6/2016 AD.
 */

@Data
public class LoginForm {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
}
