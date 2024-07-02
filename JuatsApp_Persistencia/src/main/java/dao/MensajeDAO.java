package dao;

import Conexion.ConexionBD;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import entidades.Mensaje;
import excepciones.PersistenciaException;
import interfaces.IMensajeDAO;
import java.util.LinkedList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;



/**
 * Clase de acceso a datos para la entidad Mensaje.
 * 
 * Implementa operaciones de creación, lectura, actualización y eliminación de mensajes en la base de datos.
 * 
 * @author Jesus Medina (╹ڡ╹ ) ID:00000247527
 */
public class MensajeDAO implements IMensajeDAO{
    
    private final MongoCollection<Mensaje> mensajeCollection;

    public MensajeDAO() {
        this.mensajeCollection = ConexionBD.getInstance().getDatabase().getCollection("mensajes", Mensaje.class);
    }
    @Override
    public void agregar(Mensaje mensaje) throws PersistenciaException {
        try {
            mensajeCollection.insertOne(mensaje);
        } catch (Exception e) {
            throw new PersistenciaException("No fue posible agregar el mensaje.", e);
        }
    }

    @Override
    public void actualizar(Mensaje mensaje) throws PersistenciaException {
        try {
            mensajeCollection.updateOne(
                    Filters.eq("_id", mensaje.getId()), new Document("$set", new Document() 
                            .append("usuario", mensaje.getIdUsuarioId())
                            .append("texto", mensaje.getTexto())
                            .append("imagen", mensaje.getImagen())
                            .append("fecha_de_registro", mensaje.getFecha_de_registro())
                    )
            );
        } catch (Exception e) {
            throw new PersistenciaException("No fue posible actualizar el mensaje.", e);
        }
    }

    @Override
    public List<Mensaje> consultarTodos() throws PersistenciaException {
        try {
            List<Mensaje> listaMensajes = new LinkedList<>();
            mensajeCollection.find().into(listaMensajes);
            return listaMensajes;
        } catch (Exception e) {
            throw new PersistenciaException("No fue posible consultar los mensajes.", e);
        }
    }

    @Override
    public Mensaje consultar(ObjectId idMensaje) throws PersistenciaException {
        try {
            return mensajeCollection.find(Filters.eq("_id", idMensaje)).first();
        } catch (Exception e) {
            throw new PersistenciaException("No fue posible consultar el mensaje.", e);
        }
    }

    @Override
    public List<Mensaje> consultarPorUsuario(ObjectId idUsuario) throws PersistenciaException {
        try {
            List<Mensaje> listaMensajes = new LinkedList<>();
            mensajeCollection.find(Filters.eq("usuario._id", idUsuario)).into(listaMensajes);
            return listaMensajes;
        } catch (Exception e) {
            throw new PersistenciaException("No fue posible consultar los mensajes del usuario.", e);
        }
    }
   
}
