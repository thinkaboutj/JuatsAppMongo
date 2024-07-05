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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    
    
   /**
    *   Método para crear una entidad de chat en la base de datos
    *   se espera que este metodo sea usado en el frame DlgNuevoChat para crear el chat en la base de datos
    *
    *   @param chatDTO chat que se va a crear en la base de datos
    *   @throws NegocioException cacha las excepciones de persistencia
    */
    @Override
    public void agregar(ChatDTO chatDTO) throws NegocioException {
        Chat chatEntidad = transformarEnEntidad(chatDTO);
        try {
            dao.agregar(chatEntidad);
        } catch (PersistenciaException ex) {
            throw new NegocioException(ex);
        }
    }
    
   /**
    *   Método para consultar los chats en los que participa un usuario
    *   se espera que este metodo se use en el frmChat para consultar los chats en los que participa un usuario
    *  
    *   @param idUsuario ObjectId del usuario que queremos consultar sus chats
    *   @return List regresa una lista de chats en los que participa
    *   @throws NegocioException cacha las excepciones de persistencia
    */    
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
    
   /**
    *   Actualmente usa para consultar del chat, los participantes y poder verificar en el frmChat
    *   los participantes del chat, y despues usar el metodo de esContacto de  UsuarioBO
    *   para verificar si es contacto y luego poner o el numero de telefono o el nombre del usuario en el panel
    *
    *   @param id ObjectId del chat que queremos consultar
    *   @return ChatDTO regresa un chatDTO
    *   @throws NegocioException cacha las excepciones de persistencia
    */        
    @Override
    public ChatDTO consultar(ObjectId id) throws NegocioException {        
        try {
            return transformarEnDTO(dao.consultar(id));
        } catch (PersistenciaException ex) {
            throw new NegocioException(ex);
        }
    }
    
   /**
    *   Método para insertar un mensaje en un chat, se espera que este metodo se use
    *   en el frmChat para insertarle mensaje al chat
    *
    *   @param idChat ObjectId del chat al que se le va a insertar el mensaje
    *   @param mensaje MensajeDTO que se le va a insertar al chat
    *   @throws NegocioException cacha las excepciones de persistencia
    */        
    @Override
    public void enviarMensaje(ObjectId idChat, MensajeDTO mensaje) throws NegocioException {
        try {
            dao.enviarMensaje(idChat, transformarEnEntidad(mensaje));
        } catch (PersistenciaException ex) {
            throw new NegocioException(ex);
        }
    }
    
   /**
    *   Método para consultar los mensajes de un chat, se espera que
    *   el metodo se use en el frmChat despues de dar click en la imagen del usuario
    *   para asi cargar los mensajes en el chat
    *
    *   @return List regresa la lista de mensajesDTO que tiene un chat
    *   @param chatId ObjectId del chat del que queremos consultar los mensajes
    *   @throws NegocioException cacha las excepciones de persistencia
    */      
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
    
    
   /**
    *   Método para consultar los mensajes de un chat ordenados por fecha, se espera que
    *   el metodo se use en el frmChat despues de dar click en la imagen del usuario
    *   para asi cargar los mensajes en el chat ordenados por fecha
    *
    *   @return List regresa la lista de mensajesDTO que tiene un chat
    *   @param chatId ObjectId del chat del que queremos consultar los mensajes
    *   @throws NegocioException cacha las excepciones de persistencia
    */      
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
    
   /**
    *   Método para editar un mensaje 
    *
    *   @param idChat ObjectId del chat para identificar de donde pertenece el mensaje
    *   @param mensaje Mensaje que queremos editar
    *   @throws NegocioException cacha las excepciones de persistencia
    */      
    @Override
    public void editarMensaje(ObjectId idChat, MensajeDTO mensaje) throws NegocioException {
        try {
            dao.editarMensaje(idChat, transformarEnEntidad(mensaje));
        } catch (PersistenciaException ex) {
            throw new NegocioException(ex);
        }
    }
    
   /**
    *   Método para eliminar un mensaje 
    *
    *   @param idChat ObjectId del chat para identificar de donde pertenece el mensaje
    *   @param fechaDeRegistro la fechaHora del mensaje que vamos a eliminar, 
    *   @throws NegocioException cacha las excepciones de persistencia
    */    
    @Override
    public void eliminarMensaje(ObjectId idChat, LocalDateTime fechaDeRegistro) throws NegocioException {
        try {
            dao.eliminarMensaje(idChat, fechaDeRegistro);
        } catch (PersistenciaException ex) {
            throw new NegocioException(ex);
        }
    }
    

}
