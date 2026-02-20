package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.Autor;
import com.aluracursos.literalura.model.DatosLibro;
import com.aluracursos.literalura.model.DatosResultados;
import com.aluracursos.literalura.model.Libro;
import com.aluracursos.literalura.repository.IAutorRepository;
import com.aluracursos.literalura.repository.ILibroRepository;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Clase principal que maneja la interacción con el usuario y el flujo de la aplicación.
 */
public class Principal {

    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private final ConvierteDatos conversor = new ConvierteDatos();
    private final Scanner teclado = new Scanner(System.in);
    private final ILibroRepository repositorioLibro;
    private final IAutorRepository repositorioAutor;

    public Principal(ILibroRepository repositoryLibro,  IAutorRepository repositoryAutor) {
        this.repositorioLibro = repositoryLibro;
        this.repositorioAutor = repositoryAutor;
    }

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
                        6 - GENERAR ESTADÍSTICAS DE DESCARGAS
                        7 - TOP 10 LIBROS MÁS DESCARGADOS
                        8 - BUSCAR AUTOR POR NOMBRE
                        9 - BUSCAR AUTORES POR AÑO DE NACIMIENTO

                        0 - SALIR
                        """
                ;
                System.out.println("\n==================== MENÚ ====================");
                System.out.println(menu);
                System.out.print("INGRESE UNA OPCIÓN: ");
                // --- INICIO DEL MANEJO DE ERRORES ---
                try {
                    opcion = teclado.nextInt();
                    teclado.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("==============================================");
                    System.out.println("\n==============================================");
                    System.out.println("ERROR: POR FAVOR, INGRESE UNA OPCIÓN VÁLIDA!");
                    System.out.println("==============================================");
                    teclado.nextLine();
                    continue;
                }
                // --- FIN DEL MANEJO DE ERRORES ---

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
                        mostrarLibrosRegistrados();
                        System.out.println("==============================================");
                        break;
                    case 3:
                        System.out.println("==============================================");
                        System.out.println("\n====================== 3. ====================");
                        mostrarAutoresRegistrados();
                        System.out.println("==============================================");
                        break;
                    case 4:
                        System.out.println("==============================================");
                        System.out.println("\n====================== 4. ====================");
                        listarAutoresVivosEnAnio();
                        System.out.println("==============================================");
                        break;
                    case 5:
                        System.out.println("==============================================");
                        System.out.println("\n====================== 5. ====================");
                        mostrarMenuIdiomas();
                        System.out.println("==============================================");
                        break;
                    case 6:
                        System.out.println("==============================================");
                        System.out.println("\n====================== 6. ====================");
                        mostrarEstadisticasDescargas();
                        System.out.println("==============================================");
                        break;
                    case 7:
                        System.out.println("==============================================");
                        System.out.println("\n====================== 7. ====================");
                        mostrarTop10LibrosMasDescargados();
                        System.out.println("==============================================");
                        break;
                    case 8:
                        System.out.println("==============================================");
                        System.out.println("\n====================== 8. ====================");
                        buscarAutorPorNombre();
                        System.out.println("==============================================");
                        break;
                    case 9:
                        System.out.println("==============================================");
                        System.out.println("\n====================== 9. ====================");
                        buscarAutorPorAnioNacimiento();
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
                        System.out.println("ERROR: POR FAVOR, INGRESE UNA OPCIÓN VÁLIDA!");
                        System.out.println("==============================================");
                }
            }

    }

    /**
     * Solicita al usuario un año específico y busca en la base de datos a los
     * autores que hayan nacido en ese año. Los resultados se muestran ordenados
     * alfabéticamente por nombre.
     */
    private void buscarAutorPorAnioNacimiento() {
        System.out.print("INGRESE EL AÑO DE NACIMIENTO EN EL QUE DESEA BUSCAR AUTORES: ");
        int anioBuscado;
        // --- INICIO DEL MANEJO DE ERRORES ---
        try {
            anioBuscado = teclado.nextInt();
            teclado.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("==============================================");
            System.out.println("\n==============================================");
            System.out.println("ERROR: EL AÑO DEBE SER UN NÚMERO (EJEMPLO: 1800)!");
            System.out.println("==============================================");
            teclado.nextLine();
            return;
        }
        // --- FIN DEL MANEJO DE ERRORES ---

        List<Autor> autoresEncontrados = repositorioAutor.getAutoresPorFechaNacimiento(anioBuscado);
        if (!autoresEncontrados.isEmpty()) {
            System.out.println("LA CANTIDAD DE AUTORES QUE NACIERON EN EL AÑO " + anioBuscado + " ES DE: " +  autoresEncontrados.size());
            autoresEncontrados.stream()
                    .sorted(Comparator.comparing(Autor::getNombre))
                    .forEach(System.out::println)
            ;
        } else {
            System.out.println("NO EXISTEN AUTORES REGISTRADOS QUE HAYAN NACIDO EN EL AÑO " + anioBuscado + "!");
        }
    }

    /**
     * Solicita al usuario el nombre (o parte del nombre) de un autor,
     * consulta la base de datos y muestra la información del autor si este
     * se encuentra registrado.
     */
    private void buscarAutorPorNombre() {
        System.out.print("INGRESE EL NOMBRE DEL AUTOR QUE DESEA BUSCAR: ");
        var nombreAutor = teclado.nextLine();
        Optional<Autor> autorBuscado = repositorioAutor.getAutorPorNombre(nombreAutor);

        if (autorBuscado.isPresent()) {
            System.out.println("AUTOR ENCONTRADO!");
            System.out.println(autorBuscado.get());
        } else {
            System.out.println("AUTOR NO ENCONTRADO!");
        }
    }

    /**
     * Consulta la base de datos para obtener los 10 libros con mayor número
     * de descargas y los muestra en la consola.
     */
    private void mostrarTop10LibrosMasDescargados() {
        List<Libro> listaLibros = repositorioLibro.findAll();

        if (listaLibros.isEmpty()) {
            System.out.println("NO EXISTEN LIBROS REGISTRADOS!");
        } else {
            System.out.println("---------- TOP 10 LIBROS MÁS DESCARGADOS ----------");
            listaLibros.stream()
                    .sorted(Comparator.comparing(Libro::getTotalDescargas).reversed())
                    .limit(10)
                    .forEach(System.out::println)
            ;
        }
    }

    /**
     * Calcula y muestra estadísticas de descargas de todos los libros
     * almacenados en la base de datos utilizando DoubleSummaryStatistics.
     * Los cálculos incluyen: promedio, máximo, mínimo y total de libros evaluados.
     */
    private void mostrarEstadisticasDescargas() {
        List<Libro> listaLibros = repositorioLibro.findAll();
        DoubleSummaryStatistics estadisticas = listaLibros.stream()
                .filter(l -> l.getTotalDescargas() > 0)
                .collect(Collectors.summarizingDouble(Libro::getTotalDescargas))
        ;

        System.out.println("ESTADÍSTICAS DE TODOS LOS LIBROS REGISTRADOS:");
        System.out.println("CANTIDAD MEDIA DE DESCARGAS: " + estadisticas.getAverage());
        System.out.println("CANTIDAD MÁXIMA DE DESCARGAS: " + estadisticas.getMax());
        System.out.println("CANTIDAD MÍNIMA DE DESCARGAS: " + estadisticas.getMin());
        System.out.println("CANTIDAD DE LIBROS EVALUADOS PARA CALCULAR LAS ESTADÍSTICAS: " + estadisticas.getCount());
    }

    /**
     * Muestra un submenú con opciones de idiomas, solicita al usuario
     * la sigla correspondiente y consulta la base de datos para mostrar los libros
     * encontrados, ordenados por cantidad de descargas de mayor a menor.
     */
    private void mostrarMenuIdiomas() {
        System.out.println( """
                            ---------- LISTA DE IDIOMAS ----------
                            ES - ESPAÑOL
                            EN - INGLÉS
                            FR - FRANCÉS
                            PT - PORTUGUÉS
                            --------------------------------------
                            """)
        ;

        System.out.print("INGRESE LA SIGLA DEL IDIOMA EN EL QUE DESEA BUSCAR LIBROS: ");
        var siglaIdioma = teclado.nextLine().trim();

        // --- INICIO DE VALIDACIÓN ---
        List<String> idiomasValidos = List.of("es", "en", "fr", "pt");

        if (!idiomasValidos.contains(siglaIdioma.toLowerCase())) {
            System.out.println("==============================================");
            System.out.println("ERROR: IDIOMA NO RECONOCIDO. POR FAVOR, INGRESE ES, EN, FR O PT!");
            System.out.println("==============================================");
            return;
        }
        // --- FIN DE VALIDACIÓN ---

        List<Libro> listaLibros = repositorioLibro.getLibrosEscritosEnIdioma(siglaIdioma.toLowerCase());
        if (!listaLibros.isEmpty()) {
            System.out.println("LA CANTIDAD DE LIBROS ENCONTRADOS EN ESE IDIOMA (" + siglaIdioma.toUpperCase() + ") ES DE: " + listaLibros.size());
            listaLibros.stream()
                    .sorted(Comparator.comparing(Libro::getTotalDescargas).reversed())
                    .forEach(System.out::println)
            ;
        } else {
            System.out.println("NO EXISTEN LIBROS REGISTRADOS EN ESE IDIOMA (" + siglaIdioma.toUpperCase() + ")!");
        }
    }

    /**
     * Solicita al usuario un año y busca en la base de datos los autores que
     * se encontraban con vida durante ese período. Los resultados se muestran
     * ordenados del más reciente al más antiguo.
     */
    private void listarAutoresVivosEnAnio() {
        System.out.print("INGRESE EL AÑO EN EL QUE DESEA BUSCAR AUTORES VIVOS: ");
        int anioBuscado;
        // --- INICIO DEL MANEJO DE ERRORES ---
        try {
            anioBuscado = teclado.nextInt();
            teclado.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("==============================================");
            System.out.println("\n==============================================");
            System.out.println("ERROR: EL AÑO DEBE SER UN NÚMERO (EJEMPLO: 1800)!");
            System.out.println("==============================================");
            teclado.nextLine();
            return;
        }
        // --- FIN DEL MANEJO DE ERRORES ---

        List<Autor> autoresVivos = repositorioAutor.getAutoresVivosEnAnio(anioBuscado);
        if (!autoresVivos.isEmpty()) {
            System.out.println("LA CANTIDAD DE AUTORES VIVOS EN EL AÑO " + anioBuscado + " ES DE: " +  autoresVivos.size());
            autoresVivos.stream()
                    .sorted(Comparator.comparing(Autor::getFechaNacimiento).reversed())
                    .forEach(System.out::println)
            ;
        } else {
            System.out.println("NO EXISTEN AUTORES REGISTRADOS QUE ESTÉN VIVOS EN EL AÑO " + anioBuscado + "!");
        }

    }

    /**
     * Consulta la base de datos para obtener todos los autores registrados
     * y los ordena según el nombre y los muestra en la consola.
     */
    private void mostrarAutoresRegistrados() {
        var listaAutores = repositorioAutor.findAll();

        if (!listaAutores.isEmpty()) {
            System.out.println("CANTIDAD DE AUTORES REGISTRADOS: " + listaAutores.size());
            listaAutores.stream()
                    .sorted(Comparator.comparing(Autor::getNombre))
                    .forEach(System.out::println)
            ;
        } else {
            System.out.println("NO EXISTE AUTORES REGISTRADOS!");
        }

    }

    /**
     * Consulta la base de datos para obtener todos los libros registrados 
     * y los ordena según cantidad de descargas y los muestra en la consola.
     */
    private void mostrarLibrosRegistrados() {
        var listaLibros = repositorioLibro.findAll();

        if (!listaLibros.isEmpty()) {
            System.out.println("CANTIDAD DE LIBROS REGISTRADOS: " + listaLibros.size());
            listaLibros.stream()
                    .sorted(Comparator.comparing(Libro::getTotalDescargas).reversed())
                    .forEach(System.out::println);
            ;
        } else {
            System.out.println("NO EXISTEN LIBROS REGISTRADOS!");
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
            repositorioLibro.save(libro);
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
