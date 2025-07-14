package org.example.gestiondepassementplafond.modeles;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("canal")
public class Canal {

    @Id
    @Column("id_canal")
    private Long idCanal;

    @Column("lib_canal")
    @NotBlank
    private String libCanal;

}
