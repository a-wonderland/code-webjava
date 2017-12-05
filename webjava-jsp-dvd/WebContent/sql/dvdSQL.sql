CREATE VIEW film_info_category AS
SELECT fcat.film_id, f.title, f.description, f.language_id, f.special_features, cat.name as category_name
FROM film_category fcat
INNER JOIN category cat
ON fcat.category_id = cat.category_id
INNER JOIN film f
ON f.film_id = fcat.film_id


SELECT * FROM film_info_category;

CREATE VIEW actor_film_info_category AS
SELECT fa.actor_id, fa.film_id, fcview.title, fcview.category_name
FROM film_actor fa 
INNER JOIN film_info_category fcview
ON fcview.film_id = fa.film_id

select film_id, title, category_name from actor_film_info_category where actor_id =1;

--search

Select * from actor where (first_name LIKE '%Kevin%' AND last_name LIKE '%%');

Select * from actor where first_name LIKE '%r%' ORDER BY actor_id LIMIT 20 OFFSET 0;

SELECT * FROM (SELECT *, (first_name ||' '|| last_name) as full_name FROM actor) AS temp WHERE Full_name LIKE '%Kevin Bloom%';


Select * from actor where first_name LIKE '%r%' ORDER BY actor_id;


--User Table--
CREATE TABLE users
(user_id integer PRIMARY KEY UNIQUE NOT NULL,
password character varying(50) NOT NULL,
last_update timestamp NOT NULL,
CONSTRAINT users_user_id_fkey FOREIGN KEY (user_id)
REFERENCES customer(customer_id) ON UPDATE NO ACTION ON DELETE CASCADE);

INSERT INTO users (user_id, password, last_update) VALUES (1, '12345', now());

--last_update changed to default now()
ALTER TABLE users ALTER COLUMN last_update SET DEFAULT now();

--Drop CONSTRAINT for film assign actor
      
ALTER TABLE film_actor DROP CONSTRAINT film_actor_actor_id_fkey;
ALTER TABLE film_actor ADD CONSTRAINT film_actor_actor_id_fkey FOREIGN KEY (actor_id) REFERENCES actor (actor_id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE;
