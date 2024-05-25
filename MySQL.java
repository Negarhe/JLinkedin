
//CREATE DATABASE if not exists `ap_project`;
//USE `ap_project`;
//
//SET NAMES utf8 ;
//SET character_set_client = utf8mb4 ;
//
//create table if not exists `users` (
//        `email` varchar(100) not null,
//        `name` varchar(20) not null,
//        `lastName` varchar(40) not null,
//        `password` varchar(20) not null,
//        `title` varchar(220),
//    `additionalName` varchar(40),
//    `city` varchar(60),
//    `country` varchar(60),
//    `profession` varchar(60),
//    `status` ENUM('SEEKING-JOB', 'HIRING', 'PROVIDING_SERVICES'),
//    `contactInformationId` int,
//        `followers` json,
//        `following` json,
//        `connection` json,
//        `posts` json,
//
//primary key (`email`)
//);

//CREATE TABLE if not exists `ContactInformation` (
//        -- `id` INT `AUTO_INCREMENT` PRIMARY KEY,
//    `email` VARCHAR(255),
//    `phoneNumber` VARCHAR(255),
//    `kind` ENUM('HOME', 'JOB', 'MOBILE'),
//    `address` VARCHAR(255),
//    `birthday` DATE,
//        `relationshipStatus` VARCHAR(255),
//
//foreign key (`email`) references users(`email`),
//primary key (`email`)
//
//);