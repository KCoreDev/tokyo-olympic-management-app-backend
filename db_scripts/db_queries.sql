
-- 1. Insert result types.
INSERT INTO `tokyo-olympics-management-db`.results(`id`,`name`) VALUES (1, 'NOT_STARTED');
INSERT INTO `tokyo-olympics-management-db`.results(`id`,`name`) VALUES (2, 'WON');
INSERT INTO `tokyo-olympics-management-db`.results(`id`,`name`) VALUES (3, 'LOST');
INSERT INTO `tokyo-olympics-management-db`.results(`id`,`name`) VALUES (4, 'DRAWN');


-- 2. Insert Events
INSERT INTO `tokyo-olympics-management-db`.events(`id`, `created_on`,`description`, `event_date`, `event_status`, `location`, `name`, `result_id`)
	VALUES ('1', '2021-11-20', 'Mens 100m Sprint.','2021-12-20', 'YET_TO_START', 'Stadium 1', 'Mens 100m', 1);
	
INSERT INTO `tokyo-olympics-management-db`.events(`id`, `created_on`,`description`, `event_date`, `event_status`, `location`, `name`, `result_id`)
	VALUES ('2', '2021-11-20', 'Womens 100m Sprint.','2021-12-20', 'YET_TO_START', 'Stadium 1', 'Womens 100m', 1);
	
