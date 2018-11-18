/*
Navicat MySQL Data Transfer

Source Server         : 192.168.56.11
Source Server Version : 50711
Source Host           : 192.168.56.11:3306
Source Database       : mybatis

Target Server Type    : MYSQL
Target Server Version : 50711
File Encoding         : 65001

Date: 2018-11-18 20:51:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tbl_employee
-- ----------------------------
DROP TABLE IF EXISTS `tbl_employee`;
CREATE TABLE `tbl_employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `last_name` varchar(255) DEFAULT NULL,
  `gender` char(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `d_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_employee
-- ----------------------------
INSERT INTO `tbl_employee` VALUES ('1', 'qwertyui', '0', 'jerry@asd.com', '1');
INSERT INTO `tbl_employee` VALUES ('3', 'jerry', '3', 'jerry@asd.com', '2');
INSERT INTO `tbl_employee` VALUES ('4', 'jerryds', '0', 'jerry@asd.com', '1');
INSERT INTO `tbl_employee` VALUES ('5', 'smith', '1', 'smith@atguigu.com', '1');
INSERT INTO `tbl_employee` VALUES ('6', 'allen', '0', 'allen@atguigu.com', '1');
INSERT INTO `tbl_employee` VALUES ('7', 'smith', '1', 'smith@atguigu.com', '1');
INSERT INTO `tbl_employee` VALUES ('8', 'allen', '0', 'allen@atguigu.com', '1');

-- ----------------------------
-- Table structure for tbl_tept
-- ----------------------------
DROP TABLE IF EXISTS `tbl_tept`;
CREATE TABLE `tbl_tept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dept_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_tept
-- ----------------------------
INSERT INTO `tbl_tept` VALUES ('1', '开发部');
INSERT INTO `tbl_tept` VALUES ('2', '测试部');
