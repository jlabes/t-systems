insert into Employer(id, username, password) values (1, 'johnny', 'e10adc3949ba59abbe56e057f20f883e');
insert into Role(id, role) values (1, 'USER');
insert into Role(id, role) values (2, 'ADMIN');
insert into Employer_Roles(EMPLOYER_ID, ROLES_ID) values (1, 1);
insert into Employer_Roles(EMPLOYER_ID, ROLES_ID) values (1, 2);