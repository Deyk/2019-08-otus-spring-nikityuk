insert into book (title)
values ('title_01'),
       ('title_02'),
       ('title_03');

insert into author (name)
values ('author_01'),
       ('author_02'),
       ('author_03');

insert into books_authors (book_id, author_id)
values (1, 1),
       (2, 1),
       (2, 2),
       (3, 1),
       (3, 2),
       (3, 3);

insert into comment (text, date, book_id)
values ('comment_01', '2019-11-09 20:00:00.000', 1),
       ('comment_02', '2019-11-09 20:00:00.000', 1),
       ('comment_03', '2019-11-09 20:00:00.000', 1),
       ('comment_04', '2019-11-09 20:00:00.000', 2),
       ('comment_05', '2019-11-09 20:00:00.000', 2),
       ('comment_06', '2019-11-09 20:00:00.000', 3);
