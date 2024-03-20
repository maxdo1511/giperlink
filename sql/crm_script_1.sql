create table if not exists application
(
    id                        serial
        constraint application_pk
            primary key,
    uuid                      text             not null
        constraint application_pk_2
            unique,
    creation_date             bigint           not null,
    customer_number           text             not null,
    service                   integer,
    service_kind              integer,
    service_type              integer,
    status                    integer,
    hardware_id               bigint
        constraint application_hardware_id_fk
            references hardware.hardware,
    problem_type              integer,
    description               text,
    customer_personal_account double precision not null
);

alter table application
    owner to postgres;


