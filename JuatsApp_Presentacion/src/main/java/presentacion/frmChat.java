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
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
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

    private ChatDTO chatActual;

    private String txtRuta;

    public frmChat(ObjectId idUsuarioLogeado) {
        initComponents();
        usuarioBO = new UsuarioBO();
        chatBO = new ChatBO();

        this.idUsuarioLogeado = idUsuarioLogeado;
        cargarMetodosIniciales();
    }
    
    // convertir el arreglo de bytes de la imagen del usuario consultado
    // para asi poder mostrar la imagen
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

    // convertir el icono del label a un arreglo de bytes
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

    private void llenarTablaChats(List<UsuarioChat> listaChats) {
        DefaultTableModel modeloTabla = (DefaultTableModel) this.tblChats.getModel();
        if (modeloTabla.getRowCount() > 0) {
            for (int i = modeloTabla.getRowCount() - 1; i > -1; i--) {
                modeloTabla.removeRow(i);
            }
        }
        if (listaChats != null) {
            listaChats.forEach(row -> {
                Object[] fila = new Object[4];

                Icon icono = byteArrayToIcon(row.contacto.getImagen());
                fila[0] = row.chat.getId().toHexString();
                fila[1] = icono;                
                try {
                    if(usuarioBO.esContacto(idUsuarioLogeado, new ObjectId(row.contacto.getId()))){
                        fila[2] = row.contacto.getUsuario();
                    } else {
                        fila[2] = row.contacto.getTelefono();
                    }
                } catch (NegocioException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
                modeloTabla.addRow(fila);
            });
        }
    }


    private void cargarChatsEnTabla() {
        List<UsuarioDTO> usuariosDTO = new ArrayList<>();
        List<ChatDTO> chatsDTO = new ArrayList<>();
        List<UsuarioChat> lista = new ArrayList<>();

        try {
            chatsDTO = chatBO.consultarChatsDelUsuario(idUsuarioLogeado);

            for (int i = 0; i < chatsDTO.size(); i++) {
                UsuarioDTO usuarioAux = new UsuarioDTO();

                if (chatsDTO.get(i).getIdParticipantes().get(0).equals(idUsuarioLogeado)) {
                    usuarioAux = usuarioBO.consultarUsuario(chatsDTO.get(i).getIdParticipantes().get(1));
                } else {
                    usuarioAux = usuarioBO.consultarUsuario(chatsDTO.get(i).getIdParticipantes().get(0));
                }
                usuariosDTO.add(usuarioAux);
            }

            for (int i = 0; i < chatsDTO.size(); i++) {
                lista.add(new UsuarioChat(usuariosDTO.get(i), chatsDTO.get(i)));
            }

            llenarTablaChats(lista);
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }

    protected void cargarMetodosIniciales() {
        this.cargarConfiguracionInicialTablaChats();
        this.cargarChatsEnTabla();
    }

    private void cargarConfiguracionInicialTablaChats() {
        ActionListener onVerChatClickListener = (ActionEvent e) -> {
            verChat();
        };
        TableColumn columnaImagen = tblChats.getColumnModel().getColumn(1);
        columnaImagen.setCellRenderer(new ImageRenderer());
        columnaImagen.setCellRenderer(new ImageRenderer());
        int indiceVerChat = 3;
        TableColumnModel modeloColumnas = this.tblChats.getColumnModel();
        modeloColumnas.getColumn(indiceVerChat).setCellRenderer(new JButtonRenderer("Ver chat"));
        modeloColumnas.getColumn(indiceVerChat).setCellEditor(new JButtonCellEditor("Ver chat", onVerChatClickListener));
        
    }

    
    private void llenarTablasMensajes(List<MensajeDTO> listaMensajes) {
        DefaultTableModel modeloTablaDelWey = (DefaultTableModel) this.tblMensajesDelOtroWey.getModel();
        DefaultTableModel modeloTablaMios = (DefaultTableModel) this.tblMensajesMios.getModel();
        if (modeloTablaDelWey.getRowCount() > 0) {
            for (int i = modeloTablaDelWey.getRowCount() - 1; i > -1; i--) {
                modeloTablaDelWey.removeRow(i);
            }
        }
        if (modeloTablaMios.getRowCount() > 0) {
            for (int i = modeloTablaMios.getRowCount() - 1; i > -1; i--) {
                modeloTablaMios.removeRow(i);
            }
        }
        if (listaMensajes != null) {
            for (int i = 0; i < listaMensajes.size(); i++){
                if(listaMensajes.get(i).getIdUsuario().equals(idUsuarioLogeado)){
                    
                    listaMensajes.forEach(row -> {
                        Object[] fila = new Object[4];
                        Icon icono = byteArrayToIcon(row.getImagen());
                        fila[0] = icono;
                        fila[1] = row.getTexto();
                        fila[2] = row.getFecha_de_registro();
                        modeloTablaMios.addRow(fila);
                    });
                    listaMensajes.forEach(row -> {
                        Object[] fila = new Object[3];
                        Icon icono = byteArrayToIcon(null);
                        fila[0] = icono;
                        fila[1] = null;
                        fila[2] = null;
                        modeloTablaDelWey.addRow(fila);
                    });
                } else {
                    listaMensajes.forEach(row -> {
                        Object[] fila = new Object[3];
                        Icon icono = byteArrayToIcon(row.getImagen());
                        fila[0] = icono;
                        fila[1] = row.getTexto();
                        fila[2] = row.getFecha_de_registro();
                        modeloTablaDelWey.addRow(fila);
                    });
                    listaMensajes.forEach(row -> {
                        Object[] fila = new Object[4];
                        fila[0] = null;
                        fila[1] = null;
                        fila[2] = null;
                        modeloTablaMios.addRow(fila);
                    });
                }
            }
        }        
    }

    private void cargarMensajesEnTablas() {
        try {
            List<MensajeDTO> listaMensajes = chatBO.obtenerMensajesOrdenadosPorFecha(chatActual.getId());
            llenarTablasMensajes(listaMensajes);
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }

    protected void cargarMetodosInicialesDeLaTablaDeMensajes() {
        this.cargarConfiguracionInicialTablaMensajes();
        this.cargarMensajesEnTablas();
    }

    private void cargarConfiguracionInicialTablaMensajes() {
        ActionListener onEditarClickListener = (ActionEvent e) -> {
            editar();
        };
        
        TableColumn columnaImagen = tblMensajesMios.getColumnModel().getColumn(0);
        columnaImagen.setCellRenderer(new ImageRenderer());
        
        TableColumn columnaImagen2 = tblMensajesDelOtroWey.getColumnModel().getColumn(0);
        columnaImagen.setCellRenderer(new ImageRenderer());
        
        int indiceEditar = 3;
        
        TableColumnModel modeloColumnas = this.tblMensajesMios.getColumnModel();
        modeloColumnas.getColumn(indiceEditar).setCellRenderer(new JButtonRenderer("Editar"));
        modeloColumnas.getColumn(indiceEditar).setCellEditor(new JButtonCellEditor("Editar", onEditarClickListener));

    }    
    
    public void editar(){
        JOptionPane.showMessageDialog(this, "hola");
        
    }
    
    public void verChat() {
        ObjectId idChat = new ObjectId(((String) (tblChats.getValueAt(tblChats.getSelectedRow(), 0))));
        try {
            this.chatActual = chatBO.consultar(idChat);
            cargarMetodosInicialesDeLaTablaDeMensajes();
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo consultar el chat");
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
        jScrollPane3 = new javax.swing.JScrollPane();
        tblMensajesDelOtroWey = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMensajesMios = new javax.swing.JTable();
        btnNuevoChat = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblChats = new javax.swing.JTable();
        Enviar = new javax.swing.JButton();
        btnCargarImagen = new javax.swing.JButton();
        lblImagen = new javax.swing.JLabel();
        txtMensaje = new javax.swing.JTextField();

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

        tblMensajesDelOtroWey.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "imagen", "texto", "fecha hora"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblMensajesDelOtroWey);
        if (tblMensajesDelOtroWey.getColumnModel().getColumnCount() > 0) {
            tblMensajesDelOtroWey.getColumnModel().getColumn(0).setResizable(false);
            tblMensajesDelOtroWey.getColumnModel().getColumn(1).setResizable(false);
            tblMensajesDelOtroWey.getColumnModel().getColumn(2).setResizable(false);
        }

        tblMensajesMios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "imagen", "texto", "fecha hora", "Editar"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblMensajesMios);
        if (tblMensajesMios.getColumnModel().getColumnCount() > 0) {
            tblMensajesMios.getColumnModel().getColumn(0).setResizable(false);
            tblMensajesMios.getColumnModel().getColumn(1).setResizable(false);
            tblMensajesMios.getColumnModel().getColumn(2).setResizable(false);
            tblMensajesMios.getColumnModel().getColumn(3).setResizable(false);
        }

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 153, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 423, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pnBackground.add(panelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 120, 1030, 560));

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
        pnBackground.add(btnNuevoChat, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 510, 40));

        tblChats.setBackground(new java.awt.Color(0, 102, 153));
        tblChats.setForeground(new java.awt.Color(204, 204, 204));
        tblChats.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id del chat", "Foto", "Contacto", "Ver chat"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblChats.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblChatsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblChats);
        if (tblChats.getColumnModel().getColumnCount() > 0) {
            tblChats.getColumnModel().getColumn(0).setResizable(false);
            tblChats.getColumnModel().getColumn(1).setResizable(false);
            tblChats.getColumnModel().getColumn(2).setResizable(false);
            tblChats.getColumnModel().getColumn(3).setResizable(false);
        }

        pnBackground.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 510, 690));

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnBackground, javax.swing.GroupLayout.DEFAULT_SIZE, 1600, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnBackground, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoChatActionPerformed
        // TODO add your handling code here:
        DlgNuevoChat frmNuevoChat = new DlgNuevoChat(this, true, idUsuarioLogeado);
        frmNuevoChat.setVisible(true);
        cargarChatsEnTabla();
    }//GEN-LAST:event_btnNuevoChatActionPerformed

    private void btnPerfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPerfilActionPerformed
        // TODO add your handling code here:
        FrmEditarPerfil frame = new FrmEditarPerfil(idUsuarioLogeado);
        frame.setVisible(true);

    }//GEN-LAST:event_btnPerfilActionPerformed

    private void tblChatsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChatsMouseClicked

    }//GEN-LAST:event_tblChatsMouseClicked

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
        Login frame = new Login();
        frame.setVisible(true);

        this.dispose();
    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    private void btnAgregarContactosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarContactosActionPerformed
        DlgTelefonos dialog = new DlgTelefonos(this, true, idUsuarioLogeado);
        dialog.setVisible(true);
        cargarChatsEnTabla();
    }//GEN-LAST:event_btnAgregarContactosActionPerformed

    private void btnVerContactosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerContactosActionPerformed
        DlgContactos frame = new DlgContactos(this, true, idUsuarioLogeado);
        frame.setVisible(true);
        cargarChatsEnTabla();
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
        byte[] array = iconToByteArray(lblImagen.getIcon());
        
        MensajeDTO mensajeDTO = new MensajeDTO(idUsuarioLogeado, txtMensaje.getText(), array, LocalDateTime.now());
        
        try {
            chatBO.enviarMensaje(this.chatActual.getId(), mensajeDTO);
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, "Si se hizo");
        }
        cargarMetodosInicialesDeLaTablaDeMensajes();
        
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JPanel pnBackground;
    public javax.swing.JTable tblChats;
    private javax.swing.JTable tblMensajesDelOtroWey;
    private javax.swing.JTable tblMensajesMios;
    private javax.swing.JTextField txtMensaje;
    // End of variables declaration//GEN-END:variables
}
