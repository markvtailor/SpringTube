package com.spring.tests.springtube.Repositories;

import com.spring.tests.springtube.Entities.VideoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface VideoRepository extends CrudRepository<VideoEntity, Long> {
    @Transactional
     Long deleteByUniqueVideoId(String uniqueVideoId);
    @Transactional
    VideoEntity findByUniqueVideoId(String uniqueVideoId);
}
