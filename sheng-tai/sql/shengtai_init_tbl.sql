DROP TABLE IF EXISTS `tbl_shengtai_exam`;
CREATE TABLE `tbl_shengtai_exam` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exam_id` varchar(64) NOT NULL DEFAULT '' COMMENT '考试唯一标识',
  `parent_exam_id` varchar(64) NOT NULL DEFAULT '0' COMMENT '父级菜单id',
  `exam_type` varchar(255) NOT NULL DEFAULT '' COMMENT '模板类型，考核分类，考核指标，考核要点',
  `exam_name` varchar(255) NOT NULL DEFAULT '' COMMENT '考核名称',
  `exam_desc` varchar(2048) NOT NULL DEFAULT '' COMMENT '考核描述',
  `exam_score` int(11) NOT NULL DEFAULT 0 COMMENT '分值',
  `exam_status` varchar(255) NOT NULL DEFAULT '' COMMENT '考核状态',
  `create_uid`  varchar(64) NOT NULL DEFAULT '' COMMENT '创建者的id',
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
  `end_time`   timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '结束时间',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后实名认证的时间戳',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `valid` tinyint(4) NOT NULL DEFAULT '0' COMMENT '有效性 0删除，1有效',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_exam_id` (`exam_id`(10))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='考核录入';




DROP TABLE IF EXISTS `tbl_user`;
CREATE TABLE `tbl_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(64) NOT NULL DEFAULT '' COMMENT '用户id',
  `user_name` varchar(64) NOT NULL DEFAULT '' COMMENT '用户名称',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `valid` tinyint(4) NOT NULL DEFAULT '0' COMMENT '有效性 0删除，1有效',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_uid` (`uid`(10))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';



DROP TABLE IF EXISTS `tbl_department`;
CREATE TABLE `tbl_department` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `department_id` varchar(64) NOT NULL DEFAULT '' COMMENT '部门id',
  `department_name` varchar(64) NOT NULL DEFAULT '0' COMMENT '部门名称',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `valid` tinyint(4) NOT NULL DEFAULT '0' COMMENT '有效性 0删除，1有效',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_department_id` (`department_id`(10))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门表';


DROP TABLE IF EXISTS `tbl_department_exam`;
CREATE TABLE `tbl_department_exam` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `department_id` varchar(64) NOT NULL DEFAULT '0' COMMENT '部门id',
  `exam_id` varchar(64) NOT NULL DEFAULT '0' COMMENT '考核id',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `valid` tinyint(4) NOT NULL DEFAULT '0' COMMENT '有效性 0删除，1有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门-考核';



DROP TABLE IF EXISTS `tbl_shengtai_exam_record`;
CREATE TABLE `tbl_shengtai_exam_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exam_record_id` varchar(64) NOT NULL DEFAULT '' COMMENT '考核指标id',
  `exam_index_id` varchar(64) NOT NULL DEFAULT '' COMMENT '考核指标id',
  `exam_index_desc` varchar(255) NOT NULL DEFAULT '' COMMENT '考核指标描述',
  `exam_detail_id` varchar(64) NOT NULL DEFAULT '' COMMENT '考核要点id',
  `exam_detail_desc` varchar(255) NOT NULL DEFAULT '' COMMENT '考核要点描述',
  `exam_score` int(11) NOT NULL DEFAULT 0 COMMENT '分值',
  `department_id` varchar(64) NOT NULL DEFAULT '' COMMENT '部门id',
  `record_name` varchar(64) NOT NULL DEFAULT '' COMMENT '记录名称',
  `record_abstract` varchar(64) NOT NULL DEFAULT '0' COMMENT '记录摘要',
  `record_master_name` varchar(64) NOT NULL DEFAULT '' COMMENT '主要责任人',
  `record_group_name` varchar(1024) NOT NULL DEFAULT '' COMMENT '班子成员',
  `record_status` varchar(255) NOT NULL DEFAULT '' COMMENT '考核记录状态',
  `create_uid`  varchar(64) NOT NULL DEFAULT '' COMMENT '创建者的id',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后实名认证的时间戳',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `valid` tinyint(4) NOT NULL DEFAULT '0' COMMENT '有效性 0删除，1有效',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_exam_record_id` (`exam_record_id`(10))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='考核记录';


DROP TABLE IF EXISTS `tbl_shengtai_exam_record_addition`;
CREATE TABLE `tbl_shengtai_exam_record_addition` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `addition_id` varchar(64) NOT NULL DEFAULT '' COMMENT '附件id',
  `addition_name` varchar(2048) NOT NULL DEFAULT '' COMMENT '附件名称',
  `addition_location` varchar(2048) NOT NULL DEFAULT '' COMMENT '附件相对位置',
  `create_uid`  varchar(64) NOT NULL DEFAULT '' COMMENT '创建者的id',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后实名认证的时间戳',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `valid` tinyint(4) NOT NULL DEFAULT '0' COMMENT '有效性 0删除，1有效',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_addition_id` (`addition_id`(10))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='考核记录';




