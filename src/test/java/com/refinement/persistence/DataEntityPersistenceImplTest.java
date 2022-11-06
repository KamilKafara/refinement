package com.refinement.persistence;

import com.google.common.collect.Lists;
import com.refinement.dto.ClientDTO;
import com.refinement.dto.DataDTO;
import com.refinement.mapper.ClientEntityMapper;
import com.refinement.mapper.DataEntityMapper;
import com.refinement.repository.ClientEntity;
import com.refinement.repository.DataEntity;
import com.refinement.repository.DataEntityRepository;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DataEntityPersistenceImplTest extends TestCase {

    @InjectMocks
    private DataEntityPersistenceImpl dataEntityPersistence;

    @Mock
    private ClientEntityPersistenceImpl clientEntityPersistence;
    @Mock
    private DataEntityMapper dataEntityMapper;
    @Mock
    ClientEntityMapper clientEntityMapper;

    @Mock
    private DataEntityRepository dataEntityRepository;

    private ClientDTO clientDTO;
    private ClientEntity clientEntity;
    private DataEntity dataEntity;
    private DataDTO dataDTO;

    private Timestamp updatedAt;
    private Timestamp createdAt;

    @Before
    public void setUp() {
        updatedAt = new Timestamp(System.currentTimeMillis());
        createdAt = new Timestamp(System.currentTimeMillis());
        clientDTO = createClientDTO();
        clientEntity = createClientEntity();
        dataEntity = createDataEntity();
        dataDTO = createDataDTO();
    }

    @Test
    public void testGetAllDataShouldPass() {
        //given
        List<DataEntity> expectedDataDTOs = Collections.singletonList(dataEntity);
        //when
        when(dataEntityRepository.findAll()).thenReturn(expectedDataDTOs);
        when(dataEntityMapper.toDTO(dataEntity)).thenReturn(dataDTO);
        List<DataDTO> result = dataEntityPersistence.getAllData();
        //then
        assertThat(result.size()).isEqualTo(expectedDataDTOs.size());
        assertThat(result.get(0).getId()).isEqualTo(expectedDataDTOs.get(0).getId());
        assertThat(result.get(0).getSomeData()).isEqualTo(expectedDataDTOs.get(0).getSomeData());
        assertThat(result.get(0).getCode1()).isEqualTo(expectedDataDTOs.get(0).getCode1());
        assertThat(result.get(0).getCode2()).isEqualTo(expectedDataDTOs.get(0).getCode2());
        assertThat(result.get(0).getUpdatedAt()).isEqualTo(expectedDataDTOs.get(0).getUpdatedAt());
        assertThat(result.get(0).getCreatedAt()).isEqualTo(expectedDataDTOs.get(0).getCreatedAt());
    }

    @Test
    public void testGetByIdShouldPass() {
        //given
        Long id = 123L;
        Optional<DataEntity> expectedDataDTO = Optional.ofNullable(dataEntity);
        //when
        when(dataEntityRepository.findById(id)).thenReturn(expectedDataDTO);
        when(dataEntityMapper.toDTO(dataEntity)).thenReturn(dataDTO);
        DataDTO result = dataEntityPersistence.getById(id);
        //then
        assertThat(result.getId()).isEqualTo(expectedDataDTO.get().getId());
        assertThat(result.getSomeData()).isEqualTo(expectedDataDTO.get().getSomeData());
        assertThat(result.getCode1()).isEqualTo(expectedDataDTO.get().getCode1());
        assertThat(result.getCode2()).isEqualTo(expectedDataDTO.get().getCode2());
        assertThat(result.getUpdatedAt()).isEqualTo(expectedDataDTO.get().getUpdatedAt());
        assertThat(result.getCreatedAt()).isEqualTo(expectedDataDTO.get().getCreatedAt());
    }

    @Test
    public void testSaveOrUpdateShouldPass() {
        //given
        dataEntity.setClientEntity(createClientEntity());
        dataDTO.setClientDTO(createClientDTO());
        //when
        when(dataEntityMapper.toDTO(dataEntity)).thenReturn(dataDTO);
        when(dataEntityMapper.fromDTO(dataDTO)).thenReturn(dataEntity);
        when(clientEntityPersistence.getByName(clientDTO.getName())).thenReturn(Optional.ofNullable(clientDTO));
        when(clientEntityMapper.fromDTO(clientDTO)).thenReturn(clientEntity);
        when(dataEntityMapper.fromDTO(dataDTO)).thenReturn(dataEntity);
        when(dataEntityRepository.save(dataEntity)).thenReturn(dataEntity);
        when(dataEntityMapper.toDTO(dataEntity)).thenReturn(dataDTO);
        when(clientEntityMapper.toDTO(clientEntity)).thenReturn(clientDTO);

        DataDTO result = dataEntityPersistence.saveOrUpdate(dataDTO);
        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getId()).isEqualTo(dataDTO.getId());
        assertThat(result.getSomeData()).isEqualTo(dataDTO.getSomeData());
        assertThat(result.getCode1()).isEqualTo(dataDTO.getCode1());
        assertThat(result.getCode2()).isEqualTo(dataDTO.getCode2());
        assertThat(result.getUpdatedAt()).isEqualTo(dataDTO.getUpdatedAt());
        assertThat(result.getCreatedAt()).isEqualTo(dataDTO.getCreatedAt());
    }

    private DataEntity createDataEntity() {
        DataEntity data = new DataEntity();
        data.setClientEntity(createClientEntity());
        data.setId(123L);
        data.setSomeData("someData1");
        data.setCode1("code1");
        data.setCode2("code2");
        data.setUpdatedAt(updatedAt);
        data.setCreatedAt(createdAt);
        return data;
    }

    private ClientDTO createClientDTO() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(123L);
        clientDTO.setName("SomeName");
        clientDTO.setDataDTOList(Lists.newArrayList(createDataDTO()));
        clientDTO.setUpdatedAt(updatedAt);
        return clientDTO;
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

    private ClientEntity createClientEntity() {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1L);
        clientEntity.setName("SomeName");
        clientEntity.setUpdatedAt(updatedAt);
        return clientEntity;
    }
}
