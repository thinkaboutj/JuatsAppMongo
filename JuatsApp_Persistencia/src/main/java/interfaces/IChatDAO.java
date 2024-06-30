
package interfaces;

import entidades.Chat;
import entidades.Usuario;
import excepciones.PersistenciaException;
import java.util.List;
import org.bson.types.ObjectId;


/**
 * Interfaz para el acceso a datos de la entidad Chat.
 * Define métodos para operaciones CRUD (crear, leer, actualizar, eliminar) en la base de datos.
 * 
 * @author Jesus Medina (╹ڡ╹ ) ID:00000247527
 */
public interface IChatDAO {
    
    public void agregar(Chat chat) throws PersistenciaException;

    public void actualizar(Chat chat) throws PersistenciaException;
    
    public List<Chat> consultarTodos() throws PersistenciaException;

    public List<Chat> consultarTodos(Usuario usuario) throws PersistenciaException;

    public Chat consultar(Usuario usuario, Usuario receptor) throws PersistenciaException;

    public Chat consultar(ObjectId idChat) throws PersistenciaException;

}
