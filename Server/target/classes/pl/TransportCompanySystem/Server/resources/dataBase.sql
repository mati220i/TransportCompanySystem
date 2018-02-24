DROP TABLE USERS CASCADE CONSTRAINT PURGE;
DROP SEQUENCE userSeq;
DROP TABLE package CASCADE CONSTRAINT PURGE;
DROP SEQUENCE packageSeq;
DROP TABLE COURIERS CASCADE CONSTRAINT PURGE;
DROP SEQUENCE courierSeq;
DROP TABLE CARS CASCADE CONSTRAINT PURGE;
DROP SEQUENCE carSeq;
DROP TABLE LOCATION CASCADE CONSTRAINT PURGE;
DROP SEQUENCE locationSeq;


CREATE TABLE USERS (
  userID NUMBER NOT NULL PRIMARY KEY,
  name VARCHAR2(50) NOT NULL,
  surname VARCHAR2(60) NOT NULL,
  login VARCHAR2(15) NOT NULL,
  password VARCHAR2(15) NOT NULL,
  address VARCHAR2(80) NOT NULL,
  accountType VARCHAR2(10) NOT NULL
);


 CREATE SEQUENCE userSeq
  START WITH 1
  INCREMENT BY 1
  CACHE 200;



CREATE TABLE CARS (
  carID NUMBER NOT NULL PRIMARY KEY,
  carType VARCHAR2(50) NOT NULL,
  isAvailable NUMBER NOT NULL
);



CREATE SEQUENCE carSeq
  START WITH 1
  INCREMENT BY 1
  CACHE 200;
  
  

CREATE TABLE COURIERS (
  courierID NUMBER NOT NULL PRIMARY KEY,
  carID NUMBER NOT NULL,
  login VARCHAR2(15) NOT NULL,
  password VARCHAR2(15) NOT NULL,
  name VARCHAR2(50) NOT NULL,
  surname VARCHAR2(60) NOT NULL,
  salary NUMBER NOT NULL,
  accountType VARCHAR2(10) NOT NULL,
  CONSTRAINT FK_CAR FOREIGN KEY (carID) REFERENCES CARS(carID)
);


CREATE SEQUENCE courierSeq
  START WITH 1
  INCREMENT BY 1
  CACHE 200;



CREATE TABLE LOCATION (
  locationID NUMBER NOT NULL PRIMARY KEY,
  locationName VARCHAR2(40) NOT NULL
);


CREATE SEQUENCE locationSeq
  START WITH 1
  INCREMENT BY 1
  CACHE 200;
  


  

CREATE TABLE PACKAGE (
  packageID NUMBER NOT NULL PRIMARY KEY,
  userID NUMBER NOT NULL,
  courierID NUMBER NOT NULL,
  locationID NUMBER,
  packageSize VARCHAR2(10) NOT NULL,
  packageWeight NUMBER(4,1) NOT NULL,
  withComplaint NUMBER NOT NULL,
  price NUMBER(4,2) NOT NULL,
  status VARCHAR2(2000),
  paymentMethod VARCHAR2(30),
  recipientName VARCHAR2(50),
  recipientSurname VARCHAR2(60),
  recipientAddress VARCHAR2(80),
  CONSTRAINT FK_USER FOREIGN KEY (userID) REFERENCES USERS(userID),
  CONSTRAINT FK_COURIER FOREIGN KEY (courierID) REFERENCES COURIERS(courierID)
);


CREATE SEQUENCE packageSeq
  START WITH 1
  INCREMENT BY 1
  CACHE 200;
  
  
  
  

insert into users values(userSeq.nextval,'Mateusz','Œliwa','admin','admin', 'Piñczów', 'Admin');
insert into users values(userSeq.nextval,'U¿ytkownik','Usuniêty','delete','delete', 'Usuniêty', 'User');
insert into users values(userSeq.nextval,'Tadeusz','Kozie³','tadek','tadek', 'Piñczów', 'User');
insert into users values(userSeq.nextval,'Urszula','Brejdak','ula','ula', 'Busko Zdrój', 'User');
insert into users values(userSeq.nextval,'Barbara','Rutkowska','barbara','barbara', 'Jêdrzejów', 'User');
insert into users values(userSeq.nextval,'Stefan','Jurkowski','stefan','stefan', 'Chmielnik', 'User');
insert into users values(userSeq.nextval,'Tadeusz','Badura','badura','badura', 'Kielce', 'User');
insert into users values(userSeq.nextval,'Janusz','Chytrus','janusz','janusz', 'Morawica', 'User');
insert into users values(userSeq.nextval,'Mateusz','Œliwa','mati','mati', 'Piñczów', 'User');
insert into users values(userSeq.nextval,'Ryszard','Socha','rysiek','rysiek', 'Kraków', 'User');


insert into cars values(carSeq.nextval,'Admin', 0);
insert into cars values(carSeq.nextval,'Van', 0);
insert into cars values(carSeq.nextval,'Van', 0);
insert into cars values(carSeq.nextval,'Van', 1);
insert into cars values(carSeq.nextval,'Van', 1);
insert into cars values(carSeq.nextval,'Dostawczy', 0);
insert into cars values(carSeq.nextval,'Dostawczy', 1);
insert into cars values(carSeq.nextval,'Dostawczy', 0);
insert into cars values(carSeq.nextval,'Dostawczy', 1);
insert into cars values(carSeq.nextval,'Dostawczy', 0);
insert into cars values(carSeq.nextval,'Dostawczy', 1);
insert into cars values(carSeq.nextval,'Dostawczy', 0);
insert into cars values(carSeq.nextval,'Ciê¿arówka', 1);
insert into cars values(carSeq.nextval,'Ciê¿arówka', 1);
insert into cars values(carSeq.nextval,'Ciê¿arówka', 0);
insert into cars values(carSeq.nextval,'Ciê¿arówka', 1);
insert into cars values(carSeq.nextval,'Ciê¿arówka', 0);
insert into cars values(carSeq.nextval,'Ciê¿arówka', 1);
insert into cars values(carSeq.nextval,'Ciê¿arówka', 1);
insert into cars values(carSeq.nextval,'Ciê¿arówka', 1);
insert into cars values(carSeq.nextval,'Ciê¿arówka', 1);
insert into cars values(carSeq.nextval,'Ci¹gnik Siod³owy', 0);
insert into cars values(carSeq.nextval,'Ci¹gnik Siod³owy', 1);
insert into cars values(carSeq.nextval,'Ci¹gnik Siod³owy', 1);
insert into cars values(carSeq.nextval,'Ci¹gnik Siod³owy', 1);
insert into cars values(carSeq.nextval,'Ci¹gnik Siod³owy', 1);
insert into cars values(carSeq.nextval,'Ci¹gnik Siod³owy', 1);
insert into cars values(carSeq.nextval,'Ci¹gnik Siod³owy', 1);
insert into cars values(carSeq.nextval,'Ci¹gnik Siod³owy', 1);
insert into cars values(carSeq.nextval,'Ci¹gnik Siod³owy', 1);


insert into couriers values(courierSeq.nextval, 1, 'nAdmin', 'nAdmin', 'Paczki', 'Nienadane', 2222, 'Courier');
insert into couriers values(courierSeq.nextval, 1, 'dAdmin', 'dAdmin', 'Paczki', 'Dostarczone', 2222, 'Courier');
insert into couriers values(courierSeq.nextval, 2, 'andrzej', 'andrzej', 'Andrzej', 'Duda', 2400, 'Courier');
insert into couriers values(courierSeq.nextval, 3, 'jozef', 'jozef', 'Józef', 'Kozie³', 2300, 'Courier');
insert into couriers values(courierSeq.nextval, 6, 'janusz', 'chytrus', 'Janusz', 'Chytrus', 1800, 'Courier');
insert into couriers values(courierSeq.nextval, 8, 'zbyszek', 'zbyszek', 'Zbyszek', 'Trzeciak', 3100, 'Courier');
insert into couriers values(courierSeq.nextval, 10, 'aneta', 'aneta', 'Aneta', 'Duda³a', 3400, 'Courier');
insert into couriers values(courierSeq.nextval, 12, 'jan', 'jan', 'Jan', 'Wójcik', 3700, 'Courier');
insert into couriers values(courierSeq.nextval, 15, 'dawid', 'dawid', 'Dawid', 'Warzecha', 1450, 'Courier');
insert into couriers values(courierSeq.nextval, 17, 'mirek', 'mirek', 'Miros³aw', 'Puchalski', 2900, 'Courier');
insert into couriers values(courierSeq.nextval, 22, 'mateusz', 'mateusz', 'Mateusz', 'Olesiak', 3200, 'Courier');

insert into location values(locationSeq.nextval, 'Kielce');
insert into location values(locationSeq.nextval, 'Tarnów');
insert into location values(locationSeq.nextval, 'Kraków');
insert into location values(locationSeq.nextval, 'Warszawa');
insert into location values(locationSeq.nextval, 'Bydgoszcz');
insert into location values(locationSeq.nextval, 'Katowice');


insert into package values(packageSeq.nextval, 7, 1, 3, 'Ma³a', 7.2, 0, 14.2, 'Paczka zosta³a nadana do oddzia³u w Kielce', 'Karta', 'Jan', 'Kowalski', 'Kielce');
insert into package values(packageSeq.nextval, 3, 3, 4, 'Ma³a', 2.2, 0, 21.2, 'Paczka zosta³a nadana do oddzia³u w Jêdrzejów', 'Karta', 'Antoni', 'Gadawski', 'Jêdrzejów');
insert into package values(packageSeq.nextval, 3, 3, 1, 'Ma³a', 1.3, 1, 24.2 , 'Paczka zosta³a nadana do oddzia³u w Piñczów', 'Gotówka', 'Agata', 'Kozie³', 'Piñczów');
insert into package values(packageSeq.nextval, 4, 4, 1, 'Ma³a', 5.4, 0, 4.2, 'Paczka zosta³a nadana do oddzia³u w Chmielnik', 'Karta', 'Dawid', 'Mroziñski', 'Chmielnik');
insert into package values(packageSeq.nextval, 5, 5, 1, 'Ma³a', 2.2, 0, 24.2, 'Paczka zosta³a nadana do oddzia³u w Daleszyce', 'Gotówka', 'Mateusz', 'Zaj¹c', 'Daleszyce');
insert into package values(packageSeq.nextval, 6, 6, 1, 'Œrednia', 17.5, 1, 14.2, 'Paczka zosta³a nadana do oddzia³u w Morawica', 'Karta', 'Stefan', 'Rutkowski', 'Morawica');
insert into package values(packageSeq.nextval, 6, 3, 2, 'Œrednia', 5.2, 0, 34.2, 'Paczka zosta³a nadana do oddzia³u w Chêciny', 'Gotówka', 'Sebastian', 'Sat³awa', 'Chêciny');
insert into package values(packageSeq.nextval, 3, 6, 2, 'Œrednia', 11.4, 0, 24.2, 'Paczka zosta³a nadana do oddzia³u w Kielce', 'Karta', 'Wioletta', 'Dudkowski', 'Kielce');
insert into package values(packageSeq.nextval, 3, 4, 3, 'Œrednia', 10.6, 1, 12.2, 'Paczka zosta³a nadana do oddzia³u w Morawica', 'Gotówka', 'Katarzyna', 'Parlicka', 'Morawica');
insert into package values(packageSeq.nextval, 3, 2, 4, 'Œrednia', 1.8, 0, 15.2, 'Paczka zosta³a nadana do oddzia³u w Chmielnik', 'Karta', 'Magdalena', 'Nowak', 'Chmielnik');
insert into package values(packageSeq.nextval, 5, 3, 5, 'Œrednia', 0.2, 0, 24.2, 'Paczka zosta³a nadana do oddzia³u w Piñczów', 'Gotówka', 'Marta', 'Jaœko', 'Piñczów');
insert into package values(packageSeq.nextval, 6, 6, 6, 'Œrednia', 0.6, 0, 14.2, 'Paczka zosta³a nadana do oddzia³u w Jêdrzejów', 'Karta', 'Barbara', 'Gaw³owska', 'Jêdrzejów');
insert into package values(packageSeq.nextval, 7, 3, 5, 'Du¿a', 0.3, 0, 14.2, 'Paczka zosta³a nadana do oddzia³u w Kielce', 'Karta', 'Tadeusz', 'Kieliœ', 'Kielce');
insert into package values(packageSeq.nextval, 3, 9, 5, 'Du¿a', 3.2, 0, 24.2, 'Paczka zosta³a nadana do oddzia³u w Busko Zdrój', 'Gotówka', 'Jan', 'Krêcisz', 'Busko Zdrój');
insert into package values(packageSeq.nextval, 6, 5, 4, 'Du¿a', 5.5, 1, 5.2, 'Paczka zosta³a nadana do oddzia³u w Kraków', 'Karta', 'Józef', 'Nowak', 'Kraków');
insert into package values(packageSeq.nextval, 6, 3, 4, 'Du¿a', 2.2, 0, 14.2, 'Paczka zosta³a nadana do oddzia³u w Warszawa', 'Gotówka', 'Janusz', 'Ciszowski', 'Warszawa');
insert into package values(packageSeq.nextval, 6, 6, 3, 'Du¿a', 14.5, 0, 25.2, 'Paczka zosta³a nadana do oddzia³u w Rzeszów', 'Gotówka', 'Czes³aw', 'Brejdak', 'Rzeszów');
insert into package values(packageSeq.nextval, 5, 9, 3, 'Du¿a', 16.2, 0, 6.2, 'Paczka zosta³a nadana do oddzia³u w Kielce', 'Karta', 'Robert', 'Puchalski', 'Kielce');
insert into package values(packageSeq.nextval, 3, 6, 3, 'Du¿a', 22.3, 0, 14.2, 'Paczka zosta³a nadana do oddzia³u w Busko Zdrój', 'Gotówka', 'Tomasz', 'Wolny', 'Busko Zdrój');
insert into package values(packageSeq.nextval, 4, 3, 6, 'Du¿a', 17.6, 0, 14.2, 'Paczka zosta³a nadana do oddzia³u w Piñczów', 'Gotówka', 'Marek', 'Markowski', 'Piñczów');
insert into package values(packageSeq.nextval, 4, 8, 6, 'Du¿a', 30.6, 0, 14.2, 'Paczka zosta³a nadana do oddzia³u w Kielce', 'Karta', 'Justyna', 'Socha', 'Kielce');


commit;