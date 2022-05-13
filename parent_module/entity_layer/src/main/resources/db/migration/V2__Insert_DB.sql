insert into users(birthday, first_name, last_name, login, middle_name, password, sex)
values (DATE '1987-07-21', 'Админ', 'Админов', 'admin', 'Админович', 'admin', 'MALE'),
       (DATE '1995-09-01', 'Андрей', 'Андреев', 'andrey', 'Андреевич', 'andrey', 'MALE'),
       (DATE '1988-11-09', 'Иван', 'Иванов', 'ivan', 'Иванович', 'ivan', 'MALE'),
       (DATE '1973-01-15', 'Елена', 'Рябова', 'elena', 'Олеговна', 'elena', 'FEMALE');

insert into roles(name)
values ('ADMIN'),
       ('USER');

insert into user_roles(user_id, role_id)
values (1, 1),
       (2, 2),
       (3, 2),
       (4, 2);

insert into accounts(balance_account, bank_name, bik, corr_account, inn, kpp, number_account)
values (500000, 'Сбер', 777777777, '20002000200020002000', 852852963741, 777456123, '20000000000000000000'),
       (200750, 'Сбер', 777777777, '20002000200020002000', 852852963741, 777456123, '20000000008524896248'),
       (325440, 'Сбер', 777777777, '20002000200020002000', 852852963741, 777456123, '20000000532178965231'),
       (105008, 'Сбер', 777777777, '20002000200020002000', 852852963741, 777456123, '20000000478565478952'),
       (75310, 'Сбер', 777777777, '20002000200020002000', 852852963741, 777456123, '20000000965442575456');

insert into cards (balance_card, cvv, number_card, password, validity, account_id)
values (500000, 752, 1234567890000000, 5656, DATE '2030-01-01', 1),
       (200750, 222, 1234567898510010, 8523, DATE '2025-07-01', 2),
       (325440, 652, 1234568777889987, 9632, DATE '2027-09-01', 3),
       (105008, 456, 1234562555266428, 7451, DATE '2026-03-01', 4),
       (75310, 962, 1234567895240100, 6842, DATE '2029-08-01', 5);

insert into users_cards(user_id, cards_id)
values (2, 1),
       (2, 2),
       (3, 3),
       (3, 4),
       (4, 5);

insert into users_accounts(user_id, accounts_id)
values (2, 1),
       (2, 2),
       (3, 3),
       (3, 4),
       (4, 5);

insert into debit_cards(id)
values (1),
       (2),
       (3),
       (4),
       (5);