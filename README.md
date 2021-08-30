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

2- Crear Cama UTI: Crea el objeto Cama y lo almacena en la base de datos.

3- Eliminar Cama UTI: Crea el objeto Cama y lo borra de la base de datos.

4- Ocupar Cama UTI: Crea el objeto Cama y lo actualiza en la base de datos, cambiando su campo a “ocupado”.

5- Desocupar Cama UTI: Crea el objeto Cama y lo actualiza en la base de datos, cambiando su campo a “desocupado”.

6- Desconectar el servidor: Se encarga de finalizar la ejecución del programa servidor.

7- Revisar log: Se encarga de mostrar el registro de las operaciones realizadas por el servidor en memoria principal, imprimiendo los resultados en el programa servidor.

### Especificar la forma de invocación y parámetros de cada servicio ofrecido por el servidor.
Para invocar cada servicio debe ejecutar el servidor y el cliente, desde el cliente se ingresa el nombre del hospital y luego se despliega los servicios ofrecidos, se elige la opción que se quiere como se muestra en Cómo compilar y ejecutar el/los clientes.
El servidor retorna un String en formato JSON con la siguiente estructura:
* estado, donde:
    "0" corresponde a una transacción exitosa.
    "-1" transacción errónea.
* mensaje:
    Palabra “ok” si no existe error.
    El detalle del error si existe o “Error”.
* tipo_operacion:
    1: ver_estado
    2: crear_cama
    3: eliminar_cama
    4: ocupar_cama
    5: desocupar_cama
    6:desconectar_servidor
    7:Imprimir_log

Todas las opciones poseen los mismos 4 parámetros para ser ejecutadas.
Estos parámetros son: opción, nombre del hospital, número de cama y estado de la cama.

Sin embargo, algunas opciones colocan los campos de nombre del hospital, número de cama y estado de la cama en NULL, sin importar qué escriba el cliente. Detallaremos cada caso:

1- Ver el estado actual de todos los hospitales: Recibe los 4 parámetros. Convierte en NULL aquellos relacionados al nombre del hospital, número de cama y estado de la cama.

2- Crear Cama UTI: Recibe los 4 parámetros y  los utiliza para crear una cama UTI.

3- Eliminar Cama UTI: Recibe los 4 parámetros y los utiliza para eliminar una cama UTI.

4- Ocupar Cama UTI: Recibe los 4 parámetros y los utiliza para ocupar una cama UTI.

5- Desocupar Cama UTI: Recibe los 4 parámetros y los utiliza para desocupar la cama UTI.

6- Desconectar el servidor: Recibe los 4 parámetros, incluyendo los 3 parámetros del hospital: hospital, número de cama y estado, colocándolos en NULL. Luego termina el proceso en el servidor.

7- Revisar log: Recibe los 3 parámetros del hospital: hospital, número de cama y estado, colocándolos en NULL. Luego muestra el registro de operaciones en memoria principal en el programa servidor.

### Cómo compilar y ejecutar el/los clientes.

Una vez dentro del IDE compilamos y runeamos el UDPServer.java del proyecto.

![imagen](https://user-images.githubusercontent.com/88010175/131272624-19b2459f-9593-4d74-b080-c9f6118d34a0.png)


Hecho esto, podemos ejecutar la aplicación del cliente llamada “UDPClient” del proyecto que se encuentra en src\main\java\py\una\cliente, que nos lleva al siguiente menú, nos pide que ingresemos el nombre del hospital para luego desplegar las opciones de ver los estados de la cama, los ABM, desconectar el servidor, etc.
Puede ejecutar varios clientes en simultáneo.

![imagen](https://user-images.githubusercontent.com/88010175/131272645-9cac9fb0-0967-4e11-8819-856a8c6bb1e6.png)

Si deseamos crear una cama en UTI nos pide el número de la cama y el estado de la misma.

![imagen](https://user-images.githubusercontent.com/88010175/131272681-70827d00-ccaa-45f3-90ec-1f43c7f9bc93.png)

Una vez hecho esto, corroboramos viendo el estado actual con la opción 1

![imagen](https://user-images.githubusercontent.com/88010175/131272708-70fec4a1-bb85-4df5-9e53-d23cdb9aa627.png)

Y finalmente si queremos eliminar una cama podemos hacerlo con la opción 3, ingresando el número de cama a eliminar.

![imagen](https://user-images.githubusercontent.com/88010175/131272743-d9c80ace-f3a2-42a4-acb2-84a80e5aac95.png)

Si queremos desocupar una cama, entramos en la opción 4

![imagen](https://user-images.githubusercontent.com/88010175/131272784-ef375e49-4a92-47c4-975a-648bbb302568.png)

Ejecutamos la opción y seleccionamos la cama a desocupa

![imagen](https://user-images.githubusercontent.com/88010175/131272821-d9c39f51-250c-4599-8bdd-37d530254edc.png)

Resultado en la base de datos

![imagen](https://user-images.githubusercontent.com/88010175/131272857-551b8053-b735-422d-a644-a647139a9e25.png)

Desconectarse del servidor: Una vez realizado todas las operaciones que deseemos, nos desconectamos del server con la opción 6

![imagen](https://user-images.githubusercontent.com/88010175/131272881-49cd4445-c9f6-4961-a8e6-9558493cbe37.png)





