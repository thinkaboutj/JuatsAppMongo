/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package presentacion;

import DTOs.ChatDTO;
import DTOs.UsuarioDTO;
import excepciones.NegocioException;
import interfaces.IChatBO;
import interfaces.IUsuarioBO;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
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
 *
 * @author crazy
 */
public class DlgNuevoChat extends javax.swing.JDialog {
    
    private final IUsuarioBO usuarioBO;
    private final IChatBO chatBO;
    private final ObjectId idUsuarioLogeado;
    
    
    
    private List<ObjectId> participantesChat;
    private String txtRuta;
    
    private ArrayList array = new ArrayList<>();
    private DefaultListModel modelo = new DefaultListModel();

    public DlgNuevoChat(java.awt.Frame parent, boolean modal, ObjectId idUsuarioLogeado) {
        super(parent, modal);
        initComponents();
        
        this.idUsuarioLogeado = idUsuarioLogeado;
        usuarioBO = new UsuarioBO();
        chatBO = new ChatBO();
        
        participantesChat = new ArrayList<>();
        participantesChat.add(idUsuarioLogeado);
        
        listParticipantes.setModel(modelo);
        
        cargarMetodosIniciales();
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
    
    private void llenarTablaContactos(List<UsuarioDTO> listaContactos) {
        DefaultTableModel  modeloTabla = (DefaultTableModel)  this.tblContactos.getModel();
        if (modeloTabla.getRowCount() > 0) {
            for (int i = modeloTabla.getRowCount() - 1; i > -1; i--) {
                modeloTabla.removeRow(i);
            }
        }
        if (listaContactos != null) {
            listaContactos.forEach(row -> {
                Object[] fila = new Object[3];
                fila[0] = row.getId();
                fila[1] = row.getUsuario();
                modeloTabla.addRow(fila);
            });
        }
    }
    
    private void cargarContactosEnTabla() {
        try {
            List<UsuarioDTO> listaContactos = usuarioBO.consultarContactos(idUsuarioLogeado);
            llenarTablaContactos(listaContactos);
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }
    
    protected void cargarMetodosIniciales(){
        this.cargarConfiguracionInicialTablaContactos();
        this.cargarContactosEnTabla();
    }
    
    private void cargarConfiguracionInicialTablaContactos() {
        ActionListener onAgregarClickListener = (ActionEvent e) -> {
            agregar();
        };
        int indiceAgregar = 2;
        TableColumnModel modeloColumnas = this.tblContactos.getColumnModel();
        modeloColumnas.getColumn(indiceAgregar).setCellRenderer(new JButtonRenderer("  +  "));
        modeloColumnas.getColumn(indiceAgregar).setCellEditor(new JButtonCellEditor("  +  ",onAgregarClickListener));
    }

    public void agregar() {
        ObjectId idContacto = new ObjectId( (String) (tblContactos.getValueAt(tblContactos.getSelectedRow(), 0)));
        participantesChat.add(idContacto);
        
        array.add(tblContactos.getValueAt(tblContactos.getSelectedRow(), 1));
        modelo.removeAllElements();
        for (int i = 0; i < array.size(); i++){
            modelo.addElement(array.get(i));
        }
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        txtNombreChat = new javax.swing.JTextField();
        btnCancelar = new javax.swing.JButton();
        btnCargarImagen = new javax.swing.JButton();
        lblImagen = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblContactos = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listParticipantes = new javax.swing.JList<>();
        jLabel19 = new javax.swing.JLabel();
        btnCrearChat1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel6.add(txtNombreChat, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 120, 268, 28));

        btnCancelar.setBackground(new java.awt.Color(0, 51, 102));
        btnCancelar.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setBorder(null);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel6.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 530, 90, 40));

        btnCargarImagen.setBackground(new java.awt.Color(0, 51, 102));
        btnCargarImagen.setForeground(new java.awt.Color(255, 255, 255));
        btnCargarImagen.setText("Subir Imagen");
        btnCargarImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarImagenActionPerformed(evt);
            }
        });
        jPanel6.add(btnCargarImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 450, 120, 30));

        lblImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/JuatsConejo.png"))); // NOI18N
        jPanel6.add(lblImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, 250, 210));

        tblContactos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Id", "Contacto", "Agregar"
            }
        ));
        tblContactos.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tblContactos.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jScrollPane2.setViewportView(tblContactos);

        jPanel6.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 90, 230, 380));

        jLabel17.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 51, 102));
        jLabel17.setText("Nombre del chat");
        jPanel6.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, -1, -1));

        jLabel18.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 51, 102));
        jLabel18.setText("Agregar al chat:");
        jPanel6.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 40, -1, -1));

        jScrollPane1.setViewportView(listParticipantes);

        jPanel6.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 90, 140, 380));

        jLabel19.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 51, 102));
        jLabel19.setText("Tus contactos:");
        jPanel6.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 40, -1, -1));

        btnCrearChat1.setBackground(new java.awt.Color(0, 51, 102));
        btnCrearChat1.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnCrearChat1.setText("Crear chat");
        btnCrearChat1.setBorder(null);
        btnCrearChat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearChat1ActionPerformed(evt);
            }
        });
        jPanel6.add(btnCrearChat1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 530, 90, 40));

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

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

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

    private void btnCrearChat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearChat1ActionPerformed

        ChatDTO chatDTO = new ChatDTO();
        chatDTO.setNombre(txtNombreChat.getText());
        byte[] imagenChat = iconToByteArray(lblImagen.getIcon());
        chatDTO.setImagen(imagenChat);
        chatDTO.setIdParticipantes(participantesChat);
        
        try {
            chatBO.agregar(chatDTO);
            JOptionPane.showMessageDialog(this, "Grupo creado");
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo crear el grupo");
        }
    }//GEN-LAST:event_btnCrearChat1ActionPerformed
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCargarImagen;
    private javax.swing.JButton btnCrearChat1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JList<String> listParticipantes;
    private javax.swing.JTable tblContactos;
    private javax.swing.JTextField txtNombreChat;
    // End of variables declaration//GEN-END:variables
}
