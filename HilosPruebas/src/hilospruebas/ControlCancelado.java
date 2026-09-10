/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hilospruebas;

/**
 *
 * @author andre
 */
public class ControlCancelado {
    
    private volatile boolean cancelado = false;
    
    
    public void cancelar(){
        cancelado = true;
    }
    
    public void reiniciar(){
        cancelado = false;
    }

    public boolean isCancelado() {
        return cancelado;
    }
    
    
    
}
