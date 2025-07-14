package org.example.gestiondepassementplafond.modeles;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Table("plafond_apure")
public class PlafondApure {

    @Id
    @Column("id_plafond_apure")
    private Long idPlafondApure;

    @Column("nom_client")
    private String nomClient;

    @Column("prenom_client")
    private String prenomClient;

    @Column("date_saisie")
    private LocalDate dateSaisie;

    @Column("num_tel")
    private String numTel;

    @Column("email_clt")
    private String emailClt;

    @Column("cumul_trans")
    private Double cumulTrans;

    @Column("mois_voyage")
    private Integer moisVoyage;

    @Column("annee_voyage")
    private String anneeVoyage;

    @Column("nbre_jr_apur")
    private Integer nbreJrApur;
}
