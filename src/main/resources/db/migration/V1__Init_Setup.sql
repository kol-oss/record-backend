CREATE TABLE accounts
(
    id         UUID    NOT NULL,
    balance    INTEGER NOT NULL,
    updated_at date,
    CONSTRAINT pk_accounts PRIMARY KEY (id)
);

CREATE TABLE categories
(
    id   UUID         NOT NULL,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_categories PRIMARY KEY (id)
);

CREATE TABLE records
(
    id          UUID    NOT NULL,
    user_id     UUID,
    category_id UUID,
    created_at  date,
    amount      INTEGER NOT NULL,
    CONSTRAINT pk_records PRIMARY KEY (id)
);

CREATE TABLE users
(
    id         UUID        NOT NULL,
    name       VARCHAR(64) NOT NULL,
    account_id UUID,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_account UNIQUE (account_id);

ALTER TABLE records
    ADD CONSTRAINT FK_RECORDS_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES categories (id);

ALTER TABLE records
    ADD CONSTRAINT FK_RECORDS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE users
    ADD CONSTRAINT FK_USERS_ON_ACCOUNT FOREIGN KEY (account_id) REFERENCES accounts (id);