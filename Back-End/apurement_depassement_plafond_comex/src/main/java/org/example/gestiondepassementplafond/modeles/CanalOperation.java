package org.example.gestiondepassementplafond.modeles;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("canal_operation")
public class CanalOperation {

    @Id
    private Long key;

    @Column("id_canal")
    private Long idCanal;

    @Column("id_operation")
    private Integer idOperation;

    private String comment; // colonne "comment"
}