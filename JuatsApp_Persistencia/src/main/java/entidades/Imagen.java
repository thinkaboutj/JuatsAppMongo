package entidades;

import java.io.Serializable;

/**
 * Clase que representa la entidad de imagen
 *
 * @author Jesus Medina (╹ڡ╹ ) ID:00000247527
 */
public class Imagen implements Serializable {

    private Long idImagen;
    private String nombre;

    private byte[] datosImagen;

    public Imagen() {
    }

    public Imagen(String nombre, byte[] datosImagen) {
        this.nombre = nombre;
        this.datosImagen = datosImagen;
    }

    public Imagen(Long idImagen, String nombre, byte[] datosImagen) {
        this.idImagen = idImagen;
        this.nombre = nombre;
        this.datosImagen = datosImagen;
    }

    public Long getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(Long idImagen) {
        this.idImagen = idImagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getDatosImagen() {
        return datosImagen;
    }

    public void setDatosImagen(byte[] datosImagen) {
        this.datosImagen = datosImagen;
    }
}
