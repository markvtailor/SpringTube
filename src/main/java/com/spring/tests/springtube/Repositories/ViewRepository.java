package com.spring.tests.springtube.Repositories;

import com.spring.tests.springtube.Entities.ViewEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ViewRepository extends CrudRepository<ViewEntity,Long> {
    @Transactional
    ViewEntity findByUniqueVideoId(String uniqueVideoId);

}
