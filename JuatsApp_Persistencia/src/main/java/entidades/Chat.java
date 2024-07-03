package entidades;

import java.util.LinkedList;
import java.util.List;
import org.bson.types.ObjectId;

/**
 * Clase que representa un chat en la aplicación. Un chat puede contener
 * usuarios y mensajes asociados.
 *
 * @author Jesus Medina (╹ڡ╹ ) ID:00000247527
 */
public class Chat {

    private ObjectId id;
    private List<ObjectId> idParticipantes;  // está pensado para que se cree un chat con los ids de los participantes
    private List<Mensaje> mensajes;
        
    public Chat(){
    }

    public Chat(List<ObjectId> idParticipantes, List<Mensaje> mensajes) {
        this.idParticipantes = idParticipantes;
        this.mensajes = mensajes;
    }
    
    public Chat(ObjectId id, List<ObjectId> idParticipantes, List<Mensaje> mensajes) {
        this.id = id;
        this.idParticipantes = idParticipantes;
        this.mensajes = mensajes;
    }

    public Chat(ObjectId id, List<ObjectId> idParticipantes) {
        this.id = id;
        this.idParticipantes = idParticipantes;
    }

    public Chat( List<ObjectId> idParticipantes) {
        this.idParticipantes = idParticipantes;
    }
    
    public void addMensaje(Mensaje mensaje) {
        if (mensaje == null) {
            return;
        }
        if (this.mensajes == null) {
            this.mensajes = new LinkedList();
        }
        this.mensajes.add(mensaje);
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
    
    public List<ObjectId> getIdParticipantes() {
        return idParticipantes;
    }

    public void setIdParticipantes(List<ObjectId> idParticipantes) {
        this.idParticipantes = idParticipantes;
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    @Override
    public String toString() {
        return "Chat{" + "id=" + id + ", idParticipantes=" + idParticipantes + ", mensajes=" + mensajes + '}';
    }
    
}
