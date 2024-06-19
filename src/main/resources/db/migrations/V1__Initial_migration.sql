-- V1__Initial_migration.sql

-- Enum: RoleType
CREATE TYPE RoleType AS ENUM (
    'ROLE_ADMIN',
    'ROLE_USER'
);

-- Table: addresses
CREATE TABLE addresses (
    id UUID PRIMARY KEY,
    street VARCHAR(255),
    urban_village VARCHAR(255),
    subdistrict VARCHAR(255),
    city VARCHAR(255),
    province VARCHAR(255),
    postal_code VARCHAR(10)
);

-- Table: document_types
CREATE TABLE document_types (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

-- Table: users
CREATE TABLE users (
    id UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

-- Table: bedrooms
CREATE TABLE bedrooms (
    id UUID PRIMARY KEY,
    name VARCHAR(255),
    bed_room_type VARCHAR(255) NOT NULL
);

-- Table: profiles
CREATE TABLE profiles (
    id UUID PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    profile_picture VARCHAR(255),
    birthday DATE,
    join_date DATE,
    leave_date DATE,
    bio TEXT,
    phone_number VARCHAR(25),
    gender VARCHAR(255) NOT NULL,
    user_id UUID NOT NULL,
    address_id UUID,
    bedroom_id UUID,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_address FOREIGN KEY (address_id) REFERENCES addresses (id) ON DELETE CASCADE,
    CONSTRAINT fk_bedroom FOREIGN KEY (bedroom_id) REFERENCES bedrooms (id)
);

-- Table: documents
CREATE TABLE documents (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    url VARCHAR(255) NOT NULL,
    owner_id UUID NOT NULL,
    document_type_id UUID NOT NULL,
    CONSTRAINT fk_owner FOREIGN KEY (owner_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_document_type FOREIGN KEY (document_type_id) REFERENCES document_types (id)
);

-- Table: user_roles
CREATE TABLE user_roles (
    user_id UUID NOT NULL,
    role RoleType NOT NULL,
    CONSTRAINT fk_user_role FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role)
);
