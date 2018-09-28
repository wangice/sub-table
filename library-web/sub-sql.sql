
DROP  TABLE IF EXISTS  tb_base_user_info;
CREATE TABLE `tb_base_user_info` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `account` varchar(50) NOT NULL COMMENT '账号',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `user_name` varchar(50) NOT NULL COMMENT '用户名',
  `identity` varchar(30) DEFAULT NULL COMMENT '身份证号',
  `real_name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `phone_number` varchar(50) DEFAULT NULL COMMENT '电话号码',
  `address` varchar(50) DEFAULT NULL COMMENT '地址',
  `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '状态(0-正常；1-已冻结；)',
  `freezing_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '冻结时间',
  `last_login_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次登录的时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息';

DROP  TABLE IF EXISTS  tb_extra_user_info;
CREATE TABLE `tb_extra_user_info` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `address` varchar(50) DEFAULT NULL COMMENT '地址',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `introduction` varchar(300) DEFAULT NULL COMMENT '简介',
  `age` smallint(6) DEFAULT '0' COMMENT '年龄',
  `birth_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '出生日期',
  `avatar` varchar(200) DEFAULT '' COMMENT '头像路径',
  `sex` tinyint(4) DEFAULT NULL COMMENT '性别',
  `career` varchar(100) DEFAULT NULL COMMENT '职业',
  `income` double(20,3) DEFAULT NULL COMMENT '收入',
  `constellation` varchar(20) DEFAULT NULL COMMENT '星座',
  `height` float(8,3) DEFAULT NULL COMMENT '身高(cm)',
  `body_weight` float(8,3) DEFAULT NULL COMMENT '体重(kg)',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户扩展信息';