/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import DTOs.ChatDTO;
import DTOs.MensajeDTO;
import dao.ChatDAO;
import entidades.Chat;
import entidades.Mensaje;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import interfaces.IChatBO;
import interfaces.IChatDAO;
import java.util.ArrayList;
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
        chatDTO.setId(chat.getId());
        return chatDTO;
    }
    
    private MensajeDTO transformarEnDTO(Mensaje mensaje){
        return new MensajeDTO(mensaje.getIdUsuario(), mensaje.getTexto(), mensaje.getImagen(), mensaje.getFecha_de_registro());
    }
    private Mensaje transformarEnEntidad(MensajeDTO mensaje){
        return new Mensaje(mensaje.getIdUsuario(), mensaje.getTexto(), mensaje.getImagen(), mensaje.getFecha_de_registro());
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
            for (int i = 0; i < chatsDelUsuario.size(); i++){
                ChatDTO chatDTO = transformarEnDTO(chatsDelUsuario.get(i));
                chats.add(chatDTO);
            } 
            return chats;
        } catch (PersistenciaException ex) {
            throw new NegocioException();
        }
    }

    @Override
    public ChatDTO consultar(ObjectId id) throws NegocioException {        
        try {
            return transformarEnDTO(dao.consultar(id));
        } catch (PersistenciaException ex) {
            throw new NegocioException(ex);
        }
    }

    @Override
    public void enviarMensaje(ObjectId idChat, MensajeDTO mensaje) throws NegocioException {
        
        try {
            dao.enviarMensaje(idChat, transformarEnEntidad(mensaje));
        } catch (PersistenciaException ex) {
            throw new NegocioException(ex);
        }
    }

    @Override
    public List<MensajeDTO> obtenerMensajes(ObjectId chatId) throws NegocioException {
        try {
            List<Mensaje> mensajes = dao.obtenerMensajes(chatId);
            List<MensajeDTO> mensajesDTO = new ArrayList<>();
            
            for(int i = 0; i < mensajes.size(); i++){
                mensajesDTO.add(transformarEnDTO(mensajes.get(i)));
            }
            return mensajesDTO;
        } catch (PersistenciaException ex) {
            throw new NegocioException(ex);
        }
    }

    @Override
    public List<MensajeDTO> obtenerMensajesOrdenadosPorFecha(ObjectId chatId) throws NegocioException {
        try {
            List<Mensaje> mensajes = dao.obtenerMensajesOrdenadosPorFecha(chatId);
            List<MensajeDTO> mensajesDTO = new ArrayList<>();
            for(int i = 0; i < mensajes.size(); i++){
                mensajesDTO.add(transformarEnDTO(mensajes.get(i)));
            }
            return mensajesDTO;
        } catch (PersistenciaException ex) {
            throw new NegocioException(ex);
        }   
    }
    

}
