DROP TABLE IF EXISTS project_category;
DROP TABLE IF EXISTS category;
drop table if exists step;
drop table if exists material;
DROP TABLE IF EXISTS project;

CREATE TABLE project (
project_id INT NOT NULL,
PRIMARY KEY (project_id),
project_name VARCHAR(128) NOT NULL,
estimated_hours DECIMAL(7,2) NULL,
actual_hours DECIMAL(7,2) NULL,
difficulty INT NULL,
notes TEXT NULL
);

CREATE TABLE material ();

CREATE TABLE step ();

CREATE TABLE category ();

CREATE TABLE project_category ();