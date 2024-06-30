/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import DTOs.UsuarioDTO;
import excepciones.NegocioException;

/**
 *
 * @author crazy
 */
public interface IUsuarioBO {
    public void registrarUsuario(UsuarioDTO usuarioDTO) throws NegocioException;
    public UsuarioDTO login(String contrasena, String telefono) throws NegocioException;
}
