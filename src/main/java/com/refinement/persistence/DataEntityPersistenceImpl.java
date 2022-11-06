package com.refinement.persistence;

import com.google.common.base.Preconditions;
import com.refinement.dto.ClientDTO;
import com.refinement.dto.DataDTO;
import com.refinement.mapper.DataEntityMapper;
import com.refinement.repository.DataEntity;
import com.refinement.repository.DataEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DataEntityPersistenceImpl implements DataEntityPersistence {
    private final DataEntityRepository dataEntityRepository;
    private final ClientEntityPersistence clientEntityPersistence;

    @Autowired
    public DataEntityPersistenceImpl(DataEntityRepository dataEntityRepository, ClientEntityPersistence clientEntityPersistence) {
        this.dataEntityRepository = dataEntityRepository;
        this.clientEntityPersistence = clientEntityPersistence;
    }

    public List<DataDTO> getAllData() {
        return dataEntityRepository.findAll().stream()
                .map(DataEntityMapper::toDTO)
                .collect(Collectors.toList());
    }

    public DataDTO getById(Integer id) {
        Optional<DataEntity> entity = dataEntityRepository.findById(Long.valueOf(id));
        if (entity.isEmpty()) {
            System.out.println("Not found dataEntity by this id " + id);
        }
        return DataEntityMapper.toDTO(entity.get());
    }

    public DataDTO save(DataDTO dataDTO) {
        Preconditions.checkNotNull(dataDTO, "DataDTO cannot be null.");
        List<DataEntity> dataEntityFromDB = dataEntityRepository.findDataEntitiesByCode1AndCode2(dataDTO.getCode1(), dataDTO.getCode2());
        if (!dataEntityFromDB.isEmpty()) {
            return update(dataDTO, dataDTO.getId());
        }
        DataEntity dataEntity = DataEntityMapper.fromDTO(dataDTO);
        DataEntity newDataEntityFromDB = dataEntityRepository.save(dataEntity);
        DataDTO dataDTO1 = DataEntityMapper.toDTO(newDataEntityFromDB);

        ClientDTO save = clientEntityPersistence.save(dataDTO1.getClientDTO());
//    newDataEntityFromDB.setClientEntity(save);

        System.out.println(newDataEntityFromDB);
        return dataDTO1;
    }

    public DataDTO update(DataDTO dataDTO, Long id) {
        Preconditions.checkNotNull(dataDTO, "DataDTO cannot be null.");
        if (!Objects.equals(dataDTO.getId(), id)) {
            System.out.println("DataDTO have different id.");
        }
        dataDTO.updateTimestamp();
        DataEntity entityFromDB = dataEntityRepository.save(DataEntityMapper.fromDTO(dataDTO));
        return DataEntityMapper.toDTO(entityFromDB);
    }


}
