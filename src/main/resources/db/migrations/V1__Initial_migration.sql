-- V1__Initial_migration.sql

CREATE TABLE addresses (
    id UUID NOT NULL,
    street VARCHAR(255),
    urban_village VARCHAR(255),
    subdistrict VARCHAR(255),
    city VARCHAR(255),
    province VARCHAR(255),
    postal_code VARCHAR(5),
    PRIMARY KEY (id)
);

CREATE TABLE bedroom_types (
    id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE bedrooms (
    id UUID NOT NULL,
    name VARCHAR(255),
    bedroom_type_id UUID NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (bedroom_type_id) REFERENCES bedroom_types(id)
);

CREATE TABLE document_types (
    id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE users (
    id UUID NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id)
);

CREATE TABLE profiles (
    id UUID NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    profile_picture VARCHAR(255),
    birthday DATE,
    join_date DATE,
    leave_date DATE,
    bio TEXT,
    phone_number VARCHAR(25),
    gender VARCHAR(50) NOT NULL,
    user_id UUID NOT NULL,
    address_id UUID,
    bedroom_id UUID,
    guardian_id UUID,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (address_id) REFERENCES addresses(id),
    FOREIGN KEY (bedroom_id) REFERENCES bedrooms(id),
    FOREIGN KEY (guardian_id) REFERENCES guardians(id)
);

CREATE TABLE documents (
    id UUID NOT NULL,
    name VARCHAR(255),
    url VARCHAR(255),
    owner_id UUID NOT NULL,
    document_type_id UUID NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (owner_id) REFERENCES users(id),
    FOREIGN KEY (document_type_id) REFERENCES document_types(id)
);

CREATE TABLE guardians (
    id UUID NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(25),
    address_id UUID,
    guardian_type_id UUID NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (address_id) REFERENCES addresses(id),
    FOREIGN KEY (guardian_type_id) REFERENCES guardian_types(id)
);

CREATE TABLE guardian_types (
    id UUID NOT NULL,
    type VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE inventories (
    id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    quantity BIGINT NOT NULL,
    inventory_type_id UUID NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (inventory_type_id) REFERENCES inventory_types(id)
);

CREATE TABLE inventory_types (
    id UUID NOT NULL,
    name VARCHAR(255) NOT NULL UNIQUE,
    type VARCHAR(255) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE user_roles (
    user_id UUID NOT NULL,
    role VARCHAR(255) NOT NULL,
    PRIMARY KEY (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users(id)
);
