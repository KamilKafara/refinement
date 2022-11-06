package com.refinement.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ClientDTO {
    private Long id;
    private String name;
    private Timestamp updatedAt;
    @JsonIgnore
    private List<DataDTO> dataDTOList;

    public void updateTimestamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        this.updatedAt = Objects.requireNonNull(timestamp);
    }
}
