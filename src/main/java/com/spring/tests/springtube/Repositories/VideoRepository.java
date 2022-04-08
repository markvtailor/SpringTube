package com.spring.tests.springtube.Repositories;

import com.spring.tests.springtube.Entities.VideoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends CrudRepository<VideoEntity, Long> {
}
