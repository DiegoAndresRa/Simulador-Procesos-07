package practica3;

/**
 * Clase con funciones para generar numeros necesarios
 * para localidades e instrucciones de un proceso, o para imprimir
 * a estos
 * @author molk
 */
public class Utilerias {
    static int numLocalidades(){
        int local=1;
        int num = (int) (Math.random() * (5 - 1)) + 1;
        
        if(num==1){
            local=64;
        }
        if(num==2){
            local=128;
        }
        if(num==3){
            local=256;
        }
        if(num==4){
            local=512;
        }
        return local;
        
    }
    
    static int numInstrucciones(){
        int inst= (int) (Math.random() * (30-10+1)+10);        
        return inst;
    }
     
    static int numPaginas(int localidades){
        int paginas=1;
        if(localidades==64){
           paginas=64/16;
        }
        if(localidades==128){
            paginas=128/16;
        }
        if(localidades==256){
            paginas=256/16;
        }
        if(localidades==512){
            paginas=512/16;
        }
        return paginas;
    
    }
    
}
