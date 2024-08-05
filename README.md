# API para Tienda en Línea

## Descripción

Esta API proporciona una solución completa para gestionar una tienda en línea. Permite manejar productos, categorías, clientes, pedidos y pagos, y soporta operaciones CRUD para todas las entidades. La API está diseñada para ofrecer una experiencia de compra completa.

## Características

- **Categorías**: Gestión de categorías de productos.
- **Productos**: Manejo de productos, incluyendo atributos como nombre, precio, stock, etc.
- **Clientes**: Administración de información de clientes.
- **Pedidos**: Gestión de pedidos, incluyendo detalles y estado.
- **Detalles del Pedido**: Manejo de los productos incluidos en cada pedido.
- **Pagos**: Registro de pagos asociados a pedidos.

## Endpoints

### Categorías

- **Crear Categoría**: `POST /api/categories`
- **Leer Categorías**: `GET /api/categories`
- **Leer Categoría por ID**: `GET /api/categories/{id}`
- **Actualizar Categoría**: `PUT /api/categories/{id}`
- **Eliminar Categoría**: `DELETE /api/categories/{id}`

### Productos

- **Crear Producto**: `POST /api/products`
- **Leer Productos**: `GET /api/products`
- **Leer Producto por ID**: `GET /api/products/{id}`
- **Actualizar Producto**: `PUT /api/products/{id}`
- **Eliminar Producto**: `DELETE /api/products/{id}`

### Clientes

- **Crear Cliente**: `POST /api/customers`
- **Leer Clientes**: `GET /api/customers`
- **Leer Cliente por ID**: `GET /api/customers/{id}`
- **Actualizar Cliente**: `PUT /api/customers/{id}`
- **Eliminar Cliente**: `DELETE /api/customers/{id}`

### Pedidos

- **Crear Pedido**: `POST /api/orders`
- **Leer Pedidos**: `GET /api/orders`
- **Leer Pedido por ID**: `GET /api/orders/{id}`
- **Actualizar Pedido**: `PUT /api/orders/{id}`
- **Eliminar Pedido**: `DELETE /api/orders/{id}`

### Detalles del Pedido

- **Crear Detalle del Pedido**: `POST /api/orderdetails`
- **Leer Detalles del Pedido**: `GET /api/orderdetails`
- **Leer Detalle del Pedido por ID**: `GET /api/orderdetails/{id}`
- **Actualizar Detalle del Pedido**: `PUT /api/orderdetails/{id}`
- **Eliminar Detalle del Pedido**: `DELETE /api/orderdetails/{id}`

### Pagos

- **Crear Pago**: `POST /api/payments`
- **Leer Pagos**: `GET /api/payments`
- **Leer Pago por ID**: `GET /api/payments/{id}`
- **Actualizar Pago**: `PUT /api/payments/{id}`
- **Eliminar Pago**: `DELETE /api/payments/{id}`

## Documentación Swagger
La API está documentada usando Swagger, que proporciona una interfaz interactiva para explorar y probar los endpoints de la API.

# Acceso a la Documentación Swagger
URL de Swagger UI: http://localhost:8080/swagger-ui/index.html
Nota: Asegúrate de que tu aplicación esté en ejecución y accesible en el puerto especificado para poder acceder a la interfaz Swagger.

# Funcionalidades de Swagger UI
Explorar Endpoints: Navega por todos los endpoints disponibles y consulta sus descripciones.
Probar Endpoints: Realiza solicitudes a los endpoints directamente desde la interfaz.
Ver Modelos: Examina las estructuras de datos utilizadas en los requests y responses.
Para más detalles sobre cómo usar Swagger UI, consulta la documentación de Swagger.


