/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practica.automatas;

/**
 *
 * @author Julian
 * @author Felipe
 */
public class estado {
   private String icono;
   private String Descripcion;
   private boolean inicial;
   private boolean estado;

    /**
     * @return the icono
     */
   
   public  estado(String i, String D, boolean a,boolean e){
       icono = i;
       Descripcion = D;
       inicial=a;    
       estado = e;
   }
   
    public String getIcono() {
        return icono;
    }

    /**
     * @param icono the icono to set
     */
    public void setIcono(String icono) {
        this.icono = icono;
    }

    /**
     * @return the Descripcion
     */
    public String getDescripcion() {
        return Descripcion;
    }

    /**
     * @param Descripcion the Descripcion to set
     */
    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    /**
     * @return the inicial
     */
    public boolean isInicial() {
        return inicial;
    }

    /**
     * @param inicial the inicial to set
     */
    public void setInicial(boolean inicial) {
        this.inicial = inicial;
    }

    /**
     * @return the estado
     */
    public boolean isAceptacion() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setAceptacion(boolean estado) {
        this.estado = estado;
    }
   
    
}
