/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package presentacion;

import DTOs.DomicilioDTO;
import DTOs.UsuarioDTO;
import excepciones.NegocioException;
import interfaces.IUsuarioBO;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import negocio.UsuarioBO;

/**
 * Frame para registrar usuarios
 *
 * @author Jesus Medina (╹ڡ╹ ) ID:00000247527
 */
public class FrmRegistroUsuario extends javax.swing.JFrame {
    
    private IUsuarioBO usuarioBO;
    private String txtRuta;
    
    public FrmRegistroUsuario() {
        initComponents();
        usuarioBO = new UsuarioBO();
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
    
    public void registrar() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();

        // Validación de campos vacíos
        if (txtUsuario.getText().isEmpty() || txtContrasena.getPassword().length == 0
                || dcFechaCumple.getDate() == null || txtTelefono.getText().isEmpty()
                || txtCalle.getText().isEmpty() || txtColonia.getText().isEmpty()
                || txtCodigoPostal.getText().isEmpty() || txtNumero.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Sale del método si hay campos vacíos
        }

        // Validación de teléfono
        if (!txtTelefono.getText().matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Formato de teléfono incorrecto (10 dígitos numéricos)", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Sale del método si el teléfono no tiene el formato correcto
        }

        // Validación de código postal
        if (!txtCodigoPostal.getText().matches("\\d{5}")) {
            JOptionPane.showMessageDialog(this, "Formato de código postal incorrecto (5 dígitos numéricos)", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Sale del método si el código postal no tiene el formato correcto
        }

        // Convertir el icono del label a un arreglo de bytes
        byte[] bytesImagen = iconToByteArray(lblImagen.getIcon());

        // Setear los datos del usuarioDTO
        usuarioDTO.setContrasena(String.valueOf(txtContrasena.getPassword()));
        usuarioDTO.setFechaNacimiento(dcFechaCumple.getDate());
        usuarioDTO.setSexo(cbxGenero.getSelectedItem().toString());
        usuarioDTO.setUsuario(txtUsuario.getText());
        usuarioDTO.setTelefono(txtTelefono.getText());
        usuarioDTO.setImagen(bytesImagen);

        // Crear el domicilioDTO
        DomicilioDTO domicilioDTO = new DomicilioDTO();
        domicilioDTO.setCalle(txtCalle.getText());
        domicilioDTO.setColonia(txtColonia.getText());
        domicilioDTO.setCodigoPostal(txtCodigoPostal.getText());
        domicilioDTO.setNumero(txtNumero.getText());

        // Asignar el domicilio al usuarioDTO
        usuarioDTO.setDomicilio(domicilioDTO);
        
        try {
            // Intentar registrar el usuario
            usuarioBO.registrarUsuario(usuarioDTO);
            JOptionPane.showMessageDialog(this, "Usuario registrado correctamente");
            
            Login login = new Login();
            
            login.setVisible(true);
            this.dispose();
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error al registrar usuario", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlBackground = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtContrasena = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCalle = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtColonia = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtCodigoPostal = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtNumero = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnCargarImagen = new javax.swing.JButton();
        verContrasena = new javax.swing.JCheckBox();
        JLDireccion = new javax.swing.JLabel();
        lblImagen = new javax.swing.JLabel();
        dcFechaCumple = new com.github.lgooddatepicker.components.DatePicker();
        txtUsuario = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        cbxGenero = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registrar Usuario");
        setMinimumSize(new java.awt.Dimension(800, 500));
        setResizable(false);

        pnlBackground.setBackground(new java.awt.Color(255, 255, 255));
        pnlBackground.setMinimumSize(new java.awt.Dimension(800, 500));
        pnlBackground.setPreferredSize(new java.awt.Dimension(800, 500));
        pnlBackground.setRequestFocusEnabled(false);
        pnlBackground.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 51, 102));
        jPanel1.setPreferredSize(new java.awt.Dimension(600, 50));

        jLabel5.setText("Registrar Datos");
        jLabel5.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(306, 306, 306)
                .addComponent(jLabel5)
                .addContainerGap(317, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        pnlBackground.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, -1));

        jLabel1.setText("Usuario:");
        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        pnlBackground.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, -1, -1));
        pnlBackground.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 150, 300, 30));

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Fecha Nacimiento:");
        pnlBackground.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 340, -1, -1));
        pnlBackground.add(txtContrasena, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 210, 300, 30));

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Contraseña:");
        pnlBackground.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, -1, -1));

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Numero:");
        pnlBackground.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 390, -1, -1));

        jLabel6.setText("Genero:");
        jLabel6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        pnlBackground.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 270, -1, -1));

        txtCalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCalleActionPerformed(evt);
            }
        });
        pnlBackground.add(txtCalle, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 300, 320, 30));

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Calle:");
        pnlBackground.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 300, -1, -1));
        pnlBackground.add(txtColonia, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 340, 320, 30));

        jLabel8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Colonia:");
        pnlBackground.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 340, -1, -1));
        pnlBackground.add(txtCodigoPostal, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 380, 110, 30));

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Codigo Postal:");
        pnlBackground.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 390, -1, -1));
        pnlBackground.add(txtNumero, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 380, 110, 30));

        jLabel10.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 51, 102));
        jLabel10.setText("Domicilio");
        pnlBackground.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 270, -1, -1));

        btnAceptar.setText("Aceptar");
        btnAceptar.setBackground(new java.awt.Color(0, 51, 102));
        btnAceptar.setBorder(null);
        btnAceptar.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnAceptar.setForeground(new java.awt.Color(255, 255, 255));
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });
        pnlBackground.add(btnAceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 440, 90, 40));

        btnCancelar.setText("Cancelar");
        btnCancelar.setBackground(new java.awt.Color(0, 51, 102));
        btnCancelar.setBorder(null);
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        pnlBackground.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 440, 100, 40));

        btnCargarImagen.setText("Subir Imagen");
        btnCargarImagen.setBackground(new java.awt.Color(0, 51, 102));
        btnCargarImagen.setForeground(new java.awt.Color(255, 255, 255));
        btnCargarImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarImagenActionPerformed(evt);
            }
        });
        pnlBackground.add(btnCargarImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 210, 120, 30));

        verContrasena.setText("Ver");
        verContrasena.setForeground(new java.awt.Color(0, 0, 0));
        verContrasena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verContrasenaActionPerformed(evt);
            }
        });
        pnlBackground.add(verContrasena, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 220, -1, -1));
        pnlBackground.add(JLDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 280, 120, 20));

        lblImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/JuatsConejo.png"))); // NOI18N
        pnlBackground.add(lblImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 70, 140, 140));
        pnlBackground.add(dcFechaCumple, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 370, -1, -1));
        pnlBackground.add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 300, 30));

        jLabel12.setBackground(new java.awt.Color(0, 0, 0));
        jLabel12.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Teléfono:");
        pnlBackground.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, -1, -1));

        cbxGenero.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hombre", "Mujer", "Robot", "Ninja", "otro" }));
        pnlBackground.add(cbxGenero, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 270, 200, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCalleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCalleActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
        registrar();
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        
        Login login = new Login();
        login.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

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

    private void verContrasenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verContrasenaActionPerformed
        // TODO add your handling code here:
        if (verContrasena.isSelected()) {
            txtContrasena.setEchoChar((char) 0);
        } else {
            txtContrasena.setEchoChar('*');
        }

    }//GEN-LAST:event_verContrasenaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel JLDireccion;
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCargarImagen;
    private javax.swing.JComboBox<String> cbxGenero;
    private com.github.lgooddatepicker.components.DatePicker dcFechaCumple;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JPanel pnlBackground;
    private javax.swing.JTextField txtCalle;
    private javax.swing.JTextField txtCodigoPostal;
    private javax.swing.JTextField txtColonia;
    private javax.swing.JPasswordField txtContrasena;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtUsuario;
    private javax.swing.JCheckBox verContrasena;
    // End of variables declaration//GEN-END:variables
}
