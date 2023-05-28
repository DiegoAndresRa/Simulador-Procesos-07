package practica3;

import java.util.LinkedList;

/**
 * Clase para crear el objeto Colas, con
 * las LinkedList necesarias guardar procesos
 * creados, terminados o eliminados
 * @author molk
 */
public class Colas {
    
    //*****ATRIBUTOS*****//
    LinkedList Listos= new LinkedList();
    LinkedList Terminados = new LinkedList();
    LinkedList Eliminados = new LinkedList();
    LinkedList<Pagina> Paginas = new LinkedList(); // ------------> Para los marcos 
}
