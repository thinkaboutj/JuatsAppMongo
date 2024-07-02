package DTOs;

import java.time.LocalDate;
import org.bson.types.ObjectId;

/**
 * Clase que representa la entidad mensajes la cual utiliza usuario
 *
 * @author Jesus Medina (╹ڡ╹ ) ID:00000247527
 */
public class MensajeDTO {
    
    private String id;
    private ObjectId idUsuario;
    private String texto;
    private byte[] imagen; // Arreglo de bytes para la imagen
    private LocalDate fecha_de_registro;
    
    public MensajeDTO() {
    }

    public MensajeDTO(ObjectId idUsuario, String texto, LocalDate fecha_de_registro) {
        this.idUsuario = idUsuario;
        this.texto = texto;
        this.fecha_de_registro = fecha_de_registro;
    }

    public MensajeDTO(ObjectId idUsuario, String texto, byte[] imagen, LocalDate fecha_de_registro) {
        this.idUsuario = idUsuario;
        this.texto = texto;
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

    public LocalDate getFecha_de_registro() {
        return fecha_de_registro;
    }

    public void setFecha_de_registro(LocalDate fecha_de_registro) {
        this.fecha_de_registro = fecha_de_registro;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
    

}
