/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import DTOs.ChatDTO;
import dao.ChatDAO;
import entidades.Chat;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import interfaces.IChatBO;
import interfaces.IChatDAO;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * Clase de negocio para operaciones relacionadas con chats.
 *
 * @author Jesus Medina (╹ڡ╹ ) ID:00000247527
 */
public class ChatBO implements IChatBO{
    
    private IChatDAO dao;
    
    public ChatBO(){
        dao = new ChatDAO();
    }
    
    private Chat transformarEnEntidad(ChatDTO chatDTO){
        Chat chatEntidad = new Chat(chatDTO.getNombre(), chatDTO.getImagen(), chatDTO.getIdParticipantes());
        return chatEntidad;
    }
    
    
    @Override
    public void agregar(ChatDTO chatDTO) throws NegocioException {
        Chat chatEntidad = transformarEnEntidad(chatDTO);
        
        try {
            dao.agregar(chatEntidad);
        } catch (PersistenciaException ex) {
            throw new NegocioException(ex);
        }
    }
    
    

}
