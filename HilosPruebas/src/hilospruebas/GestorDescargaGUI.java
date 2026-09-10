/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hilospruebas;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;


/**
 *
 * @author andre
 */
public class GestorDescargaGUI extends JFrame implements DescargaListener {

    private static final int NUM_ARCHIVOS = 3;
    private final JProgressBar[] barras = new JProgressBar[NUM_ARCHIVOS];
    private final JButton btnIniciar = new JButton("Iniciar descargas");
    private final JButton btnCancelar = new JButton ("Cancelar");
    private final JTextArea bitacora = new JTextArea();
    
    private final ControlCancelado control = new ControlCancelado();
    
    private int descargasCompletadas = 0;
    
    public GestorDescargaGUI(){
        super("Descargas pro");
      construirGUI();
      setSize(480,420);
      setLocationRelativeTo(null);
    }
    
    private void construirGUI(){
        JPanel panelPrincipal = new JPanel(new BorderLayout(10,10));
        panelPrincipal.setBorder(new EmptyBorder(12,12,12,12));
        
        JPanel panelBarras = new JPanel(new GridLayout(NUM_ARCHIVOS,1,6,10));
        for(int i = 0; i < NUM_ARCHIVOS;i++){
            barras[i] = new JProgressBar(0,100);
            barras[i].setStringPainted(true);
            barras[i].setString("Archivo " +(i + 1) + ": 0%" );
            panelBarras.add(envolverConEtiqueta("Archivo " + (i + 1), barras[i]));
        }
        panelPrincipal.add(panelBarras,BorderLayout.NORTH);
        bitacora.setEditable(false);
        bitacora.setLineWrap(true);
        JScrollPane scroll = new JScrollPane(bitacora);
        scroll.setBorder(BorderFactory.createTitledBorder("bitacora"));
        panelPrincipal.add(scroll, BorderLayout.CENTER);
        
        JPanel panelBotones = new JPanel (new FlowLayout(FlowLayout.CENTER,10,0));
        btnCancelar.setEnabled(false);
        panelBotones.add(btnIniciar);
        panelBotones.add(btnCancelar);
        panelPrincipal.add(panelBotones,BorderLayout.SOUTH);
        
        btnIniciar.addActionListener(e -> iniciarDescargas());
        btnIniciar.addActionListener(e -> cancelarDescargas());
        
        setContentPane(panelPrincipal);
    }
    
    
    private JPanel envolverConEtiqueta(String texto, JProgressBar barra){
        JPanel p = new JPanel(new BorderLayout(4,0));
        p.add(new JLabel(texto), BorderLayout.WEST);
        p.add(barra,BorderLayout.CENTER);
        return p;
    }
    
    private void iniciarDescargas(){
        control.reiniciar();
        descargasCompletadas = 0;
        bitacora.setText("");
        for(JProgressBar barra : barras){
            barra.setValue(0);
        }
        btnIniciar.setEnabled(true);
        btnCancelar.setEnabled(true);
        enMensaje("Iniciado" +NUM_ARCHIVOS + "descagas...." );
        
        for(int i = 1; i <= NUM_ARCHIVOS;i++){
            DescargaArchivos tarea = new DescargaArchivos(i,control,this);
            Thread hilo = new Thread(tarea, "Hilo-Descarga-" + i);
            hilo.start();
        }
        
    }
    
    private void cancelarDescargas(){
        control.cancelar();
        enMensaje("Cancelado");
        btnCancelar.setEnabled(false);
    }
    
    @Override
    public void enProgreso(int idArchivo, int progreso) {
   SwingUtilities.invokeLater(()-> {
       JProgressBar barra =  barras[idArchivo - 1];
       barra.setValue(progreso);
       barra.setString("Archivo " +idArchivo + ": " + progreso + "%");
   });
    }

    @Override
    public void enMensaje(String mensaje) {
         SwingUtilities.invokeLater(()-> {
        bitacora.append(mensaje + "\n");
        bitacora.setCaretPosition(bitacora.getDocument().getLength());
   });
    }

    @Override
    public void enDescargaFinalizada(int idArchivo, boolean completada) {
         
             if(completada && incrementarYVerificarTodasListas()){
                 enMensaje("Todas los archivos han sido descargados correctamente");
                 
             }
SwingUtilities.invokeLater(()-> {
    btnIniciar.setEnabled(true);
    btnCancelar.setEnabled(false);
   });
    }
    
    
    private synchronized boolean incrementarYVerificarTodasListas(){
        descargasCompletadas++;
        return descargasCompletadas == NUM_ARCHIVOS;
    }
    
    
}
