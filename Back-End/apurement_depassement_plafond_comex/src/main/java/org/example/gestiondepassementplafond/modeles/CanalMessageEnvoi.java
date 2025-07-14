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
@Table("canal_message_envoi")
public class CanalMessageEnvoi {

    @Id
    @Column("id_mess_send")
    private Long idMessSend;

    @Column("id_message")
    private Long idMessage;

    @Column("lib_canal")
    private String libCanal;
}
