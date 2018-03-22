set character set utf8;
-- ----------------------------
-- Table structure for oa_customer
-- ----------------------------
DROP TABLE IF EXISTS `crm_customer`;
CREATE TABLE `crm_customer` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `customer_name` varchar(32) NOT NULL COMMENT '姓名',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号码',
  `source_type` char(1) DEFAULT NULL COMMENT '来源',
  `customer_group` char(1) NOT NULL COMMENT '客户组',
  `sex` char(1) DEFAULT NULL COMMENT '性别',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `address` varchar(255) DEFAULT NULL COMMENT '联系地址',
  
  `wechat` varchar(32) DEFAULT NULL COMMENT '微信号',
  `birthday` datetime DEFAULT NULL COMMENT '出生日期',
  `membercard` varchar(32) DEFAULT NULL COMMENT '会员卡号',
  `introducer` varchar(32) DEFAULT NULL COMMENT '介绍人',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `qq` varchar(16) DEFAULT NULL COMMENT 'qq号码',
  `job` varchar(16) DEFAULT NULL COMMENT '职业',
  `idcard` varchar(16) DEFAULT NULL COMMENT '证件号码',
  `customer_no` varchar(16) DEFAULT NULL COMMENT '档案号码',
  
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`),
  KEY `crm_customer_customer_name` (`customer_name`),
  KEY `crm_customer_membercard` (`membercard`),
  KEY `crm_customer_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户档案表';