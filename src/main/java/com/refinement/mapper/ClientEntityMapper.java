package com.refinement.mapper;

import com.refinement.dto.ClientDTO;
import com.refinement.repository.ClientEntity;
import org.modelmapper.ModelMapper;

import java.util.stream.Collectors;

public class ClientEntityMapper {
    private ClientEntityMapper() {
    }

    public static ClientEntity fromDTO(ClientDTO clientDTO) {
        ClientEntity clientEntity = new ModelMapper().map(clientDTO, ClientEntity.class);
        clientEntity.setDataEntities(
                clientDTO.getDataDTOList().stream()
                        .map(DataEntityMapper::fromDTO)
                        .collect(Collectors.toList()));
        return clientEntity;
    }


    public static ClientDTO toDTO(ClientEntity clientEntity) {
        ClientDTO clientDTO = new ModelMapper().map(clientEntity, ClientDTO.class);
        clientDTO.setDataDTOList(
                clientEntity.getDataEntities().stream()
                        .map(DataEntityMapper::toDTO)
                        .collect(Collectors.toList()));
        return clientDTO;
    }


}
