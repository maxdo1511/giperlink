create table if not exists employee
(
    id       serial
        constraint employee_pk
            primary key,
    email    text not null
        constraint employee_pk_2
            unique,
    password text not null
);

alter table employee
    owner to postgres;


