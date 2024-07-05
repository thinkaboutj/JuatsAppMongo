package DTOs;

import java.time.LocalDate;
import java.util.List;
import org.bson.types.ObjectId;

/**
 * Clase USUARIODTO la cual representa la entidad usuario y se utilizara para
 * persistir en la base de datos
 *
 * @author Jesus Medina (╹ڡ╹ ) ID:00000247527
 */
public class UsuarioDTO {

    private String id;
    private String usuario;
    private String contrasena;
    private DomicilioDTO domicilio;

    private String telefono;
    private String sexo;
    private byte[] imagen; // Arreglo de bytes para la imagen
    private LocalDate fechaNacimiento;

    private List<ObjectId> contactos; 
    
    public UsuarioDTO() {
    }
    
    public UsuarioDTO(String id, String usuario, String contrasena, DomicilioDTO domicilio, String telefono, String sexo, byte[] imagen, LocalDate fechaNacimiento, List<ObjectId> contactos) {
        this.id = id;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.sexo = sexo;
        this.imagen = imagen;
        this.fechaNacimiento = fechaNacimiento;
        this.contactos = contactos;
    }

    public UsuarioDTO(String id, String usuario, String contrasena, DomicilioDTO domicilio, String telefono, String sexo, byte[] imagen, LocalDate fechaNacimiento) {
        this.id = id;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.sexo = sexo;
        this.imagen = imagen;
        this.fechaNacimiento = fechaNacimiento;
    }

    public UsuarioDTO(String usuario, String contrasena, DomicilioDTO domicilio, String telefono, String sexo, byte[] imagen, LocalDate fechaNacimiento) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.sexo = sexo;
        this.imagen = imagen;
        this.fechaNacimiento = fechaNacimiento;
    }

    public UsuarioDTO(String usuario, String contrasena, DomicilioDTO domicilio, String telefono, String sexo, LocalDate fechaNacimiento) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
    }

    public List<ObjectId> getContactos() {
        return contactos;
    }

    public void setContactos(List<ObjectId> contactos) {
        this.contactos = contactos;
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

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        if(imagen == null){
            byte[] byteArray = {1};
            this.imagen = byteArray;
        } else {
            this.imagen = imagen;
        }
        
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" + "id=" + id + ", usuario=" + usuario + ", contrasena=" + contrasena + ", domicilio=" + domicilio + ", telefono=" + telefono + ", sexo=" + sexo + ", imagen=" + imagen + ", fechaNacimiento=" + fechaNacimiento + '}';
    }
    

}
