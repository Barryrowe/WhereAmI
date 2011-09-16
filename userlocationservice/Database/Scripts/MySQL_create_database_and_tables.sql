delimiter $$

CREATE DATABASE `pineapplepiranha` /*!40100 DEFAULT CHARACTER SET utf8 */$$

delimiter $$

CREATE TABLE `Users` (
  `UserName` varchar(25) NOT NULL,
  `Password` varchar(45) NOT NULL,
  `Email` varchar(50) default NULL,
  `LastKnownLocation` int(11) default NULL,
  PRIMARY KEY  (`UserName`),
  KEY `User_To_Location` (`LastKnownLocation`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$

delimiter $$

CREATE TABLE `Locations` (
  `LocationId` int(11) NOT NULL,
  `Longitude` decimal(19,16) default NULL,
  `Latitude` decimal(19,16) default NULL,
  `Altitude` decimal(19,16) default NULL,
  `Confidence` decimal(3,2) default NULL,
  `UserName` varchar(25) default NULL,
  `CreateTimeStamp` timestamp NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY  (`LocationId`),
  KEY `Location_to_User` (`UserName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Holds User Location Data'$$


