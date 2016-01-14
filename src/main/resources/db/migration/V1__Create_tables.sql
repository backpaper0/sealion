CREATE TABLE Account (
    id IDENTITY,
    username VARCHAR(50)
);

CREATE TABLE Grant (
    account BIGINT NOT NULL,
    role VARCHAR(10) NOT NULL,
    PRIMARY KEY (account, role),
    FOREIGN KEY (account) REFERENCES Account (id)
);

CREATE TABLE Password (
    account BIGINT NOT NULL,
    hash VARCHAR(200) NOT NULL,
    salt VARCHAR(200) NOT NULL,
    hashAlgorithm VARCHAR(50) NOT NULL,
    PRIMARY KEY (account),
    FOREIGN KEY (account) REFERENCES Account (id)
);

CREATE TABLE Project (
    id IDENTITY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE Milestone (
    id IDENTITY,
    name VARCHAR(100) NOT NULL,
    fixedDate TIMESTAMP
);

CREATE TABLE Task (
    id IDENTITY,
    title VARCHAR(100) NOT NULL,
    content VARCHAR(1000),
    status VARCHAR(10) NOT NULL,
    postedBy BIGINT NOT NULL,
    postedAt TIMESTAMP NOT NULL,
    project BIGINT NOT NULL,
    Milestone BIGINT NOT NULL,
    FOREIGN KEY (postedBy) REFERENCES Account (id),
    FOREIGN KEY (project) REFERENCES Project (id),
    FOREIGN KEY (Milestone) REFERENCES Milestone (id)
);

CREATE TABLE Comment (
    id IDENTITY,
    task BIGINT NOT NULL,
    content VARCHAR(1000) NOT NULL,
    postedBy BIGINT NOT NULL,
    postedAt TIMESTAMP NOT NULL,
    FOREIGN KEY (postedBy) REFERENCES Account (id),
    FOREIGN KEY (task) REFERENCES Task (id)
);

CREATE TABLE Assignment (
    task BIGINT NOT NULL,
    account BIGINT NOT NULL,
    PRIMARY KEY (task, account),
    FOREIGN KEY (task) REFERENCES Task (id),
    FOREIGN KEY (account) REFERENCES Account (id)
);
