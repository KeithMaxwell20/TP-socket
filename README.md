# Sistema Cliente/Servidor para gestión de Camas UTI vía socket UDP
### Universidad Nacional de Asunción - Facultad Politécnica
## Trabajo Grupal de Sistemas Ditribuidos
## Ingeniería Informática

### Colaboradores
* Alejandro Adorno
* Jessica Alarcón
* Romina Alfonzo 
* Victor Ayala
* Matías Franco

### Requerimientos
* Postgresql.
* Visual Studio Code (Extensión de Code Run) o NetBeans IDE.
* Lenguaje Java.
* Java JDK 8.

### Cómo abrir el proyecto en Netbeans.
Ingrese en la IDE y abra la carpeta TP-Socket como proyecto, te reconocerá como un proyecto de java.

### Cómo abrir el proyecto en VSC.
Ingrese en el editor y abra la carpeta TP-Socket.

### Creación de la estructura de datos
* Iniciar un servidor en postgresql desde su Shell, registrar el puerto, pues se utilizará.
* Usar pgAdmin 4 para crear la base de datos
* Crear la base de datos llamada “TP-Sockets”
* Añadir una tabla llamada “camas”
* Añadir los siguientes campos: “hospital” de tipo character varying, “cama” de tipo character varying y “estado” de tipo character varying.

### Población de los datos iniciales necesarios de Base de Datos
Ingrese el puerto de la base de datos y su contraseña aquí:

![imagen](https://user-images.githubusercontent.com/88010175/131271235-eda4248a-50d6-44db-8286-78aca883beee.png)
Para poblar la base de datos con valores iniciales, siga esa estructura para una mejor organización de la DB, ejecute el archivo TestCamaDAO que se encuentra en la carpeta src\main\java\py\una\bd.

### Compilar y ejecutar 
Copiar el repositorio

![imagen](https://user-images.githubusercontent.com/88010175/131270398-8bc40fe3-d7e6-4b5f-bb50-410c5d8bd528.png)

Ejecutar el comando

    & git clone https://github.com/KeithMaxwell20/TP-socket.git

El servidor se encuentra en src\main\java\py\una\server\udp, si está ejecutando en NetBeans tiene que darle a Run.
Si se encuentra en VSC tiene que darle a Run Java (aparece con Code Run).

Hecho esto, podemos ejecutar la aplicación del cliente llamada “UDPClient” del proyecto que se encuentra en src\main\java\py\una\cliente, que nos lleva al siguiente menú, nos pide que ingresemos el nombre del hospital para luego desplegar las opciones de ver los estados de la cama, los ABM, desconectar el servidor, etc.

![imagen](https://user-images.githubusercontent.com/88010175/131270872-8e418357-0a56-4480-9dd8-c08f04c60915.png)

### Documentación de un API de servicios ofrecidos por el Servidor.
El servidor cuenta con la capacidad para manejar una variedad de peticiones que puede solicitar el cliente. Listamos la totalidad de las operaciones que pueden ser realizadas:

1- Ver el estado actual de todos los hospitales: Esta operación lista todos los hospitales almacenados en la base de datos.
2- Crear Cama UTI: Crea el objeto Cama y lo almacena en la base de datos
3- Eliminar Cama UTI: Crea el objeto Cama y lo borra de la base de datos.
4- Ocupar Cama UTI: Crea el objeto Cama y lo actualiza en la base de datos, cambiando su campo a “ocupado”.
5- Desocupar Cama UTI: Crea el objeto Cama y lo actualiza en la base de datos, cambiando su campo a “desocupado”.
6- Desconectar el servidor: Se encarga de finalizar la ejecución del programa servidor.
7- Revisar log: Se encarga de mostrar el registro de las operaciones realizadas por el servidor en memoria principal, imprimiendo los resultados en el programa servidor.

### Documentación TP-Sockets
https://docs.google.com/document/d/1PNgfD7ImLgAtXumwQ-XTzcml3ikComTWwn59j0DMva4/edit
