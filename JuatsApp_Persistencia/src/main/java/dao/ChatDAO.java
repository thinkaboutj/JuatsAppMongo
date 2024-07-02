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
    public List<Chat> consultarChatsDelUsuario(ObjectId idUsuario) throws PersistenciaException {
        try {
            List<Chat> listaChats = new ArrayList<>();
            List<Bson> etapas = new ArrayList<>();
            
            etapas.add(match(in("idParticipantes", Arrays.asList(idUsuario))));
            
            chatCollection.aggregate(etapas).into(listaChats);
            return listaChats;
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible consultar los chats del usuario.", e);
        }
    }

    
}