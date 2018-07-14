delete from bank_account;
delete from customer;
delete from users;
INSERT INTO customer(customer_id, first_name, last_name, active) VALUES(1, 'Juan', 'Perez', 1);
INSERT INTO customer(customer_id, first_name, last_name, active) VALUES(2, 'Julio', 'Morales', 1);

INSERT INTO users(username, password, customer_id, email, enabled) VALUES('frankche88', 'password', '1', 'frank.che88@gmail.com', 1);
INSERT INTO users(username, password, customer_id, email, enabled) VALUES('julio', 'password', '2', 'julio.che88@gmail.com', 1);


INSERT INTO bank_account(number, balance, locked, customer_id) VALUES('123-456-001', 1500, 0, 1);
INSERT INTO bank_account(number, balance, locked, customer_id) VALUES('123-456-002', 1800, 0, 1);
INSERT INTO bank_account(number, balance, locked, customer_id) VALUES('123-456-003', 1500, 0, 1);
INSERT INTO bank_account(number, balance, locked, customer_id) VALUES('123-456-004', 1800, 0, 1);
INSERT INTO bank_account(number, balance, locked, customer_id) VALUES('123-456-005', 1500, 0, 1);
INSERT INTO bank_account(number, balance, locked, customer_id) VALUES('123-456-006', 1800, 0, 1);
INSERT INTO bank_account(number, balance, locked, customer_id) VALUES('123-456-007', 1500, 0, 1);
INSERT INTO bank_account(number, balance, locked, customer_id) VALUES('123-456-008', 1800, 0, 1);
INSERT INTO bank_account(number, balance, locked, customer_id) VALUES('123-456-009', 1500, 0, 1);
INSERT INTO bank_account(number, balance, locked, customer_id) VALUES('123-456-010', 1800, 0, 1);

INSERT INTO bank_account(number, balance, locked, customer_id) VALUES('123-456-101', 1500, 0, 1);
INSERT INTO bank_account(number, balance, locked, customer_id) VALUES('123-456-102', 1800, 0, 1);
INSERT INTO bank_account(number, balance, locked, customer_id) VALUES('123-456-103', 1500, 0, 1);
INSERT INTO bank_account(number, balance, locked, customer_id) VALUES('123-456-104', 1800, 0, 1);
INSERT INTO bank_account(number, balance, locked, customer_id) VALUES('123-456-105', 1500, 0, 1);
INSERT INTO bank_account(number, balance, locked, customer_id) VALUES('123-456-106', 1800, 0, 1);
INSERT INTO bank_account(number, balance, locked, customer_id) VALUES('123-456-107', 1500, 0, 1);
INSERT INTO bank_account(number, balance, locked, customer_id) VALUES('123-456-108', 1800, 0, 1);
INSERT INTO bank_account(number, balance, locked, customer_id) VALUES('123-456-109', 1500, 0, 1);
INSERT INTO bank_account(number, balance, locked, customer_id) VALUES('123-456-110', 1800, 0, 1);