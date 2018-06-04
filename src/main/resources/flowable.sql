/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Version : 50722
 Source Host           : localhost
 Source Database       : flowable

 Target Server Version : 50722
 File Encoding         : utf-8

 Date: 05/31/2018 14:52:00 PM
*/

create database flowabl;

CREATE TABLE `events` (
  `eventId` bigint(20) NOT NULL AUTO_INCREMENT,
  `correlationId` varchar(50) NOT NULL,
  `stage` varchar(255) NOT NULL,
  `headers` varchar(255) NOT NULL,
  `throwable` text NOT NULL,
  PRIMARY KEY (`eventId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

CREATE TABLE `user` (
  `name` varchar(100) NOT NULL,
  `org` varchar(100) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
