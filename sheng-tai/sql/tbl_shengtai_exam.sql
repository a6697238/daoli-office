DROP TABLE IF EXISTS `tbl_shengtai_exam`;
CREATE TABLE `tbl_shengtai_exam` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
`exam_id` varchar(64) NOT NULL DEFAULT '' COMMENT '考试唯一标识',
`parent_exam_id` int(11)  NOT NULL DEFAULT '0' COMMENT '父级菜单id',
`exam_type` varchar(255) NOT NULL DEFAULT '' COMMENT '模板类型，考核分类，考核指标，考核要点',
`exam_name` varchar(255) NOT NULL DEFAULT '' COMMENT '考核名称',
`exam_desc` varchar(2048) NOT NULL DEFAULT '' COMMENT '考核描述',
`exam_score` int(11) NOT NULL DEFAULT 0 COMMENT '分值',
`exam_status` varchar(255) NOT NULL DEFAULT '' COMMENT '考核状态',
`create_uid`  varchar(64) NOT NULL DEFAULT '' COMMENT '创建者的id',
`start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
`end_time`   timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '结束时间',
`modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录修改时间',
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
`valid` tinyint(4) NOT NULL DEFAULT '0' COMMENT '有效性 0删除，1有效',
PRIMARY KEY (`id`),
UNIQUE KEY `idx_exam_id` (`exam_id`(10))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='考核录入';