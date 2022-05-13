package com.spring.tests.springtube.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@Entity
@Table(name = "commentaries")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(name ="author",nullable = false )
    private String author;

    @Column(name = "comment_body",nullable = false)
    private String body;

    @Column(name = "date",nullable = false,updatable = false)
    private Date date;

    @Column(name = "video",nullable = false, updatable = false)
    private String video;



}
