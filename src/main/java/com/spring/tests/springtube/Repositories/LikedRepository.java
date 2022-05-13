package com.spring.tests.springtube.Repositories;

import com.spring.tests.springtube.Entities.LikedEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public interface LikedRepository extends CrudRepository<LikedEntity,Long> {
    @Transactional
    Iterable<LikedEntity> findByUniqueVideoId(String uniqueVideoId);
    @Transactional
    Iterable<LikedEntity> findByUsername(String username);
    @Transactional
    LikedEntity findByUniqueVideoIdAndUsername(String uniqueVideoId, String username);
    @Transactional
    Iterable<LikedEntity> findAllByUniqueVideoId(String uniqueVideoId);
    @Transactional
    Iterable<LikedEntity> findAllByUsernameAndValue(String username, int value);

}
