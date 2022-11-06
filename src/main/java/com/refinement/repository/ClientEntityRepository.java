package com.refinement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientEntityRepository extends JpaRepository<ClientEntity, Long> {
    List<ClientEntity> findClientEntityByName(String name);
}
