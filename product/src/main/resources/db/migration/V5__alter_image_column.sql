ALTER TABLE image
    ALTER COLUMN image TYPE bytea USING image::bytea;