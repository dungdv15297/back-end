-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 07, 2020 at 07:59 PM
-- Server version: 10.4.13-MariaDB
-- PHP Version: 7.4.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `da2_motel`
--

-- --------------------------------------------------------


DROP DATABASE IF EXISTS da2_motel;
CREATE DATABASE da2_motel;
USE da2_motel;

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `ACCOUNT_ID` varchar(255) NOT NULL,
  `USERNAME` varchar(12) NOT NULL UNIQUE,
  `PASSWORD` varchar(255) NOT NULL,
  `STATUS` int(11) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT current_timestamp(),
  `UPDATE_DATE` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `CREATED_BY` varchar(50) NOT NULL,
  `UPDATED_BY` varchar(50) NOT NULL,
  `ROLE` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `account_detail`
--

CREATE TABLE `account_detail` (
  `ACCOUNT_DETAIL_ID` varchar(255) NOT NULL,
  `NAME` varchar(50) NULL,
  `BIRTHDAY` varchar(50) NOT NULL,
  `GENDER` int(11) DEFAULT NULL,
  `ADDRESS` varchar(255) DEFAULT NULL,
  `PHONE` varchar(11) NOT NULL,
  `EMAIL` varchar(50) NULL,
  `BALANCE` int(11) DEFAULT 0,
  `CREATED_DATE` timestamp NOT NULL DEFAULT current_timestamp(),
  `UPDATE_DATE` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `CREATED_BY` varchar(50) NOT NULL,
  `UPDATED_BY` varchar(50) NOT NULL,
  `STATUS` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `acreage_range`
--

CREATE TABLE `acreage_range` (
  `ACREAGE_RANGE_ID` int(11) NOT NULL,
  `MIN` int(11) NOT NULL,
  `MAX` int(11) NOT NULL,
  `STATUS` int(11) NOT NULL,
  `DESCRIPTION` varchar(255) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT current_timestamp(),
  `UPDATE_DATE` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `CREATED_BY` varchar(50) NOT NULL,
  `UPDATED_BY` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `district`
--

CREATE TABLE `district` (
  `DISTRICT_ID` int(11) NOT NULL,
  `PROVINCE_ID` int(11) NOT NULL,
  `NAME` varchar(50) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT current_timestamp(),
  `UPDATE_DATE` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `CREATED_BY` varchar(50) NOT NULL,
  `UPDATED_BY` varchar(50) NOT NULL,
  `STATUS` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `PAYMENT_ID` varchar(255) NOT NULL,
  `PAYMENT_INFOR` varchar(255) NOT NULL,
  `STATUS` int(11) NOT NULL,
  `ACCOUNT_ID` varchar(255) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT current_timestamp(),
  `UPDATE_DATE` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `CREATED_BY` varchar(50) NOT NULL,
  `UPDATED_BY` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `picture`
--

CREATE TABLE `picture` (
  `PICTURE_ID` int(11) NOT NULL,
  `ROOM_ID` varchar(255) NOT NULL,
  `SRC` varchar(255) NOT NULL,
  `STATUS` int(11) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT current_timestamp(),
  `UPDATE_DATE` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `CREATED_BY` varchar(50) NOT NULL,
  `UPDATED_BY` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `price_range`
--

CREATE TABLE `price_range` (
  `PRICE_RANGE_ID` int(11) NOT NULL,
  `MIN` int(11) NOT NULL,
  `MAX` int(11) NOT NULL,
  `STATUS` int(11) NOT NULL,
  `DESCRIPTION` varchar(255) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT current_timestamp(),
  `UPDATE_DATE` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `CREATED_BY` varchar(50) NOT NULL,
  `UPDATED_BY` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `province`
--

CREATE TABLE `province` (
  `PROVINCE_ID` int(11) NOT NULL,
  `NAME` varchar(50) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT current_timestamp(),
  `UPDATE_DATE` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `CREATED_BY` varchar(50) NOT NULL,
  `UPDATED_BY` varchar(50) NOT NULL,
  `STATUS` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `room`
--

CREATE TABLE `room` (
  `ROOM_ID` varchar(255) NOT NULL,
  `PRICE_RANGE_ID` int(11) NOT NULL,
  `ACREAGE_RANGE_ID` int(11) NOT NULL,
  `STREET_ID` int(11) NOT NULL,
  `ADDRESS` varchar(255) NOT NULL,
  `DESCRIPTION` varchar(255) NOT NULL,
  `PRICE_MIN` int(11) NOT NULL,
  `PRICE_MAX` int(11) NOT NULL,
  `ACREAGE_MIN` int(11) NOT NULL,
  `ACREAGE_MAX` int(11) NOT NULL,
  `STATUS` int(11) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT current_timestamp(),
  `UPDATE_DATE` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `CREATED_BY` varchar(50) NOT NULL,
  `UPDATED_BY` varchar(50) NOT NULL,
  `ACCOUNT_ID` varchar(255) NOT NULL,
  `LONGITUDE` varchar(20) DEFAULT NULL,
  `LATITUDE` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `street`
--

CREATE TABLE `street` (
  `STREET_ID` int(11) NOT NULL,
  `DISTRICT_ID` int(11) NOT NULL,
  `NAME` varchar(50) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT current_timestamp(),
  `UPDATE_DATE` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `CREATED_BY` varchar(50) NOT NULL,
  `UPDATED_BY` varchar(50) NOT NULL,
  `STATUS` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account_detail`
--
ALTER TABLE `account_detail`
  ADD PRIMARY KEY (`ACCOUNT_DETAIL_ID`);

--
-- Indexes for table `acreage_range`
--
ALTER TABLE `acreage_range`
  ADD PRIMARY KEY (`ACREAGE_RANGE_ID`);

--
-- Indexes for table `district`
--
ALTER TABLE `district`
  ADD PRIMARY KEY (`DISTRICT_ID`),
  ADD KEY `FK_DISTRICT_PROVINCE` (`PROVINCE_ID`);

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`PAYMENT_ID`),
  ADD KEY `FK_PAYMENT_ACCOUNT` (`ACCOUNT_ID`);

--
-- Indexes for table `picture`
--
ALTER TABLE `picture`
  ADD PRIMARY KEY (`PICTURE_ID`),
  ADD KEY `FK_ROOM_PICTURE` (`ROOM_ID`);

--
-- Indexes for table `price_range`
--
ALTER TABLE `price_range`
  ADD PRIMARY KEY (`PRICE_RANGE_ID`);

--
-- Indexes for table `province`
--
ALTER TABLE `province`
  ADD PRIMARY KEY (`PROVINCE_ID`);

--
-- Indexes for table `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`ROOM_ID`),
  ADD KEY `FK_ROOM_ACCOUNT` (`ACCOUNT_ID`),
  ADD KEY `FK_ROOM_STREET` (`STREET_ID`),
  ADD KEY `FK_ROOM_PRICE_RANGE` (`PRICE_RANGE_ID`),
  ADD KEY `FK_ROOM_ACREAGE_RANGE` (`ACREAGE_RANGE_ID`);

--
-- Indexes for table `street`
--
ALTER TABLE `street`
  ADD PRIMARY KEY (`STREET_ID`),
  ADD KEY `FK_STREET_DISTRICT` (`DISTRICT_ID`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `account`
--
ALTER TABLE `account`
  ADD CONSTRAINT `FK_ACCOUNT_DETAIL_ACCOUNT` FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `account_detail` (`ACCOUNT_DETAIL_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `district`
--
ALTER TABLE `district`
  ADD CONSTRAINT `FK_DISTRICT_PROVINCE` FOREIGN KEY (`PROVINCE_ID`) REFERENCES `province` (`PROVINCE_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `payment`
--
ALTER TABLE `payment`
  ADD CONSTRAINT `FK_PAYMENT_ACCOUNT` FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `account` (`ACCOUNT_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `picture`
--
ALTER TABLE `picture`
  ADD CONSTRAINT `FK_ROOM_PICTURE` FOREIGN KEY (`ROOM_ID`) REFERENCES `room` (`ROOM_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `room`
--
ALTER TABLE `room`
  ADD CONSTRAINT `FK_ROOM_ACCOUNT` FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `account` (`ACCOUNT_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_ROOM_ACREAGE_RANGE` FOREIGN KEY (`ACREAGE_RANGE_ID`) REFERENCES `acreage_range` (`ACREAGE_RANGE_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_ROOM_PRICE_RANGE` FOREIGN KEY (`PRICE_RANGE_ID`) REFERENCES `price_range` (`PRICE_RANGE_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_ROOM_STREET` FOREIGN KEY (`STREET_ID`) REFERENCES `street` (`STREET_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `street`
--
ALTER TABLE `street`
  ADD CONSTRAINT `FK_STREET_DISTRICT` FOREIGN KEY (`DISTRICT_ID`) REFERENCES `district` (`DISTRICT_ID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
