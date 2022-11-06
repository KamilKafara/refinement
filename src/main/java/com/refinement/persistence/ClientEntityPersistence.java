package com.refinement.persistence;

import com.refinement.dto.ClientDTO;

import java.util.List;

public interface ClientEntityPersistence {
    List<ClientDTO> getAllClient();

    ClientDTO getById(Long id);

    ClientDTO saveOrUpdate(ClientDTO clientDTO, Long id);

    ClientDTO save(ClientDTO clientDTO);

    ClientDTO update(ClientDTO clientDTO, Long id);

    ClientDTO getByName(String name);
}
