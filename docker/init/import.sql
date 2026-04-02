COPY continents(id, name)
    FROM '/data/continents.csv'
    DELIMITER ','
    CSV HEADER;

COPY countries(id, name, continent_id, population, area)
    FROM '/data/countries.csv'
    DELIMITER ','
    CSV HEADER;

COPY people(id, name)
    FROM '/data/people.csv'
    DELIMITER ','
    CSV HEADER;

COPY person_country(person_id, country_id)
    FROM '/data/person_country.csv'
    DELIMITER ','
    CSV HEADER;