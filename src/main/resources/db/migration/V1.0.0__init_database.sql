CREATE TABLE IF NOT EXISTS cash_receipt (
                                            id_cash_receipt SERIAL NOT NULL,
                                            date_creation TIMESTAMP DEFAULT NOW() NOT NULL,
                                            id_shop BIGINT NOT NULL ,
                                            id_discount_card BIGINT
);

ALTER TABLE cash_receipt ADD CONSTRAINT pk_cash_receipt PRIMARY KEY (id_cash_receipt);

CREATE TABLE IF NOT EXISTS discount_card(
                                            id_discount_card SERIAL NOT NULL ,
                                            number_card BIGINT NOT NULL,
                                            discount INTEGER NOT NULL
);

ALTER TABLE discount_card ADD CONSTRAINT pk_discount_card PRIMARY KEY (id_discount_card);

CREATE TABLE IF NOT EXISTS product (
                                       id_product SERIAL NOT NULL ,
                                       product_name VARCHAR (45) NOT NULL ,
                                       price NUMERIC(9,2) NOT NULL
);

ALTER TABLE product ADD CONSTRAINT pk_product PRIMARY KEY (id_product);

CREATE TABLE IF NOT EXISTS shop(
                                   id_shop BIGSERIAL NOT NULL ,
                                   shop_name VARCHAR (45) NOT NULL ,
                                   address VARCHAR (100) NOT NULL ,
                                   phone_number VARCHAR (20)
);

ALTER TABLE shop ADD CONSTRAINT pk_shop PRIMARY KEY (id_shop);

CREATE TABLE IF NOT EXISTS receipt_product (
                                               id_cash_receipt BIGINT NOT NULL ,
                                               id_product BIGINT NOT NULL
);

ALTER TABLE receipt_product ADD CONSTRAINT fk_receipt_product_receipt
    FOREIGN KEY (id_cash_receipt) REFERENCES cash_receipt (id_cash_receipt) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE receipt_product ADD CONSTRAINT fk_receipt_product_product
    FOREIGN KEY (id_product) REFERENCES product (id_product) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE cash_receipt ADD CONSTRAINT fk_cash_receipt_shop
    FOREIGN KEY (id_shop) REFERENCES shop (id_shop) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE cash_receipt ADD CONSTRAINT fk_cash_receipt_discount_card
    FOREIGN KEY (id_discount_card) REFERENCES discount_card (id_discount_card) ON DELETE CASCADE ON UPDATE CASCADE;
