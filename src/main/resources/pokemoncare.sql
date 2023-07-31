-- Drop the database if it exists
DROP DATABASE IF EXISTS pokemoncare;

-- Create the database
CREATE DATABASE pokemoncare;

-- Use the database
USE pokemoncare;

-- Create the Trainer table
CREATE TABLE trainer (
    TrainerID INT PRIMARY KEY AUTO_INCREMENT,
    TrainerName VARCHAR(100) NOT NULL,
    TrainerAge INT,
    TrainerEmail VARCHAR(100)
);

-- Create the Pokemon table
CREATE TABLE pokemon (
    PokemonID INT PRIMARY KEY AUTO_INCREMENT,
    PokemonName VARCHAR(100) NOT NULL,
    Species VARCHAR(100) NOT NULL,
    PokemonLevel INT,
    TrainerID INT,
    FOREIGN KEY (TrainerID) REFERENCES trainer(TrainerID)
);

-- Create the Type table
CREATE TABLE type (
    TypeID INT PRIMARY KEY AUTO_INCREMENT,
    TypeName VARCHAR(50) NOT NULL
);

-- Create the PokemonType table to represent the many-to-many relationship between Pokemon and types
CREATE TABLE pokemontype (
    PokemonID INT,
    TypeID INT,
    PRIMARY KEY (PokemonID, TypeID),
    FOREIGN KEY (PokemonID) REFERENCES pokemon(PokemonID),
    FOREIGN KEY (TypeID) REFERENCES type(TypeID)
);

-- Create the PokemonCenter table
CREATE TABLE pokemoncenter (
    PokemonCenterID INT PRIMARY KEY AUTO_INCREMENT,
    CenterName VARCHAR(100) NOT NULL,
    CenterAddress VARCHAR(255) NOT NULL
);

-- Create the HealthRecord table
CREATE TABLE healthrecord (
    HealthRecordID INT PRIMARY KEY AUTO_INCREMENT,
    HealthDescription VARCHAR(255) NOT NULL,
    HealthDateRecorded DATETIME NOT NULL,
    PokemonID INT,
    PokemonCenterID INT,
    FOREIGN KEY (PokemonID) REFERENCES pokemon(PokemonID),
    FOREIGN KEY (PokemonCenterID) REFERENCES pokemoncenter(PokemonCenterID)
);

-- Insert data into Trainer table
INSERT INTO trainer (TrainerName, TrainerAge, TrainerEmail)
VALUES
    ('Ash Ketchum', 10, 'ash@example.com'),
    ('Misty', 12, 'misty@example.com'),
    ('Brock', 15, 'brock@example.com'),
    ('Red', 18, 'red@example.com'),
    ('Salma Khairat', 22, 'salma@example.com'),
    ('Melissa Augustin', 30, 'melissa@example.com'),
    ('Sophia Zhou', 25, 'sophia@example.com'),
    ('Lien Nguyen', 27, 'lien@example.com'),
    ('Lupita Peralta', 28, 'lupita@example.com'),
    ('Maimouna Sow', 24, 'maimouna@example.com'),
    ('Jenna Triolo', 26, 'jenna@example.com'),
    ('Phil Williams', 35, 'phil@example.com'),
    ('Navdeep Kaur', 29, 'navdeep@example.com');

-- Insert data into Pokemon table with Species and correct TrainerID values
-- Insert data into Pokemon table with Species and correct TrainerID values
INSERT INTO pokemon (PokemonName, Species, PokemonLevel, TrainerID)
VALUES
    ('Pikachu', 'Mouse Pokemon', 15, 1),
    ('Charmander', 'Lizard Pokemon', 12, 2),
    ('Squirtle', 'Tiny Turtle Pokemon', 10, 3),
    ('Bulbasaur', 'Seed Pokemon', 18, 4),
    ('Eevee', 'Evolution Pokemon', 22, 5),
    ('Snorlax', 'Sleeping Pokemon', 30, 6),
    ('Mewtwo', 'Genetic Pokemon', 40, 7),
    ('Dragonite', 'Dragon Pokemon', 38, 8),
    ('Gengar', 'Shadow Pokemon', 33, 9),
    ('Lucario', 'Aura Pokemon', 36, 10),
    ('Alakazam', 'Psychic Pokemon', 32, 11),
    ('Tyranitar', 'Armor Pokemon', 39, 12),
    ('Scizor', 'Pincer Pokemon', 35, 13),
    ('Salamence', 'Dragon Pokemon', 37, 1),
    ('Garchomp', 'Mach Pokemon', 34, 2),
    ('Lapras', 'Transport Pokemon', 31, 3),
    ('Mamoswine', 'Twin Tusk Pokemon', 29, 4);

-- Insert data into Type table
INSERT INTO type (TypeName)
VALUES
    ('Electric'),
    ('Fire'),
    ('Water'),
    ('Grass'),
    ('Normal'),
    ('Flying'),
    ('Fighting'),
    ('Psychic'),
    ('Poison'),
    ('Ground'),
    ('Rock'),
    ('Ice'),
    ('Bug'),
    ('Dragon');

-- Insert data into PokemonType table with multiple types for a Pokemon
INSERT INTO pokemontype (PokemonID, TypeID)
VALUES
    (1, 1), -- Pikachu is Electric type
    (2, 2), -- Charmander is Fire type
    (3, 3), -- Squirtle is Water type
    (4, 4), -- Bulbasaur is Grass type
    (4, 9), -- Bulbasaur is also Poison type
    (5, 5), -- Eevee is Normal type
    (6, 5), -- Snorlax is also Normal type
    (7, 8), -- Mewtwo is Psychic type
    (8, 11), -- Dragonite is Dragon type
    (9, 8), -- Gengar is Psychic type
    (10, 7), -- Lucario is Fighting type
    (11, 6), -- Alakazam is Flying type
    (12, 11), -- Tyranitar is Rock type
    (13, 8), -- Scizor is Psychic type
    (14, 11), -- Salamence is Dragon type
    (15, 3), -- Lapras is Water type
    (16, 12); -- Mamoswine is Ice type

-- Insert data into PokemonCenter table
INSERT INTO pokemoncenter (CenterName, CenterAddress)
VALUES
    ('Pallet Town Pokecenter 1', '9919 Turf Something'),
    ('Cerulean City Pokecenter 2', '3355 Fully Cove'),
    ('Saffron City Pokecenter 3', '4464 Over Drive'),
    ('Celadon City Pokecenter 4', '777 Green Street'),
    ('Vermilion City Pokecenter 5', '123 Thunder Avenue'),
    ('Lavender Town Pokecenter 6', '246 Ghostly Lane');

-- Insert data into HealthRecord table
INSERT INTO healthrecord (HealthDescription, HealthDateRecorded, PokemonID, PokemonCenterID)
VALUES
    ('Recovered from a cold', '2023-07-20 10:00:00', 1, 1),
    ('Checked for injuries', '2023-07-22 12:30:00', 2, 2),
    ('Regular checkup', '2023-07-20 14:15:00', 3, 3),
    ('Feeling great!', '2023-07-20 16:45:00', 4, 1),
    ('Flu vaccination', '2023-07-21 11:30:00', 5, 4),
    ('Annual checkup', '2023-07-19 09:00:00', 6, 5),
    ('Psychic abilities test', '2023-07-18 15:30:00', 7, 3),
    ('Dragon training evaluation', '2023-07-23 13:00:00', 8, 6),
    ('Nightmare check', '2023-07-24 08:45:00', 9, 1),
    ('Aura control assessment', '2023-07-19 16:00:00', 10, 2),
    ('Mega Evolution exam', '2023-07-20 09:15:00', 11, 3),
    ('Rock-hard determination test', '2023-07-22 10:30:00', 12, 4),
    ('Psychic development check', '2023-07-21 15:45:00', 13, 5),
    ('Dragon-type aptitude evaluation', '2023-07-23 12:00:00', 14, 6),
    ('Water survival assessment', '2023-07-18 13:45:00', 15, 3),
    ('Ice training evaluation', '2023-07-24 10:15:00', 16, 1);