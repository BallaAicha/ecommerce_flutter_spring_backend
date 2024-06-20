-- Créer la table favoris
CREATE TABLE IF NOT EXISTS favoris
(
    id BIGSERIAL PRIMARY KEY
);

-- Créer la table de jointure entre favoris et product
CREATE TABLE IF NOT EXISTS favoris_product
(
    favoris_id BIGINT,
    product_id INTEGER,
    CONSTRAINT fk_favoris_product_favoris
        FOREIGN KEY (favoris_id)
            REFERENCES favoris (id),
    CONSTRAINT fk_favoris_product_product
        FOREIGN KEY (product_id)
            REFERENCES product (id)
);

-- Ajouter une contrainte pour éviter les doublons dans la table de jointure
ALTER TABLE favoris_product
    ADD CONSTRAINT favoris_product_unique
        UNIQUE (favoris_id, product_id);