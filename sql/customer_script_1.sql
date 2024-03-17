create table if not exists customer_private
(
    customer_id            bigint  not null
        constraint customer_private_pk
            primary key,
    name                   text    not null,
    surname                text    not null,
    patronimic             text,
    pasport_series         integer not null,
    pasport_number         integer not null,
    pasport_date_of_issue  bigint  not null,
    passport_issue_by_whom text    not null,
    constraint customer_private_uk_1
        unique (pasport_series, pasport_number)
);

alter table customer_private
    owner to postgres;

create table if not exists contract
(
    id                  serial
        constraint contract_pk
            primary key,
    contract_number     text   not null
        constraint contract_uk_2
            unique,
    date_of_issue       bigint not null,
    type                bigint not null,
    date_of_termination bigint,
    termination_reason  text
);

alter table contract
    owner to postgres;

create table if not exists customer
(
    id               serial
        constraint customer_pk
            primary key,
    number           text   not null
        constraint customer_pk_2
            unique,
    contract_id      bigint not null
        constraint customer_contract_id_fk
            references contract,
    personal_account real   not null
);

alter table customer
    owner to postgres;

create table if not exists customer_service
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

create table if not exists connected_service
(
    id          serial
        constraint connected_service_pk
            primary key,
    customer_id bigint not null
        constraint connected_service_customer_id_fk
            references customer,
    service_id  bigint not null
        constraint connected_service_customer_service_id_fk
            references customer_service
);

alter table connected_service
    owner to postgres;


