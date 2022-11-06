package com.refinement.persistence;

import com.google.common.base.Preconditions;
import com.refinement.dto.DataDTO;
import com.refinement.mapper.DataEntityMapper;
import com.refinement.repository.ClientEntity;
import com.refinement.repository.DataEntity;
import com.refinement.repository.DataEntityRepository;
import org.modelmapper.ModelMapper;
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
    private final DataEntityMapper dataEntityMapper;

    @Autowired
    public DataEntityPersistenceImpl(DataEntityRepository dataEntityRepository, ClientEntityPersistence clientEntityPersistence, DataEntityMapper dataEntityMapper) {
        this.dataEntityRepository = dataEntityRepository;
        this.clientEntityPersistence = clientEntityPersistence;
        this.dataEntityMapper = dataEntityMapper;
    }

    public List<DataDTO> getAllData() {
        return dataEntityRepository.findAll().stream()
                .map(dataEntityMapper::toDTO)
                .collect(Collectors.toList());
    }

    public DataDTO getById(Integer id) {
        Optional<DataEntity> entity = dataEntityRepository.findById(Long.valueOf(id));
        if (entity.isEmpty()) {
            System.out.println("Not found dataEntity by this id " + id);
        }
        return dataEntityMapper.toDTO(entity.get());
    }

    public DataDTO save(DataDTO dataDTO) {
        Preconditions.checkNotNull(dataDTO, "DataDTO cannot be null.");
        List<DataEntity> dataEntityFromDB = dataEntityRepository.findDataEntitiesByCode1AndCode2(dataDTO.getCode1(), dataDTO.getCode2());
        if (!dataEntityFromDB.isEmpty()) {
            return update(dataDTO, dataDTO.getId());
        }
        DataEntity fromDTO = dataEntityMapper.fromDTO(dataDTO);
        fromDTO.setClientEntity(new ModelMapper().map(dataDTO.getClientDTO(), ClientEntity.class));
        DataEntity dataEntity = dataEntityRepository.save(fromDTO);
        return dataEntityMapper.toDTO(dataEntity);

    }

    public DataDTO update(DataDTO dataDTO, Long id) {
        Preconditions.checkNotNull(dataDTO, "DataDTO cannot be null.");
        if (!Objects.equals(dataDTO.getId(), id)) {
            System.out.println("DataDTO have different id.");
        }
        dataDTO.updateTimestamp();
        DataEntity entityFromDB = dataEntityRepository.save(dataEntityMapper.fromDTO(dataDTO));
        return dataEntityMapper.toDTO(entityFromDB);
    }


}
