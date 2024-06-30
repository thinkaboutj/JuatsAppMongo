package entidades;

import java.time.LocalDate;
import org.bson.types.ObjectId;

/**
 * Clase que representa la entidad mensajes la cual utiliza usuario
 *
 * @author Jesus Medina (╹ڡ╹ ) ID:00000247527
 */
public class Mensaje {

    private ObjectId id;
    private Usuario usuario;
    private String texto;
    private byte[] imagen; // Arreglo de bytes para la imagen
    private LocalDate fecha_de_registro;

    public Mensaje() {
    }

    public Mensaje(ObjectId id, Usuario usuario, String texto, LocalDate fecha_de_registro) {
        this.id = id;
        this.usuario = usuario;
        this.texto = texto;
        this.fecha_de_registro = fecha_de_registro;
    }

    public Mensaje(Usuario usuario, String texto, LocalDate fecha_de_registro) {
        this.usuario = usuario;
        this.texto = texto;
        this.fecha_de_registro = fecha_de_registro;
    }

    public Mensaje(Usuario usuario, String texto, byte[] imagen, LocalDate fecha_de_registro) {
        this.usuario = usuario;
        this.texto = texto;
        this.imagen = imagen;
        this.fecha_de_registro = fecha_de_registro;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public LocalDate getFecha_de_registro() {
        return fecha_de_registro;
    }

    public void setFecha_de_registro(LocalDate fecha_de_registro) {
        this.fecha_de_registro = fecha_de_registro;
    }

    @Override
    public String toString() {
        return "Mensaje{" + "id=" + id + ", usuario=" + usuario + ", texto=" + texto + ", fecha_de_registro=" + fecha_de_registro + '}';
    }

}
