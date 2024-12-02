DROP TABLE IF EXISTS `usuarios`;

CREATE TABLE usuarios (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(30) DEFAULT NULL,
    password VARCHAR(255) DEFAULT NULL,
    email VARCHAR(100) DEFAULT NULL,
    isLogged TINYINT(1) DEFAULT NULL,
    PRIMARY KEY (id)
);