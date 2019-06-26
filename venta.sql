-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 26-06-2019 a las 22:25:22
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
-- Estructura de tabla para la tabla `venta`
--

CREATE TABLE `venta` (
  `idVenta` int(11) NOT NULL,
  `Usuario_id` int(11) NOT NULL,
  `cliente_id` int(11) NOT NULL,
  `Total_venta` float NOT NULL,
  `FechaHora` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `venta`
--

INSERT INTO `venta` (`idVenta`, `Usuario_id`, `cliente_id`, `Total_venta`, `FechaHora`) VALUES
(1, 1, 1, 0, ''),
(2, 1, 1, 0, ''),
(3, 1, 1, 0, '2019-06-18 09:37:30'),
(4, 1, 1, 0, '2019-06-18 09:43:34'),
(6, 1, 1, 0, '2019-06-18 21:45:29'),
(7, 2, 1, 0, '2019-06-19 16:17:43'),
(8, 2, 1, 350, '2019-06-19 16:55:12'),
(9, 2, 1, 300, '2019-06-19 16:58:57'),
(10, 1, 1, 400, '2019-06-19 16:59:57'),
(11, 1, 1, 180, '2019-06-19 18:22:59'),
(12, 1, 7, 350, '2019-06-19 23:10:38'),
(13, 1, 7, 300, '2019-06-20 00:04:00'),
(14, 2, 1, 580, '2019-06-20 00:44:36'),
(15, 2, 2, 300, '2019-06-21 16:43:04'),
(16, 2, 8, 600, '2019-06-21 16:45:21'),
(18, 2, 2, 350, '2019-06-21 17:20:09'),
(19, 2, 2, 1050, '2019-06-21 17:28:09'),
(20, 1, 9, 540, '2019-06-21 19:29:21'),
(21, 1, 1, 450, '2019-06-26 10:29:10'),
(22, 2, 1, 600, '2019-06-26 14:08:03');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `venta`
--
ALTER TABLE `venta`
  ADD PRIMARY KEY (`idVenta`),
  ADD KEY `idUsuario` (`Usuario_id`),
  ADD KEY `cliente_id` (`cliente_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `venta`
--
ALTER TABLE `venta`
  MODIFY `idVenta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `venta`
--
ALTER TABLE `venta`
  ADD CONSTRAINT `venta_ibfk_3` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id_cliente`) ON DELETE CASCADE,
  ADD CONSTRAINT `venta_ibfk_4` FOREIGN KEY (`Usuario_id`) REFERENCES `empleados` (`idempleado`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
