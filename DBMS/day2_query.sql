--Delete Data--
DELETE FROM departments;
DELETE FROM accounts;

--Select--
SELECT * FROM employees;
SELECT * FROM departments;

--Insert Data--
INSERT INTO departments (id, name) VALUES (1, 'ADMIN'), (2, 'SD'), (3, 'Infra'), (4, 'HR'), (5, 'SALE');

INSERT INTO employees (id, name, salary, deptid) VALUES
(1, 'Mg Mg', 1000, 1),
(2, 'Mya Mya', 1500, 1),
(3, 'Hla Hla', 1200, 2),
(4, 'Kyaw Kyaw', 1300, 2),
(5, 'Aung Aung', 1900, 2),
(6, 'Zaw Zaw', 1800, 2),
(7, 'Bo Bo', 1600, 3),
(8, 'Than Than', 1700, 3),
(9, 'Win Win', 1300, 3),
(10, 'Htet Htet', 2000, 4);

--INSERT data with no department--
INSERT INTO employees (id, name, salary) VALUES
(11, 'Myint Myint', 1000),
(12, 'Tun Tun', 1500);

--【RETURNING】--
INSERT INTO employees (name, salary) VALUES ('Win', 9999) RETURNING id;

--【Query】--
SELECT * FROM employees WHERE deptid=2;

--【INNER JOIN】 NOT NULL DATA--
SELECT dept.name, emp.name, emp.salary
FROM employees emp INNER JOIN departments dept
ON emp.deptid = dept.id;

--【LEFT JOIN】--
SELECT dept.name, emp.name, emp.salary
FROM employees emp LEFT JOIN departments dept
ON emp.deptid = dept.id;

--【RIGHT JOIN】--
SELECT dept.name, emp.name, emp.salary
FROM employees emp RIGHT JOIN departments dept
ON emp.deptid = dept.id;

--【FULL JOIN】--
SELECT dept.name, emp.name, emp.salary
FROM employees emp FULL JOIN departments dept
ON emp.deptid = dept.id;

--【Joining Multiple Tables (INNER JOIN)】--
SELECT inven.film_id, film.title, COUNT(rent.customer_id) FROM rental rent 
INNER JOIN inventory inven
ON inven.inventory_id = rent.inventory_id
INNER JOIN film
ON film.film_id = inven.film_id
GROUP BY inven.film_id, film.title
ORDER BY inven.film_id ASC 
LIMIT 50;

--【aggregate function】 COUNT & GROUP BY--
SELECT dept.name, count(emp.id)
FROM employees emp RIGHT JOIN departments dept
ON emp.deptid = dept.id
GROUP BY dept.name;

--【aggregate function】 SUM--
SELECT dept.name, dept.id, count(emp.id), SUM(emp.salary)
FROM employees emp RIGHT JOIN departments dept
ON emp.deptid = dept.id
GROUP BY dept.name, dept.id;

--【aggregate function】 HAVING--
SELECT dept.name, dept.id, COUNT(emp.id), SUM(emp.salary)
FROM employees emp RIGHT JOIN departments dept
ON emp.deptid = dept.id
GROUP BY dept.name, dept.id
HAVING COUNT(emp.id) > 2;

--【aggregate function】 HAVING & WHERE--
SELECT dept.name, dept.id, COUNT(emp.id), SUM(emp.salary)
FROM employees emp RIGHT JOIN departments dept
ON emp.deptid = dept.id 
WHERE dept.id = 2
GROUP BY dept.name, dept.id
HAVING COUNT(emp.id) > 2;

