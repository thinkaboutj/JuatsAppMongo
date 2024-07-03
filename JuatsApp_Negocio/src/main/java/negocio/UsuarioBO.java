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
import Encriptadores.EncriptadorAES;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.bson.types.ObjectId;
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
        
        usuarioDTO.setImagen(usuario.getImagen());
        usuarioDTO.setId(usuario.getId().toHexString());
        usuarioDTO.setContrasena(usuario.getContrasena());
        usuarioDTO.setSexo(usuario.getSexo());
        usuarioDTO.setTelefono(usuario.getTelefono());
        usuarioDTO.setFechaNacimiento(usuario.getFechaNacimiento());
        usuarioDTO.setUsuario(usuario.getUsuario());
        usuarioDTO.setContactos(usuario.getContactos());
        
        DomicilioDTO domicilio = new DomicilioDTO();
        domicilio.setCalle(usuario.getDomicilio().getCalle());
        domicilio.setNumero(usuario.getDomicilio().getNumero());
        domicilio.setColonia(usuario.getDomicilio().getColonia());
        domicilio.setCodigoPostal(usuario.getDomicilio().getCodigoPostal());
        
        usuarioDTO.setDomicilio(domicilio);
        
        return usuarioDTO;    
    }
    
    
    // se usa para agregar
    public Usuario convertirAEntidad(UsuarioDTO usuarioDTO){
        Usuario usuario = new Usuario();
        
        usuario.setImagen(usuarioDTO.getImagen());
        usuario.setContrasena(usuarioDTO.getContrasena());
        usuario.setSexo(usuarioDTO.getSexo());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setFechaNacimiento(usuarioDTO.getFechaNacimiento());
        usuario.setUsuario(usuarioDTO.getUsuario());  
        
        usuario.setContactos(new ArrayList<ObjectId>());
        
        
        Domicilio domicilio = new Domicilio();
        domicilio.setCalle(usuarioDTO.getDomicilio().getCalle());
        domicilio.setNumero(usuarioDTO.getDomicilio().getNumero());
        domicilio.setColonia(usuarioDTO.getDomicilio().getColonia());
        domicilio.setCodigoPostal(usuarioDTO.getDomicilio().getCodigoPostal());
        usuario.setDomicilio(domicilio);
        
        return usuario;
    }
    
    // este metodo esta pensado para que tambien convierta el id
    // entonces este metodo se usa para actualizar, y el de arriba que es 
    // convertirAEntidad el normalito que está arriba, ese se usa para agregar
    // debido a que no hace nada con el id
    public Usuario convertirAEntidadConID(UsuarioDTO usuarioDTO){
        Usuario usuario = new Usuario();
        
        usuario.setImagen(usuarioDTO.getImagen());
        usuario.setId(new ObjectId(usuarioDTO.getId()));
        usuario.setContrasena(usuarioDTO.getContrasena());
        usuario.setSexo(usuarioDTO.getSexo());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setFechaNacimiento(usuarioDTO.getFechaNacimiento());
        usuario.setUsuario(usuarioDTO.getUsuario());
        usuario.setContactos(usuarioDTO.getContactos());
        
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
            } else {
                throw new NegocioException("Usuario no encontrado o inexistente");
            }
            
        } catch (PersistenciaException | UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {
            throw new NegocioException(ex);
        }
        
    }
    
    
    @Override
    public UsuarioDTO consultarUsuario(ObjectId idUsuario) throws NegocioException {
        String contrasenaDecriptada;
        UsuarioDTO usuarioConsultado = null;
        
        try {
            usuarioConsultado = convertirADTO(dao.consultar(idUsuario));
            contrasenaDecriptada = encriptador.desencriptar(usuarioConsultado.getContrasena());
            usuarioConsultado.setContrasena(contrasenaDecriptada);
            return usuarioConsultado;
        } catch (PersistenciaException | UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {
            throw new NegocioException(ex);
        }
        
    }
    
    @Override
    public void actualizar(UsuarioDTO usuarioDTO) throws NegocioException {
        String contrasenaEncriptada;
        Usuario usuario = convertirAEntidadConID(usuarioDTO);
        
        try {
            contrasenaEncriptada = encriptador.encriptar(usuarioDTO.getContrasena());
            usuario.setContrasena(contrasenaEncriptada);
            dao.actualizar(usuario);
        } catch (PersistenciaException | UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {
            throw new NegocioException(ex);
        }
        
    }

    @Override
    public List<UsuarioDTO> consultarTodos() throws NegocioException {
        
        try{
            List<Usuario> listaDeUsuarios = dao.consultarTodos();
            List<UsuarioDTO> usuariosConsultados = new ArrayList<>();


            for (int i = 0; i < listaDeUsuarios.size(); i++){
                UsuarioDTO usuario = convertirADTO(listaDeUsuarios.get(i));
                System.out.println(usuario.toString());
                usuariosConsultados.add(usuario);
            }
            return usuariosConsultados;

        } catch (PersistenciaException e){
            throw new NegocioException(e);
        }
        
    }

    @Override
    public void agregarContacto(ObjectId idUsuario, ObjectId idContacto) throws NegocioException {
        try {
            dao.agregarContacto(idUsuario, idContacto);
        } catch (PersistenciaException ex) {
            throw new NegocioException(ex);
        }
        
    }

    @Override
    public List<UsuarioDTO> consultarContactos(ObjectId idUsuario) throws NegocioException {
        
        try{
            List<Usuario> listaDeUsuarios = dao.consultarContactos(idUsuario);
            List<UsuarioDTO> usuariosConsultados = new ArrayList<>();
            
            for (int i = 0; i < listaDeUsuarios.size(); i++){
                UsuarioDTO usuario = convertirADTO(listaDeUsuarios.get(i));
                usuariosConsultados.add(usuario);
            }
            return usuariosConsultados;

        } catch (PersistenciaException e){
            throw new NegocioException(e);
        }
    }
    
    @Override
    public void eliminarContacto(ObjectId idUsuario, ObjectId idContacto) throws NegocioException {
        try {
            dao.eliminarContacto(idUsuario, idContacto);
        } catch (PersistenciaException ex) {
            throw new NegocioException(ex);
        }
    }
    
    @Override
    public List<UsuarioDTO> consultarTelefonosQueNoTieneEnContactos(ObjectId idUsuario) throws NegocioException {
        
        try{
            List<Usuario> listaDeUsuarios = dao.consultarTelefonosQueNoTieneEnContactos(idUsuario);
            List<UsuarioDTO> usuariosConsultados = new ArrayList<>();
            for (int i = 0; i < listaDeUsuarios.size(); i++){
                UsuarioDTO usuario = convertirADTO(listaDeUsuarios.get(i));
                usuariosConsultados.add(usuario);
            }
            return usuariosConsultados;
        } catch (PersistenciaException e){
            throw new NegocioException(e);
        }
    }

    @Override
    public List<UsuarioDTO> consultarContactosSinChat(ObjectId idUsuario) throws NegocioException {
        try {
            List<UsuarioDTO> usuariosDTO = new ArrayList<>();
            List<Usuario> usuariosEntidades = new ArrayList<>();
            
            usuariosEntidades = dao.consultarContactosSinChat(idUsuario);
            
            for (Usuario usuariosEntidade : usuariosEntidades) {
                usuariosDTO.add(convertirADTO(usuariosEntidade));
            }
            
            return usuariosDTO;
        } catch (PersistenciaException | NoSuchElementException ex) {
            throw new NegocioException(ex);
        }
        
    }
    
    
    

    

   
}
