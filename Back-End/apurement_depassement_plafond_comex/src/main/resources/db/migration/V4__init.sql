CREATE TABLE IF NOT EXISTS tab_erreur (
     id_tab_erreur          BIGSERIAL PRIMARY KEY,
     id_operation           BIGINT NOT NULL,
     type_operation         INTEGER NOT NULL,
     commentaire            TEXT NOT NULL,
     mat_client             VARCHAR(50),
     nom_client             VARCHAR(50),
     date_enregistrement    DATE NOT NULL,
     username               VARCHAR(50) NOT NULL
);