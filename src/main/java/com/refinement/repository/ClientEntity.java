package com.refinement.repository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String name;
    private Timestamp updatedAt;
    @OneToMany(
            mappedBy = "clientEntity",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private List<DataEntity> dataEntities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<DataEntity> getDataEntities() {
        return dataEntities;
    }

    public void setDataEntities(List<DataEntity> dataEntities) {
        this.dataEntities = dataEntities;
    }
}
