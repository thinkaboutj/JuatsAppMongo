package dao;

import Conexion.ConexionBD;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import entidades.Chat;
import entidades.Mensaje;
import excepciones.PersistenciaException;
import interfaces.IChatDAO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
    private final MongoCollection<Mensaje> mensajeCollection;


    public ChatDAO() {
        this.chatCollection = ConexionBD.getInstance().getDatabase().getCollection("chats", Chat.class);
        this.mensajeCollection = ConexionBD.getInstance().getDatabase().getCollection("mensajes", Mensaje.class);

    }

    @Override
    public void agregar(Chat chat) throws PersistenciaException {
        chat.setMensajes(new ArrayList<>());
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
                        
            etapas.add(Aggregates.match(Filters.in("idParticipantes", Arrays.asList(idUsuario)))); 
            chatCollection.aggregate(etapas, Chat.class).into(listaChats);
            return listaChats;
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible consultar los chats del usuario.", e);
        }
    }
    
    
    @Override
    public Chat consultar(ObjectId id) throws PersistenciaException {
        try {
            return chatCollection.find(Filters.eq("_id", id)).first();
        } catch (MongoException e) {
            throw new PersistenciaException("No fue posible consultar el chat.", e);
        }
    }
    
    @Override
    public void enviarMensaje(ObjectId idChat, Mensaje mensaje) throws PersistenciaException {
        try {
            chatCollection.updateOne(new Document("_id", idChat), Updates.push("mensajes", mensaje));
        } catch (Exception e) {
            throw new PersistenciaException(e);
        }
    } 
    
    @Override
    public List<Mensaje> obtenerMensajes(ObjectId chatId) throws PersistenciaException {
        try {
            Chat chat = chatCollection.find(Filters.eq("_id", chatId)).first();

            List<Mensaje> mensajesConsultados = chat.getMensajes();
            
            return mensajesConsultados;
        } catch (Exception e) {
            throw new PersistenciaException("No fue posible obtener los mensajes del chat.", e);
        }
    }
    
    @Override
    public List<Mensaje> obtenerMensajesOrdenadosPorFecha(ObjectId chatId) throws PersistenciaException {
        try {
            Chat chat = chatCollection.find(Filters.eq("_id", chatId)).first();
            List<Mensaje> mensajes = chat.getMensajes();

            mensajes.sort((d1, d2) -> {
                LocalDateTime fecha1 = d1.getFecha_de_registro();
                LocalDateTime fecha2 = d2.getFecha_de_registro();
                return fecha1.compareTo(fecha2);
            });

            return mensajes;
        } catch (Exception e) {
            throw new PersistenciaException("No fue posible obtener los mensajes del chat.", e);
        }
    }
    
    
    
    
}