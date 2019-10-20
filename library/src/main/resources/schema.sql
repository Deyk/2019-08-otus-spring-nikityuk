drop table if exists author;
create table author
(
    id bigserial,
    name varchar(255),
    primary key (id)
);

drop table if exists book;
create table book
(
    id bigserial,
    title     varchar(255),
    author_id long references author (id) on update cascade on delete cascade,
    primary key (id)
);
