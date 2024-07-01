/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import DTOs.UsuarioDTO;
import excepciones.NegocioException;
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

}
