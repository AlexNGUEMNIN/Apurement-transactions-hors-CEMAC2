package org.example.gestiondepassementplafond.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.gestiondepassementplafond.modeles.Canal;
import org.example.gestiondepassementplafond.modeles.ListeOperations;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CanalOperationDto {

    private Long key;

    private Long idCanal;

    private Integer idOperation;

    private String comment; // colonne "comment"

    private ListeOperations operations;

    private Canal canal;

}
