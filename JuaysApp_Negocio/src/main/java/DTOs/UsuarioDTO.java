package DTOs;

import com.mongodb.Bytes;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import org.bson.types.ObjectId;

/**
 * Clase USUARIODTO la cual representa la entidad usuario y se utilizara para
 * persistir en la base de datos
 *
 * @author Jesus Medina (╹ڡ╹ ) ID:00000247527
 */
public class UsuarioDTO implements Serializable {

    private String id;
    private String usuario;
    private String contrasena;
    private DomicilioDTO domicilio;

    private String telefono;
    private String genero;
    private byte[] imagen; // Arreglo de bytes para la imagen
    private Calendar fechaNacimiento;

    public UsuarioDTO() {
    }

    public UsuarioDTO(String id, String usuario, String contrasena, DomicilioDTO domicilio, String telefono, String genero, byte[] imagen, Calendar fechaNacimiento) {
        this.id = id;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.genero = genero;
        this.imagen = imagen;
        this.fechaNacimiento = fechaNacimiento;
    }

    public UsuarioDTO(String usuario, String contrasena, DomicilioDTO domicilio, String telefono, String genero, byte[] imagen, Calendar fechaNacimiento) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.genero = genero;
        this.imagen = imagen;
        this.fechaNacimiento = fechaNacimiento;
    }

    public UsuarioDTO(String usuario, String contrasena, DomicilioDTO domicilio, String telefono, String genero, Calendar fechaNacimiento) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.genero = genero;
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public DomicilioDTO getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(DomicilioDTO domicilio) {
        this.domicilio = domicilio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public Calendar getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Calendar fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public String toString() {
        return "Usuario{" + "usuario=" + usuario + '}';
    }

}
