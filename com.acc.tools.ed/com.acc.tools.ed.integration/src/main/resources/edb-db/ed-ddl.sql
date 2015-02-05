-- MySQL dump 10.10
--
-- Host: localhost    Database: engagement
-- ------------------------------------------------------
-- Server version	5.0.20-nt

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
-- Table structure for table `clnt_engmnt`
--

CREATE DATABASE IF NOT EXISTS ENGAGEMENT;
USE ENGAGEMENT;
DROP TABLE IF EXISTS `clnt_engmnt`;
CREATE TABLE `clnt_engmnt` (
  `CLNT_ID` int(11) NOT NULL COMMENT 'Client ID',
  `CLNT_NM` varchar(50) default NULL COMMENT 'Client Name',
  `CLNT_ST_DT` date default NULL,
  `CLNT_TYP` varchar(50) default NULL COMMENT 'Diamond Client?',
  PRIMARY KEY  (`CLNT_ID`)
) ;

--
-- Dumping data for table `clnt_engmnt`
--


/*!40000 ALTER TABLE `clnt_engmnt` DISABLE KEYS */;
LOCK TABLES `clnt_engmnt` WRITE;
INSERT INTO `clnt_engmnt` VALUES (1,'HCSC','2014-01-01','Health');
UNLOCK TABLES;
/*!40000 ALTER TABLE `clnt_engmnt` ENABLE KEYS */;

--
-- Table structure for table `emp_role`
--

DROP TABLE IF EXISTS `emp_role`;
CREATE TABLE `emp_role` (
  `ROLE_ID` int(11) NOT NULL,
  `ROLE_DESC` varchar(20) default NULL,
  PRIMARY KEY  (`ROLE_ID`)
) ;

--
-- Dumping data for table `emp_role`
--


/*!40000 ALTER TABLE `emp_role` DISABLE KEYS */;
LOCK TABLES `emp_role` WRITE;
INSERT INTO `emp_role` VALUES (7,'Manager'),(8,'AM'),(9,'TL'),(10,'SSE'),(11,'SE'),(12,'ASE'),(13,'Intern');
UNLOCK TABLES;
/*!40000 ALTER TABLE `emp_role` ENABLE KEYS */;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `PROJ_ID` int(11) NOT NULL COMMENT 'Project ID',
  `CLNT_ID` int(11) NOT NULL COMMENT 'Client ID',
  `PROJ_MANAGER_NAME` varchar(100) default NULL,
  `PROJ_SUPERVISOR_NAME` varchar(100) default NULL,
  PRIMARY KEY  (`PROJ_ID`),
  KEY `CLNT_ID` (`CLNT_ID`),
  CONSTRAINT `project_ibfk_1` FOREIGN KEY (`CLNT_ID`) REFERENCES `clnt_engmnt` (`CLNT_ID`)
) ;

--
-- Dumping data for table `project`
--


/*!40000 ALTER TABLE `project` DISABLE KEYS */;
LOCK TABLES `project` WRITE;
INSERT INTO `project` VALUES (1,1,'Rakesh k chanana','Rakesh k chanana'),(2,1,'Rakesh k chanana','Murli');
UNLOCK TABLES;
/*!40000 ALTER TABLE `project` ENABLE KEYS */;

--
-- Table structure for table `emp_dtl`
--

DROP TABLE IF EXISTS `emp_dtl`;
CREATE TABLE `emp_dtl` (
  `EMP_ID` varchar(50) NOT NULL,
  `ROLE_ID` int(11) default NULL,
  `EMP_ENTRPS_ID` varchar(50) NOT NULL,
  `EMP_TL_ID` varchar(50) default NULL,
  `EMP_PROF_LVL` varchar(2) default NULL COMMENT 'proficiency level',
  `EMP_DESIG` varchar(50) default NULL,
  `EMP_FST_NM` varchar(50) default NULL COMMENT 'Employee First Name',
  `EMP_LST_NM` varchar(50) default NULL COMMENT 'Employee Last name',
  `EMP_MDL_NM` varchar(50) default NULL COMMENT 'Employee Middle Name',
  `EMP_DOJ` date default NULL COMMENT 'Employee Date of Join',
  `MAL` int(11) default NULL COMMENT 'Months @ designation',
  `EMP_PROJ_HL_DT` date default NULL COMMENT 'Project hard lock date',
  `EMP_PROJ_MAP` int(11) default NULL COMMENT 'Months @ Project',
  `EMP_FUN_SKLL` varchar(50) default NULL COMMENT 'Functional Skills',
  `EMP_TECH_SKLL` varchar(50) default NULL COMMENT 'Technical Skills',
  `EMP_RATE_CARD` int(11) default NULL,
  `PROJ_ID` int(11) NOT NULL COMMENT 'Project ID',
  PRIMARY KEY  (`EMP_ID`),
  UNIQUE KEY `EMP_ENTRPS_ID` (`EMP_ENTRPS_ID`),
  KEY `PROJ_ID` (`PROJ_ID`),
  KEY `emp_role_fk` (`ROLE_ID`),
  CONSTRAINT `emp_dtl_ibfk_1` FOREIGN KEY (`PROJ_ID`) REFERENCES `project` (`PROJ_ID`),
  CONSTRAINT `emp_role_fk` FOREIGN KEY (`ROLE_ID`) REFERENCES `emp_role` (`ROLE_ID`)
) ;

--
-- Dumping data for table `emp_dtl`
--


/*!40000 ALTER TABLE `emp_dtl` DISABLE KEYS */;
LOCK TABLES `emp_dtl` WRITE;
INSERT INTO `emp_dtl` VALUES ('10103460',10,'testing.id','nikhil.jagtiani',' ',' ',' ',' ',' ','2014-02-18',10,'2014-08-06',5,' ',' ',3,2),('1023154',9,'murli.gavarasana','murli.gavarasana','','','','','','2013-05-13',10,'2014-06-06',5,'','',4,1),('1024589',10,'madan.prakash','sarvanan.s.f','','','','','','2012-05-11',10,'2014-08-06',6,'','',3,2),('1024897',10,'radhika.palanivelu','murli.gavarasana','','','','','','2013-08-21',15,'2014-08-06',5,'','',4,2),('1025648',10,'manoj.kumar','murli.gavarasana','','','','','','2013-01-16',10,'2014-08-06',6,'','',2,2),('1441007',7,'rakesh.k.chanana','NA','p4','PPSM man','Rakesh','Chanana','K','2010-01-01',20,'2014-08-06',4,' ',' ',4,1),('NO725428',13,'nikhil.jagtiani','ramsethu.samy','','','','','','2014-02-18',10,'2014-08-06',6,'','',2,1);
UNLOCK TABLES;
/*!40000 ALTER TABLE `emp_dtl` ENABLE KEYS */;

--
-- Table structure for table `emp_weekly_hrs`
--

DROP TABLE IF EXISTS `emp_weekly_hrs`;
/*!50001 DROP VIEW IF EXISTS `emp_weekly_hrs`*/;
/*!50001 DROP TABLE IF EXISTS `emp_weekly_hrs`*/;
/*!50001 CREATE TABLE `emp_weekly_hrs` (
  `WEEK_NUMBER` varbinary(7),
  `EMP_ID` varchar(50),
  `Hours` decimal(32,0)
) */;

--
-- Table structure for table `hl_weekly_hrs`
--

DROP TABLE IF EXISTS `hl_weekly_hrs`;
/*!50001 DROP VIEW IF EXISTS `hl_weekly_hrs`*/;
/*!50001 DROP TABLE IF EXISTS `hl_weekly_hrs`*/;
/*!50001 CREATE TABLE `hl_weekly_hrs` (
  `MSTR_HL_TSK_ID` int(11),
  `WEEK_NUMBER` varbinary(7),
  `EMP_ID` varchar(50),
  `HRS` decimal(32,0)
) */;

--
-- Table structure for table `hrs_actl_tsk`
--

DROP TABLE IF EXISTS `hrs_actl_tsk`;
CREATE TABLE `hrs_actl_tsk` (
  `ACTL_FRN_ID` int(11) NOT NULL,
  `HRS_TODAY` int(11) default NULL,
  `DATE_FILLED` date NOT NULL default '0000-00-00',
  `Status` varchar(45) NOT NULL default 'Not Yet Started',
  `Comment` varchar(45) NOT NULL default '',
  PRIMARY KEY  (`ACTL_FRN_ID`,`DATE_FILLED`)
) ;

--
-- Dumping data for table `hrs_actl_tsk`
--


/*!40000 ALTER TABLE `hrs_actl_tsk` DISABLE KEYS */;
LOCK TABLES `hrs_actl_tsk` WRITE;
INSERT INTO `hrs_actl_tsk` VALUES (1,5,'2014-01-07','Not Yet Started',''),(1,5,'2014-01-08','Not Yet Started',''),(1,5,'2014-01-09','Not Yet Started',''),(1,5,'2014-01-10','Not Yet Started',''),(1,5,'2014-01-11','Not Yet Started',''),(1,2,'2014-06-20','Not Yet Started',''),(1,3,'2014-06-25','Not Yet Started',''),(2,1,'2014-01-04','Not Yet Started',''),(2,1,'2014-01-05','Not Yet Started',''),(2,1,'2014-01-06','Not Yet Started',''),(2,1,'2014-01-07','Not Yet Started',''),(2,1,'2014-01-08','Not Yet Started',''),(2,1,'2014-01-09','Not Yet Started',''),(2,1,'2014-01-10','Not Yet Started',''),(2,1,'2014-01-12','Not Yet Started',''),(2,5,'2014-06-27','Halted','asds'),(2,2,'2014-07-01','Not Yet Started','as'),(2,11,'2014-07-02','In Progress','Service1 DAO1,DAO2'),(4,4,'2014-01-06','Not Yet Started',''),(4,4,'2014-01-07','Not Yet Started',''),(4,3,'2014-01-08','Not Yet Started',''),(4,5,'2014-01-09','Not Yet Started',''),(5,2,'2014-01-13','Not Yet Started',''),(5,3,'2014-01-14','Not Yet Started',''),(5,6,'2014-01-15','Not Yet Started',''),(6,6,'2014-01-04','Not Yet Started',''),(6,3,'2014-01-05','Not Yet Started',''),(6,5,'2014-01-06','Not Yet Started',''),(6,5,'2014-01-07','Not Yet Started',''),(6,3,'2014-01-08','Not Yet Started',''),(6,6,'2014-01-09','Not Yet Started',''),(6,5,'2014-01-10','Not Yet Started',''),(6,3,'2014-01-11','Not Yet Started',''),(6,6,'2014-01-12','Not Yet Started',''),(7,5,'2014-06-25','Not Yet Started',''),(7,10,'2014-06-27','Halted 123','In progress'),(10,6,'2014-01-04','Not Yet Started',''),(10,3,'2014-01-05','Not Yet Started',''),(10,5,'2014-01-06','Not Yet Started',''),(10,5,'2014-01-10','Not Yet Started',''),(10,6,'2014-01-11','Not Yet Started',''),(10,3,'2014-01-12','Not Yet Started',''),(10,6,'2014-01-14','Not Yet Started',''),(10,3,'2014-01-15','Not Yet Started',''),(10,5,'2014-01-16','Not Yet Started',''),(11,5,'2014-01-06','Halted',''),(11,5,'2014-01-10','Not Yet Started',''),(11,5,'2014-01-14','Not Yet Started',''),(12,5,'2014-01-07','Not Yet Started',''),(12,5,'2014-01-10','Not Yet Started',''),(12,5,'2014-01-14','Not Yet Started',''),(12,3,'2014-06-27','Design Phase','comments'),(12,10,'2014-06-30','Design Phase','321'),(13,3,'2014-01-13','Not Yet Started',''),(13,5,'2014-01-14','Not Yet Started',''),(13,15,'2014-06-27','In Progress','errors n big bugs'),(14,4,'2014-01-06','Not Yet Started',''),(15,2,'2014-01-14','Not Yet Started',''),(15,3,'2014-01-15','Not Yet Started',''),(15,6,'2014-01-16','Not Yet Started','');
UNLOCK TABLES;
/*!40000 ALTER TABLE `hrs_actl_tsk` ENABLE KEYS */;

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
CREATE TABLE `login` (
  `lid` int(10) unsigned NOT NULL auto_increment,
  `emp_id` varchar(45) NOT NULL default '',
  `pass` varchar(45) NOT NULL default '',
  PRIMARY KEY  (`lid`),
  KEY `FK_login_1` (`emp_id`),
  CONSTRAINT `FK_login_1` FOREIGN KEY (`emp_id`) REFERENCES `emp_dtl` (`EMP_ID`)
) ;

--
-- Dumping data for table `login`
--


/*!40000 ALTER TABLE `login` DISABLE KEYS */;
LOCK TABLES `login` WRITE;
INSERT INTO `login` VALUES (1,'NO725428','password'),(2,'1023154','password'),(3,'1024589','password'),(4,'1024897','pass'),(5,'1025648','pass'),(6,'10103460','pass'),(7,'1441007','pass');
UNLOCK TABLES;
/*!40000 ALTER TABLE `login` ENABLE KEYS */;

--
-- Table structure for table `proj_milestone`
--

DROP TABLE IF EXISTS `proj_milestone`;
CREATE TABLE `proj_milestone` (
  `MLSTN_ID` int(11) NOT NULL auto_increment,
  `MLSTN_RELS_NAME` varchar(40) default NULL COMMENT 'Release Name',
  `PROJ_ID` int(11) NOT NULL COMMENT 'Project ID',
  `MLSTN_ST_DT` date NOT NULL,
  `MLSTN_END_DT` date NOT NULL,
  PRIMARY KEY  (`MLSTN_ID`),
  KEY `PROJ_ID` (`PROJ_ID`),
  CONSTRAINT `proj_milestone_ibfk_1` FOREIGN KEY (`PROJ_ID`) REFERENCES `project` (`PROJ_ID`)
) ;

--
-- Dumping data for table `proj_milestone`
--


/*!40000 ALTER TABLE `proj_milestone` DISABLE KEYS */;
LOCK TABLES `proj_milestone` WRITE;
INSERT INTO `proj_milestone` VALUES (3,'EPP',1,'2014-01-01','2014-03-01'),(4,'LSS',2,'2014-06-05','2014-06-05'),(6,'HC1',2,'2014-05-05','2014-08-08'),(7,'HC_122',1,'2014-04-15','2014-07-05');
UNLOCK TABLES;
/*!40000 ALTER TABLE `proj_milestone` ENABLE KEYS */;

--
-- Table structure for table `mstr_hl_pln_tsk`
--

DROP TABLE IF EXISTS `mstr_hl_pln_tsk`;
CREATE TABLE `mstr_hl_pln_tsk` (
  `MSTR_HL_TSK_ID` int(11) NOT NULL auto_increment,
  `MLSTN_ID` int(11) NOT NULL,
  `MSTR_HL_TSK_TYP` varchar(20) NOT NULL,
  `MSTR_HL_TSK_ST_DT` date NOT NULL COMMENT 'Task Start Date',
  `MSTR_HL_TSK_END_DT` date NOT NULL,
  `EST_HRS` int(11) default NULL COMMENT 'Estimated Hours',
  `MSTR_HL_TSK_NAME` varchar(150) default NULL,
  `work_completed` varchar(45) default NULL,
  PRIMARY KEY  (`MSTR_HL_TSK_ID`),
  KEY `MLSTN_ID` (`MLSTN_ID`),
  CONSTRAINT `mstr_hl_pln_tsk_ibfk_1` FOREIGN KEY (`MLSTN_ID`) REFERENCES `proj_milestone` (`MLSTN_ID`)
) ;

--
-- Dumping data for table `mstr_hl_pln_tsk`
--


/*!40000 ALTER TABLE `mstr_hl_pln_tsk` DISABLE KEYS */;
LOCK TABLES `mstr_hl_pln_tsk` WRITE;
INSERT INTO `mstr_hl_pln_tsk` VALUES (1,3,'build','2014-01-01','2014-02-01',500,'Module1','Bugs in system'),(7,3,'build','2014-02-01','2014-03-01',70,'Module3','Completed'),(8,6,'Deploy','2014-05-05','2014-06-08',500,'Module 2','Deployment Testing left'),(9,7,'Test','2014-06-15','2014-07-05',500,'Module 1','UA3 going');
UNLOCK TABLES;
/*!40000 ALTER TABLE `mstr_hl_pln_tsk` ENABLE KEYS */;

--
-- Table structure for table `mstr_ll_pln_tsk`
--

DROP TABLE IF EXISTS `mstr_ll_pln_tsk`;
CREATE TABLE `mstr_ll_pln_tsk` (
  `LLID` int(11) NOT NULL auto_increment,
  `MSTR_HL_TSK_ID` int(11) NOT NULL,
  `MSTR_LL_TSK_NAME` varchar(150) default NULL,
  `ll_st_dt` date NOT NULL default '0000-00-00',
  `ll_end_dt` date NOT NULL default '0000-00-00',
  `requirement` varchar(145) NOT NULL default 'default requirement',
  PRIMARY KEY  (`LLID`),
  UNIQUE KEY `LLID` (`LLID`)
) ;

--
-- Dumping data for table `mstr_ll_pln_tsk`
--


/*!40000 ALTER TABLE `mstr_ll_pln_tsk` DISABLE KEYS */;
LOCK TABLES `mstr_ll_pln_tsk` WRITE;
INSERT INTO `mstr_ll_pln_tsk` VALUES (1,1,'Database','2014-01-20','2014-08-20','default requirement'),(3,1,'design','2014-01-20','2014-08-20','default requirement'),(4,7,'Integration Testing','2014-01-20','2014-08-20','default requirement'),(5,7,'Assembly Testing','2014-01-20','2014-08-20','default requirement'),(6,8,'WebService Deployoment','2014-01-20','2014-08-20','default requirement'),(7,8,'Scripts Creation','2014-02-20','2014-04-20','default requirement'),(8,11,'user acceptance Testing','2014-02-20','2014-04-20','default requirement'),(9,11,'user acceptance 2 Testing','2014-02-20','2014-04-20','default requirement'),(10,10,'Test Data Creation ','2014-01-01','2014-01-20','default requirement'),(11,10,'Performance load testing','2014-01-01','2014-01-20','default requirement');
UNLOCK TABLES;
/*!40000 ALTER TABLE `mstr_ll_pln_tsk` ENABLE KEYS */;

--
-- Table structure for table `proj_weekly_hrs`
--

DROP TABLE IF EXISTS `proj_weekly_hrs`;
/*!50001 DROP VIEW IF EXISTS `proj_weekly_hrs`*/;
/*!50001 DROP TABLE IF EXISTS `proj_weekly_hrs`*/;
/*!50001 CREATE TABLE `proj_weekly_hrs` (
  `WEEK_NUMBER` varbinary(7),
  `EMP_ID` varchar(50),
  `sum(HRS_ACTL_TSK.HRS_TODAY)` decimal(32,0)
) */;


--
-- Table structure for table `resr_actl_tsk`
--

DROP TABLE IF EXISTS `resr_actl_tsk`;
CREATE TABLE `resr_actl_tsk` (
  `LLID` int(11) NOT NULL,
  `ACTL_FRN_ID` int(11) NOT NULL auto_increment,
  `EMP_ID` varchar(50) NOT NULL,
  `description` varchar(145) NOT NULL default 'Default desc',
  PRIMARY KEY  (`ACTL_FRN_ID`),
  KEY `EMP_ID` (`EMP_ID`),
  CONSTRAINT `resr_actl_tsk_ibfk_1` FOREIGN KEY (`EMP_ID`) REFERENCES `emp_dtl` (`EMP_ID`)
) ;

--
-- Dumping data for table `resr_actl_tsk`
--


/*!40000 ALTER TABLE `resr_actl_tsk` DISABLE KEYS */;
LOCK TABLES `resr_actl_tsk` WRITE;
INSERT INTO `resr_actl_tsk` VALUES (1,1,'NO725428','Default desc'),(3,2,'1023154','Default desc'),(4,3,'1025648','Default desc'),(5,4,'1024589','Default desc'),(6,5,'1024897','Default desc'),(5,7,'1025648','Default desc'),(4,8,'1024589','Default desc'),(6,9,'1024897','Default desc'),(8,11,'NO725428','Default desc'),(9,12,'1023154','Default desc'),(10,13,'1025648','Default desc'),(11,14,'1024589','Default desc'),(8,15,'1024897','Default desc');
UNLOCK TABLES;
/*!40000 ALTER TABLE `resr_actl_tsk` ENABLE KEYS */;

--
-- Table structure for table `varience_view`
--

DROP TABLE IF EXISTS `varience_view`;
/*!50001 DROP VIEW IF EXISTS `varience_view`*/;
/*!50001 DROP TABLE IF EXISTS `varience_view`*/;
/*!50001 CREATE TABLE `varience_view` (
  `emp_id` varchar(50),
  `total_hrs` decimal(54,0),
  `total_weeks_worked` bigint(21)
) */;

--
-- View structure for view `emp_weekly_hrs`
--

/*!50001 DROP TABLE IF EXISTS `emp_weekly_hrs`*/;
/*!50001 DROP VIEW IF EXISTS `emp_weekly_hrs`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `emp_weekly_hrs` AS select date_format(`hrs_actl_tsk`.`DATE_FILLED`,_utf8'%u %x') AS `WEEK_NUMBER`,`resr_actl_tsk`.`EMP_ID` AS `EMP_ID`,sum(`hrs_actl_tsk`.`HRS_TODAY`) AS `Hours` from (`hrs_actl_tsk` join `resr_actl_tsk` on((`hrs_actl_tsk`.`ACTL_FRN_ID` = `resr_actl_tsk`.`ACTL_FRN_ID`))) group by `resr_actl_tsk`.`EMP_ID`,date_format(`hrs_actl_tsk`.`DATE_FILLED`,_utf8'%u %x') */;

--
-- View structure for view `hl_weekly_hrs`
--

/*!50001 DROP TABLE IF EXISTS `hl_weekly_hrs`*/;
/*!50001 DROP VIEW IF EXISTS `hl_weekly_hrs`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `hl_weekly_hrs` AS select `high`.`MSTR_HL_TSK_ID` AS `MSTR_HL_TSK_ID`,date_format(`h`.`DATE_FILLED`,_utf8'%u %x') AS `WEEK_NUMBER`,`r`.`EMP_ID` AS `EMP_ID`,sum(`h`.`HRS_TODAY`) AS `HRS` from (((`hrs_actl_tsk` `h` join `resr_actl_tsk` `r` on((`h`.`ACTL_FRN_ID` = `r`.`ACTL_FRN_ID`))) join `mstr_ll_pln_tsk` `l` on((`l`.`LLID` = `r`.`LLID`))) join `mstr_hl_pln_tsk` `high` on((`l`.`MSTR_HL_TSK_ID` = `high`.`MSTR_HL_TSK_ID`))) group by `l`.`MSTR_HL_TSK_ID`,date_format(`h`.`DATE_FILLED`,_utf8'%u %x'),`r`.`EMP_ID` */;

--
-- View structure for view `proj_weekly_hrs`
--

/*!50001 DROP TABLE IF EXISTS `proj_weekly_hrs`*/;
/*!50001 DROP VIEW IF EXISTS `proj_weekly_hrs`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `proj_weekly_hrs` AS select date_format(`hrs_actl_tsk`.`DATE_FILLED`,_utf8'%u %x') AS `WEEK_NUMBER`,`resr_actl_tsk`.`EMP_ID` AS `EMP_ID`,sum(`hrs_actl_tsk`.`HRS_TODAY`) AS `sum(HRS_ACTL_TSK.HRS_TODAY)` from (`hrs_actl_tsk` left join `resr_actl_tsk` on((`hrs_actl_tsk`.`ACTL_FRN_ID` = `resr_actl_tsk`.`ACTL_FRN_ID`))) group by date_format(`hrs_actl_tsk`.`DATE_FILLED`,_utf8'%u %x') */;

--
-- View structure for view `varience_view`
--

/*!50001 DROP TABLE IF EXISTS `varience_view`*/;
/*!50001 DROP VIEW IF EXISTS `varience_view`*/;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `varience_view` AS select `e`.`EMP_ID` AS `emp_id`,sum(`e`.`Hours`) AS `total_hrs`,count(`e`.`WEEK_NUMBER`) AS `total_weeks_worked` from `emp_weekly_hrs` `e` group by `e`.`EMP_ID` */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

