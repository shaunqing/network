/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50718
Source Host           : 127.0.0.1:3306
Source Database       : network

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2017-10-10 17:17:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for itscy_web_scan
-- ----------------------------
DROP TABLE IF EXISTS `itscy_web_scan`;
CREATE TABLE `itscy_web_scan` (
  `scan_id` int(11) NOT NULL AUTO_INCREMENT,
  `system_id` varchar(20) DEFAULT NULL,
  `state` varchar(10) DEFAULT NULL,
  `info` varchar(300) DEFAULT NULL,
  `file_count` int(11) DEFAULT NULL,
  `file_extension` varchar(10) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`scan_id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of itscy_web_scan
-- ----------------------------
INSERT INTO `itscy_web_scan` VALUES ('29', '364527034778517504', '测试中', '呜呜呜呜', '1', 'jpg', '2017-10-02 21:40:00');
INSERT INTO `itscy_web_scan` VALUES ('30', '364527034778517504', '待修复', '啊啊大方', '1', 'doc', '2017-10-02 21:40:55');
INSERT INTO `itscy_web_scan` VALUES ('31', '364527034778517504', '复测中', 'adfafsd', '1', 'jpg', '2017-10-02 21:57:28');
INSERT INTO `itscy_web_scan` VALUES ('32', '364527034778517504', '已通过', '已通过', '0', '', '2017-10-02 21:57:41');
INSERT INTO `itscy_web_scan` VALUES ('34', '364531835927003136', '测试中', '55555', '1', 'doc', '2017-10-02 22:00:17');
INSERT INTO `itscy_web_scan` VALUES ('35', '365076151120429056', '测试中', 'asdvasv', '1', 'jpg', '2017-10-04 10:01:00');
INSERT INTO `itscy_web_scan` VALUES ('36', '365076151120429056', '待修复', 'lklkl', '1', 'doc', '2017-10-04 10:02:43');
INSERT INTO `itscy_web_scan` VALUES ('37', '365079370127114240', '测试中', '', '1', 'doc', '2017-10-04 10:14:00');
INSERT INTO `itscy_web_scan` VALUES ('38', '365079370127114240', '待修复', 'sadfafa', '1', 'doc', '2017-10-05 21:38:05');
INSERT INTO `itscy_web_scan` VALUES ('39', '364531835927003136', '待修复', 'dd', '1', 'doc', '2017-10-05 21:47:46');
INSERT INTO `itscy_web_scan` VALUES ('40', '364531835927003136', '复测中', 'ss', '1', 'doc', '2017-10-05 21:51:36');
INSERT INTO `itscy_web_scan` VALUES ('41', '364531835927003136', '测试中', '555', '1', 'doc', '2017-10-05 21:55:22');
INSERT INTO `itscy_web_scan` VALUES ('42', '364531835927003136', '测试中', '', '1', 'doc', '2017-10-05 22:13:24');
INSERT INTO `itscy_web_scan` VALUES ('43', '364531835927003136', '测试中', '', '1', 'doc', '2017-10-05 22:22:02');
INSERT INTO `itscy_web_scan` VALUES ('44', '364531835927003136', '测试中', '', '1', 'doc', '2017-10-05 22:26:03');
INSERT INTO `itscy_web_scan` VALUES ('45', '365079370127114240', '测试中', '', '1', 'doc', '2017-10-05 22:27:36');
INSERT INTO `itscy_web_scan` VALUES ('46', '365079370127114240', '测试中', '', '1', 'doc', '2017-10-05 22:29:04');
INSERT INTO `itscy_web_scan` VALUES ('47', '365079370127114240', '测试中', '', '1', 'doc', '2017-10-05 22:30:22');
INSERT INTO `itscy_web_scan` VALUES ('48', '365079370127114240', '测试中', '', '1', 'doc', '2017-10-05 22:33:01');
INSERT INTO `itscy_web_scan` VALUES ('49', '365079370127114240', '测试中', '', '1', 'doc', '2017-10-05 23:00:00');
INSERT INTO `itscy_web_scan` VALUES ('50', '365079370127114240', '待修复', 'sssss', '0', '', '2017-10-09 09:50:12');

-- ----------------------------
-- Table structure for itscy_web_scan_file
-- ----------------------------
DROP TABLE IF EXISTS `itscy_web_scan_file`;
CREATE TABLE `itscy_web_scan_file` (
  `scan_file_id` int(11) NOT NULL AUTO_INCREMENT,
  `scan_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `type` varchar(10) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`scan_file_id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of itscy_web_scan_file
-- ----------------------------
INSERT INTO `itscy_web_scan_file` VALUES ('13', '29', null, '20171002214017_1_170926 - 11.jpg', '安全服务工作单', '2017-10-02 21:40:00');
INSERT INTO `itscy_web_scan_file` VALUES ('14', '30', null, '20171002214054_0_222.doc', '安全服务工作单', '2017-10-02 21:40:55');
INSERT INTO `itscy_web_scan_file` VALUES ('15', '31', null, '20171002215727_8_170926 - 11.jpg', '安全服务工作单', '2017-10-02 21:57:28');
INSERT INTO `itscy_web_scan_file` VALUES ('16', '35', null, '20171004100217_5_20171002215727_8_170926 - 11.jpg', '安全服务工作单', '2017-10-04 10:01:00');
INSERT INTO `itscy_web_scan_file` VALUES ('17', '36', null, '20171004100243_6_20171002214054_0_222.doc', '漏洞报告', '2017-10-04 10:02:43');
INSERT INTO `itscy_web_scan_file` VALUES ('18', '38', null, '20171005213804_4_20171004100243_6_20171002214054_0_222.doc', '漏洞报告', '2017-10-05 21:38:05');
INSERT INTO `itscy_web_scan_file` VALUES ('19', '37', null, '20171005213843_0_20171004100243_6_20171002214054_0_222.doc', '漏洞报告', '2017-10-05 21:38:44');
INSERT INTO `itscy_web_scan_file` VALUES ('20', '34', null, '20171005214439_7_20171004100243_6_20171002214054_0_222.doc', '安全服务工作单', '2017-10-05 21:44:39');
INSERT INTO `itscy_web_scan_file` VALUES ('21', '39', null, '20171005214745_2_20171004100243_6_20171002214054_0_222.doc', '漏洞报告', '2017-10-05 21:47:46');
INSERT INTO `itscy_web_scan_file` VALUES ('22', '40', null, '20171005215135_6_20171004100243_6_20171002214054_0_222.doc', '安全服务工作单', '2017-10-05 21:51:36');
INSERT INTO `itscy_web_scan_file` VALUES ('23', '41', null, '20171005215521_9_20171004100243_6_20171002214054_0_222.doc', '安全服务工作单', '2017-10-05 21:55:22');
INSERT INTO `itscy_web_scan_file` VALUES ('24', '42', null, '20171005221323_0_20171004100243_6_20171002214054_0_222.doc', '漏洞报告', '2017-10-05 22:13:24');
INSERT INTO `itscy_web_scan_file` VALUES ('25', '43', null, '20171005222201_8_20171004100243_6_20171002214054_0_222.doc', '安全服务工作单', '2017-10-05 22:22:02');
INSERT INTO `itscy_web_scan_file` VALUES ('26', '44', null, '20171005222603_6_20171004100243_6_20171002214054_0_222.doc', '安全服务工作单', '2017-10-05 22:26:03');
INSERT INTO `itscy_web_scan_file` VALUES ('27', '45', null, '20171005222736_9_20171004100243_6_20171002214054_0_222.doc', '安全服务工作单', '2017-10-05 22:27:36');
INSERT INTO `itscy_web_scan_file` VALUES ('28', '46', null, '20171005222903_6_20171004100243_6_20171002214054_0_222.doc', '安全服务工作单', '2017-10-05 22:29:04');
INSERT INTO `itscy_web_scan_file` VALUES ('29', '47', null, '20171005223022_4_20171004100243_6_20171002214054_0_222.doc', '安全服务工作单', '2017-10-05 22:30:22');
INSERT INTO `itscy_web_scan_file` VALUES ('30', '48', null, '20171005223301_7_20171004100243_6_20171002214054_0_222.doc', '安全服务工作单', '2017-10-05 22:33:01');
INSERT INTO `itscy_web_scan_file` VALUES ('31', '49', null, '20171005225959_2_20171004100243_6_20171002214054_0_222.doc', '安全服务工作单', '2017-10-05 23:00:00');

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
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of itscy_web_scan_record
-- ----------------------------
INSERT INTO `itscy_web_scan_record` VALUES ('19', '364527034778517504', '30', '啊啊大方', '2017-10-02 21:40:55');
INSERT INTO `itscy_web_scan_record` VALUES ('20', '364527034778517504', '32', '已通过', '2017-10-02 21:57:41');
INSERT INTO `itscy_web_scan_record` VALUES ('21', '365076151120429056', '36', 'lklkl', '2017-10-04 10:02:43');
INSERT INTO `itscy_web_scan_record` VALUES ('22', '365079370127114240', '38', 'sadfafa', '2017-10-05 21:38:05');
INSERT INTO `itscy_web_scan_record` VALUES ('23', '364531835927003136', '39', 'dd', '2017-10-05 21:47:46');
INSERT INTO `itscy_web_scan_record` VALUES ('24', '365079370127114240', '50', 'sssss', '2017-10-09 09:50:12');

-- ----------------------------
-- Table structure for itscy_web_system
-- ----------------------------
DROP TABLE IF EXISTS `itscy_web_system`;
CREATE TABLE `itscy_web_system` (
  `system_id` varchar(20) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `project` varchar(30) DEFAULT NULL,
  `local_ip` varchar(15) DEFAULT NULL,
  `inter_ip` varchar(15) DEFAULT NULL,
  `framework_ver` varchar(30) DEFAULT NULL,
  `database_ver` varchar(30) DEFAULT NULL,
  `middleware_ver` varchar(30) DEFAULT NULL,
  `linker` varchar(30) DEFAULT NULL,
  `net_type` varchar(10) DEFAULT NULL,
  `scan_type` varchar(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`system_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of itscy_web_system
-- ----------------------------
INSERT INTO `itscy_web_system` VALUES ('364527034778517504', null, '这是一个测试系统名2', '11', '11', '11adf', '11', '11', '1111', '11sss', '工作网', '深度渗透测试', '2017-10-02 21:40:00');
INSERT INTO `itscy_web_system` VALUES ('364531835927003136', null, 'sadfasdf', 'd', 'sd', 'd', 'sd', 'd', 'ddd', 'd', '业务网', '深度渗透测试', '2017-10-02 21:58:00');
INSERT INTO `itscy_web_system` VALUES ('365076151120429056', null, 'dfaafda', 'dvdv', 'adfaf', 'dvd', 'adsfaf', 'vdv', 'davavd', 'dvavv', '业务网', '深度渗透测试', '2017-10-04 10:01:00');
INSERT INTO `itscy_web_system` VALUES ('365079370127114240', null, '地方地方大幅度', '打发打发', '得到', '得到', '得到', '地方', '得到', '得到', '业务网', '深度渗透测试', '2017-10-04 10:14:00');

-- ----------------------------
-- View structure for itscy_v_web_system
-- ----------------------------
DROP VIEW IF EXISTS `itscy_v_web_system`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `itscy_v_web_system` AS select `scan`.`scan_id` AS `scan_id`,`sys`.`system_id` AS `system_id`,`sys`.`name` AS `name`,`sys`.`project` AS `project`,`sys`.`local_ip` AS `local_ip`,`sys`.`inter_ip` AS `inter_ip`,`sys`.`framework_ver` AS `framework_ver`,`sys`.`database_ver` AS `database_ver`,`sys`.`middleware_ver` AS `middleware_ver`,`sys`.`linker` AS `linker`,`scan`.`state` AS `state`,`scan`.`info` AS `info`,`sys`.`create_time` AS `system_create_time`,`scan`.`create_time` AS `scan_create_time` from (`itscy_web_system` `sys` join `itscy_web_scan` `scan`) where (`sys`.`system_id` = `scan`.`system_id`) ;
