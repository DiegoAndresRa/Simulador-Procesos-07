package practica3;

import java.util.LinkedList;

/**
 * Clase utilizada para modificar las listas 
 * del objeto Colas
 * @author molk
 */
public class UtileriasCola {
    
    static void muestraEstado(LinkedList<Proceso> Listos,LinkedList<Proceso> Terminados,LinkedList<Proceso> Eliminados){
        System.out.println("PROCESOS LISTOS EN COLA: "+Listos.size());
        System.out.println("---------------------------------");
        System.out.println("PROCESOS TERMINADOS CON EXITO: "+Terminados.size());
        if(!Terminados.isEmpty()){
            for(int i=0;i<Terminados.size();i++){
                System.out.println(Terminados.get(i).getName());
            }
        }
        
        System.out.println("---------------------------------");
        System.out.println("PROCESOS FINALIZADOS ANTES:"+Eliminados.size());
        if(!Eliminados.isEmpty()){
            for(int i=0;i<Eliminados.size();i++){
                System.out.println(Eliminados.get(i).getName());
            }
        }
    }
    
    static void muestraProcesosListos(LinkedList<Proceso> Listos){
        String name=null;
        int id, inst;
        if(Listos.isEmpty()){
            System.out.println("\nNO HAY PROCESOS LISTOS");
        }else{
            System.out.println("NOMBRE\t\t|| ID\t|| INSTRUCCIONES PENDIENTES");
            for(int i=0;i<Listos.size();i++){
                name=Listos.get(i).getName();
                id=Listos.get(i).getID();
                inst=Listos.get(i).getInstPendientes();
                
                System.out.println(name+"\t\t    "+id+"\t    "+inst);
            }           
        }

    }      
    
    static void mostrarLocalidades(LinkedList<Pagina> Paginas){  // ---------------> marcos
        //como la memoria está llena de huecos, se puede mostrar
        int ultimo=0;    
        for(int i=0; i<Paginas.size();i++){
                // -----------> estetico y enfocado al usuario 
                if(i== (Paginas.size()-1)){
                    ultimo=1;
                    Paginas.get(i).mostrarPagina(ultimo);                     
                }else{
                    Paginas.get(i).mostrarPagina(ultimo);      
                }                         
            }
    }
    
    static void muestraProceso(LinkedList<Proceso> Listos){
        if(Listos.peek()==null){
            System.out.println("\nNO HAY PROCESOS PARA MOSTRAR");
        }else{
            Listos.getFirst().mostrarProceso();
            Listos.getFirst().mostrarTablaPag();
        }
    }
    
    static int ejecutaProceso(LinkedList<Proceso> Listos, LinkedList<Proceso> Terminados, LinkedList<Pagina> marcos){
        int memLib=0;
        
        //verifica que hayan procesos para ejecutar
        if(Listos.isEmpty()){ 
            System.out.println("\nNO HAY PROCESOS PARA EJECUTAR");
        }else{
            // ATRIBUTOS DEL PROCESO QUE NOS AYUDARÁN
            int instrucciones=Listos.getFirst().getInstrucciones();
            int instPend=Listos.getFirst().getInstPendientes();
            int instEjec=Listos.getFirst().getInstEject();
            int id=Listos.getFirst().getID();
            String nombre=Listos.getFirst().getName();
            int localidades=Listos.getFirst().getLocalidades();
            int paginas=Listos.getFirst().getPaginas();
            String name=null; //PARA MODIFICAR EL NOMBRE EN LA COLA DE PAGINAS
            
            
            if(instPend<5 && instPend>0){
                Listos.getFirst().setInstruccionesEjecutadas(instPend);
                Listos.getFirst().setInstruccionesPendientes(instPend);
            }
            if(instPend>=5){      
                Listos.getFirst().setInstruccionesEjecutadas(5);
                Listos.getFirst().setInstruccionesPendientes(5);
                // -------->  Update
                instPend=Listos.getFirst().getInstPendientes();
                instEjec=Listos.getFirst().getInstEject();
                
            /* TODAVIA QUEDAN INSTRUCCIONES PENDIENTES*/
                if(Listos.getFirst().getInstPendientes()>0){
                    /* copiamos el proceso */
                    Proceso addProceso= new Proceso(id,nombre,localidades,
                                    instrucciones,instPend,instEjec,paginas);
                    // copiamos tabla del proceso
                    for(int i=0; i<Listos.getFirst().tabPaginas.size();i++){
                        //obtenemos ubicacion de la pagina en memoria 
                        int ubicacion=Listos.getFirst().getUbiTabla(i);
                        //obtenemos marco de la pagina
                        int idPag=Listos.getFirst().getTabla().get(i).getPagina();                        
                        Pagina pagina=new Pagina(nombre,idPag,id,ubicacion,0);
                        addProceso.tabPaginas.add(pagina);                   
                    }              
                    
                  
                    Listos.poll(); 
                    Listos.add(addProceso);                                      
                }
                
            }
            /* SI YA NO QUEDAN INSTRUCCIONES POR EJECUTAR*/
            if(Listos.getFirst().getInstPendientes() ==0){
                instPend=Listos.getFirst().getInstPendientes();
                instEjec=Listos.getFirst().getInstEject();
                
                memLib=localidades;
                //copiamos proceso
                Proceso addProceso= new Proceso(id,nombre,localidades,
                                instrucciones,instPend,instEjec,paginas);
                //copiar tabla de paginas
                for(int i=0; i<Listos.getFirst().tabPaginas.size();i++){
                    int ubicacion=Listos.getFirst().getUbiTabla(i);
                    //obtenemos marco de pagina para saber dónde se vuelve hueco
                    int idPag=Listos.getFirst().getTabla().get(i).getPagina();
                    Pagina pagina=new Pagina(nombre,idPag,id,ubicacion,1);
                    addProceso.tabPaginas.add(pagina);                   
                }
                // cambiar a 1 en esHueco de las paginas con
                // id del proceso terminado
                                
                /* MODIFICANDO COLA DE PAGINAS */
                for(int i=0;i<paginas;i++ ){ //buscar paginas y guardarlas en cola   
                    for(int j=0; j<marcos.size(); j++){ //checar donde está el proceso en la cola               
                        if(marcos.get(j).getProceso()==id){
                            int ubi=16*j;  //16 por marco de página 
                            Pagina pagina=new Pagina(name,j,id, ubi, 1);                     
                            //agregar pagina donde no habia hueco del proceso
                            marcos.add(j+1, pagina);
                            //quitar pagina anterior donde no habia hueco del proceso
                            marcos.remove(j);
                        }      
                    }
                }
                                
                Terminados.add(addProceso); 
                System.out.println("\n\tPROCESO CONCLUIDO");
                Listos.poll(); 
            }
        }
        return memLib;
    }
    
    static void sigProceso(LinkedList<Proceso> Listos, LinkedList<Pagina> marcos){
        //verifica que hayan procesos para saltar
        if(Listos.isEmpty()){ 
            System.out.println("\nNO HAY PROCESOS PARA SALTARSE");
        }else{
            int id=Listos.getFirst().getID();
            String nombre=Listos.getFirst().getName();
            int localidades=Listos.getFirst().getLocalidades();
            int instrucciones=Listos.getFirst().getInstrucciones();
            int instPend=Listos.getFirst().getInstPendientes();
            int instEjec=Listos.getFirst().getInstEject();
            int paginas=Listos.getFirst().getPaginas();

            Proceso addProceso= new Proceso(id,nombre,localidades,
                                instrucciones,instPend,instEjec, paginas);
            
            for(int i=0;i<paginas;i++){
                // SE AÑADE LA PAGINA AL PROCESO CREADO 
                addProceso.tabPaginas.add(Listos.getFirst().getTabla().get(i));
            }
          
            Listos.poll();
            Listos.add(addProceso);
        }
    }
    
    static int mataProceso(LinkedList<Proceso> Listos, LinkedList<Proceso> Detenidos, LinkedList<Pagina> marcos){
        int memLib=0;
        //verifica que hayan procesos para ejecutar
        if(Listos.isEmpty()){ 
            System.out.println("\nNO HAY PROCESOS PARA MATAR");
        }else{
            int id=Listos.getFirst().getID();
            String nombre=Listos.getFirst().getName();
            int localidades=Listos.getFirst().getLocalidades();
            int instrucciones=Listos.getFirst().getInstrucciones();
            int instPend=Listos.getFirst().getInstPendientes();
            int instEjec=Listos.getFirst().getInstEject();
            int paginas=Listos.getFirst().getPaginas();
            Proceso addProceso= new Proceso(id,nombre,localidades,
                                instrucciones,instPend,instEjec, paginas);
            
            /* MODIFICANDO COLA DE PAGINAS */
                //int encuentra=0;
            String name=null;
            
            for(int i=0;i<paginas;i++){
                for(int j=0; j<marcos.size(); j++){ //checar donde está el proceso en la cola               
                    if(marcos.get(j).getProceso()==id ){
                        int ubi=16*j;   
                        Pagina pagina=new Pagina(name,j,id,ubi, 1);                     
                        //agregar pagina donde no habia hueco del proceso
                        marcos.add(j+1, pagina);
                        //quitar pagina anterior donde no habia hueco del proceso
                        marcos.remove(j);
                        //encuentra=1;
                    }      
                }            
            }
     
            System.out.println("\nINSTRUCCIONES PENDIENTES DEL PROCESO: "+instPend);
            Listos.poll();
            memLib=localidades;
            Detenidos.add(addProceso);
        }
        return memLib;
    } 
    
    static void agregarPaginas(LinkedList<Pagina> marcos, LinkedList<Proceso> procListos, Proceso proceso, int paginas){        
        int idProc=proceso.getID();
        int ubi=0;
        String nombre= proceso.getName();
        
        for(int i=0;i<paginas;i++ ){ //generar paginas y guardarlas en tabla  
            int encuentra=0;
            Pagina pagina=new Pagina(nombre,idProc,0); 
                        
            for(int j=0; j<marcos.size(); j++){ //checar donde hay espacio en los marcos            
                int esHueco=marcos.get(j).esHueco;  // extraemos el valor de su estado
                if(esHueco == 1 && encuentra==0){
                    ubi=16*j;   //obteniendo ubicacion                   
                    pagina.setPagina(j); //establece marco
                    pagina.setUbicacion(ubi); //establece ubicacion en memoria
                    //añadimos la pagina a tabla de procesos
                    proceso.setTablaPaginas(pagina);
                    //agregar pagina despues del proceso que tiene hueco
                    marcos.add(j, pagina);
                    //quitar pagina anterior
                    marcos.remove(j+1); 
                    encuentra=1; // --------------> !!!! Indicar ya no asigne marcos, 
                }      
            }
        } 
        //añadimos el proceso con su tabla a la cola de procesos
        procListos.add(proceso);
        
    }
    
    static void desfragmentar(LinkedList<Pagina> marcos, LinkedList<Proceso> Procesos,int marcoCreado, int pagDisp,int pagOcu){
        int maxIteraciones = 0;
        if(marcoCreado==0){
            System.out.println("\tSIN NADA POR DESFRAGMENTAR");
        }else{
            //se eliminan huecos   
            int j = 0;      
            System.out.println("\tINICIANDO");
            while(maxIteraciones < 64){
                j++; // --------->  sustituimos un for 
                maxIteraciones++; // contador alterno al for para evitar un buble infinito
                if(j!=0 && marcos.get(j-1).esHueco==1){ // solo cuando hay un remove debemos considerar el defasamiento de los indices
                    j--;  // --------> defasamiento
                    Pagina nuevaPagina=marcos.get(j);
                    marcos.add(nuevaPagina);//SE AÑADE PAGINA COMO HUECO AL FINAL
                    marcos.remove(j); 
                } else if (marcos.get(j).esHueco==1){
                    Pagina nuevaPagina=marcos.get(j);
                    marcos.add(nuevaPagina);//SE AÑADE PAGINA COMO HUECO AL FINAL
                    marcos.remove(j);  
                }
            }
            
                
            
            //se modifican tablas de paginas recorriendo de nuevo los marcos
            for(int i=0;i<Procesos.size();i++){
                int paginas= Procesos.get(i).getTabla().size();//numero de paginas
                int id=Procesos.get(i).getID();//obtenemos id del Proceso
                int z = 0; // ---------> inicio del contador
                for(int k=0;k<paginas;k++){    
                    int encuentra=0;
                    while(encuentra != 1){
                        if(marcos.get(z).getProceso()==id && encuentra==0){
                            int ubi=16*z;   
                            Procesos.get(i).getTabla().get(k).setUbicacion(ubi); 
                            encuentra=1;
                        } 
                        z++;
                    }   
                }
                // actualizando las ubicaciones de los huecos
                restoreUbi(marcos);         
            }
   
        }
    }

    static void restoreUbi(LinkedList<Pagina> marcos){
        for(int i = 0;i<marcos.size();i++){    
            if(marcos.get(i).esHueco == 1){
                int ubi = 16*i;
                marcos.get(i).setUbicacion(ubi);
            }
        } 
    }
    
}
