# BookMatchApp

## Descripción

BookMatchApp es una aplicación basada en Java que se conecta a la API de Gutendex para buscar libros por título, listar libros y autores guardados, y realizar varias operaciones relacionadas con libros y autores. La aplicación proporciona una interfaz de consola para la interacción del usuario.

## Funcionalidades

- Buscar libros por título utilizando la API de Gutendex.
- Listar libros guardados.
- Listar autores guardados.
- Listar autores vivos en un año especificado.
- Listar libros por idioma.
- Listar libros por autor.
- Listar los 10 libros más descargados.

## Requisitos Previos

- Java 17 o superior
- Maven 3.6.3 o superior
- PostgreSQL 14 o superior

## Configuración del Proyecto

1. **Clonar el repositorio:**
   ```bash
   git clone <URL_DEL_REPOSITORIO>
   cd bookmatch_app

2. **Configurar la base de datos:**

- Crear una base de datos en PostgreSQL.
- Actualizar las propiedades de conexión a la base de datos en el archivo application.properties:
  ```bash
  spring.datasource.url=jdbc:postgresql://localhost:5432/<NOMBRE_DE_TU_BD>
  spring.datasource.username=<TU_USUARIO>
  spring.datasource.password=<TU_CONTRASEÑA>
  spring.jpa.hibernate.ddl-auto=update

## Uso
- Una vez iniciada la aplicación, se mostrará un menú en la consola con las siguientes opciones:
    ```bash

    Elige una opcion:

  1. Buscar libro por titulo
  2. Listar libros guardados
  3. Listar autores guardados
  4. Listar autores vivos en un determinado año
  5. Listar libros por idioma
  6. Listar libros por autor
  7. Listar 10 más descargados registrados

  0. Salir
 
- Selecciona una opción e ingresa la información solicitada para interactuar con la aplicación.

## Clases Principales
**Principal** 
- La clase principal que contiene el menú y las opciones para interactuar con la aplicación.

**ConsumoAPI**
- Clase encargada de consumir la API de Gutendex.

**ConvierteDatos**
- Clase que convierte los datos JSON obtenidos de la API en objetos Java.

**RepositorioAutor**
- Interfaz de repositorio para manejar las operaciones CRUD relacionadas con los autores.

**Modelos**
- Clases que representan los modelos de datos: Autor, Titulo, DatosConsulta, etc.

## API Utilizada
- Se utilizó la API de Gutendex para obtener la información de los libros. [Gutendex API](https://gutendex.com/)