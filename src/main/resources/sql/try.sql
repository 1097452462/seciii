USE docs;
drop table field;
CREATE TABLE field(
    id  INT AUTO_INCREMENT PRIMARY KEY,
    Field_name      TEXT,
    Paper_list      TEXT,
    Paper_num       INT
)ENGINE=InnoDB;

COMMIT;