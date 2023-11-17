/* TABLE CREATION */

create table image (
        id              int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
        title           varchar (256) NOT NULL,
        description     varchar (1024) NOT NULL,
        keywords        varchar (256) NOT NULL,
        author          varchar (256) NOT NULL, /* Original author */
        creator         varchar (256) NOT NULL, /* User inserting image information in the database */
        capture_date    varchar (10) NOT NULL,  /* Format AAAA/MM/DD Asked to the user*/
        storage_date    varchar (10) NOT NULL,  /* Format AAAA/MM/DD Filled when stored */
        filename        varchar (512) NOT NULL, /* Only the name of the file, directory is fixed by the Web Application*/
		encrypted	int NOT NULL, 				/* 0 is clear, 1 is encrypted */
        primary key (id)
);

/* DATA INSERTION */
INSERT INTO IMAGE (TITLE, DESCRIPTION, KEYWORDS, AUTHOR, CREATOR, CAPTURE_DATE, STORAGE_DATE, FILENAME, ENCRYPTED) 
	VALUES ('test1', 'This is an image','Keyword11, Keyword12','Silvia', 'Silvia', '2020/03/02','2020/09/09','file1.jpg', 0);
INSERT INTO IMAGE (TITLE, DESCRIPTION, KEYWORDS, AUTHOR, CREATOR, CAPTURE_DATE, STORAGE_DATE, FILENAME, ENCRYPTED) 
	VALUES ('test2', 'This is an image','Keyword21, Keyword22','Silvia', 'Silvia', '2020/03/02','2020/09/09','file2.jpg', 0);

/* FILTERING QUERIES */
/* This query asks for images with letter a in the title. Empty result */
select * from image where title like '%a%';

/* This query asks for images where Silvia is the author. All rows */
select * from image where author='Silvia';

/* TABLE DELETION */          
drop table image;
