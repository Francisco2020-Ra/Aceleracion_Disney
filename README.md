# alkemy-disney

Buenas!!
Mi nombre es Francisco Rafael Romero esto es un proyecto de Alkemy Disney
Se ejecuta en el puerto:8080
base de datos: MySQL

Para la utenticacion de los usuarios se establecieron roles y solo el administrador tiene acceso total a todas 
la funcionalidades.
Para poder hacer uso de dichos roles, primeramente se deben ingresar a la base de datos con los siguientes comando

INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');

Una vez ingresados, puede comenzar a hacer uso de la api.

La api cuenta con las siguientes capas
La de Controllers, Servicios y Repository,

Los datos ingresar en formado dto a traves del controller
El controller envia los datos a la capa de Servicios donde se mapean y se los convierte a formato Entity y viceversa 
para poder guardarlos o traerlos de la base de datos.