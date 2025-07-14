package org.example.gestiondepassementplafond.modeles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("list_message")
public class ListMessage {

    @Id
    @Column("id_message")
    private Long idMessage;

    @Column("lib_message")
    private String libMessage;

    @Column("lien_fich")
    private String lienFich;

    @Column("date_envoi")
    private LocalDate dateEnvoi;

    @Column("num_envoi")
    private int numEnvoi;

    @Column("mess_env")
    private Boolean messEnv;

    @Column("id_client_operation")
    private Long idClientOperation;
}
