package com.refinement.repository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class DataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code1;
    private String code2;
    private String someData;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private ClientEntity clientEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode1() {
        return code1;
    }

    public void setCode1(String code1) {
        this.code1 = code1;
    }

    public String getCode2() {
        return code2;
    }

    public void setCode2(String code2) {
        this.code2 = code2;
    }

    public String getSomeData() {
        return someData;
    }

    public void setSomeData(String someData) {
        this.someData = someData;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ClientEntity getClientEntity() {
        return clientEntity;
    }

    public void setClientEntity(ClientEntity clientEntity) {
        this.clientEntity = clientEntity;
    }
}
