CREATE OR REPLACE VIEW film_info_category AS
SELECT f.*, cat.category_id, cat.name as category_name
FROM film_category fcat
INNER JOIN category cat
ON fcat.category_id = cat.category_id
INNER JOIN film f
ON f.film_id = fcat.film_id

CREATE OR REPLACE VIEW actor_film_info_category AS
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


-- copy one data to another
ALTER TABLE users ALTER COLUMN password SET DEFAULT '123456';
INSERT INTO users (user_id)  select customer_id from customer;
ALTER TABLE users ALTER COLUMN password DROP DEFAULT;

--Drop CONSTRAINT for film assign actor
      
ALTER TABLE film_actor DROP CONSTRAINT film_actor_actor_id_fkey;
ALTER TABLE film_actor ADD CONSTRAINT film_actor_actor_id_fkey FOREIGN KEY (actor_id) REFERENCES actor (actor_id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE;

-- import example 
COPY inventory FROM 'D://inventory.txt' WITH (FORMAT csv);
-- export example
COPY inventory TO 'D:\tmp\inventory_db.csv' DELIMITER ',' CSV HEADER;

-- prepare for inventory
ALTER TABLE inventory ADD COLUMN price numeric;
-- copy replacement_cost to price and price down
UPDATE inventory inv SET price = (SELECT f.replacement_cost FROM film f WHERE f.film_id = inv.film_id)/10


--tsvector
SELECT
  film_id, title
FROM
  film, plainto_tsquery('alien') q
WHERE
  fulltext @@ q;

  SELECT * FROM film WHERE fulltext @@ plainto_tsquery('action');

 -- select film by store
CREATE OR REPLACE VIEW film_inventory AS
SELECT f.*, inv.inventory_id, inv.store_id , inv.price FROM film f
INNER JOIN inventory inv
ON inv.film_id = f.film_id;

CREATE OR REPLACE VIEW film_inventory_category AS
SELECT fcview.*, fiview.inventory_id, fiview.store_id, fiview.price FROM film_info_category fcview
INNER JOIN film_inventory fiview
ON fiview.film_id = fcview.film_id;

SELECT DISTINCT ficview.film_id, ficview.title, ficview.description, ficview.price, ficview.special_features, ficview.category_name, ficview.release_year, ficview.rental_rate, ficview.rating, (SELECT COUNT(film_id) AS quantity FROM inventory inv WHERE inv.film_id = ficview.film_id GROUP BY ficview.film_id) 
FROM film_inventory_category ficview
WHERE ficview.store_id=1 AND ficview.rental_rate>=4 AND ficview.title LIKE 'Apache%'
ORDER BY quantity DESC LIMIT 200;


select * from film_inventory_category;
