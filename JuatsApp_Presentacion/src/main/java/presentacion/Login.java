package presentacion;

import DTOs.UsuarioDTO;
import excepciones.NegocioException;
import interfaces.IUsuarioBO;
import javax.swing.JOptionPane;
import negocio.UsuarioBO;
import org.bson.types.ObjectId;


/**
 * Frama para iniciar sesion y es el frame principal de la app
 *
 * @author Jesus Medina (╹ڡ╹ ) ID:00000247527
 */
public class Login extends javax.swing.JFrame {
    
    private IUsuarioBO usuarioBO;
    
    public Login() {
        initComponents();
        usuarioBO = new UsuarioBO();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnBackground = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtTelefonoLogin = new javax.swing.JTextField();
        txtContrasenaLogin = new javax.swing.JPasswordField();
        btnIngresar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnRegistrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Login");
        setResizable(false);

        pnBackground.setBackground(new java.awt.Color(255, 255, 255));
        pnBackground.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/LogoConejo.png"))); // NOI18N
        pnBackground.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, 220, 160));

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Contraseña:");
        pnBackground.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 250, -1, -1));

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Teléfono: ");
        pnBackground.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 200, -1, -1));

        txtTelefonoLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pnBackground.add(txtTelefonoLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 200, 240, -1));
        pnBackground.add(txtContrasenaLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 250, 240, -1));

        btnIngresar.setBackground(new java.awt.Color(0, 51, 102));
        btnIngresar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnIngresar.setForeground(new java.awt.Color(255, 255, 255));
        btnIngresar.setText("Ingresar");
        btnIngresar.setBorder(null);
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });
        pnBackground.add(btnIngresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 290, 90, 40));

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 102));
        jLabel4.setText("¿No tienes Cuenta?");
        pnBackground.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 340, -1, -1));

        btnRegistrar.setBackground(new java.awt.Color(0, 51, 102));
        btnRegistrar.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnRegistrar.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrar.setText("Registrar");
        btnRegistrar.setBorder(null);
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });
        pnBackground.add(btnRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 370, 100, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnBackground, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnBackground, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarActionPerformed
        // TODO add your handling code here:
        UsuarioDTO usuarioLogin = new UsuarioDTO();
        try {
            usuarioLogin = usuarioBO.login(String.valueOf(txtContrasenaLogin.getPassword()), txtTelefonoLogin.getText());
            
            frmChat frame = new frmChat(new ObjectId(usuarioLogin.getId()));
            frame.setVisible(true);
            
            this.dispose();
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
        
    }//GEN-LAST:event_btnIngresarActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        FrmRegistroUsuario frmRegistroUsuario = new FrmRegistroUsuario();
        frmRegistroUsuario.setVisible(true);
    }//GEN-LAST:event_btnRegistrarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIngresar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel pnBackground;
    private javax.swing.JPasswordField txtContrasenaLogin;
    private javax.swing.JTextField txtTelefonoLogin;
    // End of variables declaration//GEN-END:variables
}
