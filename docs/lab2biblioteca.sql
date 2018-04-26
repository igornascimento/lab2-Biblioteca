--- MYSQL ---

CREATE SCHEMA lab2biblioteca;


CREATE TABLE lab2biblioteca.author (
  id INT NOT NULL,
  name VARCHAR(50) NULL,
  surname VARCHAR(50) NULL,
  country VARCHAR(50) NULL,
  PRIMARY KEY (id));


CREATE TABLE lab2biblioteca.book (
  isbn VARCHAR(100) NOT NULL,
  title VARCHAR(100) NULL,
  editor VARCHAR(50) NULL,
  publish-year INT NULL,
  PRIMARY KEY (isbn));


CREATE TABLE lab2biblioteca.customer (
  id INT NOT NULL,
  name VARCHAR(50) NULL,
  phone INT NULL,
  PRIMARY KEY (id));


CREATE TABLE lab2biblioteca.movimentation (
  id INT NOT NULL,
  date DATE NULL,
  customer-id INT NULL,
  PRIMARY KEY (id));


CREATE TABLE lab2biblioteca.movimentation-book (
  id INT NOT NULL,
  movimentation-id INT NULL,
  book-id INT NULL,
  PRIMARY KEY (id));



--- POSTGRE --

CREATE DATABASE postgres;


CREATE TABLE postgres.author (
  id integer NOT NULL,
  name character(50),
  surname character(50),
  country character(50),
  book_id character(50)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE postgres.author
  OWNER TO postgres;



CREATE TABLE postgres.book (
  isbn VARCHAR(100) NOT NULL,
  title VARCHAR(100) NULL,
  editor VARCHAR(50) NULL,
  publish_year INT NULL,
  PRIMARY KEY (isbn));


CREATE TABLE postgres.customer (
  id INT NOT NULL,
  name VARCHAR(50) NULL,
  phone INT NULL,
  PRIMARY KEY (id));


CREATE TABLE postgres.movimentation (
  id INT NOT NULL,
  date INTERVAL NULL,
  customer_id INT NULL,
  PRIMARY KEY (id));


CREATE TABLE postgres.movimentation_book (
  id INT NOT NULL,
  movimentation_id INT NULL,
  book_id INT NULL,
  PRIMARY KEY (id));



-------------------------------------------

insert into book
(isbn, title, editor, publish_year)
values
('8571644950', 'Ensaio sobre a cegueira', 'Companhia das Letras', 1995)