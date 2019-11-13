insert into book (title)
values ('title_01'),
       ('title_02');

insert into author (name)
values ('author_01'),
       ('author_02'),
       ('author_03');

insert into books_authors (book_id, author_id)
values (1, 1),
       (1, 2),
       (2, 2);

insert into comment (text, date, book_id)
values ('comment_01', '2019-11-09 20:00:00.000', 1);
