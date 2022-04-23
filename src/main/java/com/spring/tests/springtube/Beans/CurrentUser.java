package com.spring.tests.springtube.Beans;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CurrentUser {
    private String username;
    private String email;
    private boolean enabled;
}
