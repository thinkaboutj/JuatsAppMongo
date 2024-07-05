/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import entidades.Mensaje;
import entidades.Usuario;
import excepciones.PersistenciaException;
import java.util.List;
import org.bson.types.ObjectId;


/**
 * Interfaz para el acceso a datos de la entidad Usuario. Define métodos para
 * operaciones CRUD (crear, leer, actualizar, eliminar) en la base de datos.
 *
 * @author Jesus Medina (╹ڡ╹ ) ID:00000247527
 */
public interface IUsuarioDAO {

    public void agregar(Usuario usuario) throws PersistenciaException;

    public Usuario consultar(ObjectId idUsuario) throws PersistenciaException;

    public Usuario consultar(String telefono) throws PersistenciaException;

    public Usuario consultarPorUsuario(String username) throws PersistenciaException;

    public void actualizar(Usuario usuario) throws PersistenciaException;
        
    public Usuario login(String contrasena, String telefono) throws PersistenciaException;
    
    public List<Usuario> consultarContactosDelUsuario(ObjectId objectId) throws PersistenciaException;
    
    public void agregarContacto(ObjectId idUsuario, ObjectId idContacto) throws PersistenciaException;
    
    public List<Usuario> consultarContactos(ObjectId idUsuario) throws PersistenciaException;
    
    public void eliminarContacto(ObjectId idUsuario, ObjectId idContacto) throws PersistenciaException;

    public List<Usuario> consultarTelefonosQueNoTieneEnContactos(ObjectId idUsuario) throws PersistenciaException;
    
    public List<Usuario> consultarContactosSinChat(ObjectId idUsuario) throws PersistenciaException;
    
    public boolean esContacto(ObjectId idUsuario, ObjectId idContacto) throws PersistenciaException;



    
}
