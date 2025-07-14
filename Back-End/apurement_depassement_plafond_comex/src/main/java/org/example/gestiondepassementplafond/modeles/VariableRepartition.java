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
@Table("variable_repartition")
public class VariableRepartition {

    @Id
    @Column("id_var_rep")
    private Long idVarRep;       // colonne "ratio" (même nom)

    @Column("lib_variable")
    private String libVariable;

    @Column("typeVar")
    private String type_var;

    @Column("id_repartition")
    private Integer idRepartition;    // FK vers list

}
