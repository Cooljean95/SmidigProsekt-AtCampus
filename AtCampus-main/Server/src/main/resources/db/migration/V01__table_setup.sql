CREATE TABLE programs (
    program_id bigserial PRIMARY KEY,
    program_name varchar(100) NOT NULL
);

CREATE TABLE schools (
    school_id bigserial PRIMARY KEY,
    school_name varchar(100) NOT NULL
);

CREATE TABLE roles (
    role_id bigserial PRIMARY KEY,
    role_name varchar(40) NOT NULL
);

CREATE TABLE users (
    user_id bigserial PRIMARY KEY,
    user_first_name varchar(30) NOT NULL,
    user_last_name varchar(40) NOT NULL,
    user_email varchar(100) NOT NULL,
    user_password varchar(255) NOT NULL,
    user_phone_number varchar(15) NOT NULL,
    user_school bigint NOT NULL references schools(school_id),
    user_program bigint NOT NULL references programs(program_id),
    user_profile_image varchar(255),
    user_date_created date not null default current_date
);

CREATE TABLE user_roles (
    user_id bigint references users(user_id),
    role_id bigint references roles(role_id)
);

CREATE TABLE groups (
    group_id bigserial PRIMARY KEY,
    group_name varchar(100) NOT NULL,
    group_description TEXT,
    group_image varchar(255),
    group_school bigint references schools(school_id),
    group_admin bigint references users(user_id),
    group_date_created date not null default current_date
);

CREATE TABLE group_request (
    group_request_id bigserial PRIMARY KEY,
    group_request_message TEXT,
    user_id bigint NOT NULL references users(user_id),
    group_id bigint NOT NULL references groups(group_id)
);

CREATE TABLE user_group (
    user_group_id bigserial PRIMARY KEY,
    user_id bigint NOT NULL references users(user_id),
    group_id bigint NOT NULL references groups(group_id),
    is_favorite bool NOT NULL default false,
    date_joined date NOT NULL default current_date
);

CREATE TABLE post (
    post_id bigserial PRIMARY KEY,
    post_title varchar(70) NOT NULL,
    post_body text NOT NULL,
    user_id bigint NOT NULL references users(user_id),
    group_id bigint NOT NULL references groups(group_id),
    post_date_created date NOT NULL default current_date
);

CREATE TABLE comment (
    comment_id bigserial PRIMARY KEY,
    comment_body text NOT NULL,
    comment_post_id bigint NOT NULL references post(post_id),
    comment_user_id bigint NOT NULL references users(user_id),
    comment_date date NOT NULL default current_date
);

