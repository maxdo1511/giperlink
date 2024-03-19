create sequence user_id_seq
    as integer;

alter sequence user_id_seq owner to postgres;

create table if not exists user_private
(
    id                     serial
        constraint user_private_pk
            primary key,
    firstname              text    not null,
    surname                text    not null,
    patronymic             text,
    passport_series        integer not null,
    passport_number        integer not null,
    passport_date_of_issue bigint  not null,
    passport_issue_by      text    not null
);

alter table user_private
    owner to postgres;

create table if not exists g_user
(
    id              integer default nextval('"user".user_id_seq'::regclass) not null
        constraint user_pk
            primary key,
    role            text                                                    not null,
    user_private_id bigint                                                  not null
        constraint g_user_user_private_id_fk
            references user_private
);

alter table g_user
    owner to postgres;

alter sequence user_id_seq owned by g_user.id;


