-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 02-07-2019 a las 05:25:21
-- Versión del servidor: 10.3.15-MariaDB
-- Versión de PHP: 7.3.6

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
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `id_cliente` int(11) NOT NULL,
  `nit_cliente` text NOT NULL,
  `nombre_cliente` tinytext NOT NULL,
  `telefono_cliente` int(11) NOT NULL,
  `email_cliente` tinytext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`id_cliente`, `nit_cliente`, `nombre_cliente`, `telefono_cliente`, `email_cliente`) VALUES
(1, '45902', 'juan perez ', 12315543, 'algo@gmail.com'),
(2, '12312', 'luis', 12313, 'luis@gmail.com'),
(3, '09998', 'juan', 3424432, 'juan@gmail.com'),
(4, '34442', 'pedro', 293848, 'pedro@gmail.com'),
(5, '4556', 'juan', 13123, 'algo@gmail.com'),
(6, '657578', 'alberto', 3938485, 'alberto@gmail.com'),
(7, '47902', 'marco', 13123012, 'marco@gmail.com'),
(8, '123112', 'fernando', 2313123, 'fernando@hotmail.com'),
(9, '673610', 'daniel', 23123, 'daniel@yhoo'),
(10, '9998', 'byron', 3423432, 'byron@gmail.com');

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
(19, 21, 15, 1, 450),
(20, 21, 16, 1, 600),
(21, 22, 16, 1, 600);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `dinero`
--

CREATE TABLE `dinero` (
  `idDinero` int(11) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `cantidad` int(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `dinero`
--

INSERT INTO `dinero` (`idDinero`, `nombre`, `cantidad`) VALUES
(1, 'efectivo', 6700),
(2, 'iva', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleados`
--

CREATE TABLE `empleados` (
  `idempleado` int(11) NOT NULL,
  `nombreempleado` varchar(50) NOT NULL,
  `cui` int(30) NOT NULL,
  `telefono` int(30) NOT NULL,
  `nacimiento` varchar(20) NOT NULL,
  `sueldo` int(30) NOT NULL,
  `genero` varchar(20) NOT NULL,
  `estadocivil` varchar(20) NOT NULL,
  `cargar` varchar(50) NOT NULL,
  `contrasena` varchar(20) NOT NULL,
  `nivel_acceso` int(11) NOT NULL,
  `email_empleado` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `empleados`
--

INSERT INTO `empleados` (`idempleado`, `nombreempleado`, `cui`, `telefono`, `nacimiento`, `sueldo`, `genero`, `estadocivil`, `cargar`, `contrasena`, `nivel_acceso`, `email_empleado`) VALUES
(1, 'daniel', 12345, 98765, '2/6/99', 2500, 'Masculino ', 'soltero', 'src\\ImagenesEmpleados\\daniel.jpg', '1234', 2, 'daniel@gmail.com'),
(2, 'pedro', 18887, 657789832, '5/7/98', 2500, 'Masculino ', 'soltero', 'src\\ImagenesEmpleados\\pedro.jpg', '5678', 1, 'pedro@gmail.com');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inventario`
--

CREATE TABLE `inventario` (
  `ID` int(11) NOT NULL,
  `nombre` text CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `costo` float NOT NULL,
  `precio` float NOT NULL,
  `existencia` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `inventario`
--

INSERT INTO `inventario` (`ID`, `nombre`, `costo`, `precio`, `existencia`) VALUES
(9, '3x3x3 Wailong GTS M', 375, 401, 195),
(10, '3x3x3 Valk Power M', 240, 300, 43),
(11, '4x4x4 WuQue mini', 280, 350, 162),
(12, '5x5x5 WuShuang', 320, 350, 247),
(13, '5x5x5 Kirin', 250, 300, 140),
(14, '6x6x6 Shadow', 375, 400, 84),
(15, '6x6x6 Shadow M', 400, 450, 58),
(16, '7x7x7 Hays 7', 545, 600, 97),
(19, 'Square-1 Volt', 120, 180, 28);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `idUsuario` int(11) NOT NULL,
  `nombreU` tinytext NOT NULL,
  `apellidoU` tinytext NOT NULL,
  `contraseña` text NOT NULL,
  `nivel` int(11) NOT NULL,
  `email` tinytext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`idUsuario`, `nombreU`, `apellidoU`, `contraseña`, `nivel`, `email`) VALUES
(1, 'daniel ', 'tistoj', '1234', 1, 'josetisrey@gmail.com');

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
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id_cliente`);

--
-- Indices de la tabla `detalleventa`
--
ALTER TABLE `detalleventa`
  ADD PRIMARY KEY (`num`),
  ADD KEY `venta_id` (`venta_id`),
  ADD KEY `producto_id` (`producto_id`);

--
-- Indices de la tabla `dinero`
--
ALTER TABLE `dinero`
  ADD PRIMARY KEY (`idDinero`);

--
-- Indices de la tabla `empleados`
--
ALTER TABLE `empleados`
  ADD PRIMARY KEY (`idempleado`);

--
-- Indices de la tabla `inventario`
--
ALTER TABLE `inventario`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`idUsuario`);

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
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `id_cliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `detalleventa`
--
ALTER TABLE `detalleventa`
  MODIFY `num` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT de la tabla `dinero`
--
ALTER TABLE `dinero`
  MODIFY `idDinero` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `empleados`
--
ALTER TABLE `empleados`
  MODIFY `idempleado` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `inventario`
--
ALTER TABLE `inventario`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `idUsuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `venta`
--
ALTER TABLE `venta`
  MODIFY `idVenta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detalleventa`
--
ALTER TABLE `detalleventa`
  ADD CONSTRAINT `detalleventa_ibfk_1` FOREIGN KEY (`venta_id`) REFERENCES `venta` (`idVenta`),
  ADD CONSTRAINT `detalleventa_ibfk_2` FOREIGN KEY (`producto_id`) REFERENCES `inventario` (`ID`);

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
