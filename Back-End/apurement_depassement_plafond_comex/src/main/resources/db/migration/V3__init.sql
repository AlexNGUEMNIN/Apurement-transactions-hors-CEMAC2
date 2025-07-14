-- Clients
INSERT INTO client (nom_client, prenom_client, mat_client, reseau, agence, num_carte, nom_porteur, num_tel, email_clt, cumul_trans, mois_voyage, annee_voyage, pays_use, ville_use, nbre_jr_apur, date_saisie, user_id, date_trans)
VALUES
    ('DUPONT', 'Jean', '0000124', 'Reseau A', 'Agence 1', '1234-5678-9012-3456', 'Jean Dupont', '0600000001', 'jean.dupont@example.com', 1200.50, 5, '2025', 'FR', 'PAR', 3, CURRENT_DATE, 'USR1', '2025-04-23'),
    ('MARTIN', 'Claire', '0000125', 'Reseau B', 'Agence 2', '9876-5432-1098-7654', 'Claire Martin', '0600000002', 'claire.martin@example.com', 2500.00, 6, '2025', 'BE', 'BRU', 2, CURRENT_DATE, 'USR2', '2025-04-23');

-- Opérations
INSERT INTO liste_operation (lib_op, nbre_jour)
VALUES
    ('Apurement Depassement Plafond COMEX', 3),
    ('Vérification adresse', 2);

-- Client -> Opérations
INSERT INTO client_operations (id_client, id_operation, presence_doc, date_debut, date_fin, nbre_envoi)
VALUES
    (1, 1, TRUE, '2025-06-01', '2025-06-03', 1),
    (2, 2, FALSE, '2025-06-02', NULL, 0);

-- Messages
INSERT INTO list_message (lib_message, lien_fich, date_envoi, num_envoi, id_client_operation)
VALUES
    ('Demande de document identité', 'http://example.com/id.pdf', '2025-06-03', 1, 1),
    ('Relance adresse', NULL, '2025-06-04', 1, 2);

-- Paramètres associés
INSERT INTO liste_parametre (variable, valeur, type_d, id_client_operation)
VALUES
    ('DOC_TYPE', 'CNI', 'STRING', 1),
    ('RELANCE_DELAY', '7', 'NUMERIC', 2);

-- Répartition des envois
INSERT INTO repartition_envoi (ratio, presence_fic, model_fichier, comment, lib_message, id_operation)
VALUES
    (0, TRUE, 'template_rappel0.html', 'Message envoyé initialement', 'Bonjour Mr %nomClient%, \n Suite à une transaction effectuée hors CEMAC le %dateTJ% avec votre carte, nous vous prions de bien vouloir transmettre les justificatifs de voyage dans un délai de 30 jours. \n Plus d''infos : https://cbl.link/SRLFCJi Merci pour votre diligence. \n - AFRILAND FIRST BANK', 1),
    (0.3, FALSE, NULL, 'Sans modèle', 'Message adresse', 2),
    (0.2, TRUE, 'template_rappel1', 'Message envoyé a 0.2 de l envoi de mise en demeure', 'Bonjour %nomClient%, \n Il vous reste %nbreJrRestant% jours pour nous faire parvenir les justificatifs relatifs à votre transaction hors CEMAC du %dateTJ%. \n Vous pouvez les envoyer via WhatsApp : https://cbl.link/SRLFCJi \n Nous restons à votre disposition. \n – AFRILAND FIRST BANK', 1),
    (0.5, TRUE, 'template_rappel1', 'Message envoyé a 0.5 de l envoi de mise en demeure', 'Bonjour %nomClient%, \n Il vous reste %nbreJrRestant% jours pour nous faire parvenir les justificatifs relatifs à votre transaction hors CEMAC du %dateTJ%. \n Vous pouvez les envoyer via WhatsApp : https://cbl.link/SRLFCJi \n Nous restons à votre disposition. \n – AFRILAND FIRST BANK', 1),
    (0.8, TRUE, 'template_rappel1', 'Message envoyé a 0.8 de l envoi de mise en demeure', 'Bonjour %nomClient%, \n Il vous reste %nbreJrRestant% jours pour nous faire parvenir les justificatifs relatifs à votre transaction hors CEMAC du %dateTJ%. \n Vous pouvez les envoyer via WhatsApp : https://cbl.link/SRLFCJi \n Nous restons à votre disposition. \n – AFRILAND FIRST BANK', 1),
    (1, TRUE, 'mise_en_demeure', 'Message envoyé le jour de la mise en demeure', 'Bonjour %nomClient%, \n Mise en demeure envoyée concernant vos transactions hors CEMAC non justifiées. \n Merci de consulter le document. \n – AFRILAND FIRST BANK', 1);

-- Variables de répartition
INSERT INTO variable_repartition (lib_variable, type_var, id_repartition)
VALUES
    ('matriculeClient', 'STRING', 1),
    ('nomClient', 'STRING', 1),
    ('dateTJ', 'STRING', 1),
    ('nbreJr', 'INTEGER', 1),
    ('numCarte', 'STRING', 1),
    ('dateTransaction', 'STRING', 1),
    ('numCarte', 'STRING', 3),
    ('nbreJrEcoule', 'INTEGER', 3),
    ('nbreJr', 'INTEGER', 3),
    ('dateTransaction', 'STRING', 3),
    ('nomClient', 'STRING', 3),
    ('nbreJrRestant', 'INTEGER', 3),
    ('dateTransaction', 'STRING', 4),
    ('numCarte', 'STRING', 4),
    ('nbreJrEcoule', 'INTEGER', 4),
    ('nbreJr', 'INTEGER', 4),
    ('nbreJrRestant', 'INTEGER', 4),
    ('nomClient', 'STRING', 4),
    ('numCarte', 'STRING', 5),
    ('nbreJrEcoule', 'INTEGER', 5),
    ('dateTransaction', 'STRING', 5),
    ('nbreJr', 'INTEGER', 5),
    ('nbreJrRestant', 'INTEGER', 5),
    ('nomClient', 'STRING', 5),
    ('numSerie', 'STRING', 6),
    ('dateMiseDem', 'STRING', 6),
    ('nomClient', 'STRING', 6),
    ('dateTransaction', 'STRING', 6);

-- Canaux
INSERT INTO canal (lib_canal)
VALUES
    ('Email'),
    ('SMS');

-- Canaux ↔ Opérations
INSERT INTO canal_operation (id_canal, id_operation, comment)
VALUES
    (1, 1, 'Email standard'),
    (2, 2, 'SMS de rappel');

-- Canaux ↔ Messages
INSERT INTO canal_message_envoi (id_message, lib_canal)
VALUES
    (1, 'Email'),
    (2, 'SMS');

-- Plafond Apuré
INSERT INTO plafond_apure (nom_client, prenom_client, date_saisie, num_tel, email_clt, cumul_trans, mois_voyage, annee_voyage, nbre_jr_apur)
VALUES
    ('DUPONT', 'Jean', CURRENT_DATE, '0600000001', 'jean.dupont@example.com', 1200.50, 5, '2025', 3);

-- Plafond Non Apuré
INSERT INTO plafond_non_apure (nom_client, prenom_client, date_saisie, num_tel, email_clt, cumul_trans, mois_voyage, annee_voyage, nbre_jr_apur)
VALUES
    ('MARTIN', 'Claire', CURRENT_DATE, '0600000002', 'claire.martin@example.com', 2500.00, 6, '2025', 2);
