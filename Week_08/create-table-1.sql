-- MySQL dump 10.13  Distrib 5.7.25, for Win64 (x86_64)
--
-- Host: localhost    Database: db1
-- ------------------------------------------------------
-- Server version	5.7.25

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_order`
--

USE demo_ds_1;

DROP TABLE IF EXISTS `t_order_0`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_order_0` (
  `id` bigint(20) NOT NULL COMMENT '订单ID',
  `userId` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `productId` bigint(20) DEFAULT NULL COMMENT '商品ID',
  `price` decimal(10,0) DEFAULT NULL COMMENT '订单价格',
  `status` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '订单状态',
  `payType` int(1) DEFAULT NULL,
  `country` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '国家',
  `province` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '省市',
  `city` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '城市',
  `street` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '街道',
  `detail` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '详细地址',
  `recipient` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '收件人',
  `recipientPhone` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '收件人电话',
  `recordStatus` varchar(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '记录状态',
  `recordChangedTime` bigint(20) DEFAULT NULL COMMENT '记录修改时间',
  `recordChangedBy` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '记录修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `t_order_1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_order_1` (
  `id` bigint(20) NOT NULL COMMENT '订单ID',
  `userId` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `productId` bigint(20) DEFAULT NULL COMMENT '商品ID',
  `price` decimal(10,0) DEFAULT NULL COMMENT '订单价格',
  `status` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '订单状态',
  `payType` int(1) DEFAULT NULL,
  `country` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '国家',
  `province` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '省市',
  `city` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '城市',
  `street` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '街道',
  `detail` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '详细地址',
  `recipient` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '收件人',
  `recipientPhone` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '收件人电话',
  `recordStatus` varchar(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '记录状态',
  `recordChangedTime` bigint(20) DEFAULT NULL COMMENT '记录修改时间',
  `recordChangedBy` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '记录修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;
