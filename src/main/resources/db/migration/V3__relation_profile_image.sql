ALTER TABLE users
    ADD COLUMN profile_image_id BIGINT;

ALTER TABLE users
    ADD CONSTRAINT fk_profile_image
        FOREIGN KEY (profile_image_id)
            REFERENCES image_data (id);
