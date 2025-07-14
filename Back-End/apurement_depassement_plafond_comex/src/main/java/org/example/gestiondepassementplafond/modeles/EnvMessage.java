package org.example.gestiondepassementplafond.modeles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("env_message")
public class EnvMessage {
    @Id
    @Column("id_mess")
    private Integer idMess;

    @Column("id_client_operation")
    private Integer idClientOperation;

    @Column("id_message")
    private Long idMessage;

    @Column("lib_mess")
    private String libMess;

    @Column("piece_joint")
    private Boolean pieceJoint;

    @Column("lien_piece")
    private String lienPiece;

    @Column("date_env")
    private LocalDateTime dateEnv;

    @Column("mess_env")
    private Boolean messEnv;
}
