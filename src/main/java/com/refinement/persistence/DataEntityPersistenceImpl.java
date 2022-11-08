package com.refinement.persistence;

import com.google.common.base.Preconditions;
import com.refinement.dto.ClientDTO;
import com.refinement.dto.DataDTO;
import com.refinement.mapper.ClientEntityMapper;
import com.refinement.mapper.DataEntityMapper;
import com.refinement.repository.DataEntity;
import com.refinement.repository.DataEntityRepository;
import lombok.SneakyThrows;
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
    public DataEntityPersistenceImpl(DataEntityRepository dataEntityRepository,
                                     ClientEntityPersistence clientEntityPersistence,
                                     DataEntityMapper dataEntityMapper, ClientEntityMapper clientEntityMapper) {
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

    @SneakyThrows
    public DataDTO getById(Long id) {
        Optional<DataEntity> entity = dataEntityRepository.findById(id);
        Preconditions.checkNotNull(entity, " Not found dataEntity by this id " + id);
        return dataEntityMapper.toDTO(entity.get());
    }

    public DataDTO saveOrUpdate(DataDTO dataDTO) {
        Preconditions.checkNotNull(dataDTO, "DataDTO cannot be null.");
        List<DataEntity> dataEntityFromDB = dataEntityRepository.findDataEntitiesByCode1AndCode2(dataDTO.getCode1(), dataDTO.getCode2());
        if (!dataEntityFromDB.isEmpty()) {
            DataEntity dataEntity = prepareDataEntityToUpdate(dataDTO, dataEntityFromDB);
            return update(dataDTO, dataEntity.getId());
        }
        DataEntity entityToSave = dataEntityMapper.fromDTO(dataDTO);
        findExistingClient(dataDTO, entityToSave);
        DataEntity entityFromDB = dataEntityRepository.save(entityToSave);

        return setupDTOAfterSave(entityFromDB);
    }

    private DataDTO setupDTOAfterSave(DataEntity entityFromDB) {
        DataDTO dto = dataEntityMapper.toDTO(entityFromDB);
        if (entityFromDB.getClientEntity() != null) {
            dto.setClientDTO(clientEntityMapper.toDTO(entityFromDB.getClientEntity()));
        }
        return dto;
    }

    private DataEntity prepareDataEntityToUpdate(DataDTO dataDTO, List<DataEntity> dataEntityFromDB) {
        DataEntity dataEntity = dataEntityFromDB.stream().findFirst().get();
        if (dataEntity.getClientEntity() != null) {
            dataDTO.setClientDTO(clientEntityMapper.toDTO(dataEntity.getClientEntity()));
        }
        return dataEntity;
    }

    public DataDTO update(DataDTO dataDTO, Long id) {
        Preconditions.checkNotNull(dataDTO, "DataDTO cannot be null.");
        dataDTO.updateTimestamp();
        DataEntity fromDTO = dataEntityMapper.fromDTO(dataDTO);
        findExistingClient(dataDTO, fromDTO);
        prepareDataToUpdate(dataDTO, id, fromDTO);
        DataEntity entityFromDB = dataEntityRepository.save(fromDTO);
        return dataEntityMapper.toDTO(entityFromDB);
    }

    private void prepareDataToUpdate(DataDTO dataDTO, Long id, DataEntity fromDTO) {
        ClientDTO clientDTO = dataDTO.getClientDTO();
        if (clientDTO != null) {
            clientDTO.updateTimestamp();
            fromDTO.setId(id);
            fromDTO.setClientEntity(clientEntityMapper.fromDTO(clientDTO));
        }
    }

    private void findExistingClient(DataDTO dataDTO, DataEntity fromDTO) {
        if ((dataDTO == null) && (dataDTO.getClientDTO() == null)) {
            return;
        }
        Optional<ClientDTO> findClient = clientEntityPersistence.getByName(dataDTO.getClientDTO().getName());
        if (findClient.isPresent()) {
            fromDTO.setClientEntity(clientEntityMapper.fromDTO(findClient.get()));
        } else {
            fromDTO.setClientEntity(clientEntityMapper.fromDTO(dataDTO.getClientDTO()));
        }
    }
}
