DROP TABLE IF EXISTS `member`;

CREATE TABLE `member` (
                          `id` varchar(36) NOT NULL,
                          `name` varchar(100) NOT NULL,
                          `address` varchar(300) NOT NULL,
                          `contact` varchar(20) NOT NULL,
                          PRIMARY KEY (`id`)
);

INSERT INTO `member`
VALUES ('05bcea20-fae2-4621-a66e-23bfa942d454','Sampath','Galle','071-1234567'),
       ('7453a9bc-7091-44ae-90f5-e55a63c95956','Gayan','Kandy','077-1478523'),
       ('7453a9bc-7091-44ae-90f5-e55a63c959c3','Kasun','Galle','071-1478523'),
       ('7453a9bc-7091-44ae-90f5-e55a63c959cc','Tharindu','Kurunegala','071-2581478'),
       ('c2af11a9-4186-4479-b059-b9f0073b33fa','Chathura','Matara','071-1478523');

DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
                        `isbn` varchar(13) NOT NULL,
                        `title` varchar(300) NOT NULL,
                        `author` varchar(300) NOT NULL,
                        `copies` int NOT NULL DEFAULT '1',
                        PRIMARY KEY (`isbn`)
);



INSERT INTO `book` VALUES ('1111-2222','Tee','Mosh',4),
                          ('1117-7111','BackUp','Me',2),
                          ('1234-1234','Patterns of Enterprise Application Architecture','Marin Fowler',2),
                          ('1234-4567','Applied Architecture','Microsoft',2),
                          ('1234-7891','Clean Code','Robert Decil',3),
                          ('4567-1234','UML Distilled','Martin Fowler',1),
                          ('4567-4567','SQL Specification 2011','Ansi',1),
                          ('4567-7891','ECMAScript Specification 2022','ECMA Body',1),
                          ('7852-1478','Test','Test Me',3),
                          ('7891-1234','Java Language Specification','James Gosling',2);

DROP TABLE IF EXISTS `issue_note`;

CREATE TABLE `issue_note` (
                              `id` int NOT NULL AUTO_INCREMENT,
                              `date` date NOT NULL,
                              `member_id` varchar(36) NOT NULL,
                              PRIMARY KEY (`id`),
                              CONSTRAINT `issue_note_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
);
INSERT INTO `issue_note` VALUES (11,'2022-11-15','7453a9bc-7091-44ae-90f5-e55a63c959cc'),(12,'2022-11-15','05bcea20-fae2-4621-a66e-23bfa942d454'),(13,'2022-11-15','7453a9bc-7091-44ae-90f5-e55a63c95956');


DROP TABLE IF EXISTS `issue_item`;

CREATE TABLE `issue_item` (
  `issue_id` int NOT NULL,
  `isbn` varchar(13) NOT NULL,
  PRIMARY KEY (`issue_id`,`isbn`),
  CONSTRAINT `issue_item_ibfk_1` FOREIGN KEY (`issue_id`) REFERENCES `issue_note` (`id`),
  CONSTRAINT `issue_item_ibfk_2` FOREIGN KEY (`isbn`) REFERENCES `book` (`isbn`)
);

INSERT INTO `issue_item` VALUES (12,'1234-1234'),(11,'1234-7891'),(12,'1234-7891'),(13,'4567-1234'),(11,'4567-4567'),(12,'7891-1234');







DROP TABLE IF EXISTS `return`;

CREATE TABLE `return` (
  `date` date NOT NULL,
  `issue_id` int NOT NULL,
  `isbn` varchar(13) NOT NULL,
  PRIMARY KEY (`issue_id`,`isbn`),
  CONSTRAINT `return_ibfk_1` FOREIGN KEY (`issue_id`, `isbn`) REFERENCES `issue_item` (`issue_id`, `isbn`)
);

INSERT INTO `return` VALUES ('2022-11-15',11,'4567-4567'),('2022-11-15',12,'1234-7891');


