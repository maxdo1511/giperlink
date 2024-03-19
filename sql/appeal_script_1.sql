create sequence themes_id_seq
    as integer;

alter sequence themes_id_seq owner to postgres;

create table if not exists theme
(
    id                integer default nextval('appeal.themes_id_seq'::regclass) not null
        constraint themes_pk
            primary key,
    title             text                                                      not null,
    creation_date     bigint                                                    not null,
    last_message_date bigint                                                    not null
);

alter table theme
    owner to postgres;

alter sequence themes_id_seq owned by theme.id;

create table if not exists appeal
(
    id       serial
        constraint appeal_pk
            primary key,
    theme_id bigint not null
        constraint appeal_themes_id_fk
            references theme,
    user_id  bigint not null
        constraint appeal_g_user_id_fk
            references g_user.g_user,
    date     bigint not null
);

alter table appeal
    owner to postgres;

create table if not exists message
(
    id        serial
        constraint message_pk
            primary key,
    sender_id bigint not null
        constraint message_g_user_id_fk
            references g_user.g_user,
    message   text   not null,
    date      bigint not null,
    appeal_id bigint not null
        constraint message_appeal_id_fk
            references appeal
);

alter table message
    owner to postgres;


