CREATE TABLE customer (
  customer_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  document_number VARCHAR(20)NOT NULL, 
  active BIT NOT NULL,
  PRIMARY KEY (customer_id),
  INDEX IX_customer_last_first_name (last_name, first_name),
  INDEX IX_customer_document_number (document_number)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
