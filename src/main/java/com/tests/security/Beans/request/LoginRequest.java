package com.tests.security.Beans.request;


import lombok.*;

import javax.validation.constraints.NotBlank;


@Getter
@EqualsAndHashCode
public class LoginRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
