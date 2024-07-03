/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package presentacion;

import DTOs.UsuarioDTO;
import excepciones.NegocioException;
import interfaces.IUsuarioBO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import negocio.UsuarioBO;
import org.bson.types.ObjectId;
import utilerias.ImageRenderer;
import utilerias.JButtonCellEditor;
import utilerias.JButtonRenderer;

/**
 *
 * @author crazy
 */
public class DlgTelefonos extends javax.swing.JDialog {
    
    private IUsuarioBO usuarioBO;
    private ObjectId idUsuarioLogeado;
    
    
    public DlgTelefonos(java.awt.Frame parent, boolean modal, ObjectId idUsuarioLogeado) {
        super(parent, modal);
        initComponents();
        usuarioBO = new UsuarioBO();
        this.idUsuarioLogeado = idUsuarioLogeado;
        
        cargarMetodosIniciales();
    }
    
    // metodo para convertir los bytes de la imagen guardada en la base de datos a icono
    public static ImageIcon byteArrayToIcon(byte[] imageData) {
        if (imageData != null) {
            try {
                ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
                BufferedImage bufferedImage = ImageIO.read(bais);
                return new ImageIcon(bufferedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTelefonos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblTelefonos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Foto", "Telefono", "Id", "Agregar a contactos"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTelefonos.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblTelefonos);
        if (tblTelefonos.getColumnModel().getColumnCount() > 0) {
            tblTelefonos.getColumnModel().getColumn(0).setResizable(false);
            tblTelefonos.getColumnModel().getColumn(1).setResizable(false);
            tblTelefonos.getColumnModel().getColumn(2).setResizable(false);
            tblTelefonos.getColumnModel().getColumn(3).setResizable(false);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 680, 540));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void llenarTablaTelefonos(List<UsuarioDTO> listaUsuarios) {
        DefaultTableModel  modeloTabla = (DefaultTableModel)  this.tblTelefonos.getModel();
        if (modeloTabla.getRowCount() > 0) {
            for (int i = modeloTabla.getRowCount() - 1; i > -1; i--) {
                modeloTabla.removeRow(i);
            }
        }
        if (listaUsuarios != null) {
            listaUsuarios.forEach(row -> {
                Object[] fila = new Object[4];
                Icon icono = byteArrayToIcon(row.getImagen());
                fila[0] = icono;
                fila[1] = row.getTelefono();
                fila[2] = row.getId();
                modeloTabla.addRow(fila);
            });
        }
    }
     
    private void cargarTelefonosEnTabla() {
        try {
            List<UsuarioDTO> listaTelefonos = usuarioBO.consultarTelefonosQueNoTieneEnContactos(idUsuarioLogeado);
            llenarTablaTelefonos(listaTelefonos);
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }
    
    protected void cargarMetodosIniciales(){
        this.cargarConfiguracionInicialTablaTelefonos();
        this.cargarTelefonosEnTabla();
    }
    
    private void cargarConfiguracionInicialTablaTelefonos() {
        ActionListener onAgregarContactoClickListener = (ActionEvent e) -> {
            agregarContacto();
        };
        TableColumn columnaImagen = tblTelefonos.getColumnModel().getColumn(0);
        columnaImagen.setCellRenderer(new ImageRenderer());       
        int indiceAgregarContacto = 3;
        TableColumnModel modeloColumnas = this.tblTelefonos.getColumnModel();
        modeloColumnas.getColumn(indiceAgregarContacto).setCellRenderer(new JButtonRenderer("Agregar a contactos"));
        modeloColumnas.getColumn(indiceAgregarContacto).setCellEditor(new JButtonCellEditor("Agregar a contactos",onAgregarContactoClickListener));
        
    }

    public void agregarContacto() {
        ObjectId idContacto = new ObjectId((String) (tblTelefonos.getValueAt(tblTelefonos.getSelectedRow(), 2)));

        try {
            UsuarioDTO usuario = usuarioBO.consultarUsuario(idContacto);
            int opcion = JOptionPane.showConfirmDialog(this, "¿Seguro que quiere agregar como contacto al usuario " + usuario.getUsuario() + " con teléfono " + usuario.getTelefono() + "?", "Confirmación", JOptionPane.YES_NO_OPTION);

            if (opcion == JOptionPane.YES_OPTION) {
                try {
                    usuarioBO.agregarContacto(idUsuarioLogeado, idContacto);
                    JOptionPane.showMessageDialog(this, "Se ha agregado correctamente el contacto");
                    this.cargarTelefonosEnTabla();
                } catch (NegocioException ex) {
                    JOptionPane.showMessageDialog(this, "No se pudo agregar el contacto");
                }
            } else {
                JOptionPane.showMessageDialog(this, "El contacto no fue agregado.");
            }
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo consultar el usuario");
        }
    }
    
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblTelefonos;
    // End of variables declaration//GEN-END:variables



}
