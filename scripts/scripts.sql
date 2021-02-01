--scrapingdb

CREATE TABLE TABLE_ARCHIVE (
 id_archive serial PRIMARY KEY,
 url varchar(255) NOT NULL, 
 name varchar(100),
 extension varchar(60),
 lines varchar(30),
 size varchar(30)
);
commit;



delete from table_archive where url = 'https://github.com/rikes/scraping-github-api';

commit;