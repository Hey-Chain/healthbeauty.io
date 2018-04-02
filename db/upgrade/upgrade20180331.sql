set character set utf8;

alter table oa_project add unit_id varchar(64) NULL comment '单位';
alter table oa_project add price decimal(10,2) NULL comment '单价';
