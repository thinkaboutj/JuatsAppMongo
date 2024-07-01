package dao;

import Conexion.ConexionBD;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import entidades.Usuario;
import excepciones.PersistenciaException;
import interfaces.IUsuarioDAO;
import java.util.ArrayList;
import org.bson.Document;
import java.util.List;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;




/**
 * Clase de acceso a datos para la entidad Usuario.
 *
 * Implementa operaciones de creación, lectura, actualización y eliminación de
 * usuarios en la base de datos.
 *
 * @author Jesus Medina (╹ڡ╹ ) ID:00000247527
 */
public class UsuarioDAO implements IUsuarioDAO {
    
    private final MongoCollection<Usuario> usuarioCollection;

    public UsuarioDAO() {
        this.usuarioCollection = ConexionBD.getInstance().getDatabase().getCollection("usuarios", Usuario.class);
    }
    
    @Override
    public void agregar(Usuario usuario) throws PersistenciaException {
        try {
            usuarioCollection.insertOne(usuario);
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible agregar el usuario.", e);
        }
    }

    @Override
    public List<Usuario> consultarTodos() throws PersistenciaException {
        try {
            return usuarioCollection.find().into(new ArrayList<>());
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible consultar los usuarios.", e);
        }
    }

    @Override
    public Usuario consultar(ObjectId idUsuario) throws PersistenciaException {
        try {
            return usuarioCollection.find(Filters.eq("_id", idUsuario)).first();
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible consultar el usuario.", e);
        }
    }

    @Override
    public Usuario consultar(String telefono) throws PersistenciaException {
        try {
            return usuarioCollection.find(Filters.eq("telefono", telefono)).first();
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible consultar el usuario.", e);
        }
    }

    @Override
    public Usuario consultarPorUsuario(String username) throws PersistenciaException {
        try {
            return usuarioCollection.find(Filters.eq("username", username)).first();
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible consultar el usuario.", e);
        }
    }

    @Override
    public void actualizar(Usuario usuario) throws PersistenciaException {
        try {
            usuarioCollection.replaceOne(Filters.eq("_id", usuario.getId()), usuario);
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible actualizar el usuario.", e);
        }
    }

    @Override
    public void actualizarPassword(Usuario usuario) throws PersistenciaException {
        try {
            usuarioCollection.updateOne(Filters.eq("_id", usuario.getId()), 
                new Document("$set", new Document("password", usuario.getContrasena())));
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible actualizar la contraseña.", e);
        }
    }

    @Override
    public Usuario login(String contrasena, String telefono) throws PersistenciaException {
        try {
            Bson filter = Filters.and(
                Filters.eq("contrasena", contrasena),
                Filters.eq("telefono", telefono)
            );
            return usuarioCollection.find(filter).first();
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible consultar el usuario.", e);
        }
    }
    

   
}
