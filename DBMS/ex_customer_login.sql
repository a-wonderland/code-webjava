--Customer Table--
CREATE TABLE customers
(id serial PRIMARY KEY,
name character varying(50) NOT NULL,
birth_date date NOT NULL CHECK (birth_date > '1900-01-01'),
ph character varying(50),
address character varying(50) NOT NULL);

--User Table--
CREATE TABLE users
(user_id integer PRIMARY KEY UNIQUE NOT NULL,
password character varying(50) NOT NULL,
last_update timestamp NOT NULL,
CONSTRAINT users_user_id_fkey FOREIGN KEY (user_id)
REFERENCES customers(id) ON UPDATE NO ACTION ON DELETE CASCADE);

--INSERT--
INSERT INTO customers (name, birth_date, ph, address) VALUES ('Mike', '1990-02-02', '12345-6789', 1);
INSERT INTO customers (name, birth_date, ph, address) VALUES ('David', '1990-02-02', '12345-6789', 2);

INSERT INTO users (user_id, password, last_update) VALUES (1, '12345', now());

--Test cascade--
DELETE FROM customers WHERE id=2;

SELECT * FROM customers;
SELECT * FROM users;
SELECT * FROM users WHERE user_id=1;

--last_update changed to default now()
ALTER TABLE users ALTER COLUMN last_update SET DEFAULT now();

