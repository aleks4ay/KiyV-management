CREATE SCHEMA test;
SET SCHEMA 'test';

CREATE TABLE orders_test
(
  big_number integer NOT NULL PRIMARY KEY,
  iddoc varchar(9),
  idclient varchar(9),
  idmanager varchar(9),
  duration integer,
  docno VARCHAR(10),
  docno_manuf VARCHAR(10),
  docno_invoice VARCHAR(10),
  pos_count INTEGER,
  client_name VARCHAR(50),
  manager_name VARCHAR(30),
  t_create TIMESTAMP,
  t_factory TIMESTAMP,
  t_end TIMESTAMP,
  time22 BIGINT,
  price DEC(14,3),
  payment DEC(14,3),
  time_manuf TIMESTAMP,
  time_invoice TIMESTAMP
);

CREATE TABLE descriptions_test
(
  kod integer NOT NULL PRIMARY KEY,
  big_number integer,
  iddoc varchar(9),
  pos integer,
  id_tmc varchar(9),
  amount integer,
  descr_second VARCHAR(200),
  size_a INTEGER,
  size_b INTEGER,
  size_c INTEGER,
  embodiment varchar(9)
);

CREATE TABLE statuses_test
(
  kod integer NOT NULL PRIMARY KEY,
  iddoc VARCHAR(9),
  time_0 bigint,
  time_1 bigint,
  time_2 bigint,
  time_3 bigint,
  time_4 bigint,
  time_5 bigint,
  time_6 bigint,
  time_7 bigint,
  time_8 bigint,
  time_9 bigint,
  time_10 bigint,
  time_11 bigint,
  time_12 bigint,
  time_13 bigint,
  time_14 bigint,
  time_15 bigint,
  time_16 bigint,
  time_17 bigint,
  time_18 bigint,
  time_19 bigint,
  time_20 bigint,
  time_21 bigint,
  time_22 bigint,
  time_23 bigint,
  time_24 bigint,
  type_index INTEGER,
  status_index INTEGER,
  designer_name VARCHAR(30),
  is_technologichka INTEGER,
  descr_first VARCHAR(100)
);


CREATE TABLE tmc_test
(
  id VARCHAR(9) NOT NULL PRIMARY KEY,
  id_parent VARCHAR(9),
  code VARCHAR(5),
  descr VARCHAR(50),
  is_folder integer,
  descr_all VARCHAR(100),
  type varchar(9)
);

CREATE TABLE set_technologichka_test
(
  id VARCHAR(9) NOT NULL PRIMARY KEY,
  parentid VARCHAR(9),
  descr varchar(100),
  size_a INTEGER,
  size_b INTEGER,
  size_c INTEGER
);

CREATE TABLE manufacture_test
(
  id SERIAL NOT NULL PRIMARY KEY,
  iddoc varchar(9),
  position INTEGER,
  docno VARCHAR(10),
  id_order varchar(9),
  time_manuf TIMESTAMP,
  time21 BIGINT,
  amount integer,
  id_tmc varchar(9),
  descr_second VARCHAR(168),
  size_a INTEGER,
  size_b INTEGER,
  size_c INTEGER,
  embodiment varchar(9)
);

CREATE TABLE invoice_test
(
  iddoc varchar(9) NOT NULL PRIMARY KEY,
  docno VARCHAR(10),
  id_order varchar(9),
  time_invoice TIMESTAMP,
  time22 BIGINT,
  price DEC(14,3),
  repeat INTEGER DEFAULT 1
);

CREATE VIEW order_view_test (
    kod, big_number, pos, amount, descr_second, size_a, size_b, size_c,
    iddoc, duration, docno, pos_count, t_create, t_factory, t_end, t_invoice,  manager, client,
    type_index, status_index, is_technologichka, designer, descr_first,
    time_0, time_1, time_2, time_3, time_4, time_5, time_6, time_7, time_8, time_9,
    time_10, time_11, time_12, time_13, time_14, time_15, time_16, time_17, time_18, time_19,
    time_20, time_21, time_22, time_23, time_24, price, payment
)
AS SELECT
     d.kod, d.big_number, d.pos, d.amount, d.descr_second, d.size_a, d.size_b, d.size_c,
     o.iddoc, o.duration, o.docno, o.pos_count, o.t_create, o.t_factory, o.t_end, o.time22, o.manager_name, o.client_name,
     t.type_index, t.status_index, t.is_technologichka, t.designer_name, t.descr_first,
     t.time_0, t.time_1, t.time_2, t.time_3, t.time_4, t.time_5, t.time_6, t.time_7, t.time_8, t.time_9,
     t.time_10, t.time_11, t.time_12, t.time_13, t.time_14, t.time_15, t.time_16, t.time_17, t.time_18, t.time_19,
     t.time_20, t.time_21, t.time_22, t.time_23, t.time_24, o.price, o.payment
   from orders_test o, descriptions_test d, statuses_test t
   WHERE o.IDDOC = d.IDDOC and d.kod = t.kod
   ORDER BY d.kod;


CREATE VIEW manufacture_view_test (
    id, iddoc, docno, pos, kod, id_tmc, amount, id_order, time21, descr_second, size_a, size_b, size_c, embodiment
)
AS SELECT
     m.id, m.iddoc, m.docno, m.position, d.kod, m.id_tmc, m.amount, m.id_order, m.time21, m.descr_second, m.size_a, m.size_b, m.size_c, m.embodiment
   from descriptions_test d, manufacture_test m
   WHERE m.id_order = d.iddoc AND m.id_tmc = d.id_tmc AND m.amount = d.amount AND m.size_a = d.size_a AND m.size_b = d.size_b AND m.size_c = d.size_c
   ORDER BY m.id, m.iddoc;
