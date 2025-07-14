package org.example.gestiondepassementplafond.modeles;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("client")
public class Client {

    @Id
    @Column("id_client")
    private Long idClient;

    @Column("nom_client")
    @NotBlank(message = "Le nom du Client ne doit pas etre null ou vide veuillez corriger avant de reessayer")
    private String nomClient;

    @Column("prenom_client")
    private String prenomClient;

    @Column("mat_client")
    private String matriculeClient;

    private String reseau;   // colonne "reseau" (même nom)
    private String agence;   // colonne "agence" (même nom)

    @Column("num_carte")
    @NotBlank(message = "Le numero de carte ne doit pas etre nulle ou vide")
    private String numCarte;

    @Column("nom_porteur")
    @NotBlank(message = "Le nom du propriétaire ne doit pas etre nulle ou vide")
    private String nomPorteur;

    @Column("num_tel")
    @NotBlank(message = "Le numero de telephone ne doit pas etre nulle ou vide")
    private String numTel;

    @Column("email_clt")
    @NotBlank(message = "L'adresse email ne doit pas etre vide ou nulle, veuillez corriger avant de continuer")
    @Email(message = "L'adresse email ne respecte pas le format reglementaire veuillez corriger")
    private String emailClt;

    @Column("cumul_trans")
    private Double cumulTrans;

    @Column("mois_voyage")
    private Integer moisVoyage;

    @Column("annee_voyage")
    private String anneeVoyage;

    @Column("nbre_jr_apur")
    private Integer nbreJrApur;

    @Column("date_saisie")
    private LocalDate dateSaisie;

    @Column("date_trans")
    private String dateTrans;

}
