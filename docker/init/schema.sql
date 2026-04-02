DROP TABLE IF EXISTS person_country;
DROP TABLE IF EXISTS people;
DROP TABLE IF EXISTS countries;
DROP TABLE IF EXISTS continents;

CREATE TABLE continents
(
    id   SERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE
);

CREATE TABLE countries
(
    id           SERIAL PRIMARY KEY,
    name         TEXT           NOT NULL,
    continent_id INT            NOT NULL REFERENCES continents (id) ON DELETE CASCADE,
    population   BIGINT         NOT NULL CHECK (population >= 0),
    area         NUMERIC(15, 2) NOT NULL CHECK (area > 0)
);

CREATE TABLE people
(
    id   SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE person_country
(
    person_id  INT REFERENCES people (id) ON DELETE CASCADE,
    country_id INT REFERENCES countries (id) ON DELETE CASCADE,
    PRIMARY KEY (person_id, country_id)
);

--Country with the biggest population (id + name)
SELECT id, name
FROM countries
ORDER BY population DESC
LIMIT 1;

--Top 10 countries with the lowest population density
SELECT name
FROM countries
ORDER BY (population::numeric / area)
LIMIT 10;

--Countries with density higher than average
SELECT name
FROM countries
WHERE (population::numeric / area) >
      (SELECT AVG(population::numeric / area) FROM countries);

--Country with the longest name (handle ties)
SELECT name
FROM countries
WHERE LENGTH(name) = (
    SELECT MAX(LENGTH(name)) FROM countries
);

--Countries containing letter "F" (sorted)
SELECT name
FROM countries
WHERE name ILIKE '%f%'
ORDER BY name;

--Country closest to average population
SELECT name, population
FROM countries
ORDER BY ABS(population - (SELECT AVG(population) FROM countries))
LIMIT 1;

--Count of countries per continent
SELECT c.name, COUNT(ct.id) AS country_count
FROM continents c
         LEFT JOIN countries ct ON c.id = ct.continent_id
GROUP BY c.name;

--Total area per continent (sorted DESC)
SELECT c.name, SUM(ct.area) AS total_area
FROM continents c
         JOIN countries ct ON c.id = ct.continent_id
GROUP BY c.name
ORDER BY total_area DESC;

--Average population density per continent
SELECT c.name,
       AVG(ct.population::numeric / ct.area) AS avg_density
FROM continents c
         JOIN countries ct ON c.id = ct.continent_id
GROUP BY c.name;

--Smallest country per continent
SELECT DISTINCT ON (c.id)
    c.name AS continent,
    ct.name AS country,
    ct.area
FROM continents c
         JOIN countries ct ON c.id = ct.continent_id
ORDER BY c.id, ct.area;

--Continents with avg population < 20 million
SELECT c.name
FROM continents c
JOIN countries ct ON c.id = ct.continent_id
GROUP BY c.name
HAVING AVG(ct.population) < 200000000;

--Person with most citizenship's
SELECT p.id, p.name, COUNT(pc.country_id) AS citizenship_count
FROM people p
         JOIN person_country pc ON p.id = pc.person_id
GROUP BY p.id
ORDER BY citizenship_count DESC
LIMIT 1;

--People with NO citizenship
SELECT p.id, p.name
FROM people p
         LEFT JOIN person_country pc ON p.id = pc.person_id
WHERE pc.person_id IS NULL;

--Country with the least people
SELECT c.name
FROM countries c
         LEFT JOIN person_country pc ON c.id = pc.country_id
GROUP BY c.id
ORDER BY COUNT(pc.person_id)
LIMIT 1;

--Continent with most people
SELECT con.name
FROM continents con
         JOIN countries c ON con.id = c.continent_id
         JOIN person_country pc ON c.id = pc.country_id
GROUP BY con.name
ORDER BY COUNT(DISTINCT pc.person_id) DESC
LIMIT 1;

--Pairs of people with same name
SELECT p1.id, p2.id, p1.name
FROM people p1
         JOIN people p2
              ON p1.name = p2.name AND p1.id < p2.id;