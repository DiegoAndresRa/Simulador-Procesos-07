package practica3;

import java.util.Scanner;

/**
 * Clase con el menu para simular procesos
 * según lo que desee el usuario
 * @author Yaxca Alexa Quero Bautista
 */
public class Practica3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //*****OBJETOS PRINCIPALES**********//

        Scanner guarda = new Scanner(System.in);    //Sirve para leer entrada del usuario
        
        //*****INICIALIZAR*****//
        int opcion=0;
        int opcionDos=0;
        int memDisp=1024; // SE MODIFICÓ EL TAMAÑO DE LA MEMORIA
        int memOcu=0;
        int pagDisp=64;   // -----------> 1024/16 = 64
        int pagOcu=0;
        int id=1;
        int marcoCreado=0;
        Colas colas = new Colas();
        
        /* LLENAR COLA DE PAGINAS CON HUECOS*/
        for(int i=0;i<pagDisp;i++){
            //no nos importa el id del proceso aun
            int ubiInicio=i*16;
            Pagina pagina=new Pagina(null,i,i,ubiInicio,1); 
            colas.Paginas.add(pagina);
        }
        
        
        //*****MAIN*****//
        System.out.println(">>>>>>>>>> BIENVENIDO A LA SIMULACION <<<<<<<<<<");
        
        do{
            opcion=0;
            System.out.println("\n----------------------------------------");
            System.out.println("MEMORIA DISPONIBLE  ||\tMEMORIA UTILIZADA");
            System.out.println(" "+memDisp+" LOCALIDADES\t   "+memOcu+" LOCALIDADES" );
            System.out.println("----------------------------------------");
            
            System.out.println("\nSELECCIONE UNA OPCION:\n");
            System.out.println("1)Crear Proceso nuevo \t\t2)Ver estado de los procesos");
            System.out.println("3)Ver estado de la memoria \t4)Imprimir cola de procesos");
            System.out.println("5)Ver proceso actual \t\t6)Ejecutar proceso actual");
            System.out.println("7)Pasar al proceso siguiente\t8)Matar proceso actual");
            System.out.println("9)Desfragmentar memoria\t\t10)Salir del programa ");
            opcion= guarda.nextInt();
            
            switch(opcion){
                case 1:// crear proceso nuevo
                    int localidades= Utilerias.numLocalidades();
                    int paginas= Utilerias.numPaginas(localidades); // OBTENEMOS LAS PAGINAS DEL PROCESO
                    
                    String nombreProceso=null;
                    System.out.println("\nIntroduce el nombre para tu proceso:");
                    nombreProceso = guarda.next();
                    guarda.nextLine();
                    if(localidades<=memDisp && paginas<=pagDisp){                                              
                        Proceso proceso = new Proceso(id,nombreProceso,localidades,paginas);
                        id++;           
                        
                        /* SE AGREGAN LAS PAGINAS EN DONDE HAY HUECOS EN COLA DE PAGINAS
                           Y AÑADIMOS EL PROCESO EN LA COLA DE PROCESOS LISTOS */
                        UtileriasCola.agregarPaginas(colas.Paginas,colas.Listos, proceso, paginas);
                           
                        pagDisp=pagDisp-paginas; //se reduce el número de páginas de la memoria
                        pagOcu=pagOcu+paginas;
                        
                        System.out.println("\n<<<PROCESO CREADO CON EXITO>>>");
                        memDisp=memDisp-proceso.getLocalidades();
                        memOcu=memOcu+proceso.getLocalidades();
                        
                        marcoCreado=1;
                    }else{
                        System.out.println("\n<<<NO SE PUDO CREAR EL PROCESO "+nombreProceso+">>>");
                        System.out.println("NECESITA EJECUTAR O MATAR PROCESOS");
                    }    
                    break; 
                    
                case 2://ver estado de los procesos
                    UtileriasCola.muestraEstado(colas.Listos,colas.Terminados,colas.Eliminados);
                    break;
                /*case 3: //Estado de la memoria o localidades ocupadas por procesos
                    //localidad     proceso
                    UtileriasCola.mostrarLocalidades(colas.Listos);
                    break;*/
                case 3: // VER ESTADO DE LA MEMORIA 
                    /* MUESTRA LISTA LIGADA DE COLA Y PROCESOS*/
                    UtileriasCola.mostrarLocalidades(colas.Paginas);
                    
                    break;
                    
                case 4://imprimir cola de procesos
                    UtileriasCola.muestraProcesosListos(colas.Listos);
                    break;

                case 5://ver proceso actual
                    UtileriasCola.muestraProceso(colas.Listos);
                    break;

                case 6://ejecutar proceso actual
                    int memLib=0;
                    int pagLib=0; //paginas liberadas si se acaba el proceso
                    
                    memLib=UtileriasCola.ejecutaProceso(colas.Listos, colas.Terminados, colas.Paginas);
                    
                    if(memLib!=0){ //si la memoria liberada es diferente a cero
                        pagLib=memLib/16;
                    }
                    
                    pagDisp=pagDisp+pagLib; //paginas que se liberaron se agregan a disponibles
                    pagOcu=pagOcu-pagLib; //paginas liberadas se restan a ocupadas
                    
                    memDisp=memDisp+memLib;
                    memOcu=memOcu-memLib;   
                    
                    break;
                case 7://pasar al proceso siguiente
                    UtileriasCola.sigProceso(colas.Listos,colas.Paginas);
                    break;
                case 8://matar proceso actual
                    int memLibK=0;
                    int pagLibK=0; //paginas liberadas
                    
                    memLibK=UtileriasCola.mataProceso(colas.Listos, colas.Eliminados,colas.Paginas);
                    
                    if(memLibK!=0){ //si la memoria liberada es diferente a cero
                        pagLibK=memLibK/16;
                    }
                    
                    pagDisp=pagDisp+pagLibK; //paginas que se liberaron se agregan a disponibles
                    pagOcu=pagOcu-pagLibK; //paginas liberadas se restan a ocupadas
                    
                    memDisp=memDisp+memLibK;
                    memOcu=memOcu-memLibK;    
                    break;
                case 9: // DESFRAGMENTAR MEMORIA
                    UtileriasCola.desfragmentar(colas.Paginas, colas.Listos, marcoCreado,pagDisp,pagOcu);                   
                    break;
                
                case 10://salir del programa
                    
                    if(colas.Listos.isEmpty()){
                        opcion=99;
                        System.out.println("\n   HAS SALIDO DE LA SIMULACION:)");
                    }else{
                        if(colas.Listos.size()==1){
                            System.out.println("\n\tUN PROCESO NO SE CONCLUIRA");
                        }
                        if(colas.Listos.size()>1){
                            System.out.println(" "+colas.Listos.size()+" PROCESOS NO SE CONCLUIRAN");
                        }     
                        System.out.println("DESEA CONTINUAR?");
                        System.out.println("1) Si\t2)No");
                        opcionDos=guarda.nextInt();
                        
                        if(1==opcionDos){
                            opcion=99;
                            System.out.println("\n   HAS SALIDO DE LA SIMULACION:)");
                        }else{
                            System.out.println("SALIDA ABORTADA");
                        }         
                    }
                    break;
                default:
                    System.out.println("Opcion no existente");  
            }            
        }while(opcion!=99);     
    }    
}
