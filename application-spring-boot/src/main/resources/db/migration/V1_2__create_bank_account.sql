CREATE TABLE bank_account (
  bank_account_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  number VARCHAR(50) NOT NULL,
  balance DECIMAL(10,2) NOT NULL,
  locked BIT NOT NULL,
  customer_id BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY(bank_account_id),
  INDEX IX_bank_account_customer_id (customer_id),
  UNIQUE INDEX UQ_bank_account_number (number),
  CONSTRAINT FK_bank_account_customer_id FOREIGN KEY (customer_id) REFERENCES customer (customer_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;