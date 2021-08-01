create table machines (id bigint not null auto_increment, name varchar(100), segment varchar(25), primary key (id));
insert into machines (name, segment) values ('vibrating tamper', 'BUILDING');
insert into machines (name, segment) values ('slotting machine', 'BUILDING');
insert into machines (name, segment) values ('reach sander', 'BUILDING');
insert into machines (name, segment) values ('carpet cleaner', 'CLEANING');
insert into machines (name, segment) values ('steam cleaner', 'CLEANING');
insert into machines (name, segment) values ('industrial vacuum cleaner', 'CLEANING');
insert into machines (name, segment) values ('lawnraker', 'GARDENING');
insert into machines (name, segment) values ('shredder', 'GARDENING');

