create table contract
(
    id                  serial
        constraint contract_pk
            primary key,
    contract_number     text   not null
        constraint contract_uk_2
            unique,
    date_of_issue       bigint not null,
    type                bigint not null,
    validity_period     bigint not null,
    date_of_termination bigint,
    termination_reason  text,
    payment_trems       text   not null
);

alter table contract
    owner to postgres;

create table customer_service
(
    id   serial
        constraint customer_service_pk
            primary key,
    name text not null
        constraint customer_service_uk_1
            unique
);

alter table customer_service
    owner to postgres;

create table customer_hardware
(
    id          serial
        constraint customer_hardware_pk
            primary key,
    hardware_id serial,
    customer_id serial,
    contract_id integer not null,
    constraint customer_hardware_uk_1
        unique (customer_id, hardware_id, contract_id)
);

alter table customer_hardware
    owner to postgres;

create table adress
(
    id       serial
        constraint adress_pk
            primary key,
    country  text not null,
    city     text,
    district text,
    street   text,
    house    text,
    building text,
    room     text
);

alter table adress
    owner to postgres;

create table customer_private
(
    id                     serial
        constraint customer_private_pk
            primary key,
    name                   text    not null,
    surname                text    not null,
    patronymic             text,
    passport_series        integer not null,
    passport_number        integer not null,
    passport_date_of_issue bigint  not null,
    passport_issue_by_whom text    not null,
    adress                 bigint
        constraint customer_private_adress_id_fk
            references adress
            on update cascade on delete set null
);

alter table customer_private
    owner to postgres;

create table customer
(
    id               serial
        constraint customer_pk
            primary key,
    number           text   not null
        constraint customer_pk_2
            unique,
    contract_id      bigint not null
        constraint customer_contract_id_fk
            references contract
            on update cascade on delete cascade,
    personal_account real   not null,
    customer_id      bigint not null
        constraint customer_customer_private_id_fk
            references customer_private
);

alter table customer
    owner to postgres;

create table connected_service
(
    id          serial
        constraint connected_service_pk
            primary key,
    customer_id bigint not null
        constraint connected_service_customer_id_fk
            references customer
            on update cascade on delete cascade,
    service_id  bigint not null
        constraint connected_service_customer_service_id_fk
            references customer_service
            on update cascade on delete cascade
);

alter table connected_service
    owner to postgres;

create table history_of_appeals
(
    id          serial
        constraint history_of_appeals_pk
            primary key,
    customer_id bigint not null
        constraint history_of_appeals_customer_id_fk
            references customer
            on update cascade,
    date        bigint not null,
    topic_id    bigint not null
);

alter table history_of_appeals
    owner to postgres;


