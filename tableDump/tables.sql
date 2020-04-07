
create table users(
    id serial not null
      constraint users_pkey
          primary key,
    login varchar(50) not null,
    password varchar(50) not null,
    role varchar(50) not null
);

create table requests(
    id serial not null
     constraint requests_pkey
         primary key,
    request_xml text not null,
    response_xml text,
    request_date timestamp not null,
    response_date timestamp,
    user_id integer
        constraint requests_user_id_fkey
            references  users
);
