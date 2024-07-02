/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package presentacion;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author crazy
 */
public class DlgNuevoChat extends javax.swing.JDialog {
    
    private String txtRuta;

    public DlgNuevoChat(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        txtNombreChat5 = new javax.swing.JTextField();
        btnAceptarChat5 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        btnCargarImagen = new javax.swing.JButton();
        lblImagen = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 51, 102));
        jLabel16.setText("Nombre del chat");
        jPanel6.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 20, -1, -1));
        jPanel6.add(txtNombreChat5, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 60, 268, 28));

        btnAceptarChat5.setBackground(new java.awt.Color(0, 51, 102));
        btnAceptarChat5.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnAceptarChat5.setText("Aceptar");
        btnAceptarChat5.setBorder(null);
        btnAceptarChat5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarChat5ActionPerformed(evt);
            }
        });
        jPanel6.add(btnAceptarChat5, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 530, 90, 40));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jPanel6.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 130, 240, 370));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jPanel6.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 240, 370));

        btnCargarImagen.setBackground(new java.awt.Color(0, 51, 102));
        btnCargarImagen.setForeground(new java.awt.Color(255, 255, 255));
        btnCargarImagen.setText("Subir Imagen");
        btnCargarImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarImagenActionPerformed(evt);
            }
        });
        jPanel6.add(btnCargarImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 390, 120, 30));

        lblImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/JuatsConejo.png"))); // NOI18N
        jPanel6.add(lblImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 160, 250, 210));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarChat5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarChat5ActionPerformed

    }//GEN-LAST:event_btnAceptarChat5ActionPerformed

    private void btnCargarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarImagenActionPerformed
        File archivo;
        JFileChooser flcAbrirArchivo = new JFileChooser();
        flcAbrirArchivo.setFileFilter(new FileNameExtensionFilter("archivo de imagen","jpg","jpeg","png") );
        int respuesta = flcAbrirArchivo.showOpenDialog(this);

        if (respuesta == JFileChooser.APPROVE_OPTION ){
            archivo= flcAbrirArchivo.getSelectedFile();
            txtRuta = archivo.getAbsolutePath();
            Image foto= getToolkit().getImage(txtRuta);
            foto=foto.getScaledInstance(262, 234, 1);
            lblImagen.setIcon(new ImageIcon(foto));
        }
    }//GEN-LAST:event_btnCargarImagenActionPerformed

   
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptarChat5;
    private javax.swing.JButton btnCargarImagen;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JTextField txtNombreChat5;
    // End of variables declaration//GEN-END:variables
}
