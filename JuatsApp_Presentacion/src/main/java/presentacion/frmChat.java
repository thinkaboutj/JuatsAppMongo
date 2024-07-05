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
import java.awt.Dimension;
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
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicScrollBarUI;
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
    private UsuarioDTO contactoActual;  // Variable de instancia para almacenar el contacto actual
    private Timer mensajeTimer;

    public frmChat(ObjectId idUsuarioLogeado) {
        initComponents();
        usuarioBO = new UsuarioBO();
        chatBO = new ChatBO();
        this.idUsuarioLogeado = idUsuarioLogeado;
        cargarMetodosIniciales();
        ocultarComponentes();
        
        
//        mensajeTimer = new Timer(500, new ActionListener() { // Actualizar cada 5 segundos
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                verChat();
//            }
//        });
//        mensajeTimer.start();
        
    }

    public void ocultarComponentes() {

        lblImagen.setVisible(false);
        btnEnviar.setVisible(false);
        btnCargarImagen.setVisible(false);
        txtMensaje.setVisible(false);
        panelPrincipal.setVisible(false);
        pnlEscribir.setVisible(false);
    }

    public void cargComponentes() {

        lblImagen.setVisible(true);
        btnEnviar.setVisible(true);
        btnCargarImagen.setVisible(true);
        txtMensaje.setVisible(true);
        panelPrincipal.setVisible(true);
        pnlEscribir.setVisible(true);
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

//    private void llenarPanelChats(List<UsuarioChat> listaChats) {
//        pnlChats.removeAll();
//        if (listaChats != null) {
//            for (UsuarioChat usuarioChat : listaChats) {
//                JLabel lblContacto = new JLabel();
//                Icon icono = byteArrayToIcon(usuarioChat.contacto.getImagen());
//                lblContacto.setIcon(icono);
//
//                try {
//                    if (usuarioBO.esContacto(idUsuarioLogeado, new ObjectId(usuarioChat.contacto.getId()))) {
//                        lblContacto.setText(usuarioChat.contacto.getUsuario());
//                    } else {
//                        lblContacto.setText(usuarioChat.contacto.getTelefono());
//                    }
//                } catch (NegocioException ex) {
//                    JOptionPane.showMessageDialog(this, "no se pudo");
//                }
//
//                lblContacto.setHorizontalTextPosition(SwingConstants.RIGHT);
//                lblContacto.addMouseListener(new MouseAdapter() {
//                    @Override
//                    public void mouseClicked(MouseEvent e) {
//                        chatActual = usuarioChat.chat;
//                        contactoActual = usuarioChat.contacto; // Guarda la información del contacto actual
//                        verChat();
//                    }
//                });
//                pnlChats.add(lblContacto);
//            }
//        }
//        pnlChats.revalidate();
//        pnlChats.repaint();
//    }

    
   private void llenarPanelChats(List<UsuarioChat> listaChats) {
    pnlChats.removeAll();
    pnlChats.setLayout(new BoxLayout(pnlChats, BoxLayout.Y_AXIS));
    pnlChats.setBackground(new Color(240, 242, 245)); // Color base gris claro

    if (listaChats != null) {
        for (UsuarioChat usuarioChat : listaChats) {
            JPanel panelChat = new JPanel();
            panelChat.setLayout(new BorderLayout());
            panelChat.setMaximumSize(new Dimension(Integer.MAX_VALUE, 72)); // Altura fija para cada chat
            panelChat.setBackground(new Color(240, 242, 245)); // Color base gris claro
            panelChat.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220))); // Línea divisoria

            // Panel para la imagen de perfil
            JPanel panelImagen = new JPanel(new BorderLayout());
            panelImagen.setPreferredSize(new Dimension(60, 60));
            panelImagen.setOpaque(false);
            
            JLabel lblImagen = new JLabel();
            Icon icono = byteArrayToIcon(usuarioChat.contacto.getImagen());
            lblImagen.setIcon(icono);
            panelImagen.add(lblImagen, BorderLayout.CENTER);

            // Panel para el nombre y último mensaje
            JPanel panelInfo = new JPanel();
            panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
            panelInfo.setOpaque(false);

            JLabel lblNombre = new JLabel();
            lblNombre.setForeground(Color.BLACK);
            lblNombre.setFont(new Font("Segoe UI", Font.PLAIN, 16));

            JLabel lblUltimoMensaje = new JLabel("Último mensaje");
            lblUltimoMensaje.setForeground(new Color(100, 100, 100));
            lblUltimoMensaje.setFont(new Font("Segoe UI", Font.PLAIN, 14));

            try {
                if (usuarioBO.esContacto(idUsuarioLogeado, new ObjectId(usuarioChat.contacto.getId()))) {
                    lblNombre.setText(usuarioChat.contacto.getUsuario());
                } else {
                    lblNombre.setText(usuarioChat.contacto.getTelefono());
                }
            } catch (NegocioException ex) {
                JOptionPane.showMessageDialog(this, "No se pudo cargar el contacto");
            }

            panelInfo.add(lblNombre);
            panelInfo.add(Box.createVerticalStrut(4));
            panelInfo.add(lblUltimoMensaje);

            // Panel para la hora del último mensaje
            JPanel panelHora = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            panelHora.setOpaque(false);
            
            JLabel lblHora = new JLabel("12:45 PM");
            lblHora.setForeground(new Color(100, 100, 100));
            lblHora.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            panelHora.add(lblHora);

            panelChat.add(panelImagen, BorderLayout.WEST);
            panelChat.add(panelInfo, BorderLayout.CENTER);
            panelChat.add(panelHora, BorderLayout.EAST);

            panelChat.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    chatActual = usuarioChat.chat;
                    contactoActual = usuarioChat.contacto;
                    verChat();
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    panelChat.setBackground(new Color(0, 51, 102)); // Color seleccionado azul índigo
                    lblNombre.setForeground(Color.WHITE);
                    lblUltimoMensaje.setForeground(new Color(220, 220, 220));
                    lblHora.setForeground(new Color(220, 220, 220));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    panelChat.setBackground(new Color(240, 242, 245)); // Volver al color base
                    lblNombre.setForeground(Color.BLACK);
                    lblUltimoMensaje.setForeground(new Color(100, 100, 100));
                    lblHora.setForeground(new Color(100, 100, 100));
                }
            });

            pnlChats.add(panelChat);
            pnlChats.add(Box.createVerticalStrut(1)); // Pequeño espacio entre chats
        }
    }

    pnlChats.add(Box.createVerticalGlue()); // Empuja todo hacia arriba
    pnlChats.revalidate();
    pnlChats.repaint();

    // Configurar el JScrollPane
    JScrollPane scrollPane = new JScrollPane(pnlChats);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.getVerticalScrollBar().setUnitIncrement(16);
    scrollPane.setBorder(null);
    scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(8, Integer.MAX_VALUE)); // Barra de desplazamiento más delgada

    // Personalizar la barra de desplazamiento
    scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
        @Override
        protected void configureScrollBarColors() {
            this.thumbColor = new Color(200, 200, 200);
        }

        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }

        private JButton createZeroButton() {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0));
            button.setMinimumSize(new Dimension(0, 0));
            button.setMaximumSize(new Dimension(0, 0));
            return button;
        }
    });

    // Asumiendo que tienes un panel contenedor para el scrollPane
    panelContenedor.removeAll();
    panelContenedor.setLayout(new BorderLayout());
    panelContenedor.add(scrollPane, BorderLayout.CENTER);
    panelContenedor.revalidate();
    panelContenedor.repaint();
}
    
    private void verChat() {
        if (chatActual != null) {
            try {
                List<MensajeDTO> mensajes = chatBO.obtenerMensajesOrdenadosPorFecha(chatActual.getId());
                cargarMensajesEnPanel(mensajes);
                if (contactoActual != null) {

                    cargComponentes();
                    String nombre = contactoActual.getUsuario();

                    String telefono = contactoActual.getTelefono();
                    lblNombreContacto.setText(telefono);
                } else {
                    lblNombreContacto.setText("Contacto desconocido");
                }
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
    pnlMensajes.setLayout(new BoxLayout(pnlMensajes, BoxLayout.Y_AXIS));

    if (mensajes != null && !mensajes.isEmpty()) {
        for (MensajeDTO mensaje : mensajes) {
            JPanel panelMensaje = new JPanel();
            panelMensaje.setLayout(new BorderLayout());
            panelMensaje.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

            JTextArea txtMensaje = null;

            // Agregamos el texto si existe
            if (mensaje.getTexto() != null && !mensaje.getTexto().isEmpty()) {
                txtMensaje = new JTextArea(mensaje.getTexto());
                txtMensaje.setEditable(false);
                txtMensaje.setLineWrap(true);
                txtMensaje.setWrapStyleWord(true);
                txtMensaje.setOpaque(false);
                txtMensaje.setBorder(null);
                txtMensaje.setFont(new Font("Arial", Font.PLAIN, 16));
                panelMensaje.add(txtMensaje, BorderLayout.NORTH);
            }

            // Agregamos la imagen si existe y no es solo 1 byte
            if (mensaje.getImagen() != null && mensaje.getImagen().length > 1) {
                ImageIcon imageIcon = new ImageIcon(mensaje.getImagen());
                Image image = imageIcon.getImage().getScaledInstance(200, -1, Image.SCALE_SMOOTH);
                JLabel lblImagen = new JLabel(new ImageIcon(image));
                panelMensaje.add(lblImagen, BorderLayout.CENTER);
            }

            JPanel wrapperPanel = new JPanel();
            wrapperPanel.setLayout(new BorderLayout());
            wrapperPanel.setOpaque(false);
            boolean esMensajePropio = mensaje.getIdUsuario().equals(idUsuarioLogeado);

            if (esMensajePropio) {
                panelMensaje.setBackground(new Color(0, 51, 102));
                if (txtMensaje != null) {
                    txtMensaje.setForeground(Color.WHITE);
                }
                wrapperPanel.add(panelMensaje, BorderLayout.EAST);
            } else {
                panelMensaje.setBackground(new Color(230, 230, 230));
                if (txtMensaje != null) {
                    txtMensaje.setForeground(Color.BLACK);
                }
                wrapperPanel.add(panelMensaje, BorderLayout.WEST);
            }

            // Fijar tamaño fijo para cada mensaje
            wrapperPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

            // Añadir evento de clic solo si el mensaje es propio
            if (esMensajePropio) {
                panelMensaje.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        int option = JOptionPane.showOptionDialog(null,
                                "¿Qué deseas hacer con este mensaje?",
                                "Opciones de Mensaje",
                                JOptionPane.YES_NO_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                new String[]{"Editar", "Eliminar", "Cancelar"},
                                "Cancelar");

                        if (option == JOptionPane.YES_OPTION) {
                            DlgEditar editar = new DlgEditar(null, true, chatActual.getId(), mensaje);
                            editar.setVisible(true);
                        } else if (option == JOptionPane.NO_OPTION) {
                            try {
                                chatBO.eliminarMensaje(chatActual.getId(), mensaje.getFecha_de_registro());
                            } catch (NegocioException ex) {
                                JOptionPane.showMessageDialog(null, "No se pudo eliminar el mensaje");
                            }
                        }
                    }
                });
            }

            pnlMensajes.add(wrapperPanel);
            pnlMensajes.add(Box.createVerticalStrut(10));
        }
    }

    pnlMensajes.add(Box.createVerticalGlue());
    pnlMensajes.revalidate();
    pnlMensajes.repaint();

    SwingUtilities.invokeLater(() -> {
        JScrollPane scrollPane = (JScrollPane) SwingUtilities.getAncestorOfClass(JScrollPane.class, pnlMensajes);
        if (scrollPane != null) {
            JScrollBar vertical = scrollPane.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        }
    });
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
        lblNombreContacto = new javax.swing.JLabel();
        lblTelefono = new javax.swing.JLabel();
        btnNuevoChat = new javax.swing.JButton();
        pnlEscribir = new javax.swing.JPanel();
        lblImagen = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMensaje = new javax.swing.JTextArea();
        btnEnviar = new javax.swing.JButton();
        btnCargarImagen = new javax.swing.JButton();
        panelContenedor = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
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

        btnCerrarSesion.setBackground(new java.awt.Color(255, 255, 255));
        btnCerrarSesion.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnCerrarSesion.setForeground(new java.awt.Color(0, 0, 0));
        btnCerrarSesion.setText("Cerrar Sesión");
        btnCerrarSesion.setBorder(null);
        btnCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarSesionActionPerformed(evt);
            }
        });
        jPanel1.add(btnCerrarSesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 30, 141, 36));

        jLabel1.setFont(new java.awt.Font("Roboto Black", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("JUATSAPP");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 30, -1, -1));

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
            .addGap(0, 1027, Short.MAX_VALUE)
        );
        pnlMensajesLayout.setVerticalGroup(
            pnlMensajesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 528, Short.MAX_VALUE)
        );

        jScrollPane.setViewportView(pnlMensajes);

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 715, Short.MAX_VALUE)
                .addGap(21, 21, 21))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                        .addComponent(lblTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(363, 363, 363))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                        .addComponent(lblNombreContacto, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(379, 379, 379))))
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblNombreContacto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(169, 169, 169))
        );

        pnBackground.add(panelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 100, 750, 420));

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
        pnBackground.add(btnNuevoChat, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 320, 40));

        pnlEscribir.setBackground(new java.awt.Color(255, 255, 255));

        txtMensaje.setColumns(20);
        txtMensaje.setRows(5);
        jScrollPane1.setViewportView(txtMensaje);

        btnEnviar.setBackground(new java.awt.Color(0, 51, 102));
        btnEnviar.setForeground(new java.awt.Color(255, 255, 255));
        btnEnviar.setText("Enviar");
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });

        btnCargarImagen.setBackground(new java.awt.Color(0, 51, 102));
        btnCargarImagen.setForeground(new java.awt.Color(255, 255, 255));
        btnCargarImagen.setText("Subir Imagen");
        btnCargarImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarImagenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlEscribirLayout = new javax.swing.GroupLayout(pnlEscribir);
        pnlEscribir.setLayout(pnlEscribirLayout);
        pnlEscribirLayout.setHorizontalGroup(
            pnlEscribirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEscribirLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(btnCargarImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59))
        );
        pnlEscribirLayout.setVerticalGroup(
            pnlEscribirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEscribirLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(btnEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlEscribirLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlEscribirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlEscribirLayout.createSequentialGroup()
                        .addComponent(btnCargarImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                    .addGroup(pnlEscribirLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(lblImagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pnBackground.add(pnlEscribir, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 520, 750, 100));

        panelContenedor.setBackground(new java.awt.Color(255, 255, 255));

        pnlChats.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout pnlChatsLayout = new javax.swing.GroupLayout(pnlChats);
        pnlChats.setLayout(pnlChatsLayout);
        pnlChatsLayout.setHorizontalGroup(
            pnlChatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 454, Short.MAX_VALUE)
        );
        pnlChatsLayout.setVerticalGroup(
            pnlChatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 684, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(pnlChats);

        javax.swing.GroupLayout panelContenedorLayout = new javax.swing.GroupLayout(panelContenedor);
        panelContenedor.setLayout(panelContenedorLayout);
        panelContenedorLayout.setHorizontalGroup(
            panelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContenedorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelContenedorLayout.setVerticalGroup(
            panelContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContenedorLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 475, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 5, Short.MAX_VALUE))
        );

        pnBackground.add(panelContenedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 320, 480));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnBackground, javax.swing.GroupLayout.PREFERRED_SIZE, 1072, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnBackground, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoChatActionPerformed
        DlgNuevoChat frmNuevoChat = new DlgNuevoChat(this, true, idUsuarioLogeado);
        frmNuevoChat.setVisible(true);
        cargarChatsEnPanel();
    }//GEN-LAST:event_btnNuevoChatActionPerformed

    private void btnPerfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPerfilActionPerformed
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
        cargarChatsEnPanel();
    }//GEN-LAST:event_btnAgregarContactosActionPerformed

    private void btnVerContactosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerContactosActionPerformed
        DlgContactos frame = new DlgContactos(this, true, idUsuarioLogeado);
        frame.setVisible(true);
        cargarChatsEnPanel();
    }//GEN-LAST:event_btnVerContactosActionPerformed

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed
        // TODO add your handling code here:

        if (chatActual == null) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un chat primero.");
            return;
        }

        // Validación para asegurar que ni el texto del mensaje ni la imagen estén vacíos
        if (txtMensaje.getText().trim().isEmpty() && lblImagen.getIcon() == null) {
            JOptionPane.showMessageDialog(this, "El mensaje no puede estar vacío.");
            return;
        }

        byte[] array = iconToByteArray(lblImagen.getIcon());

        MensajeDTO mensajeDTO = new MensajeDTO(idUsuarioLogeado, txtMensaje.getText(), array, LocalDateTime.now());

        try {
            chatBO.enviarMensaje(this.chatActual.getId(), mensajeDTO);
            // Limpiar el campo de mensaje y la imagen después de enviar
            txtMensaje.setText("");
            lblImagen.setIcon(null);

            // Recargar los mensajes inmediatamente después de enviar
            verChat();
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, "Error al enviar el mensaje: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnEnviarActionPerformed

    private void btnCargarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarImagenActionPerformed
        // TODO add your handling code here:

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
    private javax.swing.JButton btnAgregarContactos;
    private javax.swing.JButton btnCargarImagen;
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnEnviar;
    private javax.swing.JButton btnNuevoChat;
    private javax.swing.JButton btnPerfil;
    private javax.swing.JButton btnVerContactos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JLabel lblNombreContacto;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JPanel panelContenedor;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JPanel pnBackground;
    private javax.swing.JPanel pnlChats;
    private javax.swing.JPanel pnlEscribir;
    private javax.swing.JPanel pnlMensajes;
    private javax.swing.JTextArea txtMensaje;
    // End of variables declaration//GEN-END:variables
}
