package com.refinement.mapper;

import com.refinement.dto.DataDTO;
import com.refinement.repository.DataEntity;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)

public class DataEntityMapperTest extends TestCase {
    @InjectMocks
    private DataEntityMapper dataEntityMapper;

    private DataDTO dataDTO;
    private Timestamp updatedAt;
    private Timestamp createdAt;

    @Before
    public void setUp() {
        updatedAt = new Timestamp(System.currentTimeMillis());
        createdAt = new Timestamp(System.currentTimeMillis());
        dataDTO = createDataDTO();
    }

    @Test
    public void testFromDTO() {
        //given
        DataEntity dataEntity = createDataEntity();
        //when
        DataDTO expectedDataDTO = dataEntityMapper.toDTO(dataEntity);
        //then
        assertThat(expectedDataDTO.getId()).isEqualTo(dataDTO.getId());
        assertThat(expectedDataDTO.getUpdatedAt()).isEqualTo(dataDTO.getUpdatedAt());
        assertThat(expectedDataDTO.getCreatedAt()).isEqualTo(dataDTO.getCreatedAt());
        assertThat(expectedDataDTO.getCode1()).isEqualTo(dataDTO.getCode1());
        assertThat(expectedDataDTO.getCode2()).isEqualTo(dataDTO.getCode2());
        assertThat(expectedDataDTO.getSomeData()).isEqualTo(dataDTO.getSomeData());
    }

    @Test
    public void testToDTO() {
        //given
        DataDTO dataDTO = createDataDTO();
        //when
        DataEntity expectedClientEntity = dataEntityMapper.fromDTO(dataDTO);
        //then
        assertThat(expectedClientEntity.getId()).isEqualTo(dataDTO.getId());
        assertThat(expectedClientEntity.getUpdatedAt()).isEqualTo(dataDTO.getUpdatedAt());
        assertThat(expectedClientEntity.getCreatedAt()).isEqualTo(dataDTO.getCreatedAt());
        assertThat(expectedClientEntity.getCode1()).isEqualTo(dataDTO.getCode1());
        assertThat(expectedClientEntity.getCode2()).isEqualTo(dataDTO.getCode2());
        assertThat(expectedClientEntity.getSomeData()).isEqualTo(dataDTO.getSomeData());
    }

    private DataEntity createDataEntity() {
        DataEntity data = new DataEntity();
        data.setId(123L);
        data.setSomeData("someData1");
        data.setCode1("code1");
        data.setCode2("code2");
        data.setUpdatedAt(updatedAt);
        data.setCreatedAt(createdAt);
        return data;
    }


    private DataDTO createDataDTO() {
        DataDTO data = new DataDTO();
        data.setId(123L);
        data.setSomeData("someData1");
        data.setCode1("code1");
        data.setCode2("code2");
        data.setUpdatedAt(updatedAt);
        data.setCreatedAt(createdAt);
        return data;
    }


}
