-- Insert roles
INSERT INTO roles (role_id, role_name) VALUES (1, 'ROLE_USER');
INSERT INTO roles (role_id, role_name) VALUES (2, 'ROLE_ADMIN');

-- Insert admin user
INSERT INTO users (email, password, username) VALUES ('admin@psaa.com', '$2a$10$7QJr1/28h3Jw4WzOGUq3heM4/ZRzm4VfOHXJfzKMJ5NodURHgKfG2', 'admin');

-- Assign roles to admin user
INSERT INTO user_roles (user_id, role_id)
SELECT id, 1 FROM users WHERE email = 'admin@psaa.com';
INSERT INTO user_roles (user_id, role_id)
SELECT id, 2 FROM users WHERE email = 'admin@psaa.com';
