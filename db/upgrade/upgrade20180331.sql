set character set utf8;

alter table oa_project add unit_id varchar(64) NULL comment '单位';
alter table oa_project add price decimal(10,2) NULL comment '单价';

-- ----------------------------
-- Table structure for f_bill
-- ----------------------------
DROP TABLE IF EXISTS `f_bill`;
CREATE TABLE `f_bill` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `customer_id` varchar(64) NOT NULL COMMENT '客户',
  `bill_time` datetime DEFAULT NULL COMMENT '开单时间',
  `attendance_id` varchar(64) DEFAULT NULL COMMENT '就诊登记Id',
  `is_paid` char(1) NOT NULL COMMENT '是否付款（0：未支付，1：已支付）',
  
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`),
  KEY `f_bill_customer` (`customer_id`),
  KEY `f_bill_time` (`bill_time`),
  KEY `f_bill_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收费单';

-- ----------------------------
-- Table structure for f_billItem
-- ----------------------------
DROP TABLE IF EXISTS `f_billItem`;
CREATE TABLE `f_billItem` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `bill_id` varchar(64) NOT NULL COMMENT '单据',
  `project_id` varchar(64) NOT NULL COMMENT '项目',
  `doctor_id` varchar(64) NOT NULL COMMENT '医生',
  `quantity` int(11) NOT NULL COMMENT '数量',
  `originalprice` decimal(10,2) NOT NULL COMMENT '原价',
  `dealprice` decimal(10,2) NOT NULL COMMENT '成交价',
  
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`),
  KEY `f_billItem_project_id` (`project_id`),
  KEY `f_billItem_doctor_id` (`doctor_id`),
  KEY `f_billItem_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收费明细';
