CREATE TABLE image_data (
                            id BIGSERIAL PRIMARY KEY NOT NULL,
                            name VARCHAR(255) NOT NULL,
                            type VARCHAR(255) NOT NULL,
                            image_data OID NOT NULL
);