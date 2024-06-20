-- Créer la table image
CREATE TABLE IF NOT EXISTS image
(
    id_image   BIGSERIAL PRIMARY KEY,
    name       VARCHAR(255),
    type       VARCHAR(255),
    image      BYTEA,
    product_id INTEGER,
    CONSTRAINT fk_image_product
    FOREIGN KEY (product_id)
    REFERENCES product (id)
    );

-- Ajouter une colonne à la table product pour le chemin de l'image
ALTER TABLE product
    ADD COLUMN IF NOT EXISTS image_path VARCHAR(255);