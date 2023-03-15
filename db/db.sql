__________________________________________________

CREATE DATABASE organization ENCODING 'UTF-8';
__________________________________________________

CREATE TABLE IF NOT EXISTS employee (
id SERIAL PRIMARY KEY,
name VARCHAR(255) NOT NULL,
phone VARCHAR(255) UNIQUE NOT NULL,
email VARCHAR(255) NOT NULL,
post VARCHAR(255) NOT NULL;
salary BIGINT NOT NULL);
__________________________________________________

INSERT INTO employee (name,phone,email,post,salary)
VALUES ('Alexey','+7(909)909-99-09','alex@mail.ru','ingener',70000),
       ('Ivan','+7(383)276-88-51','ivan@mail.ru','ingener_major',90000),
       ('Sergej','+7(952)322-65-99','serj@mail.ru','all_hand_master',120000);
__________________________________________________

CREATE TABLE IF NOT EXISTS employee_car (
id SERIAL PRIMARY KEY,
reg_num VARCHAR(20) UNIQUE NOT NULL,
brand_model VARCHAR(20) NOT NULL,
employee_id BIGINT UNSIGNED NOT NULL,
FOREIGN KEY (employee_id) REFERENCES employee(id));
__________________________________________________

INSERT INTO employee_car
(reg_num, brand_model, employee_id)
VALUES
('x123xx','bmw',1),
('e456kx','audi',2);
('k258ym','vaz',3);
__________________________________________________

SELECT e.id, e.name, e.phone, e.mail, ec.brand_model, ec.reg_num
FROM employee AS e
LEFT JOIN employee_cars AS ec
ON e.id = ec.employee_id
WHERE ec.reg_num = (?);
__________________________________________________

DELETE FROM employee
WHERE id = (?) AND name=(?) AND phone=(?) AND post= (?)
RETURNING id;
__________________________________________________

UPDATE employee
SET post=(?),salary=(?) WHERE id=(?)
RETURNING id;
__________________________________________________
