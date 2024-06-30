package DTOs;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Clase que representa un chat en la aplicación. Un chat puede contener
 * usuarios y mensajes asociados.
 *
 * @author Jesus Medina (╹ڡ╹ ) ID:00000247527
 */
public class ChatDTO {
    
    private String id;
    private String titulo;
    private List<String> idParticipantes;
    private List<MensajeDTO> mensajes;
    private List<UsuarioDTO> participantes;

    public ChatDTO() {
    }

    public ChatDTO(UsuarioDTO emisor, UsuarioDTO receptor) {
        this.titulo = receptor.getUsuario();
        this.idParticipantes = Arrays.asList(emisor.getId(), receptor.getId());
        this.mensajes = new LinkedList<>();
    }

    public ChatDTO(UsuarioDTO emisor, UsuarioDTO receptor, List<MensajeDTO> mensajes) {
        this.titulo = receptor.getUsuario();
        this.idParticipantes = Arrays.asList(emisor.getId(), receptor.getId());
        this.mensajes = mensajes;
    }

    public UsuarioDTO getParticipante(UsuarioDTO usuario) {
        if (participantes.get(0).equals(usuario)) {
            return participantes.get(1);
        }
        return participantes.get(0);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<UsuarioDTO> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<UsuarioDTO> participantes) {
        this.participantes = participantes;
    }

    public void addMensaje(MensajeDTO mensaje) {
        if (mensaje == null) {
            return;
        }
        if (this.mensajes == null) {
            this.mensajes = new LinkedList();
        }
        this.mensajes.add(mensaje);
    }

    public List<String> getIdParticipantes() {
        return idParticipantes;
    }

    public void setIdParticipantes(List<String> idParticipantes) {
        this.idParticipantes = idParticipantes;
    }

    public List<MensajeDTO> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<MensajeDTO> mensajes) {
        this.mensajes = mensajes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
  
}
