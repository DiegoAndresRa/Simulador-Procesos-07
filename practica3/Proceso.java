package practica3;
import java.util.LinkedList;
/**
 * Clase para generar al objeto Proceso
 * @author molk
 */
public class Proceso {
        //*****ATRIBUTOS*****//
        String nombre;
        int id;
        int localidades;
        int numInst;
        int numInstEjec;
        int numInstPend;
        int numPaginas;  // ---> nuevo
        LinkedList<Pagina> tabPaginas= new LinkedList();
        
        //*****CONSTRUCTORES*****//
        Proceso(int ID, String name, int local, int paginas){    //Hacemos la asignación de valores 
            id= ID;
            nombre = name;
            localidades = local;
            numInst = Utilerias.numInstrucciones();
            numInstPend = numInst;
            numInstEjec=0;
            numPaginas=paginas;
            LinkedList<Pagina> tabPaginas= new LinkedList();
        }     
        
        Proceso(int ID, String name, int local, int inst,int pend,int instEjec, int paginas){    //Hacemos la asignación de valores 
            id= ID;
            nombre = name;
            localidades = local;
            numInst = inst;
            numInstPend = pend;
            numInstEjec=instEjec;
            numPaginas= paginas;
            LinkedList<Pagina> tabPaginas= new LinkedList();
        }  
        
        //*****MÉTODOS*****//

        /**
        * Obtiene el numero de identificacion del proceso
        * @return id Número de identificacion del proceso
        */
        public int getID(){
            return id;
        }
        
        /**
         * Obtiene el nombre asignado al proceso
         * @return nombre Nombre asignado al proceso
         */
        public String getName(){
            return nombre;
        }
        
        /**
         * Obtiene el número de localidades que toma el proceso
         * @return localidades numero de localidades que toma el proceso
         */
        public int getLocalidades(){
            return localidades;
        }
        
        /**
         * Obtiene el numero de instrucciones totales del proceso
         * @return numInst Numero de instrucciones totales del proceso
         */
        public int getInstrucciones(){
            return numInst;
        }
        
        /**
         * Obtiene el numero de instrucciones pendientes por ejecutar
         * @return numInstPend Numero de instrucciones pendientes por ejecutar
         */
        public int getInstPendientes(){
            return numInstPend;
        }
        
        /**
         * Obtiene el número de instrucciones ejecutadas
         * @return numInstEjec Numero de instrucciones ejecutadas del proceso
        */
        public int getInstEject(){
            return numInstEjec;
        }
        
    /**
     * Obtiene el número de páginas que tiene el proceso
     * @return numPaginas Numero de Paginas que ocupa el proceso
     */
    public int getPaginas(){
            return numPaginas;
        }
        
    /**
     *
     * @param pagina Número de página en la tabla
     * @return Marco de la página en la memoria
     */
    public int getUbiTabla(int pagina){
            return tabPaginas.get(pagina).getUbicacion();
        }        
        
        public LinkedList<Pagina> getTabla(){
            return tabPaginas;
        }
        
        /**
         * Establece el numero de instrucciones pendientes del proceso
         * @param instEjec Número de instrucciones que se ejecutaron del proceso
         */
        public void setInstruccionesPendientes(int instEjec){
            numInstPend= numInstPend-instEjec;
        }
        
        /**
         * Suma el numero de instrucciones ejecutadas del proceso a las 
         * que ya se habían ejecutado
         * @param instEjec Numero de instrucciones ejecutadas 
         */
        public void setInstruccionesEjecutadas(int instEjec){
            numInstEjec= numInstEjec+instEjec;
        }
        
        public void setTablaPaginas(Pagina pagina){
            tabPaginas.add(pagina);                    
        }
        
        /**
         * Muestra datos del Proceso
         */
        public void mostrarProceso(){
            System.out.println("ID: "+id);
            System.out.println("NOMBRE: "+nombre);            
            System.out.println("INSTRUCCIONES TOTALES: "+numInst);
            System.out.println("INSTRUCCIONES EJECUTADAS: "+numInstEjec);
            System.out.println("ESPACIO: "+localidades+" localidades\n");
        }
        
    /**
     * Muestra la tabla de paginas del proceso
     */
    public void mostrarTablaPag(){
        int ubi;
        System.out.println("--- TABLA DE PAGINAS ---");
        System.out.println("Pagina  \tMarco");
        
        for(int i=0;i<tabPaginas.size();i++){
            ubi=tabPaginas.get(i).getPagina();
            System.out.println(" "+ i +"\t\t "+ ubi);
        }   
    }
        
    /**
     * Muestra las instrucciones pendientes por ejectuar del proceso 
     */
    public void mostrarDetenido(){
            System.out.println("INSTRUCCIONES PENDIENTES: "+numInstPend);
        }
        
}
