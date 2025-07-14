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
@Table("documents")
public class Documents {
    @Id
    @Column("id_document")
    private Long idDocument;

    @Column("id_client_operation")
    private Long idClientOperation;

    @Column("lib_message")
    private String libMessage;
}
