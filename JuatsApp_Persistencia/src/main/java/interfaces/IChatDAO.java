
package interfaces;

import entidades.Chat;
import entidades.Mensaje;
import entidades.Usuario;
import excepciones.PersistenciaException;
import java.time.LocalDateTime;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;


/**
 * Interfaz para el acceso a datos de la entidad Chat.
 * Define métodos para operaciones CRUD (crear, leer, actualizar, eliminar) en la base de datos.
 * 
 * @author Jesus Medina (╹ڡ╹ ) ID:00000247527
 */
public interface IChatDAO {
    
    public void agregar(Chat chat) throws PersistenciaException;
    
    public List<Chat> consultarChatsDelUsuario(ObjectId idUsuario) throws PersistenciaException;
    
    public Chat consultar(ObjectId id) throws PersistenciaException;
    
    public void enviarMensaje(ObjectId idChat, Mensaje mensaje) throws PersistenciaException;

    public List<Mensaje> obtenerMensajes(ObjectId chatId) throws PersistenciaException;
    
    public List<Mensaje> obtenerMensajesOrdenadosPorFecha(ObjectId chatId) throws PersistenciaException;

    public void editarMensaje(ObjectId idChat, Mensaje mensaje) throws PersistenciaException;
    
    public void eliminarMensaje(ObjectId idChat, LocalDateTime fechaDeRegistro) throws PersistenciaException;


    

}
