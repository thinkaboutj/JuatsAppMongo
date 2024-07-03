/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;

import dao.ChatDAO;
import dao.UsuarioDAO;
import entidades.Chat;
import entidades.Usuario;
import excepciones.PersistenciaException;
import interfaces.IChatDAO;
import interfaces.IUsuarioDAO;
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
        ObjectId id = new ObjectId("6684baebc0f95c1d815dcd27");
        
        List<Chat> chats = new ArrayList<>();
        
        try {
            chats = chatDAO.consultarChatsDelUsuario(id);
            System.out.println(chats);
        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        
        
    }

    
}
