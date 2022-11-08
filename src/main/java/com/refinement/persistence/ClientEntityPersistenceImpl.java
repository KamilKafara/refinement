package com.refinement.persistence;

import com.refinement.dto.ClientDTO;
import com.refinement.mapper.ClientEntityMapper;
import com.refinement.repository.ClientEntity;
import com.refinement.repository.ClientEntityRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.List;
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
        return clientEntityMapper.toDTO(entity.get());
    }

    @Override
    public Optional<ClientDTO> getByName(String name) {
        List<ClientEntity> clientFromDB = clientEntityRepository.findClientEntityByName(name);
        if (clientFromDB.isEmpty()) {
            return Optional.empty();
        }
        Optional<ClientEntity> clientEntity = clientFromDB.stream().findFirst();
        if (clientEntity.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(clientEntityMapper.toDTO(clientEntity.get()));
    }

    @Override
    public ClientDTO saveOrUpdate(ClientDTO clientDTO) {
        Optional<ClientDTO> clientFromDB = getByName(clientDTO.getName());
        if (clientFromDB.isPresent()) {
            return update(clientDTO, clientFromDB.get().getId());
        }
        ClientEntity clientEntity = clientEntityRepository.save(clientEntityMapper.fromDTO(clientDTO));
        return clientEntityMapper.toDTO(clientEntity);
    }

    @SneakyThrows
    @Override
    public ClientDTO update(ClientDTO clientDTO, Long id) {
        if (clientDTO.getId() != null) {
            throw new ValidationException("Ids are not equals.");
        }
        clientDTO.updateTimestamp();
        clientDTO.setId(id);
        ClientEntity clientEntity = clientEntityRepository.save(clientEntityMapper.fromDTO(clientDTO));
        return clientEntityMapper.toDTO(clientEntity);
    }
}
