/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import DTOs.UsuarioDTO;
import excepciones.NegocioException;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author crazy
 */
public interface IUsuarioBO {
    public void registrarUsuario(UsuarioDTO usuarioDTO) throws NegocioException;
    
    public UsuarioDTO login(String contrasena, String telefono) throws NegocioException;
    
    public UsuarioDTO consultarUsuario(ObjectId idUsuario) throws NegocioException;
    
    public void actualizar(UsuarioDTO usuarioDTO) throws NegocioException;
        
    public void agregarContacto(ObjectId idUsuario, ObjectId idContacto) throws NegocioException;
    
    public List<UsuarioDTO> consultarContactos(ObjectId idUsuario) throws NegocioException;
    
    public void eliminarContacto(ObjectId idUsuario, ObjectId idContacto) throws NegocioException;
    
    public List<UsuarioDTO> consultarTelefonosQueNoTieneEnContactos(ObjectId idUsuario) throws NegocioException;

    public List<UsuarioDTO> consultarContactosSinChat(ObjectId idUsuario) throws NegocioException;

    public boolean esContacto(ObjectId idUsuario, ObjectId idContacto) throws NegocioException;

}
