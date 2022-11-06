package com.refinement.mapper;

import com.refinement.dto.DataDTO;
import com.refinement.repository.DataEntity;

public class DataEntityMapper {

    private DataEntityMapper() {
    }

    public static DataEntity fromDTO(DataDTO dataDTO) {
        DataEntity dataEntity = new DataEntity();
        dataEntity.setId(dataDTO.getId());
        dataEntity.setSomeData(dataDTO.getSomeData());
        dataEntity.setCode1(dataDTO.getCode1());
        dataEntity.setCode2(dataDTO.getCode2());
        dataEntity.setCreatedAt(dataDTO.getCreatedAt());
        dataEntity.setUpdatedAt(dataDTO.getUpdatedAt());
        return dataEntity;
    }

    public static DataDTO toDTO(DataEntity dataEntity) {
        DataDTO dataDTO = new DataDTO();
        dataDTO.setId(dataEntity.getId());
        dataDTO.setSomeData(dataEntity.getSomeData());
        dataDTO.setCode1(dataEntity.getCode1());
        dataDTO.setCode2(dataEntity.getCode2());
        dataDTO.setCreatedAt(dataEntity.getCreatedAt());
        dataDTO.setUpdatedAt(dataEntity.getUpdatedAt());
        return dataDTO;
    }
}
