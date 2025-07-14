package org.example.gestiondepassementplafond.modeles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("liste_parametre")
public class ListeParametre {

    @Id
    @Column("id_param")
    private Long idParam;

    private String variable;        // colonne "variable"
    private String valeur;          // colonne "valeur"

    @Column("type_d")
    private String typeD;

    @Column("id_client_operation")
    private Long idClientOp;       // FK vers client(id_client)
}
