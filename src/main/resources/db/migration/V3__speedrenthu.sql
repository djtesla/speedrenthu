create table orders (id bigint not null auto_increment, date date, location varchar(255), status varchar(255), price_category_id bigint, primary key (id), foreign key (price_category_id) references price_categories (id));
insert into orders (date, location, price_category_id, status) values ('2021-08-01', 'Budapest', 1, 'WIP');
insert into orders (date, location, price_category_id, status) values ('2021-08-01', 'Vecsés', 2, 'WIP');
insert into orders (date, location, price_category_id, status) values ('2021-08-01', 'Maglód', 3, 'WIP');
insert into orders (date, location, price_category_id, status) values ('2021-08-01', 'Budapest', 4, 'WIP');
insert into orders (date, location, price_category_id, status) values ('2021-08-01', 'Vecsés', 5, 'WIP');
insert into orders (date, location, price_category_id, status) values ('2021-08-01', 'Maglód', 6, 'WIP');