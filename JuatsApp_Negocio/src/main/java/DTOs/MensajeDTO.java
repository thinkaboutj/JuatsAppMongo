package DTOs;

import java.time.LocalDateTime;
import org.bson.types.ObjectId;

/**
 * Clase que representa la entidad mensajes la cual utiliza usuario
 *
 * @author Jesus Medina (╹ڡ╹ ) ID:00000247527
 */
public class MensajeDTO {

    private ObjectId idUsuario;
    private String texto;
    private byte[] imagen;
    private LocalDateTime fecha_de_registro;

    public MensajeDTO() {
    }

    public MensajeDTO(ObjectId idUsuario, String texto, byte[] imagen, LocalDateTime fecha_de_registro) {
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

    public MensajeDTO(ObjectId idUsuario, String texto, LocalDateTime fecha_de_registro) {
        this.idUsuario = idUsuario;
        this.texto = texto;
        this.fecha_de_registro = fecha_de_registro;
        byte[] byteArray = {1};
        this.imagen = byteArray;
    }

    public MensajeDTO(ObjectId idUsuario, byte[] imagen, LocalDateTime fecha_de_registro) {
        this.idUsuario = idUsuario;
        this.texto = null;
        this.imagen = imagen;
        this.fecha_de_registro = fecha_de_registro;
    }

    public ObjectId getUsuarioId() {
        return idUsuario;
    }

    public void setUsuarioId(ObjectId idUsuario) {
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

    public ObjectId getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(ObjectId idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        return "MensajeDTO{" + "idUsuario=" + idUsuario + ", texto=" + texto + ", imagen=" + imagen + ", fecha_de_registro=" + fecha_de_registro + '}';
    }

}
