# 📚 Literalura - Catálogo de Libros

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)
![JPA](https://img.shields.io/badge/JPA-Hibernate-red)
![Jackson](https://img.shields.io/badge/Jackson-JSON-yellow)
![Maven](https://img.shields.io/badge/Maven-Build-C71A22)

Bienvenido a **Literalura**, una aplicación de consola desarrollada en Java con Spring Boot que te permite explorar, consultar y almacenar información sobre libros y autores clásicos.

Este proyecto fue construido como parte del Challenge del programa Oracle Next Education (ONE) en colaboración con Alura Latam, con el objetivo de consumir una API externa, manipular datos JSON y persistirlos en una base de datos relacional.

## 🚀 Funcionalidades

La aplicación cuenta con un menú interactivo en consola que ofrece las siguientes opciones:

1. **Buscar libro por título:** Consulta la API de Gutendex y guarda automáticamente la información del libro y su autor en la base de datos local.
2. **Listar libros registrados:** Muestra todos los libros que han sido buscados y guardados en tu base de datos local.
3. **Listar autores registrados:** Muestra una lista de todos los autores almacenados y los libros asociados a cada uno.
4. **Listar autores vivos en un determinado año:** Filtra la base de datos para encontrar autores contemporáneos a un año específico.
5. **Listar libros por idioma:** Permite filtrar los libros guardados según su idioma original (ES, EN, FR, PT).
6. **Generar estadísticas de descargas:** Utiliza Streams de Java para calcular el promedio, máximo y mínimo de descargas de los libros registrados.
7. **Top 10 libros más descargados:** Muestra un ranking de los libros más populares en tu base de datos.
8. **Buscar autor por nombre:** Búsqueda flexible (ignora mayúsculas/minúsculas) de autores registrados.
9. **Buscar autores por año de nacimiento:** Filtra el catálogo de autores según su año de nacimiento exacto.

## 🛠️ Tecnologías Utilizadas

* **Java 17+**
* **Spring Boot** (Consola)
* **Spring Data JPA / Hibernate** (Persistencia y ORM)
* **PostgreSQL** (Base de datos relacional)
* **Jackson** (Mapeo de JSON a Objetos Java)
* **Gutendex API** (Fuente de datos de libros)
* **MyMemory API** (Traducción automática de resúmenes)

## ⚙️ Requisitos Previos

Para ejecutar este proyecto localmente, necesitas tener instalado:

* Java Development Kit (JDK) 17 o superior.
* Maven.
* PostgreSQL (Servidor local o en la nube).

## 💻 Instalación y Configuración

1. **Clona este repositorio:**
```bash
git clone [https://github.com/TU_USUARIO/literalura.git](https://github.com/TU_USUARIO/literalura.git)
```

2. **Configura la Base de Datos:**
   Crea una base de datos en PostgreSQL. La aplicación está configurada para utilizar variables de entorno para la conexión. Configura las siguientes variables en tu sistema o en el entorno de ejecución de tu IDE:
* `DB_HOST`: Dirección y puerto de tu servidor (ej. `localhost:5432`).
* `DB_NAME`: Nombre de tu base de datos.
* `DB_USER`: Tu usuario de PostgreSQL.
* `DB_PASSWORD`: Tu contraseña de PostgreSQL.

3. **Ejecuta la aplicación:**
   Puedes compilar y ejecutar el proyecto utilizando Maven o directamente desde tu IDE (IntelliJ IDEA, Eclipse, VS Code).
```bash
mvn spring-boot:run
```

## 🧠 Arquitectura y Diseño

* **Consumo de APIs:** Implementación de HttpClient, HttpRequest y HttpResponse para la comunicación externa.
* **Deserialización:** Uso de ObjectMapper y Records de Java para transformar JSON anidados en objetos modelo.
* **Consultas Avanzadas:** Uso intensivo de Derived Queries y JPQL nativo para filtrados eficientes directamente en la base de datos.
* **Programación Funcional:** Uso de Lambdas, Streams API y DoubleSummaryStatistics para la manipulación y análisis de colecciones en memoria.
* **UX y Manejo de Errores:** Implementación de bloques try-catch y validaciones estrictas para evitar cierres inesperados (ej. InputMismatchException) y garantizar una experiencia de usuario fluida en la consola.

---
*Desarrollado por Desarrollado por [Yamil Rafart](https://github.com/yamilrafart2)*