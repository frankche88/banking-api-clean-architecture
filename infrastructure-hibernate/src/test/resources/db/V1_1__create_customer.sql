--CREATE SEQUENCE hibernate_sequence;
CREATE TABLE customer (
  customer_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  document_number VARCHAR(20)NOT NULL, 
  active BIT NOT NULL,
  PRIMARY KEY (customer_id),
  INDEX IX_customer_last_first_name (last_name, first_name)
);

CREATE TABLE users (
  username VARCHAR(50) NOT NULL,
  password VARCHAR(50) NOT NULL,
  customer_id BIGINT UNSIGNED NOT NULL,
  email VARCHAR(50)NOT NULL, 
  enabled BIT NOT NULL,
  PRIMARY KEY (username)
);

CREATE TABLE user_roles (
  user_role_id BIGINT UNSIGNED NOT NULL,
  username VARCHAR(50) NOT NULL,  
  role VARCHAR(50)NOT NULL,
  PRIMARY KEY (user_role_id)
);