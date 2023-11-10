CREATE TABLE encryption (
    id INT PRIMARY KEY,
    picture_id INT,
    salt BLOB,
    init_vector BLOB,
    password_hash BLOB,
    FOREIGN KEY (picture_id) REFERENCES Image(id)
)
