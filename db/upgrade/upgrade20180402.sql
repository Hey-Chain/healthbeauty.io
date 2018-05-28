set character set utf8;

-- ----------------------------
-- Table structure for f_payment
-- ----------------------------
DROP TABLE IF EXISTS `f_payment`;
CREATE TABLE `f_payment` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `bill_id` varchar(64) NOT NULL COMMENT '收费单',
  `payment_number` varchar(64) NULL COMMENT '付款单编码',
  `payment_time` datetime DEFAULT NULL COMMENT '付款时间',
  `payment_type` varchar(64) DEFAULT NULL COMMENT '支付方式',
  `payable` decimal(10, 2) DEFAULT NULL COMMENT '应付金额',
  `amount` decimal(10, 2) DEFAULT NULL COMMENT '支付金额',
  
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`),
  KEY `f_payment_bill_id` (`bill_id`),
  KEY `f_payment_time` (`payment_time`),
  KEY `f_payment_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='付款单';