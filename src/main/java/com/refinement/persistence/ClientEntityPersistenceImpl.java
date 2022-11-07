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
    private final ClientEntityMapper clientEntityMapper;

    @Autowired
    public ClientEntityPersistenceImpl(ClientEntityRepository clientEntityRepository,
                                       ClientEntityMapper clientEntityMapper) {
        this.clientEntityRepository = clientEntityRepository;
        this.clientEntityMapper = clientEntityMapper;
    }

    @Override
    public List<ClientDTO> getAllClient() {
        return clientEntityRepository.findAll().stream()
                .map(clientEntityMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClientDTO getById(Long id) {
        Optional<ClientEntity> entity = clientEntityRepository.findById(id);
        if (entity.isEmpty()) {
            System.out.println("Not found clientEntity by this id " + id);
        }
        return clientEntityMapper.toDTO(entity.get());
    }

    @Override
    public ClientDTO getByName(String name) {
        List<ClientEntity> clientFromDB = clientEntityRepository.findClientEntityByName(name);
        if (clientFromDB.isEmpty()) {
            System.out.println("Not found clientEntity by this name " + name);
            return null;
        }
        return clientEntityMapper.toDTO(clientFromDB.stream().findFirst().get());
    }

    @Override
    public ClientDTO saveOrUpdate(ClientDTO clientDTO, Long id) {
        return null;
    }

    @Override
    public ClientDTO save(ClientDTO clientDTO) {
        Optional<ClientDTO> clientFromDB = Optional.ofNullable(getByName(clientDTO.getName()));
        if (clientFromDB.isPresent()) {
            return update(clientDTO, clientFromDB.get().getId());
        }
        ClientEntity clientEntity = clientEntityRepository.save(clientEntityMapper.fromDTO(clientDTO));
        return clientEntityMapper.toDTO(clientEntity);
    }

    @Override
    public ClientDTO update(ClientDTO clientDTO, Long id) {
        if (!Objects.equals(clientDTO.getId(), id)) {
            System.out.println("ClientDTO have different id.");
        }
        clientDTO.updateTimestamp();
        clientDTO.setId(id);
        ClientEntity clientEntity = clientEntityRepository.save(clientEntityMapper.fromDTO(clientDTO));
        return clientEntityMapper.toDTO(clientEntity);
    }
}
