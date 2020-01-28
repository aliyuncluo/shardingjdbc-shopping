# shardingjdbc-shopping
shardingjdbc与springboot整合的一个案例

一、配置文件部分：
1.在mysql的主机上
修改/etc/my.cnf,添加以下内容：

#屏蔽系统库同步
binlog-ignore-db=mysql
binlog-ignore-db=information_schema
binlog-ignore-db=performance_schema
    
binlog-do-db=testdb 
binlog-do-db=user_db
binlog-do-db=store_db
binlog-do-db=product_db_1
binlog-do-db=product_db_2

2.在mysql的从机上
修改/etc/my.cnf,添加以下内容：

#设置需要同步的数据库
replicate_wild_do_table=user_db.%
replicate_wild_do_table=store_db.%
replicate_wild_do_table=product_db_1.%
replicate_wild_do_table=product_db_2.%
#屏蔽系统库同步
replicate_wild_ignore_table=mysql.%
replicate_wild_ignore_table=information_schema.%
replicate_wild_ignore_table=performance_schema.%

二.建表语句部分

create database store_db character set utf8;
create database product_db_1 character set utf8;
create database product_db_2 character set utf8;

store_db
#################################################
CREATE TABLE region (
   id  BIGINT(20) NOT NULL COMMENT 'id',
   region_code VARCHAR(50) COMMENT '地理区域编码',
   region_name VARCHAR(100) COMMENT '地理区域名称',
   LEVEL TINYINT(1) COMMENT '地理区域级别',
   parent_region_code VARCHAR(50) COMMENT '上级地理区域编码',
   PRIMARY KEY(id) USING BTREE
) ENGINE= INNODB CHARACTER SET = utf8 COLLATE= utf8_general_ci ROW_FORMAT= DYNAMIC;

INSERT INTO region VALUES (1,'110000','北京',0,NULL);
INSERT INTO region VALUES (2,'410000','河南省',0,NULL);
INSERT INTO region VALUES (3,'110100','北京市',1,'110000');
INSERT INTO region VALUES (4,'410100','郑州市',1,'410000');

CREATE TABLE store_info (
   id BIGINT(20) NOT NULL COMMENT 'id',
   store_name VARCHAR(100) COMMENT '店铺名称',
   reputation INT(11) COMMENT '信誉等级',
   region_code VARCHAR(50) COMMENT '店铺所在地',
   PRIMARY KEY (id) USING BTREE
) ENGINE = INNODB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT= DYNAMIC;

INSERT INTO store_info VALUES (1,'XX零食店',4,'110100');
INSERT INTO store_info VALUES (2,'XX饮品店',3,'410100');


product_db_1
##################################################
CREATE TABLE region (
   id  BIGINT(20) NOT NULL COMMENT 'id',
   region_code VARCHAR(50) COMMENT '地理区域编码',
   region_name VARCHAR(100) COMMENT '地理区域名称',
   LEVEL TINYINT(1) COMMENT '地理区域级别',
   parent_region_code VARCHAR(50) COMMENT '上级地理区域编码',
   PRIMARY KEY(id) USING BTREE
) ENGINE= INNODB CHARACTER SET = utf8 COLLATE= utf8_general_ci ROW_FORMAT= DYNAMIC;

INSERT INTO region VALUES (1,'110000','北京',0,NULL);
INSERT INTO region VALUES (2,'410000','河南省',0,NULL);
INSERT INTO region VALUES (3,'110100','北京市',1,'110000');
INSERT INTO region VALUES (4,'410100','郑州市',1,'410000');

CREATE TABLE product_info_1 (
    product_info_id BIGINT(20) NOT NULL COMMENT 'id',
    store_info_id BIGINT(20) COMMENT '所属店铺ID',
    product_name VARCHAR(100) COMMENT '商品名称',
    spec VARCHAR(50) COMMENT '规格',
    region_code VARCHAR(50) COMMENT '产地',
    price DECIMAL(10,0) COMMENT '商品价格',
    image_url VARCHAR(100) COMMENT '商品图片路径',
    PRIMARY KEY (product_info_id) USING BTREE,
    INDEX fk_reference_1 (store_info_id) USING BTREE
) ENGINE = INNODB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

CREATE TABLE product_info_2 (
    product_info_id BIGINT(20) NOT NULL COMMENT 'id',
    store_info_id BIGINT(20) COMMENT '所属店铺ID',
    product_name VARCHAR(100) COMMENT '商品名称',
    spec VARCHAR(50) COMMENT '规格',
    region_code VARCHAR(50) COMMENT '产地',
    price DECIMAL(10,0) COMMENT '商品价格',
    image_url VARCHAR(100) COMMENT '商品图片路径',
    PRIMARY KEY (product_info_id) USING BTREE,
    INDEX fk_reference_1 (store_info_id) USING BTREE
) ENGINE = INNODB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

CREATE TABLE product_descript_1 (
    id BIGINT(20) NOT NULL COMMENT 'id',
    product_info_id BIGINT(20) COMMENT '所属商品ID',
    descript LONGTEXT COMMENT '商品描述',
    store_info_id BIGINT(20) COMMENT '所属店铺ID',
    PRIMARY KEY (id) USING BTREE,
    INDEX fk_reference_2 (product_info_id) USING BTREE
) ENGINE = INNODB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

CREATE TABLE product_descript_2 (
    id BIGINT(20) NOT NULL COMMENT 'id',
    product_info_id BIGINT(20) COMMENT '所属商品ID',
    descript LONGTEXT COMMENT '商品描述',
    store_info_id BIGINT(20) COMMENT '所属店铺ID',
    PRIMARY KEY (id) USING BTREE,
    INDEX fk_reference_2 (product_info_id) USING BTREE
) ENGINE = INNODB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;


product_db_2
##############################################################
CREATE TABLE region (
   id  BIGINT(20) NOT NULL COMMENT 'id',
   region_code VARCHAR(50) COMMENT '地理区域编码',
   region_name VARCHAR(100) COMMENT '地理区域名称',
   LEVEL TINYINT(1) COMMENT '地理区域级别',
   parent_region_code VARCHAR(50) COMMENT '上级地理区域编码',
   PRIMARY KEY(id) USING BTREE
) ENGINE= INNODB CHARACTER SET = utf8 COLLATE= utf8_general_ci ROW_FORMAT= DYNAMIC;

INSERT INTO region VALUES (1,'110000','北京',0,NULL);
INSERT INTO region VALUES (2,'410000','河南省',0,NULL);
INSERT INTO region VALUES (3,'110100','北京市',1,'110000');
INSERT INTO region VALUES (4,'410100','郑州市',1,'410000');

CREATE TABLE product_info_1 (
    product_info_id BIGINT(20) NOT NULL COMMENT 'id',
    store_info_id BIGINT(20) COMMENT '所属店铺ID',
    product_name VARCHAR(100) COMMENT '商品名称',
    spec VARCHAR(50) COMMENT '规格',
    region_code VARCHAR(50) COMMENT '产地',
    price DECIMAL(10,0) COMMENT '商品价格',
    image_url VARCHAR(100) COMMENT '商品图片路径',
    PRIMARY KEY (product_info_id) USING BTREE,
    INDEX fk_reference_1 (store_info_id) USING BTREE
) ENGINE = INNODB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

CREATE TABLE product_info_2 (
    product_info_id BIGINT(20) NOT NULL COMMENT 'id',
    store_info_id BIGINT(20) COMMENT '所属店铺ID',
    product_name VARCHAR(100) COMMENT '商品名称',
    spec VARCHAR(50) COMMENT '规格',
    region_code VARCHAR(50) COMMENT '产地',
    price DECIMAL(10,0) COMMENT '商品价格',
    image_url VARCHAR(100) COMMENT '商品图片路径',
    PRIMARY KEY (product_info_id) USING BTREE,
    INDEX fk_reference_1 (store_info_id) USING BTREE
) ENGINE = INNODB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

CREATE TABLE product_descript_1 (
    id BIGINT(20) NOT NULL COMMENT 'id',
    product_info_id BIGINT(20) COMMENT '所属商品ID',
    descript LONGTEXT COMMENT '商品描述',
    store_info_id BIGINT(20) COMMENT '所属店铺ID',
    PRIMARY KEY (id) USING BTREE,
    INDEX fk_reference_2 (product_info_id) USING BTREE
) ENGINE = INNODB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

CREATE TABLE product_descript_2 (
    id BIGINT(20) NOT NULL COMMENT 'id',
    product_info_id BIGINT(20) COMMENT '所属商品ID',
    descript LONGTEXT COMMENT '商品描述',
    store_info_id BIGINT(20) COMMENT '所属店铺ID',
    PRIMARY KEY (id) USING BTREE,
    INDEX fk_reference_2 (product_info_id) USING BTREE
) ENGINE = INNODB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

三、表结构图
![product_db_1](https://github.com/aliyuncluo/shardingjdbc-shopping/blob/master/images/product_db_1.png)
![product_db_2](https://github.com/aliyuncluo/shardingjdbc-shopping/blob/master/images/product_db_2.png)
![store_db](https://github.com/aliyuncluo/shardingjdbc-shopping/blob/master/images/store_db.png)

