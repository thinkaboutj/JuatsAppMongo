package dao;

import Conexion.ConexionBD;
import entidades.Domicilio;
import entidades.Usuario;
import excepciones.PersistenciaException;
import interfaces.IUsuarioDAO;
import java.time.LocalDate;
import java.time.ZoneId;
import org.bson.conversions.Bson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import com.mongodb.MongoException;
import com.mongodb.client.model.Updates;
import java.util.ArrayList;
import java.util.List;



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
    
    
    
    private Usuario convertirDocumentoAUsuario(Document doc) {
        ObjectId id = doc.getObjectId("_id");
        String usuario = doc.getString("usuario");
        String contrasena = doc.getString("contrasena");
        Document domicilioDoc = doc.get("domicilio", Document.class);
        Domicilio domicilio = new Domicilio(
                domicilioDoc.getString("calle"),
                domicilioDoc.getString("codigoPostal"),
                domicilioDoc.getString("colonia"),
                domicilioDoc.getString("numero")
        );
        String telefono = doc.getString("telefono");
        String sexo = doc.getString("sexo");
        byte[] imagen = doc.get("imagen", org.bson.types.Binary.class).getData();
        LocalDate fechaNacimiento = doc.getDate("fechaNacimiento").toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        List<ObjectId> contactos = doc.getList("contactos", ObjectId.class);
        return new Usuario(id, usuario, contrasena, domicilio, telefono, sexo, imagen, fechaNacimiento, contactos);
    }
    
    @Override
    public List<Usuario> consultarContactosDelUsuario(ObjectId objectId) throws PersistenciaException {
        List<Usuario> contactos = new ArrayList<>();
        
        try {
            Usuario usuario = usuarioCollection.find(Filters.eq("_id", objectId)).first();
            if (usuario != null) {
                List<ObjectId> contactosIds = usuario.getContactos();
                if (contactosIds != null) {
                    for (ObjectId contactoId : contactosIds) {
                        Usuario contacto = usuarioCollection.find(Filters.eq("_id", contactoId)).first();
                        if (contacto != null) {
                            contactos.add(contacto);
                        }
                    }
                }
            }
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible consultar los contactos del usuario.", e);
        }

        return contactos;
    }
    
    @Override
    public void agregarContacto(ObjectId idUsuario, ObjectId idContacto) throws PersistenciaException {
        try {
            Usuario usuario = usuarioCollection.find(Filters.eq("_id", idUsuario)).first();
            if (usuario != null) {
                List<ObjectId> contactos = usuario.getContactos();
                if (contactos == null) {
                    contactos = new ArrayList<>();
                }
                if (!contactos.contains(idContacto)) {
                    contactos.add(idContacto);
                }
                usuarioCollection.updateOne(
                        Filters.eq("_id", idUsuario),
                        Updates.set("contactos", contactos)
                );
            } else {
                throw new PersistenciaException("No se encontró el usuario con ID: " + idUsuario);
            }
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible agregar el contacto al usuario.", e);
        }
    }
    
    @Override
    public List<Usuario> consultarContactos(ObjectId idUsuario) throws PersistenciaException {
        List<Usuario> contactos = new ArrayList<>();
        
        try {
            Usuario usuario = usuarioCollection.find(Filters.eq("_id", idUsuario)).first();
            if (usuario != null) {
                List<ObjectId> contactosIds = usuario.getContactos();
                if (contactosIds != null) {
                    for (ObjectId idContacto : contactosIds) {
                        Usuario contacto = usuarioCollection.find(Filters.eq("_id", idContacto)).first();
                        if (contacto != null) {
                            contactos.add(contacto);
                        }
                    }
                }
            } else {
                throw new PersistenciaException("No se encontró el usuario con ID: " + idUsuario);
            }
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible consultar los contactos del usuario.", e);
        }
        return contactos;
    }
    
    @Override
    public void eliminarContacto(ObjectId idUsuario, ObjectId idContacto) throws PersistenciaException {
        try {
            Usuario usuario = usuarioCollection.find(Filters.eq("_id", idUsuario)).first();
            if (usuario != null) {
                List<ObjectId> contactos = usuario.getContactos();
                if (contactos != null && contactos.contains(idContacto)) {
                    contactos.remove(idContacto);
                    usuarioCollection.updateOne(Filters.eq("_id", idUsuario), Updates.set("contactos", contactos));
                }
            } else {
                throw new PersistenciaException("No se encontró el usuario con ID: " + idUsuario);
            }
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible eliminar el contacto del usuario.", e);
        }
    }
    
    @Override
    public List<Usuario> consultarTelefonosQueNoTieneEnContactos(ObjectId idUsuario) throws PersistenciaException {
        List<Usuario> noContactos = new ArrayList<>();

        try {
            // Obtener el usuario por su ObjectId
            Usuario usuario = usuarioCollection.find(Filters.eq("_id", idUsuario)).first();

            if (usuario != null) {
                // Obtener la lista de ObjectIds de los contactos
                List<ObjectId> contactosIds = usuario.getContactos();

                // Obtener todos los usuarios de la base de datos
                List<Usuario> todosUsuarios = usuarioCollection.find().into(new ArrayList<>());

                // Filtrar los usuarios que no están en la lista de contactos
                for (Usuario usuarioActual : todosUsuarios) {
                    if (contactosIds == null || !contactosIds.contains(usuarioActual.getId())) {
                        noContactos.add(usuarioActual);
                    }
                }
            } else {
                throw new PersistenciaException("No se encontró el usuario con ID: " + idUsuario);
            }
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible consultar los usuarios no contactos.", e);
        }

        return noContactos;
    }
    
    
   
}
