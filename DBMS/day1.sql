--⓵【CREATE】 Table--

CREATE TABLE employees (
 id serial PRIMARY KEY,
 name VARCHAR (50) UNIQUE,
 salary numeric CHECK(salary > 0),
 deptid integer REFERENCES departments(id)
);

CREATE TABLE departments (
id serial PRIMARY KEY,
name VARCHAR(50) NOT NULL
);

--②【SELECT】 Table--
SELECT * FROM departments;
SELECT * FROM employees;

--③【INSERT】 Table--
INSERT INTO departments (name) VALUES ('SD');
INSERT INTO departments (name) VALUES ('Admin');
INSERT INTO departments (id, name) VALUES (4, 'Infra');
INSERT INTO departments (name) VALUES ('HR');

INSERT INTO employees (name, salary, deptid) VALUES ('Mg Mg', 10000, 1);
INSERT INTO employees (name, salary, deptid) VALUES ('0 TEST', 0, 1);
INSERT INTO employees (name, salary, deptid) VALUES ('Ag Ag', 5000, 2);

--④【DROP】 Table--
DROP TABLE employees;

--⑤【ALTER】 Table--
ALTER TABLE employees DROP CONSTRAINT employees_salary_check;
ALTER TABLE employees ADD CONSTRAINT employees_salary_check CHECK (salary > 0);

ALTER TABLE employees DROP CONSTRAINT employees_deptid_fkey;
ALTER TABLE employees ADD CONSTRAINT employees_deptid_fkey FOREIGN KEY (deptid) REFERENCES departments(id) ON DELETE CASCADE;
ALTER TABLE employees ADD CONSTRAINT employees_deptid_fkey FOREIGN KEY (deptid) REFERENCES departments(id) ON DELETE CASCADE ON UPDATE CASCADE;

--⑥【DELETE】 Table--
DELETE FROM employees where name='0 TEST';
DELETE FROM departments where name='Admin';

--⑦【UPDATE】 Table--
UPDATE departments SET id=4 WHERE id=2;

