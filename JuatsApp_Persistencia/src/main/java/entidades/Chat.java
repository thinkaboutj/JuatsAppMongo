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
    private String nombre;
    private List<ObjectId> idParticipantes;
    private List<Mensaje> mensajes;

    public Chat() {
    }
    
    public String getTitulo() {
        return nombre;
    }
    
    public void setTitulo(String titulo) {
        this.nombre = titulo;
    }
    
    public ObjectId getId() {
        return id;
    }
    
    public void setId(ObjectId id) {
        this.id = id;
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

}
