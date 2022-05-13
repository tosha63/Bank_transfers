create extension if not exists pgcrypto;
update users
set password = crypt(password, gen_salt('bf'));
update cards
set password = crypt(password, gen_salt('bf'));
update cards
set cvv = crypt(cvv, gen_salt('bf'));