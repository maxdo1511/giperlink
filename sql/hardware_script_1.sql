create table if not exists hardware
(
    id          serial
        constraint hardware_pk
            primary key,
    name        text not null,
    description text
);

alter table hardware
    owner to postgres;


