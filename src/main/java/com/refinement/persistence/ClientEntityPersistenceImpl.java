package com.refinement.persistence;

import com.refinement.dto.ClientDTO;
import com.refinement.mapper.ClientEntityMapper;
import com.refinement.repository.ClientEntity;
import com.refinement.repository.ClientEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
class ClientEntityPersistenceImpl implements ClientEntityPersistence {
    private final ClientEntityRepository clientEntityRepository;
//    private final DataEntityRepository dataEntityRepository;
    private final DataEntityPersistence dataEntityPersistence;

    @Autowired
    public ClientEntityPersistenceImpl(ClientEntityRepository clientEntityRepository,
//                                       DataEntityRepository dataEntityRepository,
                                       DataEntityPersistence dataEntityPersistence) {
        this.clientEntityRepository = clientEntityRepository;
//        this.dataEntityRepository = dataEntityRepository;
        this.dataEntityPersistence = dataEntityPersistence;
    }

    @Override
    public List<ClientDTO> getAllClient() {
        return clientEntityRepository.findAll().stream()
                .map(ClientEntityMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClientDTO getById(Integer id) {
        Optional<ClientEntity> entity = clientEntityRepository.findById(Long.valueOf(id));
        if (entity.isEmpty()) {
            System.out.println("Not found clientEntity by this id " + id);
        }
        return ClientEntityMapper.toDTO(entity.get());
    }

    @Override
    public ClientDTO getByName(String name) {
        List<ClientEntity> clientFromDB = clientEntityRepository.findClientEntityByName(name);
        if (clientFromDB.isEmpty()) {
            System.out.println("Not found clientEntity by this name " + name);
            return null;
        }
        return ClientEntityMapper.toDTO(clientFromDB.stream().findFirst().get());
    }

    @Override
    public ClientDTO saveOrUpdate(ClientDTO clientDTO, Long id) {
        return null;
    }

    @Override
    public ClientDTO save(ClientDTO clientDTO) {
        Optional<ClientDTO> clientFromDB = Optional.ofNullable(getByName(clientDTO.getName()));
        if (clientFromDB.isPresent()) {
            return update(clientDTO, clientDTO.getId());
        }
        ClientEntity clientEntity = ClientEntityMapper.fromDTO(clientDTO);
        ClientEntity entityFromDB = clientEntityRepository.save(clientEntity);
        System.out.println(clientEntity + "\n");

        entityFromDB.getDataEntities().forEach(dataEntity -> {
            dataEntity.setClientEntity(entityFromDB);
//            DataDTO save = dataEntityPersistence.save(DataEntityMapper.toDTO(dataEntity));
//            entityFromDB.setDataEntities(Lists.newArrayList(DataEntityMapper.fromDTO(save)));
        });

        return ClientEntityMapper.toDTO(entityFromDB);
    }

    @Override
    public ClientDTO update(ClientDTO clientDTO, Long id) {
        if (!Objects.equals(clientDTO.getId(), id)) {
            System.out.println("ClientDTO have different id.");
        }
        clientDTO.updateTimestamp();
        ClientEntity clientEntity = clientEntityRepository.save(ClientEntityMapper.fromDTO(clientDTO));
        return ClientEntityMapper.toDTO(clientEntity);
    }
}
