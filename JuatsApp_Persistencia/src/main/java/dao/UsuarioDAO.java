package dao;

import static com.mongodb.client.model.Aggregates.*;
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
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Filters.in;
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
    private final MongoCollection<Document> chatCollection;
    
    public UsuarioDAO() {
        this.usuarioCollection = ConexionBD.getInstance().getDatabase().getCollection("usuarios", Usuario.class);
        this.chatCollection = ConexionBD.getInstance().getDatabase().getCollection("chats");
    }
    
   /**
    *  Inserta un usuario en la base de datos
    *
    *  @param usuario usuario a insertar
    *  @throws PersistenciaException cacha MongoExceptions
    */
    @Override
    public void agregar(Usuario usuario) throws PersistenciaException {
        try {
            Document query = new Document("telefono", usuario.getTelefono());
            Usuario usuarioExistente = usuarioCollection.find(query).first();

            if (usuarioExistente != null) {
                throw new PersistenciaException("Ya existe un usuario con este telefono registrado");
            }
            
            usuarioCollection.insertOne(usuario);
        } catch (MongoException e) {
            throw new PersistenciaException(e);
        }
    }
    
   /**
    *  Consulta un usuario de la base de datos segun su object id
    *   
    *  @param idUsuario ObjectId del usuario que queremos consultar
    *  @return Usuario usuario encontrado
    *  @throws PersistenciaException cacha MongoExceptions
    */
    @Override
    public Usuario consultar(ObjectId idUsuario) throws PersistenciaException {
        try {
            return usuarioCollection.find(Filters.eq("_id", idUsuario)).first();
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible consultar el usuario.", e);
        }
    }
    
   /**
    *  consultar un usuario segun su telefono
    *  creo que este metodo no lo usamos para nada 
    *   
    *  @param telefono telefono del usuario
    *  @return Usuario usuario encontrado
    *  @throws PersistenciaException cacha MongoExceptions
    */
    @Override
    public Usuario consultar(String telefono) throws PersistenciaException {
        try {
            return usuarioCollection.find(Filters.eq("telefono", telefono)).first();
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible consultar el usuario.", e);
        }
    }
    
    
   /**
    *  consultar un usuario segun su nombre de usuario
    *  creo que este metodo no lo usamos para nada 
    *   
    *  @param username nombre del usuario
    *  @return Usuario usuario encontrado
    *  @throws PersistenciaException cacha MongoExceptions
    */
    @Override
    public Usuario consultarPorUsuario(String username) throws PersistenciaException {
        try {
            return usuarioCollection.find(Filters.eq("username", username)).first();
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible consultar el usuario.", e);
        }
    }
    
    
   /**
    *   Metodo para actualizar un usuario de la base de datos.
    *   El metodo lo reemplaza filtrando usuarios por id
    * 
    *  @param usuario Recibe un usuario, y se va a reemplazar segun su ObjectId en la base de datos con los nuevos atributos
    *  @throws PersistenciaException cacha MongoExceptions
    */
    @Override
    public void actualizar(Usuario usuario) throws PersistenciaException {
        try {
            usuarioCollection.replaceOne(Filters.eq("_id", usuario.getId()), usuario);
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible actualizar el usuario.", e);
        }
    }
    
   /**
    *   Metodo para validar las credenciales del usuario
    * 
    *  @return Usuario regresa el usuario para poder pasar al siguiente frame con su ObjectId
    *  @param contrasena Recibe la contraseña del usuario, ya encriptada desde la capa de negocio
    *  @param telefono Recibe el teléfono del usuario
    *  @throws PersistenciaException cacha MongoExceptions
    */
    @Override
    public Usuario login(String contrasena, String telefono) throws PersistenciaException {
        try {
            Bson filter = Filters.and(Filters.eq("contrasena", contrasena), Filters.eq("telefono", telefono));
            return usuarioCollection.find(filter).first();
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible consultar el usuario.", e);
        }
    }
    
   /**
    *   Metodo para consultar los usuarios que un usuario tiene agregados como contactos
    * 
    *  @return List regresa la lista de usuarios
    *  @param objectId ObjectId del usuario que queremos sus contactos
    *  @throws PersistenciaException cacha MongoExceptions
    */
    @Override
    public List<Usuario> consultarContactosDelUsuario(ObjectId objectId) throws PersistenciaException {
        List<Usuario> contactos = new ArrayList<>();
        
        try {
            Usuario usuario = usuarioCollection.find(Filters.eq("_id", objectId)).first();
            List<ObjectId> contactosIds = usuario.getContactos();

            if (contactosIds != null) {
                for (ObjectId contactoId : contactosIds) {
                    Usuario contacto = usuarioCollection.find(Filters.eq("_id", contactoId)).first();
                    if (contacto != null) {
                        contactos.add(contacto);
                    }
                }
            }
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible consultar los contactos del usuario.", e);
        }
        return contactos;
    }
    
   /**
    *   Metodo para agregar un contacto a la lista de contactos de un usuario,
    *   el metodo lo que hace es consultar la lista de contactos del usuario, y el mismo metodo
    *   verifica si contiene el id del contacto, si no lo contiene lo agrega a la lista y actualiza la
    *   lista de contactos del usuario con la nueva creada
    * 
    *   si la lista de contactos es nula entonces la crea y le añade el contacto
    * 
    *  @param objectId ObjectId del usuario al que le vamos a agregar el contacto
    *  @param idContacto ObjectId del contacto que agregaremos
    *  @throws PersistenciaException cacha MongoExceptions
    */
    @Override
    public void agregarContacto(ObjectId idUsuario, ObjectId idContacto) throws PersistenciaException {
        try {
                Usuario usuario = usuarioCollection.find(Filters.eq("_id", idUsuario)).first();
            
                List<ObjectId> contactos = usuario.getContactos();
                
                if (contactos == null) {
                    contactos = new ArrayList<>();
                }
                
                if (!contactos.contains(idContacto)) {
                    contactos.add(idContacto);
                }
                
                usuarioCollection.updateOne(Filters.eq("_id", idUsuario), Updates.set("contactos", contactos));
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible agregar el contacto al usuario.", e);
        }
    }
    
   /**
    *   consulta el usuario, agarra la lista de sus contactos,
    *   y con sus ids va consultando uno por uno y regresa la lista
    * 
    *  @return List Regresa el array de contactos de un usuario
    *  @param idUsuario ObjectId del  usuario que queremos sus contactos
    *  @throws PersistenciaException cacha MongoExceptions
    */
    @Override
    public List<Usuario> consultarContactos(ObjectId idUsuario) throws PersistenciaException {
        List<Usuario> contactos = new ArrayList<>();
        
        try {
            Usuario usuario = usuarioCollection.find(Filters.eq("_id", idUsuario)).first();
            List<ObjectId> contactosIds = usuario.getContactos();

            if (contactosIds != null) {

                for (ObjectId idContacto : contactosIds) {
                    Usuario contacto = usuarioCollection.find(Filters.eq("_id", idContacto)).first();
                    if (contacto != null) {
                        contactos.add(contacto);
                    }

                }

            }
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible consultar los contactos del usuario.", e);
        }
        return contactos;
    }
    
   /**
    *   elimina un contacto de un usuario, 
    *   la manera en la que funciona el metodo es que consulta la lista de contactos 
    *   del usuario, y si contiene el id del contacto que le pasamos, lo eliminamos y actualizamos la lista
    *   de contactos del usuario en la base de datos
    * 
    *  @param idUsuario ObjectId de la persona en la que se encuentra el contacto
    *  @param idContacto ObjectId del contacto que eliminaremos
    *  @throws PersistenciaException cacha MongoExceptions
    */
    @Override
    public void eliminarContacto(ObjectId idUsuario, ObjectId idContacto) throws PersistenciaException {
        try {
            Usuario usuario = usuarioCollection.find(Filters.eq("_id", idUsuario)).first();
            
            List<ObjectId> contactos = usuario.getContactos();
                
            if (contactos != null && contactos.contains(idContacto)) {
                contactos.remove(idContacto);
                usuarioCollection.updateOne(Filters.eq("_id", idUsuario), Updates.set("contactos", contactos));
            }

        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible eliminar el contacto del usuario.", e);
        }
    }
    
   /**
    *   El metodo lo que hace es consultar los usuarios que están en la base de datos 
    *   y que no tiene el usuario en sus contactos  
    * 
    *   consulta todos los usuarios de la base de datos y los contactos del usuario, 
    *   y compara las dos listas  a ver si la de contactos contiene a alguno de todos los usuarios
    *   en caso de que no, agrega el contacto a una nueva lista y la regresa
    * 
    *  @return List regresa la lista de usuarios que no tiene agregados
    *  @param idUsuario ObjectId del usuario del que queremos todos los usuarios menos sus contactos
    *  @throws PersistenciaException cacha MongoExceptions
    */    
    @Override
    public List<Usuario> consultarTelefonosQueNoTieneEnContactos(ObjectId idUsuario) throws PersistenciaException {
        List<Usuario> noContactos = new ArrayList<>();

        try {
            Usuario usuario = usuarioCollection.find(Filters.eq("_id", idUsuario)).first();
            
            List<ObjectId> contactosIds = usuario.getContactos();
                List<Usuario> todosUsuarios = usuarioCollection.find().into(new ArrayList<>());
                
            for (Usuario usuarioActual : todosUsuarios) {
                if ((contactosIds == null || !contactosIds.contains(usuarioActual.getId())) && !usuarioActual.getId().equals(idUsuario)) {
                    noContactos.add(usuarioActual);
                }
            }

        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible consultar los usuarios no agregados a contactos.", e);
        }

        return noContactos;
    }
    
   /**
    *   El metodo lo que hace es consultar los usuarios del array de contactos, pero que aun no tiene chat con ellos 
    * 
    *  @return List regresa la lista de usuarios que no tiene un chat
    *  @param idUsuario ObjectId del contacto que eliminaremos
    *  @throws PersistenciaException cacha MongoExceptions
    */    
    @Override
    public List<Usuario> consultarContactosSinChat(ObjectId idUsuario) throws PersistenciaException {
        List<Usuario> contactosSinChat = new ArrayList<>();
        List<ObjectId> contactosUsuario = new ArrayList<>();

        // Crear lista de ObjectIds de los contactos del usuario
        List<Usuario> contactos = consultarContactos(idUsuario);

        for (Usuario contacto : contactos) {
            contactosUsuario.add(contacto.getId());
        }

        // Crear la etapa de agregación para buscar chats que contengan los contactos del usuario
        List<Bson> etapas = new ArrayList<>();
        etapas.add(match(in("idParticipantes", contactosUsuario)));
        etapas.add(group("$idParticipantes"));

        // Ejecutar la agregación para obtener los contactos con los que hay chat
        List<Document> contactosConChatDocs = new ArrayList<>();
        chatCollection.aggregate(etapas).into(contactosConChatDocs);

        // Crear lista de ObjectIds de contactos con los que hay chat
        List<ObjectId> contactosConChatIds = new ArrayList<>();
        for (Document doc : contactosConChatDocs) {
            List<ObjectId> idParticipantes = (List<ObjectId>) doc.get("_id");
            contactosConChatIds.addAll(idParticipantes);
        }

        // Identificar contactos sin chat comparando con todos los contactos del usuario
        for (ObjectId contacto : contactosUsuario) {
            if (!contactosConChatIds.contains(contacto) && !contacto.equals(idUsuario)) {
                Usuario usuario = consultar(contacto);
                contactosSinChat.add(usuario);
            }
        }

        return contactosSinChat;
    }
    
    
   /**
    *   lo que hace el metodo es que consigue la lista de los contactos del usuario
    *   y compara a ver si contiene el usuario que le pasamos como parametro para despues regresar el booleano
    * 
    *  @return boolean regresa booleano si lo tiene en su lista de contactos
    *  @param idUsuario 
    *  @param idContacto 
    *  @throws PersistenciaException cacha MongoExceptions
    */        
    @Override
    public boolean esContacto(ObjectId idUsuario, ObjectId idContacto) throws PersistenciaException {
        try {
            Usuario usuario = usuarioCollection.find(Filters.eq("_id", idUsuario)).first();

            List<ObjectId> contactosIds = usuario.getContactos();
                
            if (contactosIds != null && contactosIds.contains(idContacto)) {
                return true;
            }
            
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible verificar el contacto del usuario.", e);
        }

        return false;
    }

    
    
   
}
