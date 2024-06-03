DROP TABLE IF EXISTS project_category;
DROP TABLE IF EXISTS category;
drop table if exists step;
drop table if exists material;
DROP TABLE IF EXISTS project;

CREATE table project (
project_id INT NOT NULL AUTO_INCREMENT,
PRIMARY KEY (project_id),
project_name VARCHAR(128) NOT NULL,
estimated_hours DECIMAL(7,2) NULL,
actual_hours DECIMAL(7,2) NULL,
difficulty INT NULL,
notes TEXT NULL
);

create table material (
material_id INT NOT NULL AUTO_INCREMENT,
PRIMARY KEY (material_id),
project_id INT NOT NULL,
FOREIGN KEY (project_id) references project(project_id),
material_name VARCHAR(128) NOT NULL,
num_required INT,
cost DECIMAL(7,2)
);

CREATE TABLE step (
step_id INT NOT NULL AUTO_INCREMENT,
project_id INT NOT NULL,
step_text TEXT NOT NULL,
step_order INT NOT NULL,
PRIMARY KEY (step_id),
FOREIGN KEY (project_id) REFERENCES project(project_id)
);

CREATE TABLE category (
category_id INT NOT NULL AUTO_INCREMENT,
category_name VARCHAR(128) NOT NULL,
PRIMARY KEY (category_id)
);

CREATE TABLE project_category (
project_id INT NOT NULL,
category_id INT NOT NULL,
UNIQUE (project_id),
UNIQUE (category_id),
FOREIGN KEY (project_id) REFERENCES project(project_id),
FOREIGN KEY (category_id) REFERENCES category(category_id)
);