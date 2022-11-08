package com.refinement.persistence;

import com.refinement.dto.ClientDTO;

import java.util.List;
import java.util.Optional;

public interface ClientEntityPersistence {
    List<ClientDTO> getAllClient();

    ClientDTO getById(Long id);

    ClientDTO saveOrUpdate(ClientDTO clientDTO);

    ClientDTO update(ClientDTO clientDTO, Long id);

    Optional<ClientDTO> getByName(String name);
}
