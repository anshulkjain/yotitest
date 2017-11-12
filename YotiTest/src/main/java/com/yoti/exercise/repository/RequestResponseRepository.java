package com.yoti.exercise.repository;

import com.yoti.exercise.model.RequestResponseDataEntity;
import org.springframework.data.repository.CrudRepository;

public interface RequestResponseRepository extends CrudRepository<RequestResponseDataEntity, Long> {
}
