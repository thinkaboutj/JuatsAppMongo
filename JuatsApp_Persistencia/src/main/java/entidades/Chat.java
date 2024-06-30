package entidades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import org.bson.types.ObjectId;

/**
 * Clase que representa un chat en la aplicación. Un chat puede contener
 * usuarios y mensajes asociados.
 *
 * @author Jesus Medina (╹ڡ╹ ) ID:00000247527
 */
public class Chat {

    private ObjectId id;
    private String titulo;
    private List<ObjectId> idParticipantes;
    private List<Mensaje> mensajes;
    private List<Usuario> participantes;

    public Chat() {
    }

    public Chat(Usuario emisor, Usuario receptor) {
        this.titulo = receptor.getUsuario();
        this.idParticipantes = Arrays.asList(emisor.getId(), receptor.getId());
        this.mensajes = new LinkedList<>();
    }

    public Chat(Usuario emisor, Usuario receptor, List<Mensaje> mensajes) {
        this.titulo = receptor.getUsuario();
        this.idParticipantes = Arrays.asList(emisor.getId(), receptor.getId());
        this.mensajes = mensajes;
    }

    public Chat(ObjectId id, Usuario emisor, Usuario receptor, List<Mensaje> mensajes) {
        this.id = id;
        this.titulo = receptor.getUsuario();
        this.idParticipantes = Arrays.asList(emisor.getId(), receptor.getId());
        this.mensajes = mensajes;
    }

    public Usuario getParticipante(Usuario usuario) {
        if (participantes.get(0).equals(usuario)) {
            return participantes.get(1);
        }
        return participantes.get(0);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public List<Usuario> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<Usuario> participantes) {
        this.participantes = participantes;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Chat other = (Chat) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Chat{" + "id=" + id + ", titulo=" + titulo + ", participantes=" + participantes + ", mensajes=" + mensajes + '}';
    }

}
