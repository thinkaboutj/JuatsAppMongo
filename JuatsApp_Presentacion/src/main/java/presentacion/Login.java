package presentacion;

import DTOs.UsuarioDTO;
import excepciones.NegocioException;
import interfaces.IUsuarioBO;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
        lblRegistrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                abrirRegistro();
            }
        });
    }

    private void abrirRegistro() {
        // Abrir el frame de registro
        FrmRegistroUsuario frmRegistroUsuario = new FrmRegistroUsuario();
        frmRegistroUsuario.setVisible(true);

        // Cerrar el frame de login
        this.dispose();
    }

    public void ingresar() {
        String telefono = txtTelefonoLogin.getText().trim();
        String contrasena = String.valueOf(txtContrasenaLogin.getPassword());

        if (telefono.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
            return;
        }

        // Verificar longitud del teléfono
        if (telefono.length() != 10) {
            JOptionPane.showMessageDialog(this, "El número de teléfono debe tener 10 dígitos.");
            return;
        }

        // Verificar que el teléfono contenga solo números
        if (!telefono.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(this, "El número de teléfono solo debe contener números.");
            return;
        }

        // Intentar realizar el login
        UsuarioDTO usuarioLogin = new UsuarioDTO();
        try {
            usuarioLogin = usuarioBO.login(contrasena, telefono);

            // Si el login es exitoso, abrir la ventana del chat u otra ventana
            frmChat frame = new frmChat(new ObjectId(usuarioLogin.getId()));
            frame.setVisible(true);

            // Cerrar la ventana actual de login
            this.dispose();
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
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
        lblRegistrar = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Login");
        setResizable(false);

        pnBackground.setBackground(new java.awt.Color(255, 255, 255));
        pnBackground.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/LogoConejo.png"))); // NOI18N
        pnBackground.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 80, 220, 160));

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Contraseña:");
        pnBackground.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 330, -1, -1));

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Teléfono: ");
        pnBackground.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 280, -1, -1));

        txtTelefonoLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        pnBackground.add(txtTelefonoLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 280, 240, -1));
        pnBackground.add(txtContrasenaLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 330, 240, -1));

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
        pnBackground.add(btnIngresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 380, 90, 40));

        lblRegistrar.setBackground(new java.awt.Color(0, 0, 0));
        lblRegistrar.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lblRegistrar.setForeground(new java.awt.Color(0, 102, 204));
        lblRegistrar.setText("Registrate");
        pnBackground.add(lblRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 450, -1, -1));

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("¿No tienes Cuenta?");
        pnBackground.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 450, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnBackground, javax.swing.GroupLayout.DEFAULT_SIZE, 1067, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnBackground, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarActionPerformed
        // TODO add your handling code here:
    
        ingresar();
    }//GEN-LAST:event_btnIngresarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIngresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lblRegistrar;
    private javax.swing.JPanel pnBackground;
    private javax.swing.JPasswordField txtContrasenaLogin;
    private javax.swing.JTextField txtTelefonoLogin;
    // End of variables declaration//GEN-END:variables
}
