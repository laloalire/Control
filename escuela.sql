-- phpMyAdmin SQL Dump
-- version 4.8.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: May 09, 2018 at 01:55 AM
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

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `cambiarPass` (IN `contra` VARCHAR(128))  NO SQL
update admins set pass = SHA2('admin', 512)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `checkarAdmin` (IN `contra` VARCHAR(128))  NO SQL
select count(idadmin) from admins where pass = sha2(contra, 512)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `clasesEnCurso` ()  NO SQL
select Rg_id, CONCAT(D.nomb," ", D.ap," ", D.am) maestro, A.nomb asignatura, entrada, salida , aula_id aula from registros, docentes D, asignaturas A where fecha = CURDATE() and entrada < CURTIME() and salida > CURTIME() and registros.num_emp = D.num_emp and registros.asig_id = A.asig_id$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `eliminarRegistro` (`regid` INT)  BEGIN
delete from registroalumno where reg_Id = regid;
delete from registros where Rg_id = regid;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getDatosLista` (IN `reg_idd` INT)  BEGIN
select registros.fecha, asignaturas.nomb, carreras.nombre,grupo
from registros,asignaturas,carreras,alumnos where 
registros.rg_id=reg_idd and asignaturas.asig_id=registros.asig_id and carreras.carr_id=asignaturas.carrera and
alumnos.ncont=(select ncont from registroalumno,registros where registroalumno.reg_id=registros.rg_id limit 1);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getInfRegistros` ()  begin
select registros.aula_id,registros.fecha,registros.entrada,registros.salida,docentes.nomb,docentes.ap,docentes.am, asignaturas.nomb, carreras.nombre,registros.sexoh,registros.sexom
from registros, asignaturas,carreras,docentes where registros.num_emp=docentes.num_emp and asignaturas.carrera=carreras.carr_id and registros.asig_id=asignaturas.asig_id;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getLista` (`reg_idd` NUMERIC)  begin
select concat (alumnos.ap ," ",alumnos.am, " ",alumnos.Nombre) 'Nombre' from alumnos, registroalumno where registroalumno.ncont=alumnos.ncont and registroalumno.reg_id=reg_idd;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getRegistros` (IN `num_empp` INT, IN `fechainicio` DATE, IN `fechafin` DATE)  BEGIN
select rg_id from registros where num_emp=num_empp and fecha between fechainicio and fechafin;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `insertarAdmin` (IN `contra` VARCHAR(128))  NO SQL
insert into admins(pass) values(SHA2(contra, '512'))$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `materiasDocente` (IN `num` INT)  BEGIN
	select asig_id, nomb nombre from asignaturas where num_emp = num;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `registrarAlumno` (IN `regID` INT, IN `numControl` VARCHAR(8))  NO SQL
insert into registroalumno values (regID, numControl)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `revisarAlumno` (IN `numcontrol` VARCHAR(8))  begin 
 DECLARE cuenta int;
 set cuenta = (select count(ncont) from alumnos where ncont = numcontrol);
 if(cuenta > 0) then 
 SELECT 1;
 end IF;
 end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `revisarAlumnoEnClase` (IN `numCont` VARCHAR(8))  NO SQL
select reg_id, nomb, entrada, salida, aula_id from registroalumno ra, registros r, asignaturas a where ra.reg_id = r.Rg_id and r.asig_id = a.asig_id and fecha = CURRENT_DATE and entrada < now() and salida > now() and ra.ncont = numCont$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `revisarDocenteEnClase` (IN `numero` INT)  NO SQL
select Rg_id, salida, entrada, nomb from registros, asignaturas where registros.num_emp = numero and fecha = CURRENT_DATE and entrada < now() and salida > now() and asignaturas.asig_id = registros.asig_id$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `admins`
--

CREATE TABLE `admins` (
  `idadmin` int(11) NOT NULL,
  `pass` varchar(128) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `admins`
--

INSERT INTO `admins` (`idadmin`, `pass`) VALUES
(1, 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec');

-- --------------------------------------------------------

--
-- Table structure for table `alumnos`
--

CREATE TABLE `alumnos` (
  `ncont` varchar(8) COLLATE utf8mb4_unicode_ci NOT NULL,
  `carr_id` decimal(2,0) DEFAULT NULL,
  `sexo` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `grupo` varchar(3) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Ap` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Am` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Nombre` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `alumnos`
--

INSERT INTO `alumnos` (`ncont`, `carr_id`, `sexo`, `grupo`, `Ap`, `Am`, `Nombre`) VALUES
('15CG0011', '1', 'M', '6AM', 'PEÑA', 'RAMIREZ', 'YADIRA'),
('15CG0012', '1', 'H', '6AM', 'SILVEIRA', 'HINOJOS', 'CARLOS ALBERTO'),
('15CG0013', '1', 'M', '6AM', 'VENEGAS', 'VALDEZ', 'MARIANA'),
('15CG0034', '1', 'H', '6AM', 'ACOSTA', 'TREVIZO', 'RUBEN ARMANDO'),
('15CG0035', '1', 'H', '6AM', 'ALONSO', 'ARAGON', 'BRENDA JOANNA'),
('15CG0036', '1', 'M', '6AM', 'ESTALA', 'ENRIQUEZ', 'PAUL ALBERTO'),
('15CG0110', '1', 'm', '6AM', 'GARCIA', 'RAMIREZ', 'GERARDO'),
('15CG0146', '1', 'H', '6AM', 'CHAPARRO', 'ALIRE', 'EDUARDO'),
('15CG0192', '2', 'm', '6AM', 'ALVAREZ', 'MARTINEZ', 'MIGUEL ANGEL'),
('15CG0199', '1', 'h', '6AM', 'ESCOBEDO', 'SANCHEZ', 'COSME OMAR');

-- --------------------------------------------------------

--
-- Table structure for table `asignaturas`
--

CREATE TABLE `asignaturas` (
  `asig_id` decimal(2,0) NOT NULL,
  `nomb` varchar(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `num_emp` int(11) DEFAULT NULL,
  `carrera` decimal(2,0) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `asignaturas`
--

INSERT INTO `asignaturas` (`asig_id`, `nomb`, `num_emp`, `carrera`) VALUES
('0', 'Programacion orientada a objetos', 12347, '1'),
('1', 'Ingenieria de Software', 12348, '1'),
('2', 'Administracion de BD', 12346, '1'),
('3', 'Lenguajes Automatas ', 12347, '1'),
('4', 'Redes y conmutacion', 12345, '1'),
('6', 'Topicos Avanzados de Programacion', 12347, '1');

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
  `nombre` varchar(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `carreras`
--

INSERT INTO `carreras` (`carr_id`, `nombre`) VALUES
('1', 'Ingenieria en sistemas computacionales'),
('2', 'Ingenieria industrial'),
('3', 'Ingenieria en gestion empresarial'),
('4', 'Ingenieria en mecatronica');

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

-- --------------------------------------------------------

--
-- Table structure for table `registroalumno`
--

CREATE TABLE `registroalumno` (
  `reg_id` int(11) NOT NULL,
  `ncont` varchar(8) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `registroalumno`
--

INSERT INTO `registroalumno` (`reg_id`, `ncont`) VALUES
(1, '15CG0146'),
(1, '15CG0199'),
(1, '15CG0011'),
(1, '15CG0012'),
(1, '15CG0013'),
(1, '15CG0034'),
(1, '15CG0035'),
(1, '15CG0036'),
(1, '15CG0110'),
(1, '15CG0146'),
(1, '15CG0192'),
(1, '15CG0199');

--
-- Triggers `registroalumno`
--
DELIMITER $$
CREATE TRIGGER `deleteRegistroAlumno` BEFORE DELETE ON `registroalumno` FOR EACH ROW begin 
declare genero varchar(8);
SELECT sexo from alumnos where alumnos.ncont = OLD.ncont into genero;
if(genero = 'h') then 
update registros set sexoh = sexoh - 1 where Rg_id = OLD.reg_id;
elseif(genero = 'm') then 
update registros set sexom = sexom -1 where Rg_id = OLD.reg_id; 
end IF;
end
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `insertRegistroAlumnos` AFTER INSERT ON `registroalumno` FOR EACH ROW begin 
declare genero varchar(8);
SELECT sexo from alumnos where alumnos.ncont = NEW.ncont into genero;
if(genero = 'h') then 
update registros set sexoh = sexoh + 1 where Rg_id = NEW.reg_id;
elseif(genero = 'm') then 
update registros set sexom = sexom +1 where Rg_id = NEW.reg_id;
end IF;
end
$$
DELIMITER ;

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
(1, '23', '5', '2018-04-12', '11:00:00', '22:38:00', 101, '0', 12347, '1'),
(2, '7', '0', '2018-05-08', '08:00:00', '09:00:00', 101, '3', 12347, NULL),
(10, '0', '0', '2018-05-08', '19:00:00', '20:00:00', 102, '4', 12345, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admins`
--
ALTER TABLE `admins`
  ADD PRIMARY KEY (`idadmin`);

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
  ADD KEY `num_emp_ind` (`num_emp`),
  ADD KEY `carrera` (`carrera`);

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
-- Indexes for table `registroalumno`
--
ALTER TABLE `registroalumno`
  ADD KEY `reg_id` (`reg_id`),
  ADD KEY `ncont` (`ncont`);

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
-- AUTO_INCREMENT for table `admins`
--
ALTER TABLE `admins`
  MODIFY `idadmin` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `registros`
--
ALTER TABLE `registros`
  MODIFY `Rg_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

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
  ADD CONSTRAINT `asignaturas_ibfk_1` FOREIGN KEY (`num_emp`) REFERENCES `docentes` (`num_emp`),
  ADD CONSTRAINT `asignaturas_ibfk_2` FOREIGN KEY (`carrera`) REFERENCES `carreras` (`carr_id`);

--
-- Constraints for table `registroalumno`
--
ALTER TABLE `registroalumno`
  ADD CONSTRAINT `registroAlumno_ibfk_1` FOREIGN KEY (`reg_id`) REFERENCES `registros` (`Rg_id`),
  ADD CONSTRAINT `registroAlumno_ibfk_2` FOREIGN KEY (`ncont`) REFERENCES `alumnos` (`ncont`);

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
