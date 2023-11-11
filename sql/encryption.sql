CREATE TABLE encryption (
    id INT GENERATED ALWAYS AS IDENTITY,
    picture_id INT,
    key_salt BLOB,
    password_salt BLOB,
    init_vector BLOB,
    password_hash BLOB,
    FOREIGN KEY (picture_id) REFERENCES Image(id),
    PRIMARY KEY (Id)
);
