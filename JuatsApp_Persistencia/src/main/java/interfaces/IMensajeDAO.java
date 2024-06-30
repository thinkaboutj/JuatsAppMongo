package interfaces;

import entidades.Mensaje;
import excepciones.PersistenciaException;
import java.util.List;
import org.bson.types.ObjectId;



/**
 * Interfaz para el acceso a datos de la entidad Mensaje.
 * Define métodos para operaciones CRUD (crear, leer, actualizar, eliminar) en la base de datos.
 * 
 * @author Jesus Medina (╹ڡ╹ ) ID:00000247527
 */
public interface IMensajeDAO {

    public void agregar(Mensaje mensaje) throws PersistenciaException;

    public void actualizar(Mensaje mensaje) throws PersistenciaException;

    public List<Mensaje> consultarTodos() throws PersistenciaException;

    public Mensaje consultar(ObjectId idMensaje) throws PersistenciaException;

    public List<Mensaje> consultarPorUsuario(ObjectId idUsuario) throws PersistenciaException;
   
}
