package com.refinement.mapper;

import com.refinement.dto.DataDTO;
import com.refinement.repository.DataEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class DataEntityMapper {

    public DataEntity fromDTO(DataDTO dataDTO) {
        return new ModelMapper().map(dataDTO, DataEntity.class);
    }

    public DataDTO toDTO(DataEntity dataEntity) {
        return new ModelMapper().map(dataEntity, DataDTO.class);
    }
}
