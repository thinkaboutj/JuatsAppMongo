package entidades;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.bson.types.ObjectId;

/**
 * Clase que representa la entidad mensajes la cual utiliza usuario
 *
 * @author Jesus Medina (╹ڡ╹ ) ID:00000247527
 */
public class Mensaje {

    private ObjectId id;
    private ObjectId idUsuario;
    private String texto;
    private byte[] imagen;
    private LocalDateTime fecha_de_registro;

    public Mensaje() {
    }

    public Mensaje(ObjectId idUsuario, String texto, byte[] imagen, LocalDateTime fecha_de_registro) {
        this.idUsuario = idUsuario;
        this.texto = texto;

        byte[] byteArray = {1}; // Array con valores específicos

        if (imagen == null) {
            this.imagen = byteArray;
        } else {
            this.imagen = imagen;

        }
        
        this.fecha_de_registro = fecha_de_registro;
    }

    public Mensaje(ObjectId idUsuario, String texto, LocalDateTime fecha_de_registro) {
        this.idUsuario = idUsuario;
        this.texto = texto;
        this.fecha_de_registro = fecha_de_registro;
        byte[] byteArray = {1}; // Array con valores específicos
        this.imagen = byteArray;
    }

    public Mensaje(ObjectId idUsuario, byte[] imagen, LocalDateTime fecha_de_registro) {
        this.idUsuario = idUsuario;
        this.texto = null;
        this.imagen = imagen;
        this.fecha_de_registro = fecha_de_registro;
    }

    public ObjectId getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(ObjectId idUsuario) {
        this.idUsuario = idUsuario;
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

    public LocalDateTime getFecha_de_registro() {
        return fecha_de_registro;
    }

    public void setFecha_de_registro(LocalDateTime fecha_de_registro) {
        this.fecha_de_registro = fecha_de_registro;
    }

    @Override
    public String toString() {
        return "Mensaje{" + "idUsuario=" + idUsuario + ", texto=" + texto + ", imagen=" + imagen + ", fecha_de_registro=" + fecha_de_registro + '}';
    }

}
