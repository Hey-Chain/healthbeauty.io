set character set utf8;

alter table oa_reservation add reservation_number varchar(64) default null COMMENT '预约编码';

-- ----------------------------
-- Table structure for oa_customer
-- ----------------------------
DROP TABLE IF EXISTS `oa_attendance`;
CREATE TABLE `oa_attendance` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `customer_id` varchar(64) NOT NULL COMMENT '客户',
  `attendance_time` datetime DEFAULT NULL COMMENT '就诊时间',  
  `attendance_type` char(1) NOT NULL COMMENT '就诊类型',
  `reservation_id` varchar(64) DEFAULT NULL COMMENT '预约编号',
  
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`),
  KEY `oa_attendance_customer` (`customer_id`),
  KEY `oa_attendance_time` (`attendance_time`),
  KEY `oa_attendance_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='预约登记表';