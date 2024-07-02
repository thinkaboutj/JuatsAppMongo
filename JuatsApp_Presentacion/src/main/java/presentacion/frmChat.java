/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package presentacion;

import DTOs.ChatDTO;
import DTOs.UsuarioDTO;
import excepciones.NegocioException;
import interfaces.IChatBO;
import interfaces.IUsuarioBO;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import negocio.ChatBO;
import negocio.UsuarioBO;
import org.bson.types.ObjectId;

/**
 * Frame donde se muestran los chats del usuario
 *
 * @author Jesus Medina (╹ڡ╹ ) ID:00000247527
 */
public class FrmChat extends javax.swing.JFrame {

    private ObjectId idUsuarioLogeado;
    private IUsuarioBO usuarioBO;
    private IChatBO chatBO;

    public FrmChat(ObjectId idUsuarioLogeado) {
        initComponents();
        usuarioBO = new UsuarioBO();
        chatBO = new ChatBO();

        this.idUsuarioLogeado = idUsuarioLogeado;
    }

    public void llenarTablaChats() {
//        listaChats = usuario.getChats();
//        DefaultTableModel modeloTabla = (DefaultTableModel) this.tblChats.getModel();
//        // Limpia tabla anterior
//        modeloTabla.setRowCount(0);
//        listaChats.forEach(chat -> {
//            Object[] fila = {
//                chat.getNombre()
//            };
//            modeloTabla.addRow(fila);
//        });
//        this.tblChats.updateUI();
    }

    public void actualizarChats() {
        llenarTablaChats();
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnBackground = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btnPerfil = new javax.swing.JButton();
        btnCerrarSesion = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnAgregarContactos = new javax.swing.JButton();
        btnAgregarContactos1 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        panelPrincipal = new javax.swing.JPanel();
        btnNuevoChat = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblChats = new javax.swing.JTable();

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
        btnAgregarContactos.setText("Agregar contactos");
        btnAgregarContactos.setBorder(null);
        btnAgregarContactos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarContactosActionPerformed(evt);
            }
        });
        jPanel1.add(btnAgregarContactos, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, 114, 42));

        btnAgregarContactos1.setBackground(new java.awt.Color(204, 204, 204));
        btnAgregarContactos1.setText("Ver Contactos");
        btnAgregarContactos1.setBorder(null);
        btnAgregarContactos1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarContactos1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnAgregarContactos1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 30, 114, 42));

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 40, -1, -1));

        pnBackground.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1600, -1));

        panelPrincipal.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1040, Short.MAX_VALUE)
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 750, Short.MAX_VALUE)
        );

        pnBackground.add(panelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 120, 1040, 750));

        btnNuevoChat.setText("Nuevo Chat");
        btnNuevoChat.setBackground(new java.awt.Color(0, 51, 102));
        btnNuevoChat.setBorder(null);
        btnNuevoChat.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnNuevoChat.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevoChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoChatActionPerformed(evt);
            }
        });
        pnBackground.add(btnNuevoChat, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 450, 40));

        tblChats.setBackground(new java.awt.Color(0, 102, 153));
        tblChats.setForeground(new java.awt.Color(204, 204, 204));
        tblChats.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Chats"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
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
        }

        pnBackground.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, 690));

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
    }//GEN-LAST:event_btnNuevoChatActionPerformed

    private void btnPerfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPerfilActionPerformed
        // TODO add your handling code here:
        FrmEditarPerfil frame = new FrmEditarPerfil(idUsuarioLogeado);
        frame.setVisible(true);

    }//GEN-LAST:event_btnPerfilActionPerformed

    private void tblChatsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChatsMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_tblChatsMouseClicked

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
        // TODO add your handling code here:
        Login frame = new Login();
        frame.setVisible(true);

        this.dispose();
    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    private void btnAgregarContactosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarContactosActionPerformed
        // TODO add your handling code here:
        DlgTelefonos dialog = new DlgTelefonos(this, true, idUsuarioLogeado);
        dialog.setVisible(true);
    }//GEN-LAST:event_btnAgregarContactosActionPerformed

    private void btnAgregarContactos1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarContactos1ActionPerformed
        // TODO add your handling code here:
        DlgContactos frame = new DlgContactos(this, true, idUsuarioLogeado);
        frame.setVisible(true);
        
    }//GEN-LAST:event_btnAgregarContactos1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        List<ChatDTO> chats = new ArrayList<>();
        try {
            chats = chatBO.consultarChatsDelUsuario(idUsuarioLogeado);
        } catch (NegocioException ex) {
            Logger.getLogger(FrmChat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed
//
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
    private javax.swing.JButton btnAgregarContactos1;
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnNuevoChat;
    private javax.swing.JButton btnPerfil;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JPanel pnBackground;
    public javax.swing.JTable tblChats;
    // End of variables declaration//GEN-END:variables
}
