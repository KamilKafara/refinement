package com.refinement.persistence;

import com.google.common.base.Preconditions;
import com.refinement.dto.ClientDTO;
import com.refinement.dto.DataDTO;
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

    @Autowired
    public DataEntityPersistenceImpl(DataEntityRepository dataEntityRepository,
                                     ClientEntityPersistence clientEntityPersistence,
                                     DataEntityMapper dataEntityMapper) {
        this.dataEntityRepository = dataEntityRepository;
        this.clientEntityPersistence = clientEntityPersistence;
        this.dataEntityMapper = dataEntityMapper;
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
            DataEntity dataEntity = prepareDataEntityToUpdate(dataDTO, dataEntityFromDB);
            return update(dataDTO, dataEntity.getId());
        }
        DataEntity entityToSave = dataEntityMapper.fromDTO(dataDTO);
        findExistingClient(dataDTO, entityToSave);
        DataEntity entityFromDB = dataEntityRepository.save(entityToSave);

        return setupDTOAfterSave(entityFromDB);
    }

    private static DataDTO setupDTOAfterSave(DataEntity entityFromDB) {
        DataDTO dto = new ModelMapper().map(entityFromDB, DataDTO.class);
        dto.setClientDTO(new ModelMapper().map(entityFromDB.getClientEntity(), ClientDTO.class));
        return dto;
    }

    private static DataEntity prepareDataEntityToUpdate(DataDTO dataDTO, List<DataEntity> dataEntityFromDB) {
        DataEntity dataEntity = dataEntityFromDB.stream().findFirst().get();
        dataDTO.setClientDTO(new ModelMapper().map(dataEntity.getClientEntity(), ClientDTO.class));
        return dataEntity;
    }

    public DataDTO update(DataDTO dataDTO, Long id) {
        Preconditions.checkNotNull(dataDTO, "DataDTO cannot be null.");
        DataEntity fromDTO = dataEntityMapper.fromDTO(dataDTO);
        findExistingClient(dataDTO, fromDTO);
        prepareDataToUpdate(dataDTO, id, fromDTO);
        DataEntity entityFromDB = dataEntityRepository.save(fromDTO);
        return dataEntityMapper.toDTO(entityFromDB);
    }

    private static void prepareDataToUpdate(DataDTO dataDTO, Long id, DataEntity fromDTO) {
        dataDTO.updateTimestamp();
        fromDTO.setId(id);
        fromDTO.setClientEntity(new ModelMapper().map(dataDTO.getClientDTO(), ClientEntity.class));
    }

    private void findExistingClient(DataDTO dataDTO, DataEntity fromDTO) {
        Optional<ClientDTO> findClient = Optional.ofNullable(clientEntityPersistence.getByName(dataDTO.getClientDTO().getName()));
        if (findClient.isPresent()) {
            fromDTO.setClientEntity(new ModelMapper().map(findClient, ClientEntity.class));
        } else {
            fromDTO.setClientEntity(new ModelMapper().map(dataDTO.getClientDTO(), ClientEntity.class));
        }
    }


}
