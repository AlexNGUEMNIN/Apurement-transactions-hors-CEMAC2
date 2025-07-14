package org.example.gestiondepassementplafond.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.gestiondepassementplafond.modeles.CanalMessageEnvoi;
import org.example.gestiondepassementplafond.modeles.ClientOperations;
import org.example.gestiondepassementplafond.modeles.RepartitionEnvoi;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ListeMessageDto {

    private Long idMessage;

    private String libMessage;

    private String lienFich;

    private LocalDate dateEnvoi;

    private ClientOperations clientOperations;

    private List<CanalMessageEnvoi> listCanauxMessage;

}
