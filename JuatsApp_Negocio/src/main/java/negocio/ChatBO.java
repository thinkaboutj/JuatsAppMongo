/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import DTOs.ChatDTO;
import com.mongodb.MongoException;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Filters.in;
import dao.ChatDAO;
import entidades.Chat;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import interfaces.IChatBO;
import interfaces.IChatDAO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;



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
        Chat chatEntidad = new Chat(chatDTO.getIdParticipantes());
        return chatEntidad;
    }
    
    private ChatDTO transformarEnDTO(Chat chat){
        ChatDTO chatDTO = new ChatDTO(chat.getIdParticipantes());
        return chatDTO;
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
    
    @Override
    public List<ChatDTO> consultarChatsDelUsuario(ObjectId idUsuario) throws NegocioException {
        List<Chat> chatsDelUsuario = new ArrayList<>();
        List<ChatDTO> chats = new ArrayList<>();
        
        try {
            chatsDelUsuario = dao.consultarChatsDelUsuario(idUsuario);
            
            for (int i = 1; i < chatsDelUsuario.size(); i++){
                ChatDTO chatDTO = transformarEnDTO(chatsDelUsuario.get(i));
                chats.add(chatDTO);
            }
            
            return chats;
        } catch (PersistenciaException ex) {
            throw new NegocioException();
        }
        
        
    }
    

}
