# Agents_e1b

[![Join the chat at https://gitter.im/Agents_e1b](https://badges.gitter.im/Agents_e1b.svg)](https://gitter.im/Agents_e1b/Lobby?utm_source=share-link&utm_medium=link&utm_campaign=share-link)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/1d7cb9ab12dd4230a9a1ccdc3a723185)](https://www.codacy.com/app/jelabra/Agents_e1b?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Arquisoft/Agents_e1b&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/Arquisoft/Agents_e1b.svg?branch=master)](https://travis-ci.org/Arquisoft/Agents_e1b)
[![codecov](https://codecov.io/gh/Arquisoft/Agents_e1b/branch/master/graph/badge.svg)](https://codecov.io/gh/Arquisoft/Agents_e1b)

**Bienvenidos a nuestro modulo de agentes**
 
Este módulo forma parte de un sistema informático de gestión de incidencias, donde son los agentes los que emiten estas al sistema.


## Autores (2016/2017)

- David Lorenzo González (UO244795)
- Martín Peláez Díaz (UO236974)
- Laura Menéndez Pérez (UO244646)
- Damián Rubio Cuervo (@DamianRubio)
- Fernando Palazuelo Ginzo (UO244588)

## Autores (2017/2018)
- Esteban Montes Morales (UO246305)
- Lucía de la Granda Prieto (UO251626)
- Pablo Menéndez Suárez (UO252406)
- Sara Grimaldos Rodríguez (UO251782)


## Sobre el proyecto

### Requerimientos del sistema
El proyecto ha sido desarrollado utilizando Java 1.8, por tanto es multiplataforma y debería ejecutarse sin problema tanto en Windows, MacOS y Linux.

#### JPA  ( Java Persistance API )
Framework de java para manejar datos relacionales que persigue el diseño de esta API es no perder las ventajas de la orientación a objetos al interactuar con una base de datos (siguiendo el patrón de mapeo objeto-relacional).
Para más información: [https://es.wikipedia.org/wiki/Java_Persistence_API](https://es.wikipedia.org/wiki/Java_Persistence_API)

#### OpenCSV
Libreria de código abierto para la manipulación de ficheros CSV, (comma-separated values), para más información: [http://opencsv.sourceforge.net/](http://opencsv.sourceforge.net/)


#### Apache Common Validator
Librería de apache dedicada a la validación de datos, concretamente usado en nuestro proyecto para la validación de los correos electrónicos.
Para más información: [https://commons.apache.org/proper/commons-validator/](https://commons.apache.org/proper/commons-validator/)

#### Apache Maven
Un software para gestion de proyectos de software entre otras cosas, lo estamos usando para gestionar todas las dependencias que necesite el proyecto. Para más información: [https://maven.apache.org/](https://maven.apache.org/)

#### Spring Boot
Un framework inspirado en Spring que nos ha servido para ejecutar la aplicación web,. Para más información: [https://projects.spring.io/spring-boot/](https://projects.spring.io/spring-boot/)

### Guía rápida
Para comenzar a usar la aplicación descarga el proyecto o bien clonalo
```
git clone https://github.com/Arquisoft/Agents_e1b.git
```

Una vez descargado el proyecto, puedes lanzarlo de dos formas, o bien directamente desde una terminal usando maven, o desde tu IDE favorito, en nuestro caso eclipse.

### Desde eclipse
Sigue los siguientes pasos:
1. Abre eclipse.
2. Selecciona la opción de **Importar un proyecto de Maven existente**.
3. Situate en **Application.java**.
4. Abre las opciones desplegables con el botón derecho del ratón y clickea sobre **Ejecutar como aplicación java**.

### Desde la consola
Sigue los siguientes pasos:
```
mvn compile
```
Una vez compile, ejecuta:
```
mvn spring-boot:run
```

De cualquier manera, al acceder a [localhost:8080](http://localhost:8080) tendrás acceso a la aplicación web.

### ¿Cómo funciona la aplicación REST?

Para obtener los datos del agente, puedes hacer una petición POST a [localhost:8080/user](http://localhost:8080/user), con el siguiente formato:

##### JSON
```json
{"ident":"identificador del agente", "password":"contraseña del agente", "kind":"Código del tipo de agente"}
```

##### XML
```xml
<data>
 <ident>Identificador del agente</ident>
 <password>Contraseña del agente</password>
 <kind>Tipo del agente</kind>
</data>
```
Esto debería devolverte una salida en JSON tal que esta que vamos a simular utilizando Curl:

```
curl -H "Content-Type: application/json" -X POST -d '{"ident":"entidad1", "password":"123456", "kind":2'} http://localhost:8080/user
```

Valor de retorno:
```json
{  
   "name":"Valgrande Pajares",
   "location":"43.5479621,-5.9304147",
   "email":"pajares@hotmail.com",
   "id":"entidad1",
   "kind":"Entity",
   "kindCode":2
}
```

Usuarios de prueba:

- Nombre:Paco Contraseña:123456 Email:paco@hotmail.com Identificador:12345678P Localización:43.5479621,-5.9304147 Tipo:1

- Nombre:Pepe Contraseña:123456 Email:pepe@hotmail.com Identificador:12345678A Localización:43.5479621,-5.9304147 Tipo:1

- Nombre:Valgrande Pajares Contraseña:123456 Email:pajares@hotmail.com Identificador:entidad1 Localización:43.5479621,-5.9304147 Tipo:2

- Nombre:Estación Fuentes De Invierno Contraseña:123456 Email:fuentes@hotmail.com Identificador:entidad2 Localización:43.5479621,-5.9304147 Tipo:2

- Nombre:SensorTemperatura Contraseña:123456 Email:sensorT@hotmail.com Identificador:sensor1 Localización:43.5479621,-5.9304147 Tipo:3

- Nombre:SensorHumedad Contraseña:123456 Email:sensorH@hotmail.com Identificador:sensor2 Localización:43.5479621,-5.9304147 Tipo:3
