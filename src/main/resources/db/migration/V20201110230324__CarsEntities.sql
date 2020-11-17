create table car
(
   id bigint NOT NULL AUTO_INCREMENT,
    name varchar(255),
    braking int,
    top_speed int,
    cornering int,
    acceleration int,
    PRIMARY KEY (id)
);

INSERT INTO car(name, braking, top_speed, cornering, acceleration)
VALUES('CORV', 8, 3, 4, 9),
      ('GTR', 6, 7, 9, 8),
      ('BMW', 9, 4, 6, 10),
      ('BENZ', 9, 7, 4, 10),
      ('CLEO', 7, 3, 7, 2),
      ('GOLF', 6, 7, 9, 8);
