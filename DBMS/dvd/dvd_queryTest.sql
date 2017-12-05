SELECT * FROM payment;
SELECT * FROM rental;

--【Querying Data (Select, Order By, Distinct)】--
SELECT * FROM customer ORDER BY first_name ASC;

--【Filtering Data (WHERE)】--
SELECT first_name, last_name FROM customer WHERE first_name='Mike';
SELECT * FROM payment WHERE amount >= 5 AND amount <= 7;

--【Filtering Data (LIMIT)】--
SELECT * FROM payment WHERE amount >= 5 AND amount <= 7 LIMIT 100;

--【Filtering Data (OFFSET)】--
SELECT * FROM payment WHERE amount >= 5 AND amount <= 7 LIMIT 10 OFFSET 3;

--【Filtering Data (IN)】--
SELECT * FROM customer WHERE first_name IN ('Adam', 'Alex');

--【Filtering Data (BETWEEN)】--
SELECT * FROM payment WHERE amount BETWEEN 5 AND 7;

--【Filtering Data (LIKE)】--
SELECT * FROM customer WHERE first_name LIKE '%ne_%' LIMIT 20;

-- ■ How many rental for each film--
SELECT inven.film_id, film.title, COUNT(rent.customer_id) FROM rental rent 
INNER JOIN inventory inven
ON inven.inventory_id = rent.inventory_id
INNER JOIN film
ON film.film_id = inven.film_id
GROUP BY inven.film_id, film.title
ORDER BY inven.film_id ASC 
LIMIT 50;

-- ■ Which film is the most popular--
--①--
SELECT inven.film_id, film.title, COUNT(rent.customer_id) AS number_of_rent FROM rental rent 
INNER JOIN inventory inven
ON inven.inventory_id = rent.inventory_id
INNER JOIN film
ON film.film_id = inven.film_id
GROUP BY inven.film_id, film.title
ORDER BY number_of_rent DESC
LIMIT 1;

-- ■ Calculate total amount of rent at specific date--
SELECT date_trunc('day', pay.payment_date) AS date, SUM(pay.amount) AS total
FROM payment pay
INNER JOIN rental rent
ON rent.rental_id = pay.rental_id
WHERE CAST(pay.payment_date AS date) ='2007-02-15'
GROUP BY date;

-- ■ DESC amount of income at day--
SELECT date_trunc('day',pay.payment_date) AS date, SUM(pay.amount) AS total
FROM payment pay
INNER JOIN rental rent
ON rent.rental_id = pay.rental_id
GROUP BY date ORDER BY total DESC;

-- ■ most popular category ?
SELECT inven.film_id, COUNT(rent.rental_id)
FROM inventory inven
INNER JOIN rental rent
ON inven.inventory_id = rent.inventory_id
GROUP BY inven.film_id
ORDER BY COUNT(rent.rental_id) DESC
LIMIT 20;

--Ex--

-- ■ how many film at category--
SELECT cat.name, COUNT(*) AS total
FROM category cat
INNER JOIN film_category fcat
ON fcat.category_id = cat.category_id
GROUP BY cat.name
ORDER BY total DESC;

-- ■ film that not in inventory--
--⓵ NOT IN
SELECT DISTINCT f.title
FROM film f
WHERE f.film_id NOT IN
(SELECT film_id FROM inventory)
ORDER BY f.title;

--② IS NULL
SELECT DISTINCT f.title
FROM film f
LEFT JOIN inventory inven
ON f.film_id = inven.film_id
WHERE inven.inventory_id IS NULL
ORDER BY f.title;

--③ EXCEPT
SELECT f.title
FROM film f
EXCEPT
SELECT DISTINCT f.title
FROM film f
INNER JOIN inventory inven
ON f.film_id = inven.film_id;

-- ■ who is the most rent film--
SELECT c.customer_id, c.first_name, c.last_name, COUNT(rent.rental_id)
FROM customer c
INNER JOIN rental rent
ON c.customer_id = rent.customer_id
GROUP BY c.customer_id
ORDER BY COUNT(rent.rental_id) DESC
LIMIT 20;

-- ■ customer who is not paid yet--
SELECT rent.customer_id, rent.rental_id, rent.return_date, pay.payment_date
FROM payment pay
RIGHT JOIN rental rent
ON rent.rental_id = pay.rental_id
WHERE pay.rental_id IS NULL
ORDER BY rent.customer_id;

SELECT c.customer_id, c.first_name ||' '||c.last_name
FROM customer c 
WHERE customer_id IN
(SELECT DISTINCT rent.customer_id 
FROM payment pay 
RIGHT JOIN rental rent
ON rent.rental_id = pay.rental_id
WHERE pay.rental_id IS NULL) ORDER BY c.customer_id;

-- ■ sum of amount--
SELECT pay.customer_id, c.first_name, SUM(pay.amount)
FROM payment pay
INNER JOIN customer c
ON c.customer_id = pay.customer_id
GROUP BY pay.customer_id, c.first_name
ORDER BY SUM(pay.amount) DESC;

-- ■ film which exit in store--
SELECT f.film_id, f.title, inven.store_id, COUNT(f.film_id)
FROM film f
INNER JOIN inventory inven
ON inven.film_id = f.film_id
GROUP BY f.film_id, f.title, inven.store_id
ORDER BY f.film_id;

-- ■ film, title, store, number of rent--
SELECT f.film_id, f.title, inven.store_id, COUNT(rent.rental_id) AS number_of_rent
FROM film f
INNER JOIN inventory inven
ON inven.film_id = f.film_id
INNER JOIN rental rent
ON inven.inventory_id = rent.inventory_id
GROUP BY f.film_id, f.title, inven.store_id
ORDER BY f.film_id;

-- ■ amount of income of store at specific date--
SELECT date_trunc('day',pay.payment_date) AS date, inven.store_id, SUM(pay.amount) as total
FROM inventory inven
INNER JOIN rental rent
ON inven.inventory_id = rent.inventory_id
INNER JOIN payment pay
ON pay.rental_id = rent.rental_id
GROUP BY date, inven.store_id
ORDER BY total DESC;


