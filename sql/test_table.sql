CREATE TABLE `dang_feng_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '创建用户id',
  `md5` varchar(64) NOT NULL DEFAULT '' COMMENT '唯一标识',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '姓名',
  `bank_card_no` varchar(64) NOT NULL DEFAULT '' COMMENT '银行卡号(加密)',
  `id_card_no` varchar(64) NOT NULL DEFAULT '' COMMENT '身份证号(加密)',
  `phone` varchar(64) NOT NULL DEFAULT '' COMMENT '手机号(加密)',
  `bank_name` varchar(255) NOT NULL DEFAULT '' COMMENT '银行名称',
  `auth_result` varchar(64) NOT NULL DEFAULT '' COMMENT '实名认证结果',
  `auth_time` timestamp NULL DEFAULT NULL COMMENT '最后实名认证的时间戳',
  `ctime` timestamp NULL DEFAULT NULL COMMENT '记录创建时间',
  `valid` tinyint(4) NOT NULL DEFAULT '0' COMMENT '有效性 0删除，1有效',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_md5` (`md5`(10))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='实名认证四要素资源池';

CREATE TABLE `ze_ren_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '创建用户id',
  `md5` varchar(64) NOT NULL DEFAULT '' COMMENT '唯一标识',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '姓名',
  `bank_card_no` varchar(64) NOT NULL DEFAULT '' COMMENT '银行卡号(加密)',
  `id_card_no` varchar(64) NOT NULL DEFAULT '' COMMENT '身份证号(加密)',
  `phone` varchar(64) NOT NULL DEFAULT '' COMMENT '手机号(加密)',
  `bank_name` varchar(255) NOT NULL DEFAULT '' COMMENT '银行名称',
  `auth_result` varchar(64) NOT NULL DEFAULT '' COMMENT '实名认证结果',
  `auth_time` timestamp NULL DEFAULT NULL COMMENT '最后实名认证的时间戳',
  `ctime` timestamp NULL DEFAULT NULL COMMENT '记录创建时间',
  `valid` tinyint(4) NOT NULL DEFAULT '0' COMMENT '有效性 0删除，1有效',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_md5` (`md5`(10))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='实名认证四要素资源池';
