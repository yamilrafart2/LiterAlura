package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.DatosLibro;
import com.aluracursos.literalura.model.DatosResultados;
import com.aluracursos.literalura.model.Libro;
import com.aluracursos.literalura.repository.ILibroRepository;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;

import java.util.Optional;
import java.util.Scanner;

/**
 * Clase principal que maneja la interacción con el usuario y el flujo de la aplicación.
 */
public class Principal {

    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private final ConvierteDatos conversor = new ConvierteDatos();
    private final Scanner teclado = new Scanner(System.in);
    private final ILibroRepository repositorio;

    public Principal(ILibroRepository repository) { this.repositorio = repository; }

    /**
     * Muestra el menú principal de la aplicación y gestiona el flujo de opciones
     * seleccionadas por el usuario a través de la consola.
     */
    public void mostrarMenu() {

            var opcion = -1;
            while (opcion != 0) {
                var menu = """
                        1 - BUSCAR LIBRO POR TÍTULO
                        2 - LISTAR LIBROS REGISTRADOS
                        3 - LISTAR AUTORES REGISTRADOS
                        4 - LISTAR AUTORES VIVOS EN UN DETERMINADO AÑO
                        5 - LISTAR LIBROS POR IDIOMA

                        0 - SALIR
                        """
                ;
                System.out.println("\n==================== MENÚ ====================");
                System.out.println(menu);
                System.out.print("INGRESE UNA OPCIÓN: ");
                opcion = teclado.nextInt();
                teclado.nextLine();

                switch (opcion) {
                    case 1:
                        System.out.println("==============================================");
                        System.out.println("\n====================== 1. ====================");
                        buscarLibroAPI();
                        System.out.println("==============================================");
                        break;
                    case 2:
                        System.out.println("==============================================");
                        System.out.println("\n====================== 2. ====================");

                        System.out.println("==============================================");
                        break;
                    case 3:
                        System.out.println("==============================================");
                        System.out.println("\n====================== 3. ====================");

                        System.out.println("==============================================");
                        break;
                    case 4:
                        System.out.println("==============================================");
                        System.out.println("\n====================== 4. ====================");

                        System.out.println("==============================================");
                        break;
                    case 5:
                        System.out.println("==============================================");
                        System.out.println("\n====================== 5. ====================");

                        System.out.println("==============================================");
                        break;
                    case 0:
                        System.out.println("==============================================");
                        System.out.println("\n==============================================");
                        System.out.println("CERRANDO LA APLICACIÓN...");
                        System.out.println("==============================================");
                        break;
                    default:
                        System.out.println("==============================================");
                        System.out.println("\n==============================================");
                        System.out.println("OPCIÓN INVÁLIDA!");
                        System.out.println("==============================================");
                }
            }

    }

    /**
     * Orquesta el proceso de búsqueda: solicita los datos a la API,
     * verifica si el libro existe, lo mapea a una entidad y lo guarda en la base de datos.
     */
    private void buscarLibroAPI() {

        Optional<DatosLibro> datosLibro = getDatosLibro();
        if (datosLibro.isPresent()) {
            System.out.println("LIBRO ENCONTRADO!");
            System.out.println(datosLibro.get() + "\n");

            Libro libro = new Libro(datosLibro.get());
            repositorio.save(libro);
            System.out.println("LIBRO REGISTRADO!");
            System.out.println(libro + "\n");

        } else {
            System.out.println("LIBRO NO ENCONTRADO!");
        }

    }

    /**
     * Captura el nombre del libro por teclado, realiza la petición HTTP a Gutendex
     * y filtra el primer resultado que coincida con la búsqueda.
     * @return Un Optional que contiene los DatosLibro si se encontró, o vacío si no.
     */
    private Optional<DatosLibro> getDatosLibro() {

        System.out.print("INGRESE EL NOMBRE DEL LIBRO QUE DESEA BUSCAR: ");
        var nombreLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        System.out.println(json + "\n");

        var resultados = conversor.obtenerDatos(json,  DatosResultados.class);
        return resultados.listaLibros().stream()
                .filter(l -> l.titulo().toLowerCase().contains(nombreLibro.toLowerCase()))
                .findFirst()
        ;

    }
}
