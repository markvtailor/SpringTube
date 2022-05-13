package com.spring.tests.springtube.Beans.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class LikeRequest {
    private String user;

    private String videoId;

    private String value;
}
