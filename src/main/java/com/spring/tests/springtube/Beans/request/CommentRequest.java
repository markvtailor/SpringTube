package com.spring.tests.springtube.Beans.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class CommentRequest {

    private String author;

    private String body;

    private String videoId;
}
