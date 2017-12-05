--【CREATE VIEW】--

CREATE VIEW category_film_number
AS
SELECT c.category_id, c.name, COUNT(fc.film_id)
FROM category c
INNER JOIN film_category fc
ON fc.category_id = c.category_id
GROUP BY c.category_id, c.name
ORDER BY c.category_id;

SELECT * FROM category_film_number;

--【PL/pgSQL – Trigger】--