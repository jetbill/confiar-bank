Clonar el repositorio:
https://github.com/jetbill/confiar-bank.git
importar el proyecto a IntellijIDEA
abrir una terminal en la raiz del proyecto y ejecutar el comando docker compose up -d
para iniciar el container de Mysql. Lanzar el proyecto e importar la coleccion bank-demo en Postman.



Entidades:

1. Client: Representa la información básica del cliente
2. Account: Representa la cuenta de ahorros de un cliente
3. Transaction: Registra los movimientos (créditos/débitos) de una cuenta

Repositorios:

* ClientRepository
* AccountRepository
* TransactionRepository

Servicios:

* ClientService: Gestiona la creación y consulta de clientes
* AccountService: Crea cuentas de ahorros
* TransactionService: Gestiona los movimientos en las cuentas

Controladores:

* ClientController: Endpoints para crear y consultar clientes
* AccountController: Endpoint para crear cuentas de ahorros
* TransactionController: Endpoint para realizar movimientos

Características implementadas:

* Validación de existencia de cliente
* Verificación de cuentas unicas
* Control de saldo en movimientos
* Uso de transacciones
* Manejo de excepciones básico

Endpoints principales:

1. POST: /api/v1/clients Crear cliente
2. GET: /api/v1/clients/{nit} Consultar cliente por NIT
3. POST /api/v1/accounts: Crear cuenta de ahorros
4. POST /api/v1/transactions/{accountid}: Realizar movimiento

Consideraciones adicionales:

* Se utilizan anotaciones de Spring Boot
* Se implementa una arquitectura por capas
* Se usan relaciones JPA entre entidades
* Se manejan transacciones con @Transactional
* Se utlizan DTOS para enviar objetos a las diferentes capas
* Se hace validacion de objetos de entrada
* Se capturan excepciones basicas
* Pruebas unitarias para los Servicios

