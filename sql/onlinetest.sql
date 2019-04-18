/*
Navicat MySQL Data Transfer

Source Server         : root
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : onlinetest

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2019-04-18 09:20:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `question`
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id` varchar(255) NOT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES ('222299023', '题目1', null, '1555510571279');
INSERT INTO `question` VALUES ('515176379', '题目2', null, '1555512207514');
INSERT INTO `question` VALUES ('587992380', '题目3', null, '1555512296858');

-- ----------------------------
-- Table structure for `record`
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record` (
  `id` varchar(255) NOT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `question_id` varchar(255) DEFAULT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `start_time` bigint(20) DEFAULT NULL,
  `end_time` bigint(20) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of record
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(255) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `limit_times` int(11) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `start_time` bigint(20) DEFAULT NULL,
  `end_time` bigint(20) DEFAULT NULL,
  `questions` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('153709968', '小红', '698d51a19d8a121ce581499d7b701668', '1555513561910', 'ROLE_USER', '1', '2', null, null, '#222299023#515176379#');
INSERT INTO `user` VALUES ('225986966', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '1555470194933', 'ROLE_ADMIN', null, null, null, null, null);
INSERT INTO `user` VALUES ('566072523', '小明', '698d51a19d8a121ce581499d7b701668', '1555470194933', 'ROLE_USER', '1', '2', null, null, '#222299023#515176379#');
