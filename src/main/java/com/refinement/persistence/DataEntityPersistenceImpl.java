package com.refinement.persistence;

import com.google.common.base.Preconditions;
import com.refinement.dto.DataDTO;
import com.refinement.mapper.ClientEntityMapper;
import com.refinement.mapper.DataEntityMapper;
import com.refinement.repository.ClientEntity;
import com.refinement.repository.DataEntity;
import com.refinement.repository.DataEntityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DataEntityPersistenceImpl implements DataEntityPersistence {
    private final DataEntityRepository dataEntityRepository;
    private final ClientEntityPersistence clientEntityPersistence;
    private final DataEntityMapper dataEntityMapper;
    private final ClientEntityMapper clientEntityMapper;

    @Autowired
    public DataEntityPersistenceImpl(DataEntityRepository dataEntityRepository, ClientEntityPersistence clientEntityPersistence, DataEntityMapper dataEntityMapper, ClientEntityMapper clientEntityMapper) {
        this.dataEntityRepository = dataEntityRepository;
        this.clientEntityPersistence = clientEntityPersistence;
        this.dataEntityMapper = dataEntityMapper;
        this.clientEntityMapper = clientEntityMapper;
    }

    public List<DataDTO> getAllData() {
        return dataEntityRepository.findAll().stream()
                .map(dataEntityMapper::toDTO)
                .collect(Collectors.toList());
    }

    public DataDTO getById(Long id) {
        Optional<DataEntity> entity = dataEntityRepository.findById(id);
        if (entity.isEmpty()) {
            System.out.println("Not found dataEntity by this id " + id);
        }
        return dataEntityMapper.toDTO(entity.get());
    }

    public DataDTO save(DataDTO dataDTO) {
        Preconditions.checkNotNull(dataDTO, "DataDTO cannot be null.");
        List<DataEntity> dataEntityFromDB = dataEntityRepository.findDataEntitiesByCode1AndCode2(dataDTO.getCode1(), dataDTO.getCode2());
        if (!dataEntityFromDB.isEmpty()) {
            return update(dataDTO, dataEntityFromDB.stream().findFirst().get().getId());
        }
        DataEntity fromDTO = dataEntityMapper.fromDTO(dataDTO);
        fromDTO.setClientEntity(new ModelMapper().map(dataDTO.getClientDTO(), ClientEntity.class));
        DataEntity entityFromDB = dataEntityRepository.save(fromDTO);
        return dataEntityMapper.toDTO(entityFromDB);
    }

    public DataDTO update(DataDTO dataDTO, Long id) {
        Preconditions.checkNotNull(dataDTO, "DataDTO cannot be null.");
        DataDTO dataToUpdate = getById(id);
        dataDTO.setId(dataToUpdate.getId());
        dataToUpdate.setClientDTO(dataDTO.getClientDTO());
        dataDTO.updateTimestamp();
        DataEntity fromDTO = dataEntityMapper.fromDTO(dataDTO);
        fromDTO.setClientEntity(new ModelMapper().map(dataDTO.getClientDTO(), ClientEntity.class));
        DataEntity entityFromDB = dataEntityRepository.save(fromDTO);
        DataDTO dto = dataEntityMapper.toDTO(entityFromDB);

        dto.setClientDTO(dataDTO.getClientDTO());
        return dto;
    }


}
