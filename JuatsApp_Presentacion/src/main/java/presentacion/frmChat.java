/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package presentacion;

import DTOs.ChatDTO;
import DTOs.MensajeDTO;
import DTOs.UsuarioDTO;
import excepciones.NegocioException;
import interfaces.IChatBO;
import interfaces.IUsuarioBO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import negocio.ChatBO;
import negocio.UsuarioBO;
import org.bson.types.ObjectId;
import utilerias.ImageRenderer;
import utilerias.JButtonCellEditor;
import utilerias.JButtonRenderer;

/**
 * Frame donde se muestran los chats del usuario
 *
 * @author Jesus Medina (╹ڡ╹ ) ID:00000247527
 */
public class frmChat extends javax.swing.JFrame {

    private final ObjectId idUsuarioLogeado;
    private final IUsuarioBO usuarioBO;
    private final IChatBO chatBO;
    List<MensajeDTO> listaMensajes;
    private ChatDTO chatActual;
    private String txtRuta;

    public frmChat(ObjectId idUsuarioLogeado) {
        initComponents();
        usuarioBO = new UsuarioBO();
        chatBO = new ChatBO();
        this.idUsuarioLogeado = idUsuarioLogeado;
        cargarMetodosIniciales();
    }

    // Convertir el arreglo de bytes de la imagen del usuario consultado para así poder mostrar la imagen
    public static Icon byteArrayToIcon(byte[] bytes) {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        try {
            BufferedImage bufferedImage = ImageIO.read(bais);
            if (bufferedImage != null) {
                return new ImageIcon(bufferedImage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Convertir el icono del label a un arreglo de bytes
    public static byte[] iconToByteArray(Icon icon) {
        if (icon instanceof ImageIcon) {
            Image image = ((ImageIcon) icon).getImage();
            BufferedImage bufferedImage = new BufferedImage(
                    image.getWidth(null),
                    image.getHeight(null),
                    BufferedImage.TYPE_INT_RGB
            );
            Graphics g = bufferedImage.createGraphics();
            g.drawImage(image, 0, 0, null);
            g.dispose();
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                ImageIO.write(bufferedImage, "jpg", baos);
                return baos.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public class UsuarioChat {

        public UsuarioDTO contacto;
        public ChatDTO chat;

        public UsuarioChat(UsuarioDTO contacto, ChatDTO chat) {
            this.contacto = contacto;
            this.chat = chat;
        }
    }

    private void cargarChatsEnPanel() {
        List<UsuarioDTO> usuariosDTO = new ArrayList<>();
        List<ChatDTO> chatsDTO = new ArrayList<>();
        List<UsuarioChat> lista = new ArrayList<>();
        try {
            chatsDTO = chatBO.consultarChatsDelUsuario(idUsuarioLogeado);
            for (ChatDTO chat : chatsDTO) {
                UsuarioDTO usuarioAux = new UsuarioDTO();
                if (chat.getIdParticipantes().get(0).equals(idUsuarioLogeado)) {
                    usuarioAux = usuarioBO.consultarUsuario(chat.getIdParticipantes().get(1));
                } else {
                    usuarioAux = usuarioBO.consultarUsuario(chat.getIdParticipantes().get(0));
                }
                usuariosDTO.add(usuarioAux);
            }
            for (int i = 0; i < chatsDTO.size(); i++) {
                lista.add(new UsuarioChat(usuariosDTO.get(i), chatsDTO.get(i)));
            }
            llenarPanelChats(lista);

            // Si hay chats, selecciona el primero por defecto
            if (!lista.isEmpty()) {
                chatActual = lista.get(0).chat;
                verChat();
            }

        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }

    protected void cargarMetodosIniciales() {
        this.cargarConfiguracionInicialPanelChats();
        this.cargarChatsEnPanel();
        cargarMensajesEnPanel(listaMensajes);
    }

    private void cargarConfiguracionInicialPanelChats() {
        pnlChats.setLayout(new BoxLayout(pnlChats, BoxLayout.Y_AXIS));
        pnlMensajes.setLayout(new BoxLayout(pnlMensajes, BoxLayout.Y_AXIS));
    }

    private void llenarPanelesMensajes(List<MensajeDTO> listaMensajes) {
        pnlMensajes.removeAll();
        if (listaMensajes != null) {
            for (MensajeDTO mensaje : listaMensajes) {
                JLabel lblMensaje = new JLabel();
                lblMensaje.setOpaque(true);
                lblMensaje.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Espaciado
                lblMensaje.setText(mensaje.getTexto());
                lblMensaje.setFont(new Font("Arial", Font.PLAIN, 14)); // Fuente y tamaño

                // Estilo dependiendo del emisor del mensaje
                if (mensaje.getIdUsuario().equals(idUsuarioLogeado)) {
                    lblMensaje.setBackground(Color.BLUE); // Fondo azul para mensajes del usuario logeado
                    lblMensaje.setForeground(Color.WHITE); // Texto blanco
                    lblMensaje.setHorizontalAlignment(SwingConstants.RIGHT); // Alineación a la derecha
                } else {
                    lblMensaje.setBackground(Color.LIGHT_GRAY); // Fondo gris claro para otros mensajes
                    lblMensaje.setHorizontalAlignment(SwingConstants.LEFT); // Alineación a la izquierda
                }

                pnlMensajes.add(lblMensaje);
            }
        }
        pnlMensajes.revalidate();
        pnlMensajes.repaint();
    }

    // Otros métodos relacionados como cargar mensajes del chat actual, etc.
    public void editar() {
        JOptionPane.showMessageDialog(this, "hola");

    }

    private void llenarPanelChats(List<UsuarioChat> listaChats) {
        pnlChats.removeAll();
        if (listaChats != null) {
            for (UsuarioChat usuarioChat : listaChats) {
                JLabel lblContacto = new JLabel();
                Icon icono = byteArrayToIcon(usuarioChat.contacto.getImagen());
                lblContacto.setIcon(icono);
                lblContacto.setText(usuarioChat.contacto.getUsuario());
                lblContacto.setHorizontalTextPosition(SwingConstants.RIGHT);
                lblContacto.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        chatActual = usuarioChat.chat;
                        verChat();
                    }
                });
                pnlChats.add(lblContacto);
            }
        }
        pnlChats.revalidate();
        pnlChats.repaint();
    }

    private void verChat() {
        if (chatActual != null) {
            try {
                List<MensajeDTO> mensajes = chatBO.obtenerMensajesOrdenadosPorFecha(chatActual.getId());
                cargarMensajesEnPanel(mensajes);
                System.out.println("Número de mensajes cargados: " + mensajes.size());
            } catch (NegocioException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al cargar los mensajes del chat: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se ha seleccionado ningún chat.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarMensajesEnPanel(List<MensajeDTO> mensajes) {
        pnlMensajes.removeAll();

        if (mensajes != null && !mensajes.isEmpty()) {
            for (MensajeDTO mensaje : mensajes) {
                JPanel panelMensaje = new JPanel();
                panelMensaje.setLayout(new BorderLayout());

                JLabel lblMensaje = new JLabel(mensaje.getTexto());
                lblMensaje.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

                if (mensaje.getIdUsuario().equals(idUsuarioLogeado)) {
                    panelMensaje.setBackground(new Color(200, 230, 255));
                    panelMensaje.add(lblMensaje, BorderLayout.EAST);
                } else {
                    panelMensaje.setBackground(new Color(230, 230, 230));
                    panelMensaje.add(lblMensaje, BorderLayout.WEST);
                }

                pnlMensajes.add(panelMensaje);
            }
        }

        pnlMensajes.revalidate();
        pnlMensajes.repaint();

        // Scroll hasta el último mensaje
        JScrollPane scrollPane = (JScrollPane) SwingUtilities.getAncestorOfClass(JScrollPane.class, pnlMensajes);
        if (scrollPane != null) {
            JScrollBar vertical = scrollPane.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnBackground = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btnPerfil = new javax.swing.JButton();
        btnCerrarSesion = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnAgregarContactos = new javax.swing.JButton();
        btnVerContactos = new javax.swing.JButton();
        panelPrincipal = new javax.swing.JPanel();
        jScrollPane = new javax.swing.JScrollPane();
        pnlMensajes = new javax.swing.JPanel();
        btnNuevoChat = new javax.swing.JButton();
        Enviar = new javax.swing.JButton();
        btnCargarImagen = new javax.swing.JButton();
        lblImagen = new javax.swing.JLabel();
        txtMensaje = new javax.swing.JTextField();
        pnlChats = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Chat");
        setResizable(false);

        pnBackground.setBackground(new java.awt.Color(225, 225, 218));
        pnBackground.setPreferredSize(new java.awt.Dimension(900, 600));
        pnBackground.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 51, 102));
        jPanel1.setPreferredSize(new java.awt.Dimension(900, 100));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnPerfil.setBackground(new java.awt.Color(204, 204, 204));
        btnPerfil.setForeground(new java.awt.Color(0, 0, 0));
        btnPerfil.setText("Perfil");
        btnPerfil.setBorder(null);
        btnPerfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPerfilActionPerformed(evt);
            }
        });
        jPanel1.add(btnPerfil, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 6, 81, 75));

        btnCerrarSesion.setText("Cerrar Sesión");
        btnCerrarSesion.setBackground(new java.awt.Color(255, 255, 255));
        btnCerrarSesion.setBorder(null);
        btnCerrarSesion.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnCerrarSesion.setForeground(new java.awt.Color(0, 0, 0));
        btnCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarSesionActionPerformed(evt);
            }
        });
        jPanel1.add(btnCerrarSesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(1390, 30, 141, 36));

        jLabel1.setFont(new java.awt.Font("Roboto Black", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("JUATSAPP");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 30, -1, -1));

        btnAgregarContactos.setBackground(new java.awt.Color(204, 204, 204));
        btnAgregarContactos.setForeground(new java.awt.Color(0, 0, 0));
        btnAgregarContactos.setText("Agregar contactos");
        btnAgregarContactos.setBorder(null);
        btnAgregarContactos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarContactosActionPerformed(evt);
            }
        });
        jPanel1.add(btnAgregarContactos, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, 114, 42));

        btnVerContactos.setBackground(new java.awt.Color(204, 204, 204));
        btnVerContactos.setForeground(new java.awt.Color(0, 0, 0));
        btnVerContactos.setText("Ver Contactos");
        btnVerContactos.setBorder(null);
        btnVerContactos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerContactosActionPerformed(evt);
            }
        });
        jPanel1.add(btnVerContactos, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 30, 114, 42));

        pnBackground.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1600, -1));

        panelPrincipal.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pnlMensajesLayout = new javax.swing.GroupLayout(pnlMensajes);
        pnlMensajes.setLayout(pnlMensajesLayout);
        pnlMensajesLayout.setHorizontalGroup(
            pnlMensajesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 949, Short.MAX_VALUE)
        );
        pnlMensajesLayout.setVerticalGroup(
            pnlMensajesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 497, Short.MAX_VALUE)
        );

        jScrollPane.setViewportView(pnlMensajes);

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 955, Short.MAX_VALUE)
                .addGap(21, 21, 21))
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnBackground.add(panelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 130, 990, 560));

        btnNuevoChat.setBackground(new java.awt.Color(0, 51, 102));
        btnNuevoChat.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnNuevoChat.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevoChat.setText("Nuevo Chat");
        btnNuevoChat.setBorder(null);
        btnNuevoChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoChatActionPerformed(evt);
            }
        });
        pnBackground.add(btnNuevoChat, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 440, 40));

        Enviar.setText("Enviar");
        Enviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EnviarActionPerformed(evt);
            }
        });
        pnBackground.add(Enviar, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 730, -1, -1));

        btnCargarImagen.setText("Subir Imagen");
        btnCargarImagen.setBackground(new java.awt.Color(0, 51, 102));
        btnCargarImagen.setForeground(new java.awt.Color(255, 255, 255));
        btnCargarImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarImagenActionPerformed(evt);
            }
        });
        pnBackground.add(btnCargarImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 820, 120, 30));

        lblImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/JuatsConejo.png"))); // NOI18N
        pnBackground.add(lblImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 680, 140, 140));

        txtMensaje.setText("Tu mensaje aqui...");
        txtMensaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMensajeActionPerformed(evt);
            }
        });
        pnBackground.add(txtMensaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 730, 380, -1));

        javax.swing.GroupLayout pnlChatsLayout = new javax.swing.GroupLayout(pnlChats);
        pnlChats.setLayout(pnlChatsLayout);
        pnlChatsLayout.setHorizontalGroup(
            pnlChatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 440, Short.MAX_VALUE)
        );
        pnlChatsLayout.setVerticalGroup(
            pnlChatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
        );

        pnBackground.add(pnlChats, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 440, 580));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnBackground, javax.swing.GroupLayout.DEFAULT_SIZE, 1600, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnBackground, javax.swing.GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoChatActionPerformed
        // TODO add your handling code here:
        DlgNuevoChat frmNuevoChat = new DlgNuevoChat(this, true, idUsuarioLogeado);
        frmNuevoChat.setVisible(true);
        // cargarChatsEnTabla();
        cargarChatsEnPanel();
    }//GEN-LAST:event_btnNuevoChatActionPerformed

    private void btnPerfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPerfilActionPerformed
        // TODO add your handling code here:
        FrmEditarPerfil frame = new FrmEditarPerfil(idUsuarioLogeado);
        frame.setVisible(true);

    }//GEN-LAST:event_btnPerfilActionPerformed

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
        Login frame = new Login();
        frame.setVisible(true);

        this.dispose();
    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    private void btnAgregarContactosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarContactosActionPerformed
        DlgTelefonos dialog = new DlgTelefonos(this, true, idUsuarioLogeado);
        dialog.setVisible(true);
        // cargarChatsEnTabla();
        cargarChatsEnPanel();
    }//GEN-LAST:event_btnAgregarContactosActionPerformed

    private void btnVerContactosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerContactosActionPerformed
        DlgContactos frame = new DlgContactos(this, true, idUsuarioLogeado);
        frame.setVisible(true);
        // cargarChatsEnTabla();
        cargarChatsEnPanel();
    }//GEN-LAST:event_btnVerContactosActionPerformed

    private void btnCargarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarImagenActionPerformed
        File archivo;
        JFileChooser flcAbrirArchivo = new JFileChooser();
        flcAbrirArchivo.setFileFilter(new FileNameExtensionFilter("archivo de imagen", "jpg", "jpeg", "png"));
        int respuesta = flcAbrirArchivo.showOpenDialog(this);

        if (respuesta == JFileChooser.APPROVE_OPTION) {
            archivo = flcAbrirArchivo.getSelectedFile();
            txtRuta = archivo.getAbsolutePath();
            Image foto = getToolkit().getImage(txtRuta);
            foto = foto.getScaledInstance(262, 234, 1);
            lblImagen.setIcon(new ImageIcon(foto));
        }
    }//GEN-LAST:event_btnCargarImagenActionPerformed

    private void EnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EnviarActionPerformed
        // TODO add your handling code here:
        if (chatActual == null) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un chat primero.");
            return;
        }

        byte[] array = iconToByteArray(lblImagen.getIcon());

        MensajeDTO mensajeDTO = new MensajeDTO(idUsuarioLogeado, txtMensaje.getText(), array, LocalDateTime.now());

        try {
            chatBO.enviarMensaje(this.chatActual.getId(), mensajeDTO);
            // Limpiar el campo de mensaje y la imagen después de enviar
            txtMensaje.setText("");
            lblImagen.setIcon(new ImageIcon(getClass().getResource("/images/JuatsConejo.png")));

            // Recargar los mensajes inmediatamente después de enviar
            verChat();
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, "Error al enviar el mensaje: " + ex.getMessage());
        }

    }//GEN-LAST:event_EnviarActionPerformed

    private void txtMensajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMensajeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMensajeActionPerformed

//    public void cargarPanelChat(Chat chat, Usuario usuario) {
//        PnlChat pnlChat = new PnlChat(chat, usuario);
//        pnlChat.setSize(620, 430);
//        panelPrincipal.setSize(620, 430);
//        pnlChat.setLocation(0, 0);
//        pnlChat.setBackground(Color.GRAY);
//        panelPrincipal.removeAll();
//        panelPrincipal.add(pnlChat, BorderLayout.CENTER);
//        panelPrincipal.revalidate();
//        panelPrincipal.repaint();
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Enviar;
    private javax.swing.JButton btnAgregarContactos;
    private javax.swing.JButton btnCargarImagen;
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnNuevoChat;
    private javax.swing.JButton btnPerfil;
    private javax.swing.JButton btnVerContactos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JPanel pnBackground;
    private javax.swing.JPanel pnlChats;
    private javax.swing.JPanel pnlMensajes;
    private javax.swing.JTextField txtMensaje;
    // End of variables declaration//GEN-END:variables
}
