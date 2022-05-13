package com.spring.tests.springtube.Repositories;

import com.spring.tests.springtube.Entities.CommentEntity;
import com.spring.tests.springtube.Entities.VideoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CommentRepository extends CrudRepository<CommentEntity,Long> {
    @Transactional
    Iterable<CommentEntity> findAllByVideo(String uniqueVideoId);
}

