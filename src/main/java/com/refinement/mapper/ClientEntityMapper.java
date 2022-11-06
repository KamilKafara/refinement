package com.refinement.mapper;

import com.refinement.dto.ClientDTO;
import com.refinement.repository.ClientEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ClientEntityMapper {
    private final DataEntityMapper dataEntityMapper;

    @Autowired
    private ClientEntityMapper(DataEntityMapper dataEntityMapper) {
        this.dataEntityMapper = dataEntityMapper;
    }

    public ClientEntity fromDTO(ClientDTO clientDTO) {
        ClientEntity clientEntity = new ModelMapper().map(clientDTO, ClientEntity.class);
        clientEntity.setDataEntities(
                clientDTO.getDataDTOList().stream()
                        .map(dataEntityMapper::fromDTO)
                        .collect(Collectors.toList()));
        return clientEntity;
    }


    public ClientDTO toDTO(ClientEntity clientEntity) {
        ClientDTO clientDTO = new ModelMapper().map(clientEntity, ClientDTO.class);
        clientDTO.setDataDTOList(
                clientEntity.getDataEntities().stream()
                        .map(dataEntityMapper::toDTO)
                        .collect(Collectors.toList()));
        return clientDTO;
    }


}
