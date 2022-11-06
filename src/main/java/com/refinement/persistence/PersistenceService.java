package com.refinement.persistence;

import com.refinement.dto.DataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersistenceService {

    private final DataEntityPersistence dataEntityPersistence;
    private final ClientEntityPersistence clientEntityPersistence;

    @Autowired
    public PersistenceService(DataEntityPersistence dataEntityPersistence, ClientEntityPersistence clientEntityPersistence) {
        this.dataEntityPersistence = dataEntityPersistence;
        this.clientEntityPersistence = clientEntityPersistence;
    }

    public void save(DataDTO dataDTO) {
        dataEntityPersistence.save(dataDTO);
    }
}
