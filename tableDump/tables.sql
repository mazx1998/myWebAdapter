
create table users(
    id serial not null
        constraint users_pkey
            primary key,
    login varchar(50) not null,
    password varchar(50) not null
);

create table requests(
    id serial not null
        constraint requests_pkey
            primary key,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    patronymic varchar(50),
    gender varchar(50) not null,
    birth_date date not null,
    birth_place_id integer
        constraint requests_birth_place_id_fkey
            references birthplaces,
    passport_id integer
        constraint requests_passport_id_fkey
            references  passports,
    req_date timestamp not null,
    resp_date timestamp,
    snils varchar(15)
);

create table birthplaces(
    id serial not null
        constraint birthplaces_pkey
            primary key,
    place_type varchar(50) not null,
    settlement varchar(50) not null,
    district varchar(50),
    region varchar(50),
    country varchar(50)
);

create table passports(
    id serial not null
        constraint passports_pkey
            primary key,
    series varchar(10) not null ,
    number varchar(10) not null ,
    issue_date date not null,
    issuer varchar(200)
);
