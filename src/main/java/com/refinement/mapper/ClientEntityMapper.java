package com.refinement.mapper;

import com.refinement.dto.ClientDTO;
import com.refinement.repository.ClientEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ClientEntityMapper {

    public ClientEntity fromDTO(ClientDTO clientDTO) {
        return new ModelMapper().map(clientDTO, ClientEntity.class);
    }

    public ClientDTO toDTO(ClientEntity clientEntity) {
        return new ModelMapper().map(clientEntity, ClientDTO.class);
    }
}
