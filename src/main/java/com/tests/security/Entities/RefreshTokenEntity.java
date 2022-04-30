package com.tests.security.Entities;

import com.tests.security.Models.UserDetailsImpl;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "refreshTokens")
public class RefreshTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @Column(nullable = false ,unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expirationDate;

}
