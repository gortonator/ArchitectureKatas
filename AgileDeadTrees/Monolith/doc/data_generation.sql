CREATE SCHEMA IF NOT EXISTS book_company;
USE book_company;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `accepted_chapters`
--

DROP TABLE IF EXISTS `accepted_chapters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accepted_chapters` (
  `accepted_chapter_id` int NOT NULL AUTO_INCREMENT,
  `chapter_index` int DEFAULT NULL,
  `content` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `author_id` int NOT NULL,
  `draft_book_id` int NOT NULL,
  PRIMARY KEY (`accepted_chapter_id`),
  UNIQUE KEY `UK29yuxl4vu82f76gmb95ln3i83` (`draft_book_id`,`chapter_index`),
  KEY `FK5kbiyp3jtddn9b0j0icofwvpf` (`author_id`),
  CONSTRAINT `FK5kbiyp3jtddn9b0j0icofwvpf` FOREIGN KEY (`author_id`) REFERENCES `authors` (`author_id`),
  CONSTRAINT `FKhquwy1ytf780kl975ymmgox19` FOREIGN KEY (`draft_book_id`) REFERENCES `draft_books` (`draft_book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accepted_chapters`
--

LOCK TABLES `accepted_chapters` WRITE;
/*!40000 ALTER TABLE `accepted_chapters` DISABLE KEYS */;
INSERT INTO `accepted_chapters` VALUES (1,1,'content','title',1,1),(2,2,'content','title',1,1);
/*!40000 ALTER TABLE `accepted_chapters` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `addresses`
--

DROP TABLE IF EXISTS `addresses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `addresses` (
  `address_id` int NOT NULL AUTO_INCREMENT,
  `address_line_1` varchar(64) NOT NULL,
  `address_line_2` varchar(64) DEFAULT NULL,
  `city` varchar(45) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `phone_number` varchar(15) NOT NULL,
  `postal_code` varchar(10) NOT NULL,
  `state` varchar(45) NOT NULL,
  `reader_id` int DEFAULT NULL,
  PRIMARY KEY (`address_id`),
  KEY `FKbjvp8krk0cg0eaeu0qu628n0p` (`reader_id`),
  CONSTRAINT `FKbjvp8krk0cg0eaeu0qu628n0p` FOREIGN KEY (`reader_id`) REFERENCES `readers` (`reader_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `addresses`
--

LOCK TABLES `addresses` WRITE;
/*!40000 ALTER TABLE `addresses` DISABLE KEYS */;
INSERT INTO `addresses` VALUES (1,'addressLine1','addressLine2','Seattle','reader1','reader1','phone','11111','WA',1),(2,'addressLine1','addressLine2','Seattle','reader2','reader2','phone','22222','WA',2);
/*!40000 ALTER TABLE `addresses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authors`
--

DROP TABLE IF EXISTS `authors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authors` (
  `author_id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`author_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authors`
--

LOCK TABLES `authors` WRITE;
/*!40000 ALTER TABLE `authors` DISABLE KEYS */;
INSERT INTO `authors` VALUES (1,'author1','author1'),(2,'author2','author2'),(3,'author3','author3');
/*!40000 ALTER TABLE `authors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authors_draft_books`
--

DROP TABLE IF EXISTS `authors_draft_books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authors_draft_books` (
  `author_id` int NOT NULL,
  `draft_book_id` int NOT NULL,
  PRIMARY KEY (`author_id`,`draft_book_id`),
  KEY `FKnhj8tvhejlqr6g7ul4ry6a4ch` (`draft_book_id`),
  CONSTRAINT `FK2bn3jof6siivvohmqlw22rk3x` FOREIGN KEY (`author_id`) REFERENCES `authors` (`author_id`),
  CONSTRAINT `FKnhj8tvhejlqr6g7ul4ry6a4ch` FOREIGN KEY (`draft_book_id`) REFERENCES `draft_books` (`draft_book_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authors_draft_books`
--

LOCK TABLES `authors_draft_books` WRITE;
/*!40000 ALTER TABLE `authors_draft_books` DISABLE KEYS */;
INSERT INTO `authors_draft_books` VALUES (1,1),(2,1),(3,2);
/*!40000 ALTER TABLE `authors_draft_books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authors_published_books`
--

DROP TABLE IF EXISTS `authors_published_books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authors_published_books` (
  `author_id` int NOT NULL,
  `published_book_id` int NOT NULL,
  PRIMARY KEY (`author_id`,`published_book_id`),
  KEY `FK7abiiiqq2mbctv5qbocod5iib` (`published_book_id`),
  CONSTRAINT `FK7abiiiqq2mbctv5qbocod5iib` FOREIGN KEY (`published_book_id`) REFERENCES `published_books` (`published_book_id`),
  CONSTRAINT `FKkfv7hqdl6ubvvkcb19a0bldj8` FOREIGN KEY (`author_id`) REFERENCES `authors` (`author_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authors_published_books`
--

LOCK TABLES `authors_published_books` WRITE;
/*!40000 ALTER TABLE `authors_published_books` DISABLE KEYS */;
INSERT INTO `authors_published_books` VALUES (1,1),(2,2),(3,3);
/*!40000 ALTER TABLE `authors_published_books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book_variants`
--

DROP TABLE IF EXISTS `book_variants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book_variants` (
  `book_variant_id` int NOT NULL AUTO_INCREMENT,
  `format` varchar(255) DEFAULT NULL,
  `isbn` varchar(13) DEFAULT NULL,
  `price` decimal(19,2) DEFAULT NULL,
  `publish_date` date DEFAULT NULL,
  `published_book_id` int DEFAULT NULL,
  PRIMARY KEY (`book_variant_id`),
  KEY `FKlfnlt2gt79b2ngyerv21usogr` (`published_book_id`),
  CONSTRAINT `FKlfnlt2gt79b2ngyerv21usogr` FOREIGN KEY (`published_book_id`) REFERENCES `published_books` (`published_book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_variants`
--

LOCK TABLES `book_variants` WRITE;
/*!40000 ALTER TABLE `book_variants` DISABLE KEYS */;
INSERT INTO `book_variants` VALUES (1,'PAPERBACK','1111111111111',15.99,'1990-03-16',1),(2,'EBOOK','2222222222222',5.99,'2007-05-16',1),(3,'EBOOK','3333333333333',15.00,'2013-07-03',2),(4,'HARDCOVER','4444444444444',7.00,'2022-12-05',3);
/*!40000 ALTER TABLE `book_variants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_items`
--

DROP TABLE IF EXISTS `cart_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_items` (
  `cart_item_id` int NOT NULL AUTO_INCREMENT,
  `quantity` int NOT NULL,
  `book_variant_id` int DEFAULT NULL,
  `reader_id` int NOT NULL,
  PRIMARY KEY (`cart_item_id`),
  KEY `FKrt9mbu72n84aifu6krni11phh` (`book_variant_id`),
  KEY `FKisuaogmj6ds81jdlwapoo2xjj` (`reader_id`),
  CONSTRAINT `FKisuaogmj6ds81jdlwapoo2xjj` FOREIGN KEY (`reader_id`) REFERENCES `readers` (`reader_id`),
  CONSTRAINT `FKrt9mbu72n84aifu6krni11phh` FOREIGN KEY (`book_variant_id`) REFERENCES `book_variants` (`book_variant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_items`
--

LOCK TABLES `cart_items` WRITE;
/*!40000 ALTER TABLE `cart_items` DISABLE KEYS */;
INSERT INTO `cart_items` VALUES (1,2,1,1),(2,1,3,1),(3,1,3,2);
/*!40000 ALTER TABLE `cart_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `draft_books`
--

DROP TABLE IF EXISTS `draft_books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `draft_books` (
  `draft_book_id` int NOT NULL AUTO_INCREMENT,
  `genre` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`draft_book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `draft_books`
--

LOCK TABLES `draft_books` WRITE;
/*!40000 ALTER TABLE `draft_books` DISABLE KEYS */;
INSERT INTO `draft_books` VALUES (1,'ROMANCE','draftBook1'),(2,'ROMANCE','draftBook2');
/*!40000 ALTER TABLE `draft_books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `draft_chapters`
--

DROP TABLE IF EXISTS `draft_chapters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `draft_chapters` (
  `draft_chapter_id` int NOT NULL AUTO_INCREMENT,
  `chapter_index` int DEFAULT NULL,
  `content` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `pending_content` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `author_id` int NOT NULL,
  `draft_book_id` int NOT NULL,
  PRIMARY KEY (`draft_chapter_id`),
  KEY `FKgpy5chrqcmc0wq6ot7ocn8fy2` (`author_id`),
  KEY `FK6l9ylkutv9qpfhp3w3702pesu` (`draft_book_id`),
  CONSTRAINT `FK6l9ylkutv9qpfhp3w3702pesu` FOREIGN KEY (`draft_book_id`) REFERENCES `draft_books` (`draft_book_id`),
  CONSTRAINT `FKgpy5chrqcmc0wq6ot7ocn8fy2` FOREIGN KEY (`author_id`) REFERENCES `authors` (`author_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `draft_chapters`
--

LOCK TABLES `draft_chapters` WRITE;
/*!40000 ALTER TABLE `draft_chapters` DISABLE KEYS */;
INSERT INTO `draft_chapters` VALUES (1,3,'content','title',NULL,'CREATED',1,1),(2,4,'content','title',NULL,'CREATED',2,1),(3,1,'content','title',NULL,'CREATED',3,2);
/*!40000 ALTER TABLE `draft_chapters` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `editor_comments`
--

DROP TABLE IF EXISTS `editor_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `editor_comments` (
  `editor_comment_id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  `review_id` int NOT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`editor_comment_id`),
  KEY `FKsw4rxehtqb6tksklmiq12x94y` (`review_id`),
  KEY `FKgxhabx8m8aw9ifrjt6ucv17jg` (`user_id`),
  CONSTRAINT `FKgxhabx8m8aw9ifrjt6ucv17jg` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FKsw4rxehtqb6tksklmiq12x94y` FOREIGN KEY (`review_id`) REFERENCES `reviews` (`review_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `editor_comments`
--

LOCK TABLES `editor_comments` WRITE;
/*!40000 ALTER TABLE `editor_comments` DISABLE KEYS */;
INSERT INTO `editor_comments` VALUES (1,'content','2023-04-24 23:51:09',1,3);
/*!40000 ALTER TABLE `editor_comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_details`
--

DROP TABLE IF EXISTS `order_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_details` (
  `order_details_id` int NOT NULL AUTO_INCREMENT,
  `quantity` int NOT NULL,
  `book_variant_id` int DEFAULT NULL,
  `order_id` int NOT NULL,
  PRIMARY KEY (`order_details_id`),
  KEY `FKpwfku331naksd7ijk1ufyt1en` (`book_variant_id`),
  KEY `FKqc6g0owojc0ahn1p4cwob73g7` (`order_id`),
  CONSTRAINT `FKpwfku331naksd7ijk1ufyt1en` FOREIGN KEY (`book_variant_id`) REFERENCES `book_variants` (`book_variant_id`),
  CONSTRAINT `FKqc6g0owojc0ahn1p4cwob73g7` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_details`
--

LOCK TABLES `order_details` WRITE;
/*!40000 ALTER TABLE `order_details` DISABLE KEYS */;
INSERT INTO `order_details` VALUES (1,2,1,1),(2,2,2,2),(3,10,3,2);
/*!40000 ALTER TABLE `order_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_tracks`
--

DROP TABLE IF EXISTS `order_tracks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_tracks` (
  `order_track_id` int NOT NULL AUTO_INCREMENT,
  `details` varchar(256) NOT NULL,
  `updated_time` datetime DEFAULT NULL,
  `order_id` int NOT NULL,
  PRIMARY KEY (`order_track_id`),
  KEY `FK8f0y479icana971fcpgur0l8e` (`order_id`),
  CONSTRAINT `FK8f0y479icana971fcpgur0l8e` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_tracks`
--

LOCK TABLES `order_tracks` WRITE;
/*!40000 ALTER TABLE `order_tracks` DISABLE KEYS */;
INSERT INTO `order_tracks` VALUES (1,'Shipped.','2023-04-24 23:48:34',1);
/*!40000 ALTER TABLE `order_tracks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `datetime_created` datetime NOT NULL,
  `deliver_days` int NOT NULL,
  `expected_deliver_date` date NOT NULL,
  `grand_total` decimal(19,2) NOT NULL,
  `num_items` int NOT NULL,
  `order_status` varchar(255) NOT NULL,
  `payment_type` varchar(255) NOT NULL,
  `shipping_fee` decimal(19,2) NOT NULL,
  `shipping_type` varchar(255) NOT NULL,
  `subtotal` decimal(19,2) NOT NULL,
  `address_id` int NOT NULL,
  `reader_id` int NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `FK8t5xydxo5sxg8jjgpo3s9t637` (`address_id`),
  KEY `FKlhik693l0x49g5w68jmslkdhp` (`reader_id`),
  CONSTRAINT `FK8t5xydxo5sxg8jjgpo3s9t637` FOREIGN KEY (`address_id`) REFERENCES `addresses` (`address_id`),
  CONSTRAINT `FKlhik693l0x49g5w68jmslkdhp` FOREIGN KEY (`reader_id`) REFERENCES `readers` (`reader_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'2023-04-24 23:48:34',10,'2023-05-04',36.98,2,'SHIPPED','CARD',5.00,'STANDARD',31.98,2,2),(2,'2023-04-24 23:48:34',10,'2023-05-04',161.98,12,'DELIVERED','CARD',0.00,'NONE',161.98,2,2);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permissions` (
  `permission_id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(150) NOT NULL,
  `name` varchar(40) NOT NULL,
  PRIMARY KEY (`permission_id`),
  UNIQUE KEY `UK_pnvtwliis6p05pn6i3ndjrqt2` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissions`
--

LOCK TABLES `permissions` WRITE;
/*!40000 ALTER TABLE `permissions` DISABLE KEYS */;
INSERT INTO `permissions` VALUES (1,'create book','book:create'),(2,'read book','book:read'),(3,'update book','book:update'),(4,'delete book','book:delete'),(5,'create user','user:create'),(6,'read user','user:read'),(7,'update user','user:update'),(8,'delete user','user:delete'),(9,'create order','order:create'),(10,'read order','order:read'),(11,'update order','order:update'),(12,'delete order','order:delete');
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `published_books`
--

DROP TABLE IF EXISTS `published_books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `published_books` (
  `published_book_id` int NOT NULL AUTO_INCREMENT,
  `genre` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`published_book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `published_books`
--

LOCK TABLES `published_books` WRITE;
/*!40000 ALTER TABLE `published_books` DISABLE KEYS */;
INSERT INTO `published_books` VALUES (1,'FANTASY','publishedBook1'),(2,'HORROR','publishedBook2'),(3,'ROMANCE','publishedBook3');
/*!40000 ALTER TABLE `published_books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `readers`
--

DROP TABLE IF EXISTS `readers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `readers` (
  `reader_id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(64) NOT NULL,
  `username` varchar(64) NOT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`reader_id`),
  UNIQUE KEY `UK_e0ld69yn1ufygd7386toeyw40` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `readers`
--

LOCK TABLES `readers` WRITE;
/*!40000 ALTER TABLE `readers` DISABLE KEYS */;
INSERT INTO `readers` VALUES (1,'reader1email@some.email.com','c3d1edb5316be38fcfc48e66f9c870d4','reader1','reader1','reader1'),(2,'reader2email@some.email.com','53e9191a0201eea0c019e293d67ce012','reader2','reader2','reader2');
/*!40000 ALTER TABLE `readers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reviews` (
  `review_id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  `draft_chapter_id` int DEFAULT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`review_id`),
  KEY `FK4e1gunyqm66kn8pawv7wkhc6c` (`draft_chapter_id`),
  KEY `FKryv9ovxoh6df2nlc5a5uog2ve` (`user_id`),
  CONSTRAINT `FK4e1gunyqm66kn8pawv7wkhc6c` FOREIGN KEY (`draft_chapter_id`) REFERENCES `draft_chapters` (`draft_chapter_id`),
  CONSTRAINT `FKryv9ovxoh6df2nlc5a5uog2ve` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews`
--

LOCK TABLES `reviews` WRITE;
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
INSERT INTO `reviews` VALUES (1,'content','2023-04-24 23:51:06',1,3),(2,'content','2023-04-24 23:51:06',2,3);
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(150) NOT NULL,
  `name` varchar(40) NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `UK_ofx66keruapi6vyqpv6f2or37` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'admin role','ADMIN'),(2,'manager role','MANAGER'),(3,'editor role','EDITOR');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles_permissions`
--

DROP TABLE IF EXISTS `roles_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles_permissions` (
  `role_id` int NOT NULL,
  `permission_id` int NOT NULL,
  PRIMARY KEY (`role_id`,`permission_id`),
  KEY `FKegfm9cj2wl36ibbf2pck89je4` (`permission_id`),
  CONSTRAINT `FK32nii58e06kohnefcp8il5lkx` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`),
  CONSTRAINT `FKegfm9cj2wl36ibbf2pck89je4` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles_permissions`
--

LOCK TABLES `roles_permissions` WRITE;
/*!40000 ALTER TABLE `roles_permissions` DISABLE KEYS */;
INSERT INTO `roles_permissions` VALUES (1,1),(3,1),(1,2),(3,2),(1,3),(3,3),(1,4),(3,4),(1,5),(1,6),(1,7),(1,8),(1,9),(2,9),(1,10),(2,10),(1,11),(2,11),(1,12),(2,12);
/*!40000 ALTER TABLE `roles_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `password` varchar(64) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_r43af9ap4edm43mmtq01oddj6` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin@book.company.dummy.com','admin','admin','7361719830ec6cf2f50a3cf4096e51dd','admin'),(2,'manager@book.company.dummy.com','manager','manager','1182ee3380124e858f347de155d5ad45','manager'),(3,'editor@book.company.dummy.com','editor','editor','20404d50a1ce8b7ba46c096276a3d06e','editor'),(4,'testuser@book.company.dummy.com','testUser','testUser','8de80c6a928f746865ad5cf368b2315a','testUser');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_roles` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKn5g60d5vnnex8s9bl7i0vma05` (`role_id`),
  CONSTRAINT `FK7ursf5mr7s447yoobqh7q3x1v` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FKn5g60d5vnnex8s9bl7i0vma05` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_roles`
--

LOCK TABLES `users_roles` WRITE;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` VALUES (1,1),(2,2),(3,3);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `writer_comments`
--

DROP TABLE IF EXISTS `writer_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `writer_comments` (
  `writer_comment_id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  `review_id` int NOT NULL,
  `writer_id` int DEFAULT NULL,
  PRIMARY KEY (`writer_comment_id`),
  KEY `FKmeiace8af95bmjb39luhknkaa` (`review_id`),
  KEY `FKr7u2j01bhf0brov4wotp258yb` (`writer_id`),
  CONSTRAINT `FKmeiace8af95bmjb39luhknkaa` FOREIGN KEY (`review_id`) REFERENCES `reviews` (`review_id`),
  CONSTRAINT `FKr7u2j01bhf0brov4wotp258yb` FOREIGN KEY (`writer_id`) REFERENCES `writers` (`writer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `writer_comments`
--

LOCK TABLES `writer_comments` WRITE;
/*!40000 ALTER TABLE `writer_comments` DISABLE KEYS */;
INSERT INTO `writer_comments` VALUES (1,'content','2023-04-24 23:51:12',1,1);
/*!40000 ALTER TABLE `writer_comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `writers`
--

DROP TABLE IF EXISTS `writers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `writers` (
  `writer_id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(64) NOT NULL,
  `username` varchar(64) NOT NULL,
  `author_id` int DEFAULT NULL,
  PRIMARY KEY (`writer_id`),
  UNIQUE KEY `UK_qhs0432wjop6w5hlidb00ve1h` (`username`),
  KEY `FKtobsmqh11d1kloweycbb0x2xf` (`author_id`),
  CONSTRAINT `FKtobsmqh11d1kloweycbb0x2xf` FOREIGN KEY (`author_id`) REFERENCES `authors` (`author_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `writers`
--

LOCK TABLES `writers` WRITE;
/*!40000 ALTER TABLE `writers` DISABLE KEYS */;
INSERT INTO `writers` VALUES (1,'writer1@wirter.dummy.com','4a52b49ba957db9bf500202b8c0866f9','writer1',1),(2,'writer2@writer.dummy.com','f9842c7a6761d35df10ade2ce8fcdb99','writer2',2);
/*!40000 ALTER TABLE `writers` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

