/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebas;

import DTOs.ChatDTO;
import DTOs.MensajeDTO;
import DTOs.UsuarioDTO;
import excepciones.NegocioException;
import interfaces.IChatBO;
import interfaces.IUsuarioBO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import javax.swing.JOptionPane;
import negocio.ChatBO;
import negocio.UsuarioBO;
import org.bson.types.ObjectId;

/**
 *
 * @author crazy
 */
public class pruebas {
    
    public static void main (String [] args){
        IChatBO chatBO = new ChatBO();
        ObjectId idChat = new ObjectId("6685ead6a71c8762af445447");
        
        ObjectId idUsuario = new ObjectId("6684baebc0f95c1d815dcd27");
        MensajeDTO mensaje = new MensajeDTO(idUsuario, "Mensaje para jose", LocalDateTime.now());
        
        try {
            chatBO.enviarMensaje(idChat, mensaje);
            System.out.println(chatBO.obtenerMensajesOrdenadosPorFecha(idChat));
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        
    }
    
}
