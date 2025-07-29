CREATE TABLE `t_link_0`
(
    `id`              bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `domain`          varchar(128)                                         DEFAULT NULL COMMENT '域名',
    `short_uri`       varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '短链接',
    `full_short_url`  varchar(128)                                         DEFAULT NULL COMMENT '完整短链接',
    `origin_url`      varchar(1024)                                        DEFAULT NULL COMMENT '原始链接',
    `click_num`       int(11)                                              DEFAULT '0' COMMENT '点击量',
    `gid`             varchar(32)                                          DEFAULT 'default' COMMENT '分组标识',
    `enable_status`   tinyint(1)                                           DEFAULT '0' COMMENT '启用标识 0: 启用 1: 未启用',
    `created_type`    tinyint(1)                                           DEFAULT NULL COMMENT '创建类型 0: 接口创建 1: 控制台创建',
    `valid_date_type` tinyint(1)                                           DEFAULT NULL COMMENT '有效期类型 0: 永久有效 1: 自定义',
    `valid_date`      datetime                                             DEFAULT NULL COMMENT '有效期',
    `describe`        varchar(1024)                                        DEFAULT NULL COMMENT '描述',
    `create_time`     datetime                                             DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     datetime                                             DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `del_flag`        tinyint(1)                                           DEFAULT '0' COMMENT '删除标识 0: 未删除 1: 已删除',
    `website_tag`     varchar(64)                                          DEFAULT NULL COMMENT '网站标识',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_unique_full_short_url` (`full_short_url`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4;

-- t_link_1 ~ t_link_15
CREATE TABLE `t_link_1` LIKE `t_link_0`;
ALTER TABLE `t_link_1`
    CHANGE `short_uri` `short_uri` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '短链接';

CREATE TABLE `t_link_2` LIKE `t_link_0`;
ALTER TABLE `t_link_2`
    CHANGE `short_uri` `short_uri` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '短链接';

CREATE TABLE `t_link_3` LIKE `t_link_0`;
ALTER TABLE `t_link_3`
    CHANGE `short_uri` `short_uri` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '短链接';

CREATE TABLE `t_link_4` LIKE `t_link_0`;
ALTER TABLE `t_link_4`
    CHANGE `short_uri` `short_uri` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '短链接';

CREATE TABLE `t_link_5` LIKE `t_link_0`;
ALTER TABLE `t_link_5`
    CHANGE `short_uri` `short_uri` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '短链接';

CREATE TABLE `t_link_6` LIKE `t_link_0`;
ALTER TABLE `t_link_6`
    CHANGE `short_uri` `short_uri` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '短链接';

CREATE TABLE `t_link_7` LIKE `t_link_0`;
ALTER TABLE `t_link_7`
    CHANGE `short_uri` `short_uri` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '短链接';

CREATE TABLE `t_link_8` LIKE `t_link_0`;
ALTER TABLE `t_link_8`
    CHANGE `short_uri` `short_uri` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '短链接';

CREATE TABLE `t_link_9` LIKE `t_link_0`;
ALTER TABLE `t_link_9`
    CHANGE `short_uri` `short_uri` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '短链接';

CREATE TABLE `t_link_10` LIKE `t_link_0`;
ALTER TABLE `t_link_10`
    CHANGE `short_uri` `short_uri` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '短链接';

CREATE TABLE `t_link_11` LIKE `t_link_0`;
ALTER TABLE `t_link_11`
    CHANGE `short_uri` `short_uri` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '短链接';

CREATE TABLE `t_link_12` LIKE `t_link_0`;
ALTER TABLE `t_link_12`
    CHANGE `short_uri` `short_uri` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '短链接';

CREATE TABLE `t_link_13` LIKE `t_link_0`;
ALTER TABLE `t_link_13`
    CHANGE `short_uri` `short_uri` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '短链接';

CREATE TABLE `t_link_14` LIKE `t_link_0`;
ALTER TABLE `t_link_14`
    CHANGE `short_uri` `short_uri` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '短链接';

CREATE TABLE `t_link_15` LIKE `t_link_0`;
ALTER TABLE `t_link_15`
    CHANGE `short_uri` `short_uri` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT '短链接';



ALTER TABLE t_link_0
    ADD COLUMN
        ALTER TABLE t_link_1 ADD COLUMN website_tag varchar (64) DEFAULT NULL COMMENT '网站标识';
ALTER TABLE t_link_2
    ADD COLUMN website_tag varchar(64) DEFAULT NULL COMMENT '网站标识';
ALTER TABLE t_link_3
    ADD COLUMN website_tag varchar(64) DEFAULT NULL COMMENT '网站标识';
ALTER TABLE t_link_4
    ADD COLUMN website_tag varchar(64) DEFAULT NULL COMMENT '网站标识';
ALTER TABLE t_link_5
    ADD COLUMN website_tag varchar(64) DEFAULT NULL COMMENT '网站标识';
ALTER TABLE t_link_6
    ADD COLUMN website_tag varchar(64) DEFAULT NULL COMMENT '网站标识';
ALTER TABLE t_link_7
    ADD COLUMN website_tag varchar(64) DEFAULT NULL COMMENT '网站标识';
ALTER TABLE t_link_8
    ADD COLUMN website_tag varchar(64) DEFAULT NULL COMMENT '网站标识';
ALTER TABLE t_link_9
    ADD COLUMN website_tag varchar(64) DEFAULT NULL COMMENT '网站标识';
ALTER TABLE t_link_10
    ADD COLUMN website_tag varchar(64) DEFAULT NULL COMMENT '网站标识';
ALTER TABLE t_link_11
    ADD COLUMN website_tag varchar(64) DEFAULT NULL COMMENT '网站标识';
ALTER TABLE t_link_12
    ADD COLUMN website_tag varchar(64) DEFAULT NULL COMMENT '网站标识';
ALTER TABLE t_link_13
    ADD COLUMN website_tag varchar(64) DEFAULT NULL COMMENT '网站标识';
ALTER TABLE t_link_14
    ADD COLUMN website_tag varchar(64) DEFAULT NULL COMMENT '网站标识';
ALTER TABLE t_link_15
    ADD COLUMN website_tag varchar(64) DEFAULT NULL COMMENT '网站标识';

