CREATE TABLE IF NOT EXISTS client_rejete (
                                             id_client      SERIAL PRIMARY KEY,
                                             nom_client     VARCHAR(100) NOT NULL,
    prenom_client  VARCHAR(100) NOT NULL,
    reseau         VARCHAR(100),
    agence         VARCHAR(100),
    num_carte      VARCHAR(50),
    nom_porteur    VARCHAR(100),
    num_tel        VARCHAR(20),
    email_clt      VARCHAR(150),
    cumul_trans    NUMERIC(18,2),
    mois_voyage    INTEGER,
    annee_voyage   VARCHAR(4),
    date_saisie    DATE,
    user_id        VARCHAR(5)
    );

CREATE TABLE IF NOT EXISTS raison_rejet (
                                            id_raison      SERIAL PRIMARY KEY,
                                            raison         TEXT NOT NULL,
                                            id_client      INTEGER NOT NULL REFERENCES client_rejete(id_client) ON DELETE CASCADE
    );
