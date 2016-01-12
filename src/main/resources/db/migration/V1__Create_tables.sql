CREATE TABLE Project (
    id IDENTITY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE Task (
    id IDENTITY,
    title VARCHAR(100) NOT NULL,
    content VARCHAR(1000),
    status VARCHAR(10) NOT NULL,
    postedBy BIGINT NOT NULL,
    postedAt TIMESTAMP NOT NULL,
    project BIGINT NOT NULL,
    Milestone BIGINT
);

CREATE TABLE Comment (
    id IDENTITY,
    task BIGINT NOT NULL,
    content VARCHAR(1000) NOT NULL,
    postedBy BIGINT NOT NULL,
    postedAt TIMESTAMP NOT NULL
);

CREATE TABLE Account (
    id IDENTITY,
    username VARCHAR(50)
);

CREATE TABLE Assignment (
    task BIGINT NOT NULL,
    account BIGINT NOT NULL,
    PRIMARY KEY (task, account)
);

CREATE TABLE Grant (
    account BIGINT NOT NULL,
    role VARCHAR(10) NOT NULL,
    PRIMARY KEY (account, role)
);

CREATE TABLE Milestone (
    id IDENTITY,
    name VARCHAR(100) NOT NULL,
    fixedDate TIMESTAMP
);
