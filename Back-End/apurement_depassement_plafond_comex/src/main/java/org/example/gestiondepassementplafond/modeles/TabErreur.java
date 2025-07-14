package org.example.gestiondepassementplafond.modeles;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("tab_erreur")
public class TabErreur {

    @Id
    @Column("id_tab_erreur")
    private Long idTabErreur;

    @Column("id_operation")
    private Long idOperation;

    @Column("type_operation")
    private Integer typeOperation;

    @Column("commentaire")
    private String commentaire;

    @Column("mat_client")
    private String matClient;

    @Column("nom_client")
    private String nomClient;

    @Column("date_enregistrement")
    private LocalDate dateEnregistrement;

    @Column("username")
    private String username;
}
