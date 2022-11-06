package com.refinement.repository;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
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
}
