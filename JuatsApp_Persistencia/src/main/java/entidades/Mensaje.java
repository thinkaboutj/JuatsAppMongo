package entidades;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;

/**
 * Clase que representa la entidad mensajes la cual utiliza usuario
 *
 * @author Jesus Medina (╹ڡ╹ ) ID:00000247527
 */
public class Mensaje {

    private int id;
    private Imagen imagen;
    private String texto;
    private Calendar fechaHoraRegistro;
    private Usuario usuario;
    private Chat chat;

    public Imagen getImagen() {
        return imagen;
    }

    public Mensaje() {
    }

    public Mensaje(int id, Imagen imagen, String texto, Calendar fechaHoraRegistro, Usuario usuario, Chat chat) {
        this.id = id;
        this.imagen = imagen;
        this.texto = texto;
        this.fechaHoraRegistro = fechaHoraRegistro;
        this.usuario = usuario;
        this.chat = chat;
    }

    public Mensaje(Imagen imagen, String texto, Calendar fechaHoraRegistro, Usuario usuario, Chat chat) {
        this.imagen = imagen;
        this.texto = texto;
        this.fechaHoraRegistro = fechaHoraRegistro;
        this.usuario = usuario;
        this.chat = chat;
    }

    public Mensaje(Imagen imagen, String texto, Calendar fechaHoraRegistro) {
        this.imagen = imagen;
        this.texto = texto;
        this.fechaHoraRegistro = fechaHoraRegistro;
    }

    public Mensaje(String texto, Calendar fechaHoraRegistro, Usuario usuario, Chat chat) {
        this.texto = texto;
        this.fechaHoraRegistro = fechaHoraRegistro;
        this.usuario = usuario;
        this.chat = chat;
    }

    //Getter & setter
    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Calendar getFechaHoraRegistro() {
        return fechaHoraRegistro;
    }

    public void setFechaHoraRegistro(Calendar fechaHoraRegistro) {
        this.fechaHoraRegistro = fechaHoraRegistro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + this.id;
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
        final Mensaje other = (Mensaje) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "Mensaje{" + "id=" + id + ", imagen=" + imagen + ", texto=" + texto + ", fechaHoraRegistro=" + fechaHoraRegistro + ", usuario=" + usuario + ", chat=" + chat + '}';
    }

}
