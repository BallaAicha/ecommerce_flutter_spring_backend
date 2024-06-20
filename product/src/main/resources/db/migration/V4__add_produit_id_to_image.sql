-- Supprimer la contrainte existante si elle existe
ALTER TABLE image
    DROP CONSTRAINT IF EXISTS fk_image_produit;

-- Ajouter la contrainte
ALTER TABLE image
    ADD CONSTRAINT fk_image_produit
        FOREIGN KEY (produit_id)
            REFERENCES product (id);