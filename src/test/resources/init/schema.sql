CREATE TABLE user (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    sub VARCHAR(50) NOT NULL UNIQUE,
    username VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    status VARCHAR(20),
    language VARCHAR(50),
    colour VARCHAR(20),
    theme VARCHAR(20)
);

CREATE TABLE classroom (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(500),
    created_at TIMESTAMP NOT NULL,
    archived BOOLEAN DEFAULT FALSE
);

CREATE TABLE classroom_user (
    user_id INTEGER,
    classroom_id INTEGER,
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id, classroom_id),
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (classroom_id) REFERENCES classroom(id) ON DELETE CASCADE
);

CREATE TABLE template (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    classroom_id INTEGER,
    description VARCHAR(500),
    FOREIGN KEY (classroom_id) REFERENCES classroom(id) ON DELETE CASCADE
);

CREATE TABLE project (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    template_id INTEGER,
    name VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (template_id) REFERENCES template(id) ON DELETE CASCADE
);

INSERT INTO user (id, sub, email, first_name, last_name) VALUES (1, '101', 'testemail', 'john', 'pork');
INSERT INTO classroom (id, name, created_at) VALUES (1, 'test1', '2024-08-20 14:30:00');
INSERT INTO classroom_user (user_id, classroom_id, role) VALUES (1, 1, "OWNER");




