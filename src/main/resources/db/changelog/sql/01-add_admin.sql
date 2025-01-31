begin;
insert into users(id, created_at, deleted, created_by, first_name, last_name, father_name, phone, password, role)
values (1, current_timestamp, false, 1, 'Root', 'Admin', 'Father', '8123', '$2a$12$M3MR4Hzd9qhszNTv/g5fje0Eji3hVCCYZUnbHrvfFvdHEfvBxJyuO', 'SUPER_ADMIN');
commit;

begin;
insert into user_permissions(user_id, permissions)
values
    (1, 'CREATE_USER'),
    (1, 'UPDATE_USER'),
    (1, 'DELETE_USER'),
    (1, 'READ_USER');
commit;