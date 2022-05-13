package com.spring.tests.springtube.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Views")
public class ViewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @Column(name = "uniqueVideoId", updatable = false, unique = true, nullable = false)
    private String uniqueVideoId;
    @Column(name = "views")
    private int views;


    public ViewEntity() {
    }
}
