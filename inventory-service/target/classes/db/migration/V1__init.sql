CREATE TABLE `t_inventory` (
                            `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
                            `order_number` VARCHAR(255) DEFAULT NULL,
                            `sku` VARCHAR(255) NOT NULL,
                            `quantity` INT(11) NOT NULL,
                            PRIMARY KEY (`id`)
);