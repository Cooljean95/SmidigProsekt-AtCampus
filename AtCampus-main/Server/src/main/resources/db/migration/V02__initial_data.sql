insert into programs
values (nextval('programs_program_id_seq'), 'Programmering');

insert into programs
values (nextval('programs_program_id_seq'), 'Cybersikkerhet');

insert into programs
values (nextval('programs_program_id_seq'), 'Markedsføring');

insert into programs
values (nextval('programs_program_id_seq'), 'Frontend- og mobilutvikling');

insert into schools
values (nextval('schools_school_id_seq'), 'Høyskolen Kristiania');

insert into schools
values (nextval('schools_school_id_seq'), 'Universitetet i Oslo');

insert into schools
values (nextval('schools_school_id_seq'), 'Handelshøyskolen BI');

insert into roles values (nextval('roles_role_id_seq'), 'USER');

insert into roles values (nextval('roles_role_id_seq'), 'ADMIN');

insert into users
values (nextval('users_user_id_seq'), 'Martin', 'Olaussen', 'test@mail.com', '$2a$10$gXGktr8WhQavYsRRah732u3zoeIbbKFQmttADK2jPl6GgeX82I0Z.', '45677321', 1, 1,'https://cdn.discordapp.com/attachments/744902958663336059/778253299563364352/eivind.png'); /* Password is pirate */

insert into users
values (nextval('users_user_id_seq'), 'Syvert', 'Eidjord', 'syvert@edjord.com', '$2a$12$z6vnEayVBe.opce7Pan0cOOGwXHqSsRENoebp1D1ijha6HMKsqLum', '43121234', 1, 1,'https://i.pinimg.com/originals/77/84/7b/77847b6f7f83d2c523209030aca5b4d5.jpg'); /* Password is password */

insert into users
values (nextval('users_user_id_seq'), 'Jonas', 'Bratt', 'jonas@hansen.com', '$2a$10$gXGktr8WhQavYsRRah732u3zoeIbbKFQmttADK2jPl6GgeX82I0Z.', '95256907', 1, 1,'https://cdn.discordapp.com/attachments/744902958663336059/778253299563364352/eivind.png'); /* Password is pirate */

insert into groups
values (nextval('groups_group_id_seq'), 'testgroup', 'this is a test description', 'https://i.pinimg.com/originals/b3/ca/dd/b3caddada347387d77999cab63012c6e.jpg', 1, 1);

insert into user_roles
values (1, 1);

insert into user_roles
values (1, 2);

insert into user_roles
values (2, 1);

insert into group_request
values (nextval('group_request_group_request_id_seq'), 'Im a good student please add me', 1, 1);

insert into user_group
values (nextval('user_group_user_group_id_seq'), 1, 1, true);

insert into user_group
values (nextval('user_group_user_group_id_seq'), 2, 1, true);

insert into post
values (nextval('post_post_id_seq'), 'Amazing post by me', 'Bruv', 1, 1);

insert into post
values (nextval('post_post_id_seq'), 'Shut up', 'and dance with me', 2, 1);

insert into comment
values (nextval('comment_comment_id_seq'), 'It was not an amazing post', 1, 1);

insert into comment
values (nextval('comment_comment_id_seq'), 'No u.', 2, 2);