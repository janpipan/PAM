CREATE TABLE encryption (
    id INT PRIMARY KEY,
    picture_id INT,
    key_salt BLOB,
    password_salt VARCHAR(255),
    init_vector BLOB,
    password_hash BLOB,
    FOREIGN KEY (picture_id) REFERENCES Image(id)
)
