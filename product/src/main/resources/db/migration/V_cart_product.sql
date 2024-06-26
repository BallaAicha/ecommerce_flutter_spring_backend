
CREATE TABLE IF NOT EXISTS cart
(
    id BIGSERIAL PRIMARY KEY,
    customer_id VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS cart_product
(
    cart_id BIGINT,
    product_id INTEGER,
    CONSTRAINT fk_cart_product_cart
        FOREIGN KEY (cart_id)
            REFERENCES cart (id),
    CONSTRAINT fk_cart_product_product
        FOREIGN KEY (product_id)
            REFERENCES product (id)
);

-- Ajouter une contrainte pour éviter les doublons dans la table de jointure
ALTER TABLE cart_product
    ADD CONSTRAINT cart_product_unique
        UNIQUE (cart_id, product_id);