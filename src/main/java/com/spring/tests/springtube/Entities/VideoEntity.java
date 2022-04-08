package com.spring.tests.springtube.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "videos")
public class VideoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    //int views;
    @Column(name = "metadata")
    private String metadata;
    @Column(name = "videoUniqId")
    private String uniqid;

    public VideoEntity() {
    }
}