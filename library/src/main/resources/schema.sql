drop table if exists book;
create table book
(
    id bigserial,
    title varchar(255),
    primary key (id)
);

drop table if exists author;
create table author
(
    id bigserial,
    name varchar(255),
    book_id long references book (id) on delete cascade,
    primary key (id)
);
