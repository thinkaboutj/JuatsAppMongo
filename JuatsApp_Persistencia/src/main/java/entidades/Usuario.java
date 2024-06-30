package entidades;

import java.io.Serializable;
import java.time.LocalDate;
import org.bson.types.ObjectId;

/**
 * Clase USUARIO la cual representa la entidad usuario y se utilizara para
 * persistir en la base de datos
 *
 * @author Jesus Medina (╹ڡ╹ ) ID:00000247527
 */
public class Usuario implements Serializable{

    private ObjectId objectId;
    private String usuario;
    private String contrasena;
    private Domicilio domicilio;

    private String telefono;
    private String sexo;
    private byte[] imagen; // Arreglo de bytes para la imagen
    private LocalDate fechaNacimiento;

    public Usuario() {
    }

    public Usuario(ObjectId objectId, String usuario, String contrasena, Domicilio domicilio, String telefono, String genero, byte[] imagen, LocalDate fechaNacimiento) {
        this.objectId = objectId;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.sexo = genero;
        this.imagen = imagen;
        this.fechaNacimiento = fechaNacimiento;
    }

    public Usuario(String usuario, String contrasena, Domicilio domicilio, String telefono, String genero, byte[] imagen, LocalDate fechaNacimiento) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.sexo = genero;
        this.imagen = imagen;
        this.fechaNacimiento = fechaNacimiento;
    }

    public Usuario(String usuario, String contrasena, Domicilio domicilio, String telefono, String genero, LocalDate fechaNacimiento) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.sexo = genero;
        this.fechaNacimiento = fechaNacimiento;
    }
    
    public ObjectId getObjectId() {
        return objectId;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String genero) {
        this.sexo = genero;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
    
    
}
