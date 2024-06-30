/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import DTOs.DomicilioDTO;
import DTOs.UsuarioDTO;
import dao.UsuarioDAO;
import entidades.Domicilio;
import entidades.Usuario;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import interfaces.IUsuarioBO;
import interfaces.IUsuarioDAO;
import javax.swing.JOptionPane;
import Encriptadores.EncriptadorAES;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
/**
 * Clase de negocio para operaciones relacionadas con usuarios.
 *
 * @author Jesus Medina (╹ڡ╹ ) ID:00000247527
 */
public class UsuarioBO implements IUsuarioBO{
    private IUsuarioDAO dao;
    private EncriptadorAES encriptador;
    
    public UsuarioBO(){
        dao = new UsuarioDAO();
        encriptador = new EncriptadorAES();
    }
    
    public UsuarioDTO convertirADTO(Usuario usuario){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        
        usuarioDTO.setId(usuario.getId().toHexString());
        usuarioDTO.setContrasena(usuario.getContrasena());
        usuarioDTO.setSexo(usuario.getSexo());
        usuarioDTO.setTelefono(usuario.getTelefono());
        usuarioDTO.setFechaNacimiento(usuario.getFechaNacimiento());
        usuarioDTO.setUsuario(usuario.getUsuario());
        
        DomicilioDTO domicilio = new DomicilioDTO();
        domicilio.setCalle(usuario.getDomicilio().getCalle());
        domicilio.setNumero(usuario.getDomicilio().getNumero());
        domicilio.setColonia(usuario.getDomicilio().getColonia());
        domicilio.setCodigoPostal(usuario.getDomicilio().getCodigoPostal());
        
        usuarioDTO.setDomicilio(domicilio);
        
        return usuarioDTO;    
    }
    
    public Usuario convertirAEntidad(UsuarioDTO usuarioDTO){
        Usuario usuario = new Usuario();
        
        usuario.setContrasena(usuarioDTO.getContrasena());
        usuario.setSexo(usuarioDTO.getSexo());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setFechaNacimiento(usuarioDTO.getFechaNacimiento());
        usuario.setUsuario(usuarioDTO.getUsuario());
        
        Domicilio domicilio = new Domicilio();
        domicilio.setCalle(usuarioDTO.getDomicilio().getCalle());
        domicilio.setNumero(usuarioDTO.getDomicilio().getNumero());
        domicilio.setColonia(usuarioDTO.getDomicilio().getColonia());
        domicilio.setCodigoPostal(usuarioDTO.getDomicilio().getCodigoPostal());
        usuario.setDomicilio(domicilio);
        
        return usuario;
    }
    
    @Override
    public void registrarUsuario(UsuarioDTO usuarioDTO) throws NegocioException {
        String contrasenaEncriptada;
        Usuario usuario = convertirAEntidad(usuarioDTO);
        
        try {
            contrasenaEncriptada = encriptador.encriptar(usuarioDTO.getContrasena());
            usuario.setContrasena(contrasenaEncriptada);
            dao.agregar(usuario);
        } catch (PersistenciaException | UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {
            throw new NegocioException(ex);
        }
    }

    @Override
    public UsuarioDTO login(String contrasena, String telefono) throws NegocioException {
        String contrasenaEncriptada;
        Usuario usuarioBuscado = null;
        
        try {
            contrasenaEncriptada = encriptador.encriptar(contrasena);
            usuarioBuscado = dao.login(contrasenaEncriptada, telefono);
            
            if (usuarioBuscado != null){
                return convertirADTO(usuarioBuscado);
            }
            
        } catch (PersistenciaException | UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {
            throw new NegocioException(ex);
        }
        return null;
        
    }
    

   
}
