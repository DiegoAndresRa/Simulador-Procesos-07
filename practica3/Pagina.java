/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica3;

/**
 * Clase utilizada para generar a la Pagina
 * de la tabla del proceso
 * @author molk
 */
public class Pagina {
    /*  ATRIBUTOS   */
    int idPag; //indice de pagina dentro de la cola del proceso
    int idProceso=0; //indice del proceso al que pertenece la pagina
    String nombre;            
    int ubicacion=0; //ubicacion en la memoria
    int esHueco=1; //deja saber si se volvió hueco o no
                    // 1 sí es 0 no es

    /*  CONSTRUCTORES */
    Pagina(String name, int Proceso, int hueco){
        nombre=name;
        idProceso=Proceso; 
        esHueco=hueco;
    }
    
    
    Pagina(String name,int Pag, int Proceso, int hueco){
        nombre=name;
        idPag=Pag;
        idProceso=Proceso; 
        esHueco=hueco;
    }
    
    Pagina(String name, int Pag, int Proceso, int ubi, int hueco){
        nombre=name;
        idPag=Pag;
        idProceso=Proceso;
        ubicacion = ubi;
        esHueco=hueco;
    }
    
    /*  METODOS */
    public String getNombre(){
        return nombre;
    }
    
    public int getPagina(){    
        return idPag;
    }
    
    public int getProceso(){
        return idProceso;
    }
    
    public int getUbicacion(){
        return ubicacion;
    }
    
    public void setNombre(String name){
        nombre=name;
    }
    
    public void setPagina(int Pagina){    
        idPag=Pagina;
    }
    
    public void setProceso(int Proceso){
        idProceso=Proceso;
    }
    
    public void setUbicacion(int ubi){
        ubicacion=ubi;
    }
    
    public void mostrarPagina( int ultimo){
        int ubi=ubicacion;
        
        if(ubi!=0){
            ubi=ubi-1;
        }
        
        if(ultimo==1){ // SI EL HUECO O PROCESO ES ULTIMO DE LA LISTA
            if (esHueco==1){    
                System.out.println(" "+nombre+" H "+ubi+" 16-> X");
            }else{
                System.out.println(" "+nombre+" P "+ubi+" 16-> X");
            }
        }else{ //SI HUECO O PROCESO NO ES ULTIMO DE LA LISTA
            if (esHueco==1){    
                System.out.println(" "+nombre+" H "+ubi+" 16-> ");
            }else{
                System.out.println(" "+nombre+" P "+ubi+" 16-> ");
            }
        }      
    }
}
