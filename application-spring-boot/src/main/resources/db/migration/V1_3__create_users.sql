CREATE  TABLE users (
  username VARCHAR(45) NOT NULL ,
  password VARCHAR(60) NOT NULL ,
  customer_id BIGINT UNSIGNED NOT NULL,
  enabled BIT NOT NULL DEFAULT 1 ,
  PRIMARY KEY (username)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;