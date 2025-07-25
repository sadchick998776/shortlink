-- 创建 t_user_0 到 t_user_15 共16个表（如果需要15个，去掉最后一个）
CREATE TABLE `t_user_0` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                            `username` varchar(256) DEFAULT NULL COMMENT '用户名',
                            `password` varchar(512) DEFAULT NULL COMMENT '密码',
                            `real_name` varchar(256) DEFAULT NULL COMMENT '真实姓名',
                            `phone` varchar(128) DEFAULT NULL COMMENT '手机号',
                            `mail` varchar(512) DEFAULT NULL COMMENT '邮箱',
                            `deletion_time` bigint(20) DEFAULT NULL COMMENT '注销时间戳',
                            `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                            `del_flag` tinyint(1) DEFAULT 0 COMMENT '删除标识 0：未删除 1：已删除',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_user_1` LIKE `t_user_0`;
CREATE TABLE `t_user_2` LIKE `t_user_0`;
CREATE TABLE `t_user_3` LIKE `t_user_0`;
CREATE TABLE `t_user_4` LIKE `t_user_0`;
CREATE TABLE `t_user_5` LIKE `t_user_0`;
CREATE TABLE `t_user_6` LIKE `t_user_0`;
CREATE TABLE `t_user_7` LIKE `t_user_0`;
CREATE TABLE `t_user_8` LIKE `t_user_0`;
CREATE TABLE `t_user_9` LIKE `t_user_0`;
CREATE TABLE `t_user_10` LIKE `t_user_0`;
CREATE TABLE `t_user_11` LIKE `t_user_0`;
CREATE TABLE `t_user_12` LIKE `t_user_0`;
CREATE TABLE `t_user_13` LIKE `t_user_0`;
CREATE TABLE `t_user_14` LIKE `t_user_0`;
CREATE TABLE `t_user_15` LIKE `t_user_0`;  -- 如果只需要15个表，删除这一行