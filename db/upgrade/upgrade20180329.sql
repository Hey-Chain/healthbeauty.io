alter table oa_attendance add counselor_id varchar(64) default null COMMENT '咨询师';
alter table oa_attendance add status varchar(64) default 0 COMMENT '状态';