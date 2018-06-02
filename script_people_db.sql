-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 28-12-2015 a las 22:56:51
-- Versión del servidor: 5.6.21
-- Versión de PHP: 5.6.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `people`
--
CREATE DATABASE IF NOT EXISTS `people` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `people`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `contacto`
--

CREATE TABLE IF NOT EXISTS `contacto` (
  `idContacto` varchar(255) NOT NULL,
  `primerNombre` varchar(40) NOT NULL,
  `primerApellido` varchar(40) DEFAULT NULL,
  `telefono` varchar(10) DEFAULT NULL,
  `correo` varchar(254) DEFAULT NULL,
  `idUsuario` int(11) NOT NULL,
  `version` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `contacto`
--

INSERT INTO `contacto` (`idContacto`, `primerNombre`, `primerApellido`, `telefono`, `correo`, `idUsuario`, `version`) VALUES
('C-0a6896ba-d013-4aac-a504-6d06d6407754', 'Ana Cambio ', 'Estivas', '0000000', 'ana_es@mail.com', 2, '2015-12-28 16:55:29');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE IF NOT EXISTS `usuario` (
`idUsuario` int(11) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `contrasena` varchar(128) NOT NULL,
  `claveApi` varchar(60) NOT NULL,
  `correo` varchar(254) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`idUsuario`, `nombre`, `contrasena`, `claveApi`, `correo`) VALUES
(1, 'jamesr', '$2y$10$RTDLiEESF.j8bRZJwHBY4.QvU4hie5bKXF7ZvIvuR6YPdNIWKOAYO', '51f12924bb948d688cc9e79abf0abc7a', 'james@mail.com'),
(2, 'maestro', '$2y$10$8Xn6TT203fO9PWyxyXaL2e4BGmo40sJKyOU4Sz9H4eSZFKoulcyg.', '60d5b4e60cb6a70898f0cd17174e9edd', 'master@mail.com');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `contacto`
--
ALTER TABLE `contacto`
 ADD PRIMARY KEY (`idContacto`), ADD KEY `idUsuario` (`idUsuario`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
 ADD PRIMARY KEY (`idUsuario`), ADD UNIQUE KEY `correo` (`correo`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
MODIFY `idUsuario` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `contacto`
--
ALTER TABLE `contacto`
ADD CONSTRAINT `contacto_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`) ON DELETE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
