/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;

import dao.ChatDAO;
import dao.UsuarioDAO;
import entidades.Chat;
import entidades.Mensaje;
import entidades.Usuario;
import excepciones.PersistenciaException;
import interfaces.IChatDAO;
import interfaces.IUsuarioDAO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.bson.types.ObjectId;

/**
 *
 * @author Jesus Medina (╹ڡ╹ ) ID:00000247527
 */
public class prueba {

    public static void main(String[] args) {
        IChatDAO chatDAO = new ChatDAO();
        
        ObjectId idChat = new ObjectId("66872a1be4373941285731d2");
        
        ObjectId idUsuario = new ObjectId("6684baebc0f95c1d815dcd27");
        Mensaje mensaje = new Mensaje(idUsuario, "Mensajeeee", LocalDateTime.now());
        
        try {
            chatDAO.enviarMensaje(idChat, mensaje);
        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        
        
    }

    
}
