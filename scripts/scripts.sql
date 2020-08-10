CREATE TABLE TABLE_ARCHIVE (
 id_archive serial PRIMARY KEY,
 url varchar(255) NOT NULL, 
 name varchar(50),
 extension varchar(20),
 lines varchar(30),
 size varchar(30)
);
commit;