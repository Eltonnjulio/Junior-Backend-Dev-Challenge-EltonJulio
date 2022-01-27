
INSERT INTO users (id, name, username, email, role, password, created_at, updated_at,user_status,deleted_at) VALUES (1, 'Administrator', 'admin', 'admin@cowork.co.mz','ADMIN', '$2a$10$EJVw5HLLZg6njyh9M2qHQutabTtkJTLcqYuk0V30M2NhWVYd22RMa', current_timestamp, current_timestamp,'ACTIVE',null) ON CONFLICT (username, email) DO NOTHING;
