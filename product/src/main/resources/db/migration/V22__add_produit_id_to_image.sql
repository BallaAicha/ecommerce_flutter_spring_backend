-- -- Vérifier si la contrainte existe
-- DO
-- $do$
--     BEGIN
--         IF EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'fk_image_product') THEN
--             -- Supprimer la contrainte si elle existe
--             ALTER TABLE image DROP CONSTRAINT fk_image_product;
--         END IF;
--     END
-- $do$;
--
-- -- Ajouter la contrainte
-- ALTER TABLE image
--     ADD CONSTRAINT fk_image_product
--         FOREIGN KEY (produit_id)
--             REFERENCES product (id);