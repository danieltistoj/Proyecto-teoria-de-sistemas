/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basedatosp1;

/**
 *
 * @author Usuario
 */
public class Lista {
     private Nodo tope, fondo;
    private int size= 0;
    
    
    public void InsertarTope(Nodo nuevo){
       
        if(Vacia()){
            tope = nuevo;
            tope.setSig(null);
            
        }
        else{
            Nodo aux = tope;
            nuevo.setSig(aux);
            tope = nuevo;
            
        }
        size++;
        if(tope.getVenta()!=null){
            tope.getVenta().setNum_productos(size);
        }
        
    }
    
    public void InsertarFondo(Nodo nuevo){
       
      
        if(Vacia()){
            tope = nuevo;
            fondo = nuevo;
           
        }
        else{
            Nodo fin = fondo;
            fin.setSig(nuevo);
            fondo = nuevo;  
        }
        size++;
        if(tope.getVenta()!=null){
            tope.getVenta().setNum_productos(size);
        }
      
    }
/*
    public void Eliminar(int dato){
        if(size==1){
            tope=null;
            fondo=null;
        }
        else{
        if(dato==tope.getDato()){
            Nodo aux = tope;
            tope = aux.getSig();
            aux=null;
            
        }
        if(dato==fondo.getDato()){
            Nodo aux = fondo;
            fondo = aux.getAnt();
            fondo.setSig(null);
            aux = null;    
           
        }
        if(dato!=fondo.getDato()&&dato!=tope.getDato()){
            Nodo aux = tope;
            while(aux.getSig()!=null){
                if(dato==aux.getDato()){
                    Nodo anterior = aux.getAnt();
                    anterior.setSig(aux.getSig());
                    aux = null;
                    break;
                }
                aux = aux.getSig();
            }
        }
        }
        size--;
        
    }
    
  
    public Nodo Buscar(int dato){
        Nodo aux = tope, nodo=null;
        
        boolean existe=false;
        while(aux!=null){
            if(dato==aux.getDato()){
                nodo=aux;
            }
            aux = aux.getSig();
        }
        return nodo;
        
    }
    */
    public void Mostrar(){
        Nodo aux = tope;
        while(aux!=null){
            System.out.print(aux.getVenta().getId_venta()+" ");
            aux = aux.getSig();
        }
    }

    public Nodo getTope() {
        return tope;
    }

    public void setTope(Nodo tope) {
        this.tope = tope;
    }

    public Nodo getFondo() {
        return fondo;
    }

    public void setFondo(Nodo fondo) {
        this.fondo = fondo;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    
    
    
   
    
    public boolean Vacia(){
        return(fondo==null)?true:false;
    }
    
}
