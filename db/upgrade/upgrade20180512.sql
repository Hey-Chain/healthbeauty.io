set character set utf8;

-- ----------------------------
-- Table structure for crm_member_card
-- ----------------------------
DROP TABLE IF EXISTS `crm_member_card`;
CREATE TABLE `crm_member_card` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `card_number` varchar(32) NOT NULL COMMENT '卡号',
  `use_flag` char(1) NOT NULL DEFAULT '0' COMMENT '使用标记（0：空卡；1：占用）',
  
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`),
  KEY `crm_member_card_number` (`card_number`),
  KEY `crm_member_card_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员卡';

-- ----------------------------
-- Table structure for crm_balance_increase
-- ----------------------------
DROP TABLE IF EXISTS `crm_balance_increase`;
CREATE TABLE `crm_balance_increase` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `member_card_id` varchar(64) NOT NULL COMMENT '会员卡编号',
  `change_amount` decimal(10, 2) NOT NULL COMMENT '增加金额',
  
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`),
  KEY `crm_balance_increase_card_id` (`member_card_id`),
  KEY `crm_balance_increase_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='余额增加表';


-- ----------------------------
-- Table structure for crm_balance_decrease
-- ----------------------------
DROP TABLE IF EXISTS `crm_balance_decrease`;
CREATE TABLE `crm_balance_decrease` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `member_card_id` varchar(64) NOT NULL COMMENT '会员卡编号',
  `change_amount` decimal(10, 2) NOT NULL COMMENT '减少金额',
  
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`),
  KEY `crm_balance_decrease_card_id` (`member_card_id`),
  KEY `crm_balance_decrease_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='余额减少表';
GO

alter table crm_customer drop column membercard;
alter table crm_customer add member_card_Id varchar(64) DEFAULT NULL COMMENT '会员卡编号';
alter table crm_customer add member_card_Number varchar(32) DEFAULT NULL COMMENT '会员卡号';


-- ----------------------------
-- Table structure for crm_balance_in_out
-- ----------------------------
DROP TABLE IF EXISTS `crm_balance_in_out`;
CREATE TABLE `crm_balance_in_out` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `member_card_id` varchar(64) NOT NULL COMMENT '会员卡编号',
  `operate_type` int NOT NULL COMMENT '操作类型(0:减少,1:增加)',
  `change_amount` decimal(10, 2) NOT NULL COMMENT '改变金额',
  
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`),
  KEY `crm_balance_in_out_card_id` (`member_card_id`),
  KEY `crm_balance_in_out_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='余额减少表';
GO
