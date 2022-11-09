package com.refinement.mapper;

import com.refinement.dto.ClientDTO;
import com.refinement.repository.ClientEntity;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ClientEntityMapperTest extends TestCase {
    @InjectMocks
    ClientEntityMapper clientEntityMapper;

    @Test
    public void testFromDTO() {
        //given
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1L);
        clientEntity.setName("SomeName");
        clientEntity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        //when
        ClientDTO expectedClientDTO = clientEntityMapper.toDTO(clientEntity);
        //then
        assertThat(expectedClientDTO.getId()).isEqualTo(clientEntity.getId());
        assertThat(expectedClientDTO.getUpdatedAt()).isEqualTo(clientEntity.getUpdatedAt());
        assertThat(expectedClientDTO.getName()).isEqualTo(clientEntity.getName());
    }

    @Test
    public void testToDTO() {
        //given
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(1L);
        clientDTO.setName("SomeName");
        clientDTO.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        //when
        ClientEntity expectedClientEntity = clientEntityMapper.fromDTO(clientDTO);
        //then
        assertThat(expectedClientEntity.getId()).isEqualTo(clientDTO.getId());
        assertThat(expectedClientEntity.getUpdatedAt()).isEqualTo(clientDTO.getUpdatedAt());
        assertThat(expectedClientEntity.getName()).isEqualTo(clientDTO.getName());
    }
}
