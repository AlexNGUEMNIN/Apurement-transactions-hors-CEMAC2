-- ##################################################################
-- # Flyway Migration V1__init_schema.sql
-- # Création des tables selon le diagramme fourni
-- # Dialecte : PostgreSQL
-- ##################################################################

-- 1) Table : client
CREATE TABLE IF NOT EXISTS client (
    id_client      SERIAL PRIMARY KEY,
    nom_client     VARCHAR(100) NOT NULL,
    prenom_client  VARCHAR(100),
    mat_client     VARCHAR(100),
    reseau         VARCHAR(100),
    agence         VARCHAR(100),
    num_carte      VARCHAR(50),
    nom_porteur    VARCHAR(100),
    num_tel        VARCHAR(20),
    email_clt      VARCHAR(150),
    cumul_trans    NUMERIC(18,2),
    mois_voyage    INTEGER,
    annee_voyage   VARCHAR(4),
    pays_use       VARCHAR(50),
    ville_use      VARCHAR(4),
    nbre_jr_apur   INTEGER,
    date_saisie    DATE,
    user_id        VARCHAR(5),
    date_trans     VARCHAR(20)
);

-- 2) Table : liste_operation
CREATE TABLE IF NOT EXISTS liste_operation (
    id_operation   SERIAL PRIMARY KEY,
    lib_op         VARCHAR(200) NOT NULL,
    nbre_jour      INTEGER
);

-- 4) Table : client_operations
-- Relation entre un client et une opération, avec statut documentaire et dates
CREATE TABLE IF NOT EXISTS client_operations (
    id_client_operation   SERIAL PRIMARY KEY,
    id_client      INTEGER NOT NULL REFERENCES client(id_client) ON DELETE CASCADE,
    id_operation   INTEGER NOT NULL REFERENCES liste_operation(id_operation) ON DELETE CASCADE,
    presence_doc   BOOLEAN DEFAULT FALSE,
    date_debut     DATE,
    date_fin       DATE,
    nbre_envoi     INTEGER,
    UNIQUE (id_client, id_operation)
);

-- 3) Table : list_message
CREATE TABLE IF NOT EXISTS list_message (
    id_message     BIGSERIAL PRIMARY KEY,
    lib_message    TEXT NOT NULL,
    lien_fich      VARCHAR(255),
    date_envoi     DATE,
    num_envoi      INTEGER,
    mess_env       BOOLEAN DEFAULT FALSE,
    id_client_operation INTEGER NOT NULL REFERENCES client_operations(id_client_operation) ON DELETE CASCADE
);

-- 6) Table : liste_parametre
CREATE TABLE IF NOT EXISTS liste_parametre (
   id_param       BIGSERIAL PRIMARY KEY,
   variable       TEXT NOT NULL,
   valeur         VARCHAR(255),
   type_d         VARCHAR(50),
   id_client_operation  INTEGER NOT NULL REFERENCES client_operations(id_client_operation) ON DELETE CASCADE
);

-- 5) Table : repartition_envoi
CREATE TABLE IF NOT EXISTS repartition_envoi (
    id_repartition SERIAL PRIMARY KEY,
    ratio          NUMERIC,
    presence_fic   BOOLEAN DEFAULT FALSE,
    model_fichier  VARCHAR(255),
    comment        TEXT,
    lib_message    TEXT,
    id_operation   INTEGER NOT NULL REFERENCES liste_operation(id_operation) ON DELETE CASCADE
);

-- 6) Table : variable_template
CREATE TABLE IF NOT EXISTS variable_repartition (
    id_var_rep        SERIAL PRIMARY KEY,
    lib_variable      VARCHAR(50),
    type_var          VARCHAR(50) NOT NULL, -- ENUM('STRING', 'DATE', 'NUMERIC')
    id_repartition    INTEGER NOT NULL REFERENCES repartition_envoi(id_repartition) ON DELETE CASCADE
);


-- 7) Table : canal
CREATE TABLE IF NOT EXISTS canal (
    id_canal       BIGSERIAL PRIMARY KEY,
    lib_canal      TEXT NOT NULL
);

-- 8) Table : canal_operation
-- Liaison N–N (canal <-> liste_operation), avec un commentaire éventuel
CREATE TABLE IF NOT EXISTS canal_operation (
    id_canal_op SERIAL PRIMARY KEY,
    id_canal       BIGINT NOT NULL REFERENCES canal(id_canal) ON DELETE CASCADE,
    id_operation   INTEGER NOT NULL REFERENCES liste_operation(id_operation) ON DELETE CASCADE,
    comment        TEXT,
    UNIQUE (id_canal, id_operation)
);

-- 9) Table : canal_message_envoi
-- Liaison N–N (canal <-> message), avec libellé de canal dans le contexte de l'envoi
CREATE TABLE IF NOT EXISTS canal_message_envoi (
    id_mess_send   BIGSERIAL PRIMARY KEY,
    id_message     BIGINT NOT NULL REFERENCES list_message(id_message) ON DELETE CASCADE,
    lib_canal      VARCHAR(200)
);

-- 10) Table : plafond_apure
CREATE TABLE IF NOT EXISTS plafond_apure (
    id_plafond_apure   BIGSERIAL PRIMARY KEY,
    nom_client         VARCHAR(100)  NOT NULL,
    prenom_client      VARCHAR(100)  NOT NULL,
    date_saisie        DATE          NOT NULL,
    num_tel            VARCHAR(20),
    email_clt          VARCHAR(150),
    cumul_trans        NUMERIC(18,2),
    mois_voyage        INTEGER,
    annee_voyage       VARCHAR(4),
    nbre_jr_apur       INTEGER
);

-- 11) Table : plafond_non_apure
CREATE TABLE IF NOT EXISTS plafond_non_apure (
    id_plafond_non_apure BIGSERIAL PRIMARY KEY,
    nom_client           VARCHAR(100)  NOT NULL,
    prenom_client        VARCHAR(100)  NOT NULL,
    date_saisie          DATE          NOT NULL,
    num_tel              VARCHAR(20),
    email_clt            VARCHAR(150),
    cumul_trans          NUMERIC(18,2),
    mois_voyage          INTEGER,
    annee_voyage         VARCHAR(4),
    nbre_jr_apur         INTEGER
);

-- Indexation (facultatif) : on peut ajouter des index sur les colonnes fréquemment recherchées
CREATE INDEX IF NOT EXISTS idx_client_email ON client(email_clt);
CREATE INDEX IF NOT EXISTS idx_operation_libop ON liste_operation(lib_op);
CREATE INDEX IF NOT EXISTS idx_message_date ON list_message(date_envoi);
