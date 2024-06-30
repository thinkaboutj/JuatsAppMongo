package DTOs;

import java.time.LocalDate;

/**
 * Clase que representa la entidad mensajes la cual utiliza usuario
 *
 * @author Jesus Medina (╹ڡ╹ ) ID:00000247527
 */
public class MensajeDTO {
    
    private String id;
    private UsuarioDTO usuario;
    private String texto;
    private byte[] imagen; // Arreglo de bytes para la imagen
    private LocalDate fecha_de_registro;

    public MensajeDTO() {
    }

    public MensajeDTO(UsuarioDTO usuario, String texto, LocalDate fecha_de_registro) {
        this.usuario = usuario;
        this.texto = texto;
        this.fecha_de_registro = fecha_de_registro;
    }

    public MensajeDTO(UsuarioDTO usuario, String texto, byte[] imagen, LocalDate fecha_de_registro) {
        this.usuario = usuario;
        this.texto = texto;
        this.imagen = imagen;
        this.fecha_de_registro = fecha_de_registro;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
    

}
