-- Tworzenie tabeli z unkatowy id i auto inkrementacja
CREATE TABLE IF NOT EXISTS koty(id INTEGER PRIMARY KEY AUTOINCREMENT, nazwa VARCHAR(20));

-- Pobierz koty.
SELECT id, nazwa FROM koty;

-- pobierz nazwe kota id = 1
SELECT nazwa FROM koty WHERE id = 1;

-- pobierz ilosc kotow.
SELECT COUNT(*) AS ilosc FROM koty; // * dla kazdego rekordu

-- dodaj kota
INSERT INTO koty (nazwa) VALUES ('nazwa kota');

-- zmien nazwe kota o id = 1
UPDATE koty SET nazwa='nowa nazwa' WHERE id = 1;

-- usuwamy kota o id = 1
DELETE FROM koty WHERE id = 1;

