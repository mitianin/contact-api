create table if not exists users
(
    id        serial primary key,
    login     varchar not null unique,
    date_born timestamp
);

create table if not exists contacts
(
    id      serial primary key,
    name    varchar not null,
    value   varchar not null unique,
    type    varchar not null,
    user_id int,
    constraint pk_user_contacts foreign key (user_id) references users (id) on delete restrict
);

create table if not exists users_log
(
    login    varchar not null primary key,
    password varchar not null,
    constraint pk_user_login foreign key (login) references users (login)
);