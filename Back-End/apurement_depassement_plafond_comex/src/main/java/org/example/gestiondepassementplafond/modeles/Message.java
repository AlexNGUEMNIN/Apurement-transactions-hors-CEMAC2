package org.example.gestiondepassementplafond.modeles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("message")
public class Message {

    @Id
    @Column("id_message")
    private Long idMessage;

    @Column("lib_message")
    private String libMessage;

    @Column("type_mess")
    private Integer typeMess;

}
