package com.refinement.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Objects;

@Setter
@Getter
@Builder
public class DataDTO {
    private Long id;
    private ClientDTO clientDTO;
    private String code1;
    private String code2;
    private String someData;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public DataDTO() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        this.createdAt = Objects.requireNonNullElse(createdAt, timestamp);
    }

    public DataDTO(Long id, ClientDTO clientDTO, String code1, String code2, String someData, Timestamp updatedAt, Timestamp createdAt) {
        this.id = id;
        this.clientDTO = clientDTO;
        this.code1 = code1;
        this.code2 = code2;
        this.someData = someData;
        this.updatedAt = updatedAt;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        this.createdAt = Objects.requireNonNullElse(createdAt, timestamp);
    }

    public void updateTimestamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        this.updatedAt = Objects.requireNonNull(timestamp);
    }
}
