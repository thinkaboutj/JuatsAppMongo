/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package presentacion;


import javax.swing.table.DefaultTableModel;

/**
 * Frame donde se muestran los chats del usuario
 *
 * @author Jesus Medina (╹ڡ╹ ) ID:00000247527
 */
public class frmChat extends javax.swing.JFrame {
    
    public frmChat() {
        initComponents();
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
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnBackground = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btnPerfil = new javax.swing.JButton();
        btnCerrarSesion = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
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

        btnPerfil.setBackground(new java.awt.Color(204, 204, 204));
        btnPerfil.setText("Perfil");
        btnPerfil.setBorder(null);
        btnPerfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPerfilActionPerformed(evt);
            }
        });

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

        jLabel1.setFont(new java.awt.Font("Roboto Black", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("JUATSAPP");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(btnPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(133, 133, 133)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 365, Short.MAX_VALUE)
                .addComponent(btnCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPerfil, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                    .addComponent(btnCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(19, 19, 19))
        );

        pnBackground.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        panelPrincipal.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 620, Short.MAX_VALUE)
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 430, Short.MAX_VALUE)
        );

        pnBackground.add(panelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 120, 620, 430));

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
        pnBackground.add(btnNuevoChat, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 150, 40));

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

        pnBackground.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 146, 380));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnBackground, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnBackground, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoChatActionPerformed
        // TODO add your handling code here:
        FrmNuevoChat frmNuevoChat = new FrmNuevoChat();
        frmNuevoChat.setVisible(true);
    }//GEN-LAST:event_btnNuevoChatActionPerformed

    private void btnPerfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPerfilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPerfilActionPerformed

    private void tblChatsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChatsMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_tblChatsMouseClicked

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_btnCerrarSesionActionPerformed
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
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnNuevoChat;
    private javax.swing.JButton btnPerfil;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JPanel pnBackground;
    public javax.swing.JTable tblChats;
    // End of variables declaration//GEN-END:variables
}
