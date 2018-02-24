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
  
  
  
  

insert into users values(userSeq.nextval,'Mateusz','�liwa','admin','admin', 'Pi�cz�w', 'Admin');
insert into users values(userSeq.nextval,'U�ytkownik','Usuni�ty','delete','delete', 'Usuni�ty', 'User');
insert into users values(userSeq.nextval,'Tadeusz','Kozie�','tadek','tadek', 'Pi�cz�w', 'User');
insert into users values(userSeq.nextval,'Urszula','Brejdak','ula','ula', 'Busko Zdr�j', 'User');
insert into users values(userSeq.nextval,'Barbara','Rutkowska','barbara','barbara', 'J�drzej�w', 'User');
insert into users values(userSeq.nextval,'Stefan','Jurkowski','stefan','stefan', 'Chmielnik', 'User');
insert into users values(userSeq.nextval,'Tadeusz','Badura','badura','badura', 'Kielce', 'User');
insert into users values(userSeq.nextval,'Janusz','Chytrus','janusz','janusz', 'Morawica', 'User');
insert into users values(userSeq.nextval,'Mateusz','�liwa','mati','mati', 'Pi�cz�w', 'User');
insert into users values(userSeq.nextval,'Ryszard','Socha','rysiek','rysiek', 'Krak�w', 'User');


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
insert into cars values(carSeq.nextval,'Ci�ar�wka', 1);
insert into cars values(carSeq.nextval,'Ci�ar�wka', 1);
insert into cars values(carSeq.nextval,'Ci�ar�wka', 0);
insert into cars values(carSeq.nextval,'Ci�ar�wka', 1);
insert into cars values(carSeq.nextval,'Ci�ar�wka', 0);
insert into cars values(carSeq.nextval,'Ci�ar�wka', 1);
insert into cars values(carSeq.nextval,'Ci�ar�wka', 1);
insert into cars values(carSeq.nextval,'Ci�ar�wka', 1);
insert into cars values(carSeq.nextval,'Ci�ar�wka', 1);
insert into cars values(carSeq.nextval,'Ci�gnik Siod�owy', 0);
insert into cars values(carSeq.nextval,'Ci�gnik Siod�owy', 1);
insert into cars values(carSeq.nextval,'Ci�gnik Siod�owy', 1);
insert into cars values(carSeq.nextval,'Ci�gnik Siod�owy', 1);
insert into cars values(carSeq.nextval,'Ci�gnik Siod�owy', 1);
insert into cars values(carSeq.nextval,'Ci�gnik Siod�owy', 1);
insert into cars values(carSeq.nextval,'Ci�gnik Siod�owy', 1);
insert into cars values(carSeq.nextval,'Ci�gnik Siod�owy', 1);
insert into cars values(carSeq.nextval,'Ci�gnik Siod�owy', 1);


insert into couriers values(courierSeq.nextval, 1, 'nAdmin', 'nAdmin', 'Paczki', 'Nienadane', 2222, 'Courier');
insert into couriers values(courierSeq.nextval, 1, 'dAdmin', 'dAdmin', 'Paczki', 'Dostarczone', 2222, 'Courier');
insert into couriers values(courierSeq.nextval, 2, 'andrzej', 'andrzej', 'Andrzej', 'Duda', 2400, 'Courier');
insert into couriers values(courierSeq.nextval, 3, 'jozef', 'jozef', 'J�zef', 'Kozie�', 2300, 'Courier');
insert into couriers values(courierSeq.nextval, 6, 'janusz', 'chytrus', 'Janusz', 'Chytrus', 1800, 'Courier');
insert into couriers values(courierSeq.nextval, 8, 'zbyszek', 'zbyszek', 'Zbyszek', 'Trzeciak', 3100, 'Courier');
insert into couriers values(courierSeq.nextval, 10, 'aneta', 'aneta', 'Aneta', 'Duda�a', 3400, 'Courier');
insert into couriers values(courierSeq.nextval, 12, 'jan', 'jan', 'Jan', 'W�jcik', 3700, 'Courier');
insert into couriers values(courierSeq.nextval, 15, 'dawid', 'dawid', 'Dawid', 'Warzecha', 1450, 'Courier');
insert into couriers values(courierSeq.nextval, 17, 'mirek', 'mirek', 'Miros�aw', 'Puchalski', 2900, 'Courier');
insert into couriers values(courierSeq.nextval, 22, 'mateusz', 'mateusz', 'Mateusz', 'Olesiak', 3200, 'Courier');

insert into location values(locationSeq.nextval, 'Kielce');
insert into location values(locationSeq.nextval, 'Tarn�w');
insert into location values(locationSeq.nextval, 'Krak�w');
insert into location values(locationSeq.nextval, 'Warszawa');
insert into location values(locationSeq.nextval, 'Bydgoszcz');
insert into location values(locationSeq.nextval, 'Katowice');


insert into package values(packageSeq.nextval, 7, 1, 3, 'Ma�a', 7.2, 0, 14.2, 'Paczka zosta�a nadana do oddzia�u w Kielce', 'Karta', 'Jan', 'Kowalski', 'Kielce');
insert into package values(packageSeq.nextval, 3, 3, 4, 'Ma�a', 2.2, 0, 21.2, 'Paczka zosta�a nadana do oddzia�u w J�drzej�w', 'Karta', 'Antoni', 'Gadawski', 'J�drzej�w');
insert into package values(packageSeq.nextval, 3, 3, 1, 'Ma�a', 1.3, 1, 24.2 , 'Paczka zosta�a nadana do oddzia�u w Pi�cz�w', 'Got�wka', 'Agata', 'Kozie�', 'Pi�cz�w');
insert into package values(packageSeq.nextval, 4, 4, 1, 'Ma�a', 5.4, 0, 4.2, 'Paczka zosta�a nadana do oddzia�u w Chmielnik', 'Karta', 'Dawid', 'Mrozi�ski', 'Chmielnik');
insert into package values(packageSeq.nextval, 5, 5, 1, 'Ma�a', 2.2, 0, 24.2, 'Paczka zosta�a nadana do oddzia�u w Daleszyce', 'Got�wka', 'Mateusz', 'Zaj�c', 'Daleszyce');
insert into package values(packageSeq.nextval, 6, 6, 1, '�rednia', 17.5, 1, 14.2, 'Paczka zosta�a nadana do oddzia�u w Morawica', 'Karta', 'Stefan', 'Rutkowski', 'Morawica');
insert into package values(packageSeq.nextval, 6, 3, 2, '�rednia', 5.2, 0, 34.2, 'Paczka zosta�a nadana do oddzia�u w Ch�ciny', 'Got�wka', 'Sebastian', 'Sat�awa', 'Ch�ciny');
insert into package values(packageSeq.nextval, 3, 6, 2, '�rednia', 11.4, 0, 24.2, 'Paczka zosta�a nadana do oddzia�u w Kielce', 'Karta', 'Wioletta', 'Dudkowski', 'Kielce');
insert into package values(packageSeq.nextval, 3, 4, 3, '�rednia', 10.6, 1, 12.2, 'Paczka zosta�a nadana do oddzia�u w Morawica', 'Got�wka', 'Katarzyna', 'Parlicka', 'Morawica');
insert into package values(packageSeq.nextval, 3, 2, 4, '�rednia', 1.8, 0, 15.2, 'Paczka zosta�a nadana do oddzia�u w Chmielnik', 'Karta', 'Magdalena', 'Nowak', 'Chmielnik');
insert into package values(packageSeq.nextval, 5, 3, 5, '�rednia', 0.2, 0, 24.2, 'Paczka zosta�a nadana do oddzia�u w Pi�cz�w', 'Got�wka', 'Marta', 'Ja�ko', 'Pi�cz�w');
insert into package values(packageSeq.nextval, 6, 6, 6, '�rednia', 0.6, 0, 14.2, 'Paczka zosta�a nadana do oddzia�u w J�drzej�w', 'Karta', 'Barbara', 'Gaw�owska', 'J�drzej�w');
insert into package values(packageSeq.nextval, 7, 3, 5, 'Du�a', 0.3, 0, 14.2, 'Paczka zosta�a nadana do oddzia�u w Kielce', 'Karta', 'Tadeusz', 'Kieli�', 'Kielce');
insert into package values(packageSeq.nextval, 3, 9, 5, 'Du�a', 3.2, 0, 24.2, 'Paczka zosta�a nadana do oddzia�u w Busko Zdr�j', 'Got�wka', 'Jan', 'Kr�cisz', 'Busko Zdr�j');
insert into package values(packageSeq.nextval, 6, 5, 4, 'Du�a', 5.5, 1, 5.2, 'Paczka zosta�a nadana do oddzia�u w Krak�w', 'Karta', 'J�zef', 'Nowak', 'Krak�w');
insert into package values(packageSeq.nextval, 6, 3, 4, 'Du�a', 2.2, 0, 14.2, 'Paczka zosta�a nadana do oddzia�u w Warszawa', 'Got�wka', 'Janusz', 'Ciszowski', 'Warszawa');
insert into package values(packageSeq.nextval, 6, 6, 3, 'Du�a', 14.5, 0, 25.2, 'Paczka zosta�a nadana do oddzia�u w Rzesz�w', 'Got�wka', 'Czes�aw', 'Brejdak', 'Rzesz�w');
insert into package values(packageSeq.nextval, 5, 9, 3, 'Du�a', 16.2, 0, 6.2, 'Paczka zosta�a nadana do oddzia�u w Kielce', 'Karta', 'Robert', 'Puchalski', 'Kielce');
insert into package values(packageSeq.nextval, 3, 6, 3, 'Du�a', 22.3, 0, 14.2, 'Paczka zosta�a nadana do oddzia�u w Busko Zdr�j', 'Got�wka', 'Tomasz', 'Wolny', 'Busko Zdr�j');
insert into package values(packageSeq.nextval, 4, 3, 6, 'Du�a', 17.6, 0, 14.2, 'Paczka zosta�a nadana do oddzia�u w Pi�cz�w', 'Got�wka', 'Marek', 'Markowski', 'Pi�cz�w');
insert into package values(packageSeq.nextval, 4, 8, 6, 'Du�a', 30.6, 0, 14.2, 'Paczka zosta�a nadana do oddzia�u w Kielce', 'Karta', 'Justyna', 'Socha', 'Kielce');


commit;