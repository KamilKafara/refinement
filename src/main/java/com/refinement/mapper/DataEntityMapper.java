package com.refinement.mapper;

import com.refinement.dto.DataDTO;
import com.refinement.repository.DataEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DataEntityMapper {

    public DataEntity fromDTO(DataDTO dataDTO) {
        DataEntity dataEntity = new ModelMapper().map(dataDTO, DataEntity.class);
        if (Objects.nonNull(dataDTO.getClientDTO())) {
//            dataEntity.setClientEntity(ClientEntityMapper.fromDTO(dataDTO.getClientDTO()));
        }
        return dataEntity;
    }

    public DataDTO toDTO(DataEntity dataEntity) {
        DataDTO dataDTO = new ModelMapper().map(dataEntity, DataDTO.class);
        if (Objects.nonNull(dataEntity.getClientEntity())) {
//            dataDTO.setClientDTO(ClientEntityMapper.toDTO(dataEntity.getClientEntity()));
        }
        return dataDTO;
    }
}
