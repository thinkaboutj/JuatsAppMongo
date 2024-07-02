/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import DTOs.ChatDTO;
import excepciones.NegocioException;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author crazy
 */
public interface IChatBO {
    
    public void agregar(ChatDTO chat) throws NegocioException;
    
   public List<ChatDTO> consultarChatsDelUsuario(ObjectId idUsuario) throws NegocioException;

    
}
