DROP TABLE IF EXISTS `observatorio_extra_configuration`;
CREATE TABLE `observatorio_extra_configuration` ( 
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(255) NOT NULL ,
	`key` VARCHAR(255) NOT NULL , 
	`value` VARCHAR(255) NOT NULL , 
    PRIMARY KEY(`id`),
	UNIQUE (`key`)
);

INSERT INTO `observatorio_extra_configuration` (`name`, `key`, `value`) VALUES ('observatory.extra.config.theshold', 'umbral', '30');
INSERT INTO `observatorio_extra_configuration` (`name`, `key`, `value`) VALUES ('observatory.extra.config.realunch.timeout','timemout', '60');
INSERT INTO `observatorio_extra_configuration` (`name`, `key`, `value`) VALUES ('observatory.extra.config.width','width', '10');
INSERT INTO `observatorio_extra_configuration` (`name`, `key`, `value`) VALUES ('observatory.extra.config.depth','depth', '10');
INSERT INTO `observatorio_extra_configuration` (`name`, `key`, `value`) VALUES ('observatory.extra.config.autorelaunch','autorelaunch', '0');
INSERT INTO `observatorio_extra_configuration` (`name`, `key`, `value`) VALUES ('observatory.extra.config.first.classification.threshold','firstclassthreshold', '0.5');
INSERT INTO `observatorio_extra_configuration` (`name`, `key`, `value`) VALUES ('observatory.extra.config.second.classification.threshold','secondclassthreshold', '2');

ALTER TABLE `tanalisis_accesibilidad` ADD `COD_FUENTE` MEDIUMTEXT;

ALTER TABLE `observatorios_realizados` ADD `tags` TEXT NULL;

UPDATE observatorios_realizados obr SET obr.tags = (SELECT ob.tags from observatorio ob where ob.id_observatorio = obr.id_observatorio);

-- uras

ALTER TABLE `dependencia` ADD `emails` TEXT NULL;
ALTER TABLE `dependencia` ADD `id_ambit` INT NULL;
ALTER TABLE `dependencia` ADD `send_auto` INT NULL;
ALTER TABLE `dependencia` ADD `official` INT NULL;
ALTER TABLE `dependencia` ADD `id_tag` INT NULL;


CREATE TABLE `observatorio_range` ( 
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(255) NOT NULL ,
	`min_value` float(4,2) NOT NULL , 
	`max_value` float(4,2) ,
	`min_value_operator` VARCHAR(255) NOT NULL ,
	`max_value_operator` VARCHAR(255), 
    PRIMARY KEY(`id`),
	UNIQUE (`name`)
);

CREATE TABLE `observatorio_template_range` ( 
	`id` INT NOT NULL AUTO_INCREMENT,
	`id_observatory_execution` INT NOT NULL,
	`name` VARCHAR(255) NOT NULL ,
	`min_value` float(4,2) NOT NULL , 
	`max_value` float(4,2) ,
	`min_value_operator` VARCHAR(255) NOT NULL ,
	`max_value_operator` VARCHAR(255), 
	`template` mediumtext NOT NULL,
    PRIMARY KEY(`id`),
	UNIQUE (`name`)
);

