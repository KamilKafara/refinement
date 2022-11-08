package com.refinement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataEntityRepository extends JpaRepository<DataEntity, Long> {
    List<DataEntity> findDataEntitiesByCode1AndCode2(String code1, String code2);
}
