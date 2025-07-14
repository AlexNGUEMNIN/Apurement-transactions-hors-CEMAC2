package org.example.gestiondepassementplafond.modeles;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("liste_operation")
public class ListeOperations {

    @Id
    @Column("id_operation")
    private Long idOperation;

    @Column("lib_op")
    @NotBlank(message = "Le libellé de l'opération est obligatoire")
    private String libOp;

    @Column("nbre_jour")
    @Positive(message = "Le nombre de jour d'apurement doit etre positif")
    @Size(min = 1)
    private Integer nbreJour;

}
