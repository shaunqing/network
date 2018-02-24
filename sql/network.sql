/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50718
Source Host           : 127.0.0.1:3306
Source Database       : network

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2018-02-24 18:29:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for itscy_web_scan
-- ----------------------------
DROP TABLE IF EXISTS `itscy_web_scan`;
CREATE TABLE `itscy_web_scan` (
  `scan_id` int(11) NOT NULL AUTO_INCREMENT,
  `system_id` varchar(20) NOT NULL,
  `state` varchar(10) DEFAULT NULL,
  `info` varchar(200) DEFAULT NULL,
  `file_count` int(11) DEFAULT NULL,
  `file_extension` varchar(10) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`scan_id`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for itscy_web_scan_file
-- ----------------------------
DROP TABLE IF EXISTS `itscy_web_scan_file`;
CREATE TABLE `itscy_web_scan_file` (
  `scan_file_id` int(11) NOT NULL AUTO_INCREMENT,
  `scan_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `type` varchar(10) DEFAULT NULL,
  `preview_name` varchar(100) DEFAULT NULL COMMENT 'word预览文件的路径',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`scan_file_id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for itscy_web_scan_record
-- ----------------------------
DROP TABLE IF EXISTS `itscy_web_scan_record`;
CREATE TABLE `itscy_web_scan_record` (
  `record_id` int(11) NOT NULL AUTO_INCREMENT,
  `system_id` varchar(20) DEFAULT NULL,
  `scan_id` int(11) DEFAULT NULL,
  `scan_info` varchar(300) DEFAULT NULL,
  `scan_create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for itscy_web_system
-- ----------------------------
DROP TABLE IF EXISTS `itscy_web_system`;
CREATE TABLE `itscy_web_system` (
  `system_id` varchar(20) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `project` varchar(40) DEFAULT NULL,
  `local_ip` varchar(40) DEFAULT NULL,
  `inter_ip` varchar(40) DEFAULT NULL,
  `framework_ver` varchar(40) DEFAULT NULL,
  `database_ver` varchar(40) DEFAULT NULL,
  `middleware_ver` varchar(40) DEFAULT NULL,
  `linker` varchar(30) DEFAULT NULL,
  `net_type` varchar(30) DEFAULT NULL,
  `scan_type` varchar(30) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `generate_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`system_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_firewall
-- ----------------------------
DROP TABLE IF EXISTS `t_firewall`;
CREATE TABLE `t_firewall` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `firewall` varchar(10) DEFAULT NULL,
  `content` varchar(300) DEFAULT NULL,
  `note` varchar(200) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_firewall_file
-- ----------------------------
DROP TABLE IF EXISTS `t_firewall_file`;
CREATE TABLE `t_firewall_file` (
  `id` int(11) NOT NULL,
  `firewall_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `save_path` varchar(300) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- View structure for itscy_v_web_system
-- ----------------------------
DROP VIEW IF EXISTS `itscy_v_web_system`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `itscy_v_web_system` AS select `scan`.`scan_id` AS `scan_id`,`sys`.`system_id` AS `system_id`,`sys`.`name` AS `name`,`sys`.`project` AS `project`,`sys`.`local_ip` AS `local_ip`,`sys`.`inter_ip` AS `inter_ip`,`sys`.`framework_ver` AS `framework_ver`,`sys`.`database_ver` AS `database_ver`,`sys`.`middleware_ver` AS `middleware_ver`,`sys`.`linker` AS `linker`,`scan`.`state` AS `state`,`scan`.`info` AS `info`,`sys`.`create_time` AS `system_create_time`,`scan`.`create_time` AS `scan_create_time` from (`itscy_web_system` `sys` join `itscy_web_scan` `scan`) where (`sys`.`system_id` = `scan`.`system_id`) ;
