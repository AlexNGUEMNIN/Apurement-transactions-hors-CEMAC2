package org.example.gestiondepassementplafond.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.gestiondepassementplafond.modeles.CanalOperation;
import org.example.gestiondepassementplafond.modeles.ListeParametre;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientOperationDto {

    private Long key; // champ artificiel ajouté en base pour la PK

    private Long idClient;

    private Long idOperation;

    private Boolean presenceDoc;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    private List<ListeParametre> listeParametres;

}
