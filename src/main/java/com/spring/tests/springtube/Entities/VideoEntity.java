package com.spring.tests.springtube.Entities;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "Videos")
public class VideoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @Column(name = "name",unique = true)
    private String name;
    @Column(name = "uniqueVideoId", updatable = false, unique = true, nullable = false)
    private String uniqueVideoId;
    @Column(name = "description")
    private String description;
    @Column(name = "views")
    private int views = 0;
    @Column(name = "metadata")
    private String metadata;


    public VideoEntity() {
    }
}