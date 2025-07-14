package org.example.gestiondepassementplafond.modeles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("repartition_envoi")
public class RepartitionEnvoi {

    @Id
    @Column("id_repartition")
    private Long idRepartition;

    private BigDecimal ratio;          // colonne "ratio" (même nom)

    @Column("presence_fic")
    private Boolean presenceFic;

    @Column("model_fichier")
    private String modelFichier;

    private String comment;         // colonne "comment" (même nom)

    @Column("lib_message")
    private String libMessage;

    @Column("id_operation")
    private Integer idOperation;    // FK vers liste_operation(id_operation)
}
