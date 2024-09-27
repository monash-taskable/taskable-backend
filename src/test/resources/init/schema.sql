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
    description VARCHAR(500) NOT NULL DEFAULT '',
    created_at DATETIME NOT NULL,
    archived BOOLEAN NOT NULL DEFAULT FALSE
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
    name VARCHAR(255) NOT NULL,
    description VARCHAR(500) NOT NULL DEFAULT '',
    archived BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (classroom_id) REFERENCES classroom(id) ON DELETE CASCADE
);

CREATE TABLE project (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    classroom_id INTEGER,
    template_id INTEGER NULL,
    name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(500) NOT NULL DEFAULT '',
    created_at DATETIME NOT NULL,
    archived BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (classroom_id) REFERENCES classroom(id) ON DELETE CASCADE,
    FOREIGN KEY (template_id) REFERENCES template(id) ON DELETE CASCADE
);

CREATE TABLE project_user (
    user_id INTEGER,
    project_id INTEGER,
    PRIMARY KEY (user_id, project_id),
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE
);

CREATE TABLE task (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    project_id INTEGER,
    title VARCHAR(255),
    description VARCHAR(500) NULL DEFAULT '',
    color VARCHAR(30),
    FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE
);

CREATE TABLE subtask (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    task_id INTEGER,
    title VARCHAR(255),
    description VARCHAR(1000),
    status VARCHAR(20),
    priority VARCHAR(20),
    start_date DATETIME NOT NULL,
    due_date DATETIME,
    FOREIGN KEY (task_id) REFERENCES task(id) ON DELETE CASCADE
);

CREATE TABLE subtask_assignee (
    user_id INTEGER,
    project_id INTEGER,
    subtask_id INTEGER,
    PRIMARY KEY (user_id, project_id, subtask_id),
    FOREIGN KEY (subtask_id) REFERENCES subtask(id) ON DELETE CASCADE,
    CONSTRAINT fk_sa_pj FOREIGN KEY (user_id, project_id)
        REFERENCES project_user (user_id, project_id)
        ON DELETE CASCADE
);

CREATE TABLE subtask_comment (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    subtask_id INTEGER,
    user_id INTEGER,
    comment VARCHAR(1000),
    create_date DATETIME,
    FOREIGN KEY (subtask_id) REFERENCES subtask(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);

CREATE TABLE announcement (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    classroom_id INTEGER,
    user_id INTEGER,
    title VARCHAR(200) NOT NULL,
    message VARCHAR(1000) NOT NULL,
    sent_at DATETIME,
    FOREIGN KEY (classroom_id) REFERENCES classroom(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);

CREATE TABLE attachment (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    project_id INTEGER NULL,
    template_id INTEGER NULL,
    subtask_id INTEGER NULL,
    name VARCHAR(200),
    s3_key VARCHAR(100),
    size_bytes INTEGER,
    FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE,
    FOREIGN KEY (template_id) REFERENCES template(id) ON DELETE CASCADE,
    FOREIGN KEY (subtask_id) REFERENCES subtask(id) ON DELETE CASCADE
);