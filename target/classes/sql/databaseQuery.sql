CREATE DATABASE IF NOT EXISTS bakery;
USE bakery;

CREATE TABLE user (
                      userName varchar(40) PRIMARY KEY,
                      password varchar(40),
                      phoneNo varchar(40),
                      Role varchar(40)
);

CREATE TABLE customer (
                          customerId varchar(15) PRIMARY KEY,
                          cName varchar(40),
                          phoneNo varchar(40),
                          email varchar(40)
);

CREATE TABLE orders (
                        orderId varchar(15) PRIMARY KEY,
                        status VARCHAR(40),
                        date DATE,
                        totalAmount DECIMAL(10, 2),
                        customerId varchar(15),
                        FOREIGN KEY (customerId) REFERENCES customer(customerId) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE payment (
                         paymentId varchar(15) PRIMARY KEY,
                         paymentMethod VARCHAR(40),
                         date DATE,
                         discountAmount decimal(20, 2),
                         totalAmount decimal(20, 2),
                         orderId varchar(15),
                         discountType VARCHAR(15),
                         discountPrecentage VARCHAR(15),
                         FOREIGN KEY(orderId) REFERENCES orders(orderId) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE product (
                         productId varchar(15) PRIMARY KEY,
                         pName VARCHAR(40),
                         category VARCHAR(40),
                         qtyInSale INT,
                         price INT
);

CREATE TABLE employee (
                          employeeId varchar(15) PRIMARY KEY,
                          eName VARCHAR(40),
                          salary INT,
                          postion VARCHAR(40),
                          address VARCHAR(40),
                          phoneNo VARCHAR(40)
);

CREATE TABLE supplier (
                          supplierId varchar(15) PRIMARY KEY,
                          sName VARCHAR(40),
                          email VARCHAR(40),
                          address VARCHAR(40),
                          contact VARCHAR(40)
);

CREATE TABLE ingredient (
                            ingredientId varchar(15) PRIMARY KEY,
                            iName VARCHAR(40),
                            category VARCHAR(40)
);

CREATE TABLE wasteManage (
                             wastetId varchar(15) PRIMARY KEY,
                             wasteQty INT,
                             disposeMethod varchar(10),
                             productId varchar(10),
                             FOREIGN KEY(productId) REFERENCES product(productId) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE orderProductDetail (
                                    orderId varchar(15),
                                    productId varchar(15),
                                    unitPrice DECIMAL(20, 2),
                                    qty INT,
                                    total DECIMAL(20, 2),
                                    FOREIGN KEY(orderId) REFERENCES orders(orderId) ON DELETE CASCADE ON UPDATE CASCADE,
                                    FOREIGN KEY(productId) REFERENCES product(productId) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE productEmployeeDetail (
                                       employeeId varchar(15),
                                       productId varchar(15),
                                       assignment_type VARCHAR(10),
                                       FOREIGN KEY(employeeId) REFERENCES employee(employeeId) ON DELETE CASCADE ON UPDATE CASCADE,
                                       FOREIGN KEY(productId) REFERENCES product(productId) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE productIngredientDetail (
                                         productId varchar(15),
                                         ingredientId varchar(15),
                                         FOREIGN KEY(ingredientId) REFERENCES ingredient(ingredientId) ON DELETE CASCADE ON UPDATE CASCADE,
                                         FOREIGN KEY(productId) REFERENCES product(productId) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE supplyOrder (
                             ingredientId varchar(15),
                             supplierId VARCHAR(15),
                             date DATE,
                             qty INT,
                             price DECIMAL(10, 2),
                             total DECIMAL(10, 2),
                             FOREIGN KEY(ingredientId) REFERENCES ingredient(ingredientId) ON DELETE CASCADE ON UPDATE CASCADE,
                             FOREIGN KEY(supplierId) REFERENCES supplier(supplierId) ON DELETE CASCADE ON UPDATE CASCADE
);
