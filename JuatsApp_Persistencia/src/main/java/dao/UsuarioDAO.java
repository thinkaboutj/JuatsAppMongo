package dao;

import Conexion.ConexionBD;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import entidades.Usuario;
import excepciones.PersistenciaException;
import java.util.ArrayList;
import org.bson.Document;
import java.util.Calendar;
import java.util.List;
import org.bson.types.ObjectId;




/**
 * Clase de acceso a datos para la entidad Usuario.
 *
 * Implementa operaciones de creación, lectura, actualización y eliminación de
 * usuarios en la base de datos.
 *
 * @author Jesus Medina (╹ڡ╹ ) ID:00000247527
 */
public class UsuarioDAO  {
    
    private final MongoCollection<Usuario> usuarioCollection;

    public UsuarioDAO() {
        this.usuarioCollection = ConexionBD.getInstance().getDatabase().getCollection("usuarios", Usuario.class);
    }
    
    public void agregar(Usuario usuario) throws PersistenciaException {
        try {
            usuarioCollection.insertOne(usuario);
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible agregar el usuario.", e);
        }
    }

    public List<Usuario> consultarTodos() throws PersistenciaException {
        try {
            return usuarioCollection.find().into(new ArrayList<>());
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible consultar los usuarios.", e);
        }
    }

    public Usuario consultar(ObjectId idUsuario) throws PersistenciaException {
        try {
            return usuarioCollection.find(Filters.eq("_id", idUsuario)).first();
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible consultar el usuario.", e);
        }
    }

    public Usuario consultar(String telefono) throws PersistenciaException {
        try {
            return usuarioCollection.find(Filters.eq("telefono", telefono)).first();
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible consultar el usuario.", e);
        }
    }

    public Usuario consultarPorUsuario(String username) throws PersistenciaException {
        try {
            return usuarioCollection.find(Filters.eq("username", username)).first();
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible consultar el usuario.", e);
        }
    }

    public void actualizar(Usuario usuario) throws PersistenciaException {
        try {
            usuarioCollection.replaceOne(Filters.eq("_id", usuario.getObjectId()), usuario);
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible actualizar el usuario.", e);
        }
    }

    public void actualizarPassword(Usuario usuario) throws PersistenciaException {
        try {
            usuarioCollection.updateOne(Filters.eq("_id", usuario.getObjectId()), 
                new Document("$set", new Document("password", usuario.getContrasena())));
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible actualizar la contraseña.", e);
        }
    }
    
    
   
}
