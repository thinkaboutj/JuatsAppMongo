package DTOs;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.bson.types.ObjectId;

/**
 * Clase que representa un chat en la aplicación. Un chat puede contener
 * usuarios y mensajes asociados.
 *
 * @author Jesus Medina (╹ڡ╹ ) ID:00000247527
 */
public class ChatDTO {
    
    private ObjectId id;
    private String nombre;
    private byte[] imagen;
    private List<ObjectId> idParticipantes;
    private List<MensajeDTO> mensajes;
    
    public ChatDTO(){
    }

    public ChatDTO(String nombre, byte[] imagen, List<ObjectId> idParticipantes, List<MensajeDTO> mensajes) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.idParticipantes = idParticipantes;
        this.mensajes = mensajes;
    }
    
    public ChatDTO(ObjectId id, String nombre, byte[] imagen, List<ObjectId> idParticipantes, List<MensajeDTO> mensajes) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.idParticipantes = idParticipantes;
        this.mensajes = mensajes;
    }

    public ChatDTO(ObjectId id, String nombre, byte[] imagen, List<ObjectId> idParticipantes) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.idParticipantes = idParticipantes;
    }

    public ChatDTO(String nombre, byte[] imagen, List<ObjectId> idParticipantes) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.idParticipantes = idParticipantes;
    }


    
    public void addMensaje(MensajeDTO mensaje) {
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public List<ObjectId> getIdParticipantes() {
        return idParticipantes;
    }

    public void setIdParticipantes(List<ObjectId> idParticipantes) {
        this.idParticipantes = idParticipantes;
    }

    public List<MensajeDTO> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<MensajeDTO> mensajes) {
        this.mensajes = mensajes;
    }
    
    
  
}
