CREATE TABLE book(
    id uuid NOT NULL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(1000) NOT NULL,
    release_year INT NOT NULL

)