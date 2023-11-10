CREATE TABLE encryption (
    id INT PRIMARY KEY,
    picture_id INT,
    key_salt BLOB,
    password_salt BLOB,
    init_vector BLOB,
    password_hash VARCHAR(255),
    FOREIGN KEY (picture_id) REFERENCES Image(id)
)
