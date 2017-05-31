/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practica.automatas;

import java.util.Vector;

/**
 *
 * @author Julian
 * @author Felipe
 */
public class cola {
    private Vector elementos;
    private int cola;
    private int cabeza;
    
    public cola(){
        elementos = new Vector();
        cola=0;
        cabeza=0;
        
    }
    
    public void offer(Vector element){ 
        cola++;
        elementos.add(element);
       
    } 
    
    public Vector poll(){ 
        cabeza++; 
        return (Vector) elementos.get(cabeza-1); 
    }
    public boolean empty(){
    if (cola== cabeza){
      return(true);
    }
    return(false);
    }
            
    
}
