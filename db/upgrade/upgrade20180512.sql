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
