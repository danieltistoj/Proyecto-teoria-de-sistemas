-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 26-06-2019 a las 19:52:20
-- Versión del servidor: 10.1.40-MariaDB
-- Versión de PHP: 7.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `tienda`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalleventa`
--

CREATE TABLE `detalleventa` (
  `num` int(11) NOT NULL,
  `venta_id` int(11) NOT NULL,
  `producto_id` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `Total` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `detalleventa`
--

INSERT INTO `detalleventa` (`num`, `venta_id`, `producto_id`, `cantidad`, `Total`) VALUES
(2, 3, 10, 0, 0),
(3, 3, 14, 0, 0),
(4, 4, 11, 0, 0),
(5, 4, 13, 0, 0),
(6, 11, 11, 0, 0),
(7, 11, 13, 0, 0),
(8, 12, 11, 1, 350),
(9, 13, 10, 1, 300),
(10, 14, 14, 1, 400),
(11, 14, 19, 1, 180),
(12, 15, 13, 1, 300),
(13, 16, 13, 1, 300),
(14, 16, 10, 1, 300),
(15, 18, 11, 1, 350),
(16, 19, 11, 1, 350),
(17, 19, 12, 2, 700),
(18, 20, 19, 3, 540),
(19, 21, 15, 1, 450);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `detalleventa`
--
ALTER TABLE `detalleventa`
  ADD PRIMARY KEY (`num`),
  ADD KEY `venta_id` (`venta_id`),
  ADD KEY `producto_id` (`producto_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `detalleventa`
--
ALTER TABLE `detalleventa`
  MODIFY `num` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detalleventa`
--
ALTER TABLE `detalleventa`
  ADD CONSTRAINT `detalleventa_ibfk_1` FOREIGN KEY (`venta_id`) REFERENCES `venta` (`idVenta`),
  ADD CONSTRAINT `detalleventa_ibfk_2` FOREIGN KEY (`producto_id`) REFERENCES `inventario` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
