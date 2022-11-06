package com.refinement.persistence;

import com.refinement.dto.DataDTO;

import java.util.List;

public interface DataEntityPersistence {
    List<DataDTO> getAllData();

    DataDTO getById(Integer id);

    DataDTO save(DataDTO dataDTO);

    DataDTO update(DataDTO dataDTO, Long id);
}
