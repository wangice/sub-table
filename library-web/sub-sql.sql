
DROP  TABLE IF EXISTS  tb_base_user_info;
CREATE TABLE tb_base_user_info(
`id` INT(10) NOT NULL AUTO_INCREMENT,
user_id VARCHAR(50) NOT NULL COMMENT '用户ID',
account VARCHAR(50) NOT NULL COMMENT '账号',
`password` VARCHAR(100) NOT NULL COMMENT '密码',
user_name VARCHAR(50) NOT NULL COMMENT '用户名',
identity VARCHAR(30) COMMENT '身份证号',
real_name VARCHAR(50) COMMENT '真实姓名',
email VARCHAR(50) COMMENT '邮箱',
phone_number VARCHAR(50) COMMENT '电话号码',
address VARCHAR(50) COMMENT '地址',
`status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '状态(0-正常；1-已冻结；)',
freezing_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '冻结时间',
last_login_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次登录的时间',
PRIMARY KEY (`id`)
)ENGINE=INNODB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='用户信息';

DROP  TABLE IF EXISTS  tb_extra_user_info;
create table tb_extra_user_info(
user_id VARCHAR(50) NOT NULL COMMENT '用户ID',
address VARCHAR(50) COMMENT '地址',
create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
introduction VARCHAR(300) COMMENT '简介',
age SMALLINT DEFAULT 0 COMMENT '年龄',
birth_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '出生日期',
avatar VARCHAR(200) DEFAULT '' COMMENT '头像路径',
sex TINYINT COMMENT '性别',
career VARCHAR(100) COMMENT '职业',
income DOUBLE(20,3) COMMENT '收入',
constellation VARCHAR(20) COMMENT '星座',
height FLOAT(8,3) COMMENT '身高(cm)',
body_weight FLOAT(8,3) COMMENT '体重(kg)'
)ENGINE=INNODB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='用户扩展信息';