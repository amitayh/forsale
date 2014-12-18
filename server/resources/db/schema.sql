USE forsale;

CREATE TABLE users (
  user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_email VARCHAR(50) NOT NULL,
  user_password CHAR(32) NOT NULL,
  user_name VARCHAR(50) NOT NULL,
  user_gender ENUM('male', 'female'),
  user_birth_date DATE
);
CREATE UNIQUE INDEX users_email ON users(user_email);

CREATE TABLE categories (
  category_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  category_name VARCHAR(50) NOT NULL
);

CREATE TABLE vendors (
  vendor_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  vendor_name VARCHAR(50) NOT NULL,
  vendor_address VARCHAR(100),
  vendor_lat DOUBLE,
  vendor_lng DOUBLE
);

CREATE TABLE user_favorite_categories (
  user_id INT NOT NULL,
  category_id INT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users(user_id),
  FOREIGN KEY (category_id) REFERENCES categories(category_id)
);
CREATE INDEX user_favorite_categories_user ON user_favorite_categories(user_id);

CREATE TABLE user_favorite_vendors (
  user_id INT NOT NULL,
  vendor_id INT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users(user_id),
  FOREIGN KEY (vendor_id) REFERENCES vendors(vendor_id)
);
CREATE INDEX user_favorite_vendors_user ON user_favorite_vendors(user_id);

CREATE TABLE vendor_categories (
  vendor_id INT NOT NULL,
  category_id INT NOT NULL,
  FOREIGN KEY (vendor_id) REFERENCES vendors(vendor_id),
  FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

CREATE TABLE sales (
  sale_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  vendor_id INT NOT NULL,
  sale_title VARCHAR(100) NOT NULL,
  sale_extra VARCHAR(255),
  sale_start DATE,
  sale_end DATE,
  FOREIGN KEY (vendor_id) REFERENCES vendors(vendor_id)
);