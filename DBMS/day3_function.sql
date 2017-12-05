SELECT * FROM employees;
--【PL/pgSQL – Function】--
CREATE OR REPLACE FUNCTION 
public.calc_bonus(salary numeric) RETURNS numeric AS $$
DECLARE 
result numeric;
BEGIN
result = salary*2;

IF result > 3000 THEN
result := 3000;
ELSIF result < 2500 THEN
result := 2500;
END IF;
RETURN result;
END;
$$ LANGUAGE plpgsql;

--Call--
SELECT id, name, salary, calc_bonus(salary) FROM employees;

--【PL/pgSQL – Function】 Loop with array--
CREATE FUNCTION sum(int[]) RETURNS int8 AS $$
DECLARE
s int8 := 0;
x int;
BEGIN
FOREACH x IN ARRAY $1
LOOP
s := s + x;
END LOOP;
RETURN s;
END;
$$ LANGUAGE plpgsql;

--call--
SELECT * FROM sum(ARRAY[1,2,3]);

--【PL/pgSQL – Function】 RETURN QUERY--

CREATE OR REPLACE FUNCTION insert_employee(
    name IN text,
    salary IN numeric) RETURNS SETOF int AS $$
BEGIN

INSERT INTO employees (name,salary) VALUES(name,salary);
RETURN QUERY SELECT MAX(id) FROM employees;

END;
$$ LANGUAGE plpgsql;

--call--
SELECT * FROM  insert_employee('Hla',2000);

--【preparation】--
CREATE TABLE emp (
empname text NOT NULL,
salary integer
);

CREATE TABLE emp_audit(
operation char(1) NOT NULL,
stamp timestamp NOT NULL,
userid text NOT NULL,
empname text NOT NULL,
salary integer
);
--【PL/pgSQL – Trigger】--

CREATE OR REPLACE FUNCTION process_emp_audit() RETURNS TRIGGER AS $emp_audit$
BEGIN
-- Create a row in emp_audit to reflect the operation performed on emp,
-- make use of the special variable TG_OP to work out the operation.
IF (TG_OP = 'DELETE') THEN
INSERT INTO emp_audit SELECT 'D', now(), user, OLD.*;
RETURN OLD;
ELSIF (TG_OP = 'UPDATE') THEN
INSERT INTO emp_audit SELECT 'U', now(), user, NEW.*;
RETURN NEW;
ELSIF (TG_OP = 'INSERT') THEN
INSERT INTO emp_audit SELECT 'I', now(), user, NEW.*;
RETURN NEW;
END IF;
RETURN NULL; -- result is ignored since this is an AFTER trigger
END;
$emp_audit$ LANGUAGE plpgsql;

CREATE TRIGGER emp_audit
AFTER INSERT OR UPDATE OR DELETE ON emp
FOR EACH ROW EXECUTE PROCEDURE process_emp_audit();

--test--
SELECT * FROM emp;
SELECT * FROM emp_audit;

INSERT INTO emp (empname, salary) VALUES ('Aung', 1000);
UPDATE emp SET salary=1500 WHERE empname='Aung';
DELETE FROM emp WHERE empname='Aung';
