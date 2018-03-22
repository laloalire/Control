-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Mar 22, 2018 at 02:27 AM
-- Server version: 10.1.31-MariaDB
-- PHP Version: 7.2.3

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

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `materiasDocente` (`num` INT)  BEGIN
	select nomb nombre from asignaturas where num_emp = num;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `alumnos`
--

CREATE TABLE `alumnos` (
  `ncont` varchar(8) COLLATE utf8mb4_unicode_ci NOT NULL,
  `carr_id` decimal(2,0) DEFAULT NULL,
  `sexo` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `asignaturas`
--

CREATE TABLE `asignaturas` (
  `asig_id` decimal(2,0) NOT NULL,
  `nomb` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `num_emp` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `asignaturas`
--

INSERT INTO `asignaturas` (`asig_id`, `nomb`, `num_emp`) VALUES
('0', 'POO', 12347),
('1', 'Ingenieria de Softwa', 12348),
('2', 'Administracion de BD', 12346),
('3', 'Lenguajes Automatas ', 12347),
('4', 'Redes y conmutacion', 12345),
('6', 'Topicos A. P.', 12347);

-- --------------------------------------------------------

--
-- Table structure for table `aulas`
--

CREATE TABLE `aulas` (
  `aulaID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `aulas`
--

INSERT INTO `aulas` (`aulaID`) VALUES
(101),
(102),
(103),
(104);

-- --------------------------------------------------------

--
-- Table structure for table `carreras`
--

CREATE TABLE `carreras` (
  `carr_id` decimal(2,0) NOT NULL,
  `nombre` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `carreras`
--

INSERT INTO `carreras` (`carr_id`, `nombre`) VALUES
('1', 'Ingenieria en sistem'),
('2', 'Ingenieria industria'),
('3', 'Ingenieria en gestio'),
('4', 'Ingenieria en mecatr');

-- --------------------------------------------------------

--
-- Table structure for table `docentes`
--

CREATE TABLE `docentes` (
  `num_emp` int(11) NOT NULL,
  `nomb` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ap` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `am` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `docentes`
--

INSERT INTO `docentes` (`num_emp`, `nomb`, `ap`, `am`) VALUES
(12345, 'Isaac Neftali', 'Molina', 'Cepeda'),
(12346, 'Saul', 'Garcia', 'Andazola'),
(12347, 'Luis Alberto', 'Grijalva', 'Romero'),
(12348, 'Lilia Margarita', 'Mena', 'Castillo');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `alumnos`
--
ALTER TABLE `alumnos`
  ADD PRIMARY KEY (`ncont`),
  ADD KEY `ind_carrid` (`carr_id`);

--
-- Indexes for table `asignaturas`
--
ALTER TABLE `asignaturas`
  ADD PRIMARY KEY (`asig_id`),
  ADD KEY `num_emp_ind` (`num_emp`);

--
-- Indexes for table `aulas`
--
ALTER TABLE `aulas`
  ADD PRIMARY KEY (`aulaID`);

--
-- Indexes for table `carreras`
--
ALTER TABLE `carreras`
  ADD PRIMARY KEY (`carr_id`);

--
-- Indexes for table `docentes`
--
ALTER TABLE `docentes`
  ADD PRIMARY KEY (`num_emp`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `alumnos`
--
ALTER TABLE `alumnos`
  ADD CONSTRAINT `alumnos_ibfk_1` FOREIGN KEY (`carr_id`) REFERENCES `carreras` (`carr_id`);

--
-- Constraints for table `asignaturas`
--
ALTER TABLE `asignaturas`
  ADD CONSTRAINT `asignaturas_ibfk_1` FOREIGN KEY (`num_emp`) REFERENCES `docentes` (`num_emp`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
