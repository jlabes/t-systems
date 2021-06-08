CREATE TABLE EMPLOYEE
(
   ID BIGINT NOT NULL,
   ACTIVE BOOLEAN NOT NULL,
   AGE INTEGER NOT NULL,
   GENDER CHAR (255) NOT NULL,
   LASTNAME VARCHAR (255),
   NAME VARCHAR (255),
   SALARY DOUBLE NOT NULL,
   EMPLOYER_ID BIGINT
);
CREATE TABLE EMPLOYER_ROLES
(
   EMPLOYER_ID BIGINT NOT NULL,
   ROLES VARCHAR (255)
);
CREATE TABLE EMPLOYER
(
   ID BIGINT NOT NULL,
   PASSWORD VARCHAR (255),
   USERNAME VARCHAR (255)
);
CREATE SEQUENCE HIBERNATE_SEQUENCE;
CREATE PRIMARY KEY PRIMARY_KEY_75 ON EMPLOYER(ID);
CREATE UNIQUE INDEX UK_GS0WC7FCG8U341Q332OG9PK8O_INDEX_7 ON EMPLOYER(USERNAME);
CREATE PRIMARY KEY PRIMARY_KEY_7 ON EMPLOYEE(ID);
CREATE INDEX FKRMKNJKDEOO3MOLAL1GXKT9DAR_INDEX_7 ON EMPLOYEE(EMPLOYER_ID);
CREATE INDEX FKESI7R6G2XNBVJA0VVB3Q019FC_INDEX_C ON EMPLOYER_ROLES(EMPLOYER_ID);
ALTER TABLE EMPLOYER ADD CONSTRAINT UK_GS0WC7FCG8U341Q332OG9PK8O UNIQUE (USERNAME) INDEX UK_GS0WC7FCG8U341Q332OG9PK8O_INDEX_7;
ALTER TABLE EMPLOYER ADD CONSTRAINT CONSTRAINT_75 PRIMARY KEY(ID) INDEX PRIMARY_KEY_75;
ALTER TABLE EMPLOYEE ADD CONSTRAINT CONSTRAINT_7 PRIMARY KEY(ID) INDEX PRIMARY_KEY_7;
ALTER TABLE EMPLOYEE ADD CONSTRAINT FKRMKNJKDEOO3MOLAL1GXKT9DAR FOREIGN KEY(EMPLOYER_ID) INDEX FKRMKNJKDEOO3MOLAL1GXKT9DAR_INDEX_7 REFERENCES EMPLOYER(ID) NOCHECK;
ALTER TABLE EMPLOYER_ROLES ADD CONSTRAINT FKESI7R6G2XNBVJA0VVB3Q019FC FOREIGN KEY(EMPLOYER_ID) INDEX FKESI7R6G2XNBVJA0VVB3Q019FC_INDEX_C REFERENCES EMPLOYER(ID) NOCHECK;