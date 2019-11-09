insert into book (title)
values ('title_01'),
       ('title_02');

insert into author (name, book_id)
values ('author_01', 1),
       ('author_02', 1),
       ('author_02', 2);

insert into comment (text, date, book_id)
values ('comment_01', '2019-11-09 20:00:00.000', 1);
