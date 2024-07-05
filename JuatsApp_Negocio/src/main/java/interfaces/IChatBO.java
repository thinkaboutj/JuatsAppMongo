/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import DTOs.ChatDTO;
import DTOs.MensajeDTO;
import excepciones.NegocioException;
import java.time.LocalDateTime;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author crazy
 */
public interface IChatBO {
    
    public void agregar(ChatDTO chat) throws NegocioException;
    
    public List<ChatDTO> consultarChatsDelUsuario(ObjectId idUsuario) throws NegocioException;
    
    public ChatDTO consultar(ObjectId id) throws NegocioException;
    
    public void enviarMensaje(ObjectId idChat, MensajeDTO mensaje) throws NegocioException;

    public List<MensajeDTO> obtenerMensajes(ObjectId chatId) throws NegocioException;
    
    public List<MensajeDTO> obtenerMensajesOrdenadosPorFecha(ObjectId chatId) throws NegocioException;

    public void editarMensaje(ObjectId idChat, MensajeDTO mensaje) throws NegocioException;
    
    public void eliminarMensaje(ObjectId idChat, LocalDateTime fechaDeRegistro) throws NegocioException;

    
}
