package dao;

import Conexion.ConexionBD;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Aggregates.lookup;
import static com.mongodb.client.model.Aggregates.match;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.in;
import entidades.Chat;
import entidades.Usuario;
import excepciones.PersistenciaException;
import interfaces.IChatDAO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;


/**
 * Clase de acceso a datos para la entidad Chat. Implementa la interfaz IChatDAO
 * para operaciones CRUD en la base de datos.
 *
 * @author Jesus Medina (╹ڡ╹ ) ID:00000247527
 */
public class ChatDAO implements IChatDAO{

    private final MongoCollection<Chat> chatCollection;

    public ChatDAO() {
        this.chatCollection = ConexionBD.getInstance().getDatabase().getCollection("chats", Chat.class);

    }

    @Override
    public void agregar(Chat chat) throws PersistenciaException {
        try {
            chatCollection.insertOne(chat);
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible agregar el chat.", e);
        }
    }

    @Override
    public void actualizar(Chat chat) throws PersistenciaException {
        try {
            chatCollection.updateOne(
                    Filters.eq("_id", chat.getId()),
                    new Document("$set", new Document()
                            .append("idParticipantes", chat.getIdParticipantes())
                            .append("mensajes", chat.getMensajes())
                    )
            );
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible actualizar el chat.", e);
        }
    }

    @Override
    public List<Chat> consultarTodos() throws PersistenciaException {
        try {
            List<Chat> listaChats = new LinkedList<>();
            List<Bson> etapas = new ArrayList<>();
            
            etapas.add(lookup("usuarios", "idParticipantes", "_id", "participantes"));
            
            chatCollection.aggregate(etapas).into(listaChats);
            return listaChats;
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible consultar los chats.", e);
        }
    }

    @Override
    public List<Chat> consultarTodos(Usuario usuario) throws PersistenciaException {
        try {
            List<Chat> listaChats = new LinkedList<>();
            List<Bson> etapas = new ArrayList<>();
            
            etapas.add(match(in("idParticipantes", Arrays.asList(usuario.getId()))));
            etapas.add(lookup("usuarios", "idParticipantes", "_id", "participantes"));
            
            chatCollection.aggregate(etapas).into(listaChats);
            return listaChats;
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible consultar los chats.", e);
        }
    }

    @Override
    public Chat consultar(Usuario usuario, Usuario receptor) throws PersistenciaException {
        try {
            List<Chat> listaChats = new LinkedList<>();
            List<Bson> etapas = new ArrayList<>();
            
            etapas.add(match(Filters.and(in("idParticipantes", Arrays.asList(usuario.getId())), in("idParticipantes", Arrays.asList(receptor.getId())))));
            etapas.add(lookup("usuarios", "idParticipantes", "_id", "participantes"));
            
            chatCollection.aggregate(etapas).into(listaChats);
            
            if (listaChats.isEmpty()) {
                return null;
            }
            return listaChats.get(0);
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible consultar el chat.", e);
        }
    }

    @Override
    public Chat consultar(ObjectId idChat) throws PersistenciaException {
        try {
            List<Chat> listaChats = new LinkedList<>();
            List<Bson> etapas = new ArrayList<>();
            
            etapas.add(match(new Document("_id", idChat)));
            etapas.add(lookup("usuarios", "idParticipantes", "_id", "participantes"));
            
            chatCollection.aggregate(etapas).into(listaChats);
            
            if (listaChats.isEmpty()) {
                return null;
            }
            
            return listaChats.get(0);
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible consultar el chat.", e);
        }
    }
    
    

}