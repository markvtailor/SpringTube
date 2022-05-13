package com.spring.tests.springtube.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "liked_videos")
public class LikedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @Column(name = "uniqueVideoId",nullable = false)
    private String uniqueVideoId;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "value",nullable = false)
    private int value;


    public LikedEntity() {
    }
}
