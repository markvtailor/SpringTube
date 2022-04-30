package com.tests.security.Beans.request;



import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
public class RegistrationRequest {
    @NotBlank
    @Size(max = 24)
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(max = 36)
    private final String password;

    private Set<String> role;
    //private final boolean checked;
}
