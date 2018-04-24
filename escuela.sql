-- phpMyAdmin SQL Dump
-- version 4.8.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Apr 24, 2018 at 10:43 PM
-- Server version: 10.1.32-MariaDB
-- PHP Version: 7.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `escuela`
--

-- --------------------------------------------------------

--
-- Table structure for table `registros`
--

CREATE TABLE `registros` (
  `Rg_id` int(11) NOT NULL,
  `sexoh` decimal(2,0) DEFAULT NULL,
  `sexom` decimal(2,0) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `entrada` time DEFAULT NULL,
  `salida` time DEFAULT NULL,
  `aula_id` int(11) DEFAULT NULL,
  `asig_id` decimal(2,0) DEFAULT NULL,
  `num_emp` int(11) DEFAULT NULL,
  `carr_id` decimal(2,0) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `registros`
--

INSERT INTO `registros` (`Rg_id`, `sexoh`, `sexom`, `fecha`, `entrada`, `salida`, `aula_id`, `asig_id`, `num_emp`, `carr_id`) VALUES
(1, '11', '5', '2018-04-12', '11:00:00', '22:38:00', 101, '0', 12347, '1');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `registros`
--
ALTER TABLE `registros`
  ADD PRIMARY KEY (`Rg_id`),
  ADD KEY `aula_id_index` (`aula_id`),
  ADD KEY `asig_id_ind` (`asig_id`),
  ADD KEY `ind_num_emp` (`num_emp`),
  ADD KEY `carr_id_ind` (`carr_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `registros`
--
ALTER TABLE `registros`
  MODIFY `Rg_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `registros`
--
ALTER TABLE `registros`
  ADD CONSTRAINT `REGISTROS_ibfk_1` FOREIGN KEY (`aula_id`) REFERENCES `aulas` (`aulaID`),
  ADD CONSTRAINT `REGISTROS_ibfk_2` FOREIGN KEY (`asig_id`) REFERENCES `asignaturas` (`asig_id`),
  ADD CONSTRAINT `REGISTROS_ibfk_3` FOREIGN KEY (`num_emp`) REFERENCES `docentes` (`num_emp`),
  ADD CONSTRAINT `REGISTROS_ibfk_4` FOREIGN KEY (`carr_id`) REFERENCES `carreras` (`carr_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
