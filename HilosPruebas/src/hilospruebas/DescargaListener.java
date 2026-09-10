/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hilospruebas;

/**
 *
 * @author andre
 */
public interface DescargaListener {
    void enProgreso(int idArchivo, int progreso);
    void enMensaje(String mensaje);
    void enDescargaFinalizada(int idArchivo, boolean completada);
}
