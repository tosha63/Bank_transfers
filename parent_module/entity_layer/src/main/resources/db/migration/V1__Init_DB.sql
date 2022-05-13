create table accounts
(
    id              bigserial not null,
    balance_account int8,
    bank_name       varchar(255),
    bik             int8,
    corr_account    varchar(255),
    inn             int8,
    kpp             int8,
    number_account  varchar(255),
    primary key (id)
);

create table cards
(
    id           bigserial not null,
    balance_card int8,
    cvv          varchar(255),
    number_card  int8,
    password     varchar(255),
    validity     date,
    account_id   int8,
    primary key (id)
);

create table credit_cards
(
    credit_limit int8,
    percent_fine float8,
    rate         float8,
    id           int8 not null,
    primary key (id)
);

create table debit_cards
(
    id int8 not null,
    primary key (id)
);

create table deposits
(
    id              bigserial not null,
    balance_deposit int8,
    percent_rate    float8,
    tariff_name     varchar(255),
    validity        timestamp,
    primary key (id)
);

create table roles
(
    id   bigserial not null,
    name varchar(255),
    primary key (id)
);

create table transfers
(
    id                      bigserial not null,
    commission              float8,
    currency                varchar(255),
    local_date_time         timestamp,
    put_number_account      varchar(255),
    put_number_card         int8,
    sum_transfer            int8,
    transfer_status         varchar(255),
    withdraw_number_account varchar(255),
    withdraw_number_card    int8,
    user_id                 int8,
    primary key (id)
);

create table user_roles
(
    user_id int8 not null,
    role_id int8 not null,
    primary key (user_id, role_id)
);

create table users
(
    id          bigserial not null,
    birthday    date,
    first_name  varchar(255),
    last_name   varchar(255),
    login       varchar(255),
    middle_name varchar(255),
    password    varchar(255),
    sex         varchar(255),
    primary key (id)
);

create table users_accounts
(
    user_id     int8 not null,
    accounts_id int8 not null
);

create table users_cards
(
    user_id  int8 not null,
    cards_id int8 not null
);

create table users_deposits
(
    user_id     int8 not null,
    deposits_id int8 not null
);

alter table users
    add constraint UK_ow0gan20590jrb00upg3va2fn unique (login);

alter table users_accounts
    add constraint UK_nbnvigj13od728soebnnv9fks unique (accounts_id);

alter table users_cards
    add constraint UK_kdmfeqdqgpbgdsc4s73cqa3jk unique (cards_id);

alter table users_deposits
    add constraint UK_a3m6fer1rvwtg0eoa4p4a1b2u unique (deposits_id);

alter table cards
    add constraint FKdjw7dinkpc0f01yk4m57uq2u2 foreign key (account_id) references accounts;

alter table credit_cards
    add constraint FK1a0pu8sxfbcwa7mp6rn4sdb60 foreign key (id) references cards;

alter table debit_cards
    add constraint FK8lentt3ti9dk23un1u4go8jem foreign key (id) references cards;

alter table transfers
    add constraint FKcvgnuaekhfu7jkgfdjr2rsy6w foreign key (user_id) references users;

alter table user_roles
    add constraint FKh8ciramu9cc9q3qcqiv4ue8a6 foreign key (role_id) references roles;

alter table user_roles
    add constraint FKhfh9dx7w3ubf1co1vdev94g3f foreign key (user_id) references users;

alter table users_accounts
    add constraint FKboixenqn798l4gnaq1q7y870t foreign key (accounts_id) references accounts;

alter table users_accounts
    add constraint FKd8emsqvyyvmxgfg90ylpu9nnx foreign key (user_id) references users;

alter table users_cards
    add constraint FK6ss6ygihrlfxnqdm08v0dpbv foreign key (cards_id) references cards;

alter table users_cards
    add constraint FKr2696cs9gb8al9cj5da42wfkv foreign key (user_id) references users;

alter table users_deposits
    add constraint FK9phukbqtitgveue7vsdvjsi14 foreign key (deposits_id) references deposits;

alter table users_deposits
    add constraint FKjbqpkyjurnaxvbq2lmmidygli foreign key (user_id) references users;
