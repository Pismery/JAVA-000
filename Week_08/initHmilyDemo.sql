
drop database if not exists hmily;
create database hmily default character set utf8mb4 collate utf8mb4_unicode_ci;

USE hmily

DROP TABLE IF EXISTS local_try_log;
DROP TABLE IF EXISTS local_confirm_log;
DROP TABLE IF EXISTS local_cancel_log;

CREATE TABLE `local_try_log` (
    `tx_no` varchar(64) NOT NULL COMMENT '事务id',
    `create_time` datetime DEFAULT NULL,
    PRIMARY KEY (`tx_no`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `local_confirm_log` (
    `tx_no` varchar(64) NOT NULL COMMENT '事务id', `create_time` datetime DEFAULT NULL
)  ENGINE=InnoDB DEFAULT CHARSET=utf8 

CREATE TABLE `local_cancel_log` (
    `tx_no` varchar(64) NOT NULL COMMENT '事务id', `create_time` datetime DEFAULT NULL, PRIMARY KEY (`tx_no`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8