set character set utf8;
-- ----------------------------
-- Table structure for oa_customer
-- ----------------------------
DROP TABLE IF EXISTS `oa_reservation`;
CREATE TABLE `oa_reservation` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `customer_id` varchar(64) NOT NULL COMMENT '客户',
  `doctor_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '医生',
  `reservation_time` datetime DEFAULT NULL COMMENT '预约时间',
  `project_id` varchar(64) NOT NULL COMMENT '预约项目',
  
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`),
  KEY `oa_reservation_customer` (`customer_id`),
  KEY `oa_reservation_doctor` (`doctor_id`),
  KEY `oa_reservation_time` (`reservation_time`),
  KEY `oa_reservation_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='预约登记表';