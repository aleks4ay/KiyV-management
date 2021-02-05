CREATE TABLE orders
(
  big_number integer NOT NULL PRIMARY KEY,
  iddoc varchar(9),
  idclient varchar(9),
  idmanager varchar(9),
  duration integer,
  docno VARCHAR(10),
  pos_count INTEGER,
  client_name VARCHAR(50),
  manager_name VARCHAR(30),
  t_create TIMESTAMP,
  t_factory TIMESTAMP,
  t_end TIMESTAMP,
  t_invoice BIGINT,
  price DEC(14,3),
  payment DEC(14,3)
);

CREATE TABLE descriptions
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

CREATE TABLE statuses
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


CREATE TABLE workers
(
  id VARCHAR(9) NOT NULL PRIMARY KEY,
  id_parent VARCHAR(9),
  is_folder integer,
  name varchar(30)
);


CREATE TABLE clients
(
  id VARCHAR(9) NOT NULL PRIMARY KEY,
  id_parent VARCHAR (9),
  isfolder INTEGER,
  descr1 VARCHAR(50),
  descr2 VARCHAR(100),
  phone VARCHAR(48),
  inn VARCHAR(12),
  num_certificate VARCHAR(12),
  addr_fis VARCHAR(188),
  addr_urid VARCHAR(188)
);

CREATE TABLE tmc
(
  id VARCHAR(9) NOT NULL PRIMARY KEY,
  id_parent VARCHAR(9),
  code VARCHAR(5),
  descr VARCHAR(50),
  is_folder integer,
  descr_all VARCHAR(100),
  type varchar(9)
);

CREATE TABLE set_technologichka
(
  id VARCHAR(9) NOT NULL PRIMARY KEY,
  parentid VARCHAR(9),
  descr varchar(100),
  size_a INTEGER,
  size_b INTEGER,
  size_c INTEGER
);

CREATE TABLE manufacture
(
  id SERIAL NOT NULL PRIMARY KEY,
  iddoc varchar(9),
  position INTEGER,
  docno VARCHAR(10),
  id_order varchar(9),
  time21 BIGINT,
  amount integer,
  id_tmc varchar(9),
  descr_second VARCHAR(168),
  size_a INTEGER,
  size_b INTEGER,
  size_c INTEGER,
  embodiment varchar(9)
);

CREATE TABLE invoice
(
  iddoc varchar(9) NOT NULL PRIMARY KEY,
  docno VARCHAR(10),
  id_order varchar(9),
  time22 BIGINT,
  price DEC(14,3),
  repeat INTEGER DEFAULT 1
);


CREATE TABLE tmc
(
  id VARCHAR(9) NOT NULL PRIMARY KEY,
  id_parent VARCHAR(9),
  code VARCHAR(5),
  descr VARCHAR(50),
  is_folder integer,
  descr_all VARCHAR(100),
  type varchar(9)
);

CREATE VIEW order_view (
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
   from orders o, descriptions d, statuses t
   WHERE o.IDDOC = d.IDDOC and d.kod = t.kod
   ORDER BY d.kod;


CREATE VIEW manufacture_view (
    id, iddoc, docno, pos, kod, id_tmc, amount, id_order, time21, descr_second, size_a, size_b, size_c, embodiment
)
AS SELECT
     m.id, m.iddoc, m.docno, m.position, d.kod, m.id_tmc, m.amount, m.id_order, m.time21, m.descr_second, m.size_a, m.size_b, m.size_c, m.embodiment
   from descriptions d, manufacture m
   WHERE m.id_order = d.iddoc AND m.id_tmc = d.id_tmc AND m.amount = d.amount AND m.size_a = d.size_a AND m.size_b = d.size_b AND m.size_c = d.size_c
   ORDER BY m.id, m.iddoc;


SELECT * from orders o LEFT JOIN descriptions d ON o.big_number = d.big_number AND (o.d_create > 1555641600000) AND (o.d_create < 1556267821669);

UPDATE orders SET manager_name = (SELECT name FROM workers WHERE workers.id = orders.idManager);

UPDATE descriptions SET type = '-';

UPDATE orders SET client_name = (SELECT descr1 FROM clients WHERE clients.id = orders.idClient);








UPDATE statuses SET status_index = 24, time_24 = 1546466400010 WHERE iddoc = '  E4CQ' ;

SELECT sum(payment) FROM order_view;

SELECT m.*, d.pos FROM descriptions d, manufacture m WHERE m.id_order = d.iddoc AND m.id_tmc = d.id_tmc AND m.position <> d.pos;



SELECT * FROM tmc WHERE id = '   F9V';
SELECT * FROM tmc WHERE id = '   EXN';
SELECT * FROM tmc WHERE id = '    19';

UPDATE statuses SET time_21 = time_22;
UPDATE statuses SET time_22 = NULL;

SELECT * FROM order_view  WHERE t_create >= '2019-04-24' AND t_create <= '2019-05-24'  AND status_index < 21  ORDER BY big_number, kod;
SELECT * FROM order_view  WHERE t_create >= '2019-04-24 00:00:00.0' AND t_create <= '2019-05-24 00:00:00.0'  AND status_index < 21  ORDER BY big_number, kod;

UPDATE statuses SET time_24 = time_22 WHERE time_22 is NOT NULL;
UPDATE statuses SET status_index = 24 WHERE time_24 is NOT NULL;

SELECT * FROM order_view  WHERE t_create >= '2019-05-18' AND t_create <= '2019-05-24'  AND status_index > 22 AND pos = 1 ORDER BY big_number, kod;  /*= 57*/
SELECT * FROM order_view  WHERE t_create >= '2019-04-25' AND t_create <= '2019-05-24'  AND status_index > -1  ORDER BY big_number, kod; /*= 480 */

SELECT SUM(invoice.price) FROM invoice WHERE id_order = '  DYC0';

UPDATE orders SET payment = 0;
UPDATE o SET payment = i.price from orders o INNER JOIN   invoice i on i.id_order = o.iddoc;
UPDATE orders SET payment = (SELECT price from invoice i WHERE i.id_order = iddoc);

SELECT SUM(price), MAX(time22) FROM invoice WHERE id_order = '  DYC0' ;

/* UPDATE SQL column FROM another table.column !!!!!!!!!!!!!!!*/
UPDATE statuses SET time_22 = t_invoice
FROM orders WHERE orders.iddoc = statuses.iddoc and t_invoice NOTNULL;
/*--------------------------------------------------------------------*/

SELECT DISTINCT id_order FROM invoice;

SELECT docno FROM manufacture GROUP BY docno HAVING COUNT(docno)>1;
SELECT docno, time22 FROM manufacture GROUP BY docno, time22 HAVING COUNT(time22)>1;
SELECT docno, time22 FROM manufacture GROUP BY docno, time22 HAVING COUNT(time22)=1 AND COUNT(docno)>1;


SELECT * FROM set_technologichka WHERE descr NOT LIKE '%Стіл%' AND descr NOT LIKE '%Стелаж%' AND descr NOT LIKE '%Мийка%' AND descr NOT LIKE '%Станція бармена%'
                                       AND descr NOT LIKE '%Полка%';