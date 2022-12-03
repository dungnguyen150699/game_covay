-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: btl_ltm
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `detailmatch`
--

DROP TABLE IF EXISTS `detailmatch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detailmatch` (
  `id` int NOT NULL,
  `matchresult` varchar(45) NOT NULL,
  `idmatch` int NOT NULL,
  `iduser` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fkuser` (`iduser`),
  KEY `fkmatch` (`idmatch`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detailmatch`
--

LOCK TABLES `detailmatch` WRITE;
/*!40000 ALTER TABLE `detailmatch` DISABLE KEYS */;
INSERT INTO `detailmatch` VALUES (12,'thắng',11,2),(13,'thua',11,1),(14,'thua',12,1),(15,'thắng',12,2),(16,'thắng',13,2),(17,'thua',13,1),(18,'thắng',14,2),(19,'thua',14,1),(20,'thua',15,2),(21,'thắng',15,1),(22,'hòa',16,1),(23,'hòa',16,2),(24,'thua',17,2),(25,'thắng',17,1),(26,'thua',18,2),(27,'thắng',18,1),(28,'hòa',19,2),(29,'hòa',19,1),(30,'hòa',20,2),(31,'hòa',20,1),(32,'thua',21,2),(33,'thắng',21,1),(34,'hòa',22,2),(35,'hòa',22,1),(36,'thua',23,2),(37,'thắng',23,1),(38,'thắng',24,2),(39,'thua',24,1),(40,'thắng',25,2),(41,'thua',25,1),(42,'thua',26,2),(43,'thắng',26,1),(44,'thắng',27,2),(45,'thua',27,1),(46,'thua',28,1),(47,'thắng',28,2),(48,'thua',29,1),(49,'thắng',29,2),(50,'thua',30,1),(51,'thắng',30,2),(52,'thua',31,2),(53,'thắng',31,1),(54,'thắng',32,3),(55,'thua',32,1),(56,'thua',33,2),(57,'thắng',33,1),(58,'thua',34,3),(59,'thắng',34,2),(60,'thua',35,2),(61,'thắng',35,1),(62,'thua',36,1),(63,'thắng',36,2),(64,'thua',37,1),(65,'thắng',37,2),(66,'thua',38,1),(67,'thắng',38,2),(68,'thua',39,1),(69,'thắng',39,2),(70,'thua',40,2),(71,'thắng',40,1),(72,'thua',41,1),(73,'thắng',41,2),(74,'thua',42,1),(75,'thắng',42,2),(76,'thua',43,2),(77,'thắng',43,1),(78,'thua',44,2),(79,'thắng',44,1),(80,'thua',45,1),(81,'thắng',45,2),(82,'thua',46,2),(83,'thắng',46,1),(84,'thua',47,2),(85,'thắng',47,1),(86,'thua',48,1),(87,'thắng',48,2),(88,'thua',49,1),(89,'thắng',49,2),(90,'thua',50,1),(91,'thắng',50,2),(92,'thua',51,2),(93,'thắng',51,3);
/*!40000 ALTER TABLE `detailmatch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `match`
--

DROP TABLE IF EXISTS `match`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `match` (
  `id` int NOT NULL,
  `dateMatch` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `match`
--

LOCK TABLES `match` WRITE;
/*!40000 ALTER TABLE `match` DISABLE KEYS */;
INSERT INTO `match` VALUES (11,'2020-11-20'),(12,'2020-11-20'),(13,'2020-11-22'),(14,'2020-11-23'),(15,'2020-11-23'),(16,'2020-11-23'),(17,'2020-11-23'),(18,'2020-11-23'),(19,'2020-11-23'),(20,'2020-11-23'),(21,'2020-11-23'),(22,'2020-11-23'),(23,'2020-11-23'),(24,'2020-11-23'),(25,'2020-11-23'),(26,'2020-11-23'),(27,'2020-11-23'),(28,'2020-11-23'),(29,'2020-11-23'),(30,'2020-11-23'),(31,'2020-11-24'),(32,'2020-11-25'),(33,'2020-11-26'),(34,'2020-11-26'),(35,'2020-11-26'),(36,'2020-11-26'),(37,'2020-11-26'),(38,'2020-11-26'),(39,'2020-11-26'),(40,'2020-11-26'),(41,'2020-11-27'),(42,'2020-11-27'),(43,'2020-11-27'),(44,'2020-11-29'),(45,'2020-11-29'),(46,'2020-11-29'),(47,'2020-11-29'),(48,'2020-11-29'),(49,'2020-11-29'),(50,'2020-11-29'),(51,'2020-11-29');
/*!40000 ALTER TABLE `match` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL,
  `userName` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `totalMatch` int NOT NULL,
  `totalWin` int NOT NULL,
  `totalLost` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'a','1',39,14,21),(2,'b','1',40,21,15),(3,'dung','1',3,2,1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-03 20:27:33
