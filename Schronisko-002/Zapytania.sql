CREATE TABLE IF NOT EXISTS koty(id INT IDENTITY(1, 1) PRIMARY KEY, nazwa VARCHAR(20));

-- Pobierz koty.
SELECT id, nazwa FROM koty;

-- pobierz nazwe kota id = 1
SELECT nazwa FROM koty WHERE id = 1;

-- pobierz ilosc kotow.
SELECT COUNT(id) AS ilosc FROM koty;

-- dodaj kota
INSERT INTO koty (nazwa) VALUES ('nazwa kota');

-- zmien nazwe kota o id = 1
UPDATE koty SET nazwa='nowa nazwa' WHERE id = 1;

-- usuwamy kota o id = 1
DELETE FROM koty WHERE id = 1;

