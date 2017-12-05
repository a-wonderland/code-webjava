--Create Table--

CREATE TABLE accounts (
account_no integer PRIMARY KEY,
name text,
balance numeric
);

--Insert--
INSERT INTO accounts (account_no,name,balance) VALUES (1,'Min Phyo Thaw', 5000),(2,'Wai Yan Htun', 5000),(3,'Tun Tun Win',5000);

--⓵【Transaction】 ROLLBACK--
BEGIN;
UPDATE accounts SET balance=balance-10000 WHERE account_no=1;
UPDATE accounts SET balance=balance+10000 WHERE account_no=1;
ROLLBACK;

--②【Transaction】 COMMIT--
BEGIN;
UPDATE accounts SET balance=balance-10000 WHERE account_no=1;
UPDATE accounts SET balance=balance+2000 WHERE account_no=1;
COMMIT;

--③【Transaction】 SAVEPOINT--
BEGIN;
UPDATE accounts SET balance=balance-1000 WHERE account_no=1;
SAVEPOINT sv1;
UPDATE accounts SET balance=balance+1000 WHERE account_no=2;
ROLLBACK TO sv1;
COMMIT;

