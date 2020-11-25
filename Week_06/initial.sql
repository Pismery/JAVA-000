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
-- Table structure for table `tblorder`
--

DROP TABLE IF EXISTS `tblorder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblorder` (
  `orderId` bigint(20) NOT NULL COMMENT '订单ID',
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
  PRIMARY KEY (`orderId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblorderitem`
--

DROP TABLE IF EXISTS `tblorderitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblorderitem` (
  `id` bigint(20) NOT NULL,
  `orderId` bigint(20) DEFAULT NULL COMMENT '订单Id',
  `productId` bigint(20) DEFAULT NULL COMMENT '商品Id',
  `productPrice` decimal(10,2) DEFAULT NULL COMMENT '商品单价',
  `productQuantity` int(11) DEFAULT NULL COMMENT '商品数量',
  `integrationAmount` decimal(10,2) DEFAULT NULL COMMENT '积分优惠金额',
  `realAmount` decimal(10,2) DEFAULT NULL COMMENT '最终金额',
  `recordStatus` varchar(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '记录状态',
  `recordChangedTime` bigint(20) DEFAULT NULL COMMENT '记录修改时间',
  `recordChangedBy` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '记录修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tblproduct`
--

DROP TABLE IF EXISTS `tblproduct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblproduct` (
  `productId` bigint(20) NOT NULL,
  `category` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL,
  `name` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL,
  `price` decimal(10,0) DEFAULT NULL,
  `recordStatus` varchar(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '记录状态',
  `recordChangedTime` bigint(20) DEFAULT NULL COMMENT '记录修改时间',
  `recordChangedBy` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '记录修改人',
  PRIMARY KEY (`productId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbluser`
--

DROP TABLE IF EXISTS `tbluser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbluser` (
  `userId` bigint(20) NOT NULL,
  `name` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL,
  `gender` varchar(1) COLLATE utf8mb4_bin DEFAULT NULL,
  `identityNo` varchar(18) COLLATE utf8mb4_bin DEFAULT NULL,
  `recordStatus` varchar(1) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '记录状态',
  `recordChangedTime` bigint(20) DEFAULT NULL COMMENT '记录修改时间',
  `recordChangedBy` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '记录修改人',
  PRIMARY KEY (`userId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-11-25 22:29:16
