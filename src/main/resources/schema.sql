create table if not exists customers(
    ID INT PRIMARY KEY,
    FIRST_NAME VARCHAR2(100) NOT NULL,
    LAST_NAME VARCHAR2(100) NOT NULL
);

create sequence if not exists seq_customers increment by 1 start with 1 minvalue 1 maxvalue 999999 nocycle;