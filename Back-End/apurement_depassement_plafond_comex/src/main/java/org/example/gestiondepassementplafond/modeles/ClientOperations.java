package org.example.gestiondepassementplafond.modeles;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("client_operations")
public class ClientOperations {
    @Id
    @Column("id_client_operation")
    private Long idClientOperation; // champ artificiel ajouté en base pour la PK

    @Column("id_client")
    private Long idClient;

    @Column("id_operation")
    private Long idOperation;

    @Column("presence_doc")
    private Boolean presenceDoc;

    @Column("date_debut")
    private LocalDate dateDebut;

    @Column("date_fin")
    private LocalDate dateFin;

    @Column("nbre_envoi")
    private int nbreEnvoi;
}
