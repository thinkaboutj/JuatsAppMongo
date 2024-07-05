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
    
   /**
    *   
    *   @return UsuarioDTO convertido
    *   @param usuario a convetir
    */
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
    
   /**
    *   Se usa para agregar un usuario 
    *   @return Usuario convertido
    *   @param usuarioDTO a convetir
    */
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
    
   /**
    * este metodo esta pensado para que tambien convierta el id
    * entonces este metodo se usa para actualizar, y el de arriba que es 
    * debido a que no hace nada con el id
    * 
    * @return Usuario retorna un usuario convertido de dto a entidad
    * @param usuarioDTO 
    */
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
    
   /**
    *   Registra un usuario, esta pensado para ser usado en el frmRegistroUsuario  
    * 
    *   @param usuarioDTO usuario a registrar
    *   @throws NegocioException cacha excepcion de la persistencia, y excepciones del algoritmo al momento de encriptar
    */
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
    
   /**
    *   Método para autenticar a un usuario utilizando su contraseña y teléfono.
    *   Encripta la contraseña proporcionada y verifica las credenciales en la base de datos.
    *
    *   @param contrasena La contraseña en texto plano proporcionada por el usuario.
    *   @param telefono El número de teléfono del usuario.
    *   @return Un objeto UsuarioDTO que representa al usuario autenticado.
    *   @throws NegocioException Si ocurre algún error durante el proceso de autenticación,
    *         incluyendo errores de persistencia, encriptación o si el usuario no es encontrado.
    */
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
    
   /**
    *   El metodo esta pensado para consultar todas las cosas del usuario y poder mostrarlas en el frame de editar el perfil
    *   El metodo desencripta la contrasena para poder mostrarla en el frame si es que el usuario la quiere modificar
    *   @param idUsuario se busca el usuario por su ObjectId
    *   @return UsuarioDTO regresa el con todos sus atributos
    *   @throws NegocioException tira la excepcion que se cache de persistencia o de desencriptacion
    */
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
    
   /**
    *   El metodo esta pensado para ser usado en el Frame de editar perfil
    *   @param usuarioDTO el usuario que se va a actualizar con sus datos modificados
    *   @throws NegocioException tira la excepcion que se cache de persistencia o de encriptacion al momento de encriptar la contrasena
    */ 
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
    
   /**
    *   El metodo agrega un usuario a la lista de contactos de otro usuario
    *   El metodo esta pensado para ser usado en el DlgTelefonos
    *   @param idUsuario ObjectId del usuario al que se le va a agregar el contacto a su lista
    *   @param idContacto ObjectId del contacto
    *   @throws NegocioException cacha excepciones de persistencia
    */
    @Override
    public void agregarContacto(ObjectId idUsuario, ObjectId idContacto) throws NegocioException {
        try {
            dao.agregarContacto(idUsuario, idContacto);
        } catch (PersistenciaException ex) {
            throw new NegocioException(ex);
        }
    }
    
   /**
    *   El metodo es para consultar los contactos que tiene un usuario
    *   esta pensado para ser usado en el dialog de ver contactos
    *   @param idUsuario ObjectId del usuario del que queremos sus contactos
    *   @throws NegocioException cacha excepciones de persistencia
    */
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
    
   /**
    *   El metodo es para consultar los contactos que tiene un usuario
    *   esta pensado para ser usado en el dialog de ver contactos
    *   @param idUsuario ObjectId del usuario 
    *   @param idContacto ObjectId del contacto que se eliminará
    *   @throws NegocioException cacha excepciones de persistencia
    */
    @Override
    public void eliminarContacto(ObjectId idUsuario, ObjectId idContacto) throws NegocioException {
        try {
            dao.eliminarContacto(idUsuario, idContacto);
        } catch (PersistenciaException ex) {
            throw new NegocioException(ex);
        }
    }
    
   /**
    *   El metodo esta pensado para ser usado en el DlgTelefonos, para para poder agregar contactos
    *   @return List regresa una lista de todos los usuarios que están en la base de datos, pero que el usuario no tiene agregados como contactos
    *   @param idUsuario ObjectId del usuario 
    *   @throws NegocioException cacha excepciones de persistencia
    */
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
    
   /**
    *   El metodo esta pensado para ser usado en el DlgNuevoChat, donde consulte los contactos sin chat y pueda empezar uno nuevo
    *   @return List regresa una lista de todos los usuarios que el usuario tiene agregados como contactos, pero que no tiene chat aun con ellos
    *   @param idUsuario ObjectId del usuario del que queremos sus contactos sin chat
    *   @throws NegocioException cacha excepciones de persistencia
    */
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
    
   /**
    *   El metodo esta pensado para ser usado en el el frmChat, de tal manera que consultemos primero el frame consulte los chats del usuario
    *   y respues consulte el participante del chat y verifique si es contacto, si no es contacto, el frame mostraria solo el numero de telefono de la persona
    *   @return esContacto regresa booleano si lo tiene como contacto
    *   @param idUsuario ObjectId del usuario que queremos consultar 
    *   @param idContacto ObjectId del otro usuario, para verificar si es contacto
    *   @throws NegocioException cacha excepciones de persistencia
    */
    @Override
    public boolean esContacto(ObjectId idUsuario, ObjectId idContacto) throws NegocioException {
        try {
            return dao.esContacto(idUsuario, idContacto);
        } catch (PersistenciaException ex) {
            throw new NegocioException(ex);
        }
        
    }
    

   
}
