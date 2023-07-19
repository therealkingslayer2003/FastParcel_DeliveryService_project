CREATE TABLE "office"(
                         office_id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                         office_location VARCHAR(200) NOT NULL
);

CREATE TABLE "client"(
                         client_id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                         email VARCHAR(100) NOT NULL UNIQUE,
                         name VARCHAR(100) NOT NULL,
                         phone_number VARCHAR(16) NOT NULL UNIQUE
);

CREATE TABLE "employee_role"(
                                employee_role_id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                employee_role_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE "employee"(
                           employee_id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                           office_id INT REFERENCES "office"(office_id) NOT NULL,
                           employee_role_id INT REFERENCES "employee_role"(employee_role_id) NOT NULL,
                           email VARCHAR(100) NOT NULL UNIQUE,
                           phone_number VARCHAR(16) NOT NULL UNIQUE,
                           password VARCHAR(100) NOT NULL,
                           name VARCHAR(100) NOT NULL
);

CREATE TABLE "driver"(
                         employee_id INT PRIMARY KEY REFERENCES "employee"(employee_id) NOT NULL,
                         license_number VARCHAR(10) NOT NULL UNIQUE
);

CREATE TABLE "truck"(
                        employee_id INT PRIMARY KEY REFERENCES "driver"(employee_id) NOT NULL UNIQUE,
                        model VARCHAR(100) NOT NULL,
                        capacity FLOAT NOT NULL
);


CREATE TABLE "order_category"(
                                 order_category_id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                 order_category_name VARCHAR(200) NOT NULL
);

CREATE TABLE "order_status"(
                               order_status_id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                               order_status_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE "order"(
                        order_id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                        client_id INT REFERENCES "client"(client_id) NOT NULL,
                        office_id INT REFERENCES "office"(office_id) NOT NULL,
                        weight FLOAT NOT NULL,
                        order_category_id INT REFERENCES "order_category"(order_category_id) NOT NULL,
                        order_status_id INT REFERENCES "order_status"(order_status_id) NOT NULL
);

CREATE TABLE "order_status_history"(
                                       order_status_history_id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                       order_id INT REFERENCES "order"(order_id) NOT NULL,
                                       order_status_id INT REFERENCES "order_status"(order_status_id) NOT NULL,
                                       employee_id INT REFERENCES "employee"(employee_id) NOT NULL,
                                       changed_at TIMESTAMP NOT NULL
);

CREATE TABLE "shipment_status"(
                                  shipment_status_id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                  shipment_status_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE "shipment"(
                           shipment_id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                           employee_id INT REFERENCES "driver"(employee_id) NOT NULL,
                           shipment_status_id INT REFERENCES "shipment_status"(shipment_status_id) NOT NULL,
                           total_weight FLOAT NOT NULL,
                           current_office_id INT REFERENCES "office"(office_id),
                           destination_office_id INT REFERENCES "office"(office_id)
);

CREATE TABLE "shipment_order"(
                                 shipment_id INT REFERENCES "shipment"(shipment_id),
                                 order_id INT REFERENCES "order"(order_id),
                                 CONSTRAINT shipment_order_pkey PRIMARY KEY (shipment_id, order_id)
);
