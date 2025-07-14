package org.example.gestiondepassementplafond.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.gestiondepassementplafond.modeles.CanalOperation;
import org.example.gestiondepassementplafond.modeles.RepartitionEnvoi;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OperationDto {

    private Long idOperation;
    private String libOp;
    private Integer nbreJour;
    private List<RepartitionEnvoi> repartitionEnvoi;
    private List<CanalOperation> canalOperation;

}
