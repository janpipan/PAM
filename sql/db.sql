CREATE TABLE Images(
    Id INT GENERATED ALWAYS AS IDENTITY,
    Title VARCHAR(255),
    Description CLOB,
    Keywords VARCHAR(255),
    Author VARCHAR(255),
    Creator VARCHAR(255),
    CapturingData DATE,
    StorageData DATE DEFAULT CURRENT_DATE,
    Filename VARCHAR(255),
    Encrypted BOOLEAN,
    PRIMARY KEY (Id)
);
