/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hilospruebas;

import java.util.Random;

/**
 *
 * @author andre
 */
public class DescargaArchivos implements Runnable {

    private final int idArchivo;
    private final ControlCancelado control;
    private final DescargaListener listener;
    private final Random random = new Random();
    
    public DescargaArchivos(int idArchivo, ControlCancelado control, DescargaListener listener){
        this.idArchivo = idArchivo;
        this.control = control;
        this.listener = listener;
    }
    
    @Override
    public void run() {
    int progreso = 0;
    boolean completado = false;
    
    while (progreso < 100){
        if (control.isCancelado()){
            listener.enMensaje("Chavo, el archivo: " + idArchivo + ": descarga cancelado en el " +progreso +"%");
            break;
        }
        
        int avance = 5 + random.nextInt(11); // 5 < x < 15
        progreso = Math.min(100, progreso + avance);
        
        listener.enProgreso(idArchivo, progreso);
        listener.enMensaje("Chavo, el archivo: " + idArchivo + ": descarga al " +progreso +"%");
        
        try{
            Thread.sleep(150 + random.nextInt(750));
        } catch(InterruptedException e){
            Thread.currentThread().interrupt();
            listener.enMensaje("Chavo, el archivo" + idArchivo + ": se interrumpio");
            break;
            }
        
        if(progreso>= 100)
            completado = true;
        
        
            
        }
    
    if(completado){
        listener.enMensaje("Chavo, el archivo" + idArchivo + "se completo la descarga");
    }
        listener.enDescargaFinalizada(idArchivo, completado);
    }
    
    }
    

