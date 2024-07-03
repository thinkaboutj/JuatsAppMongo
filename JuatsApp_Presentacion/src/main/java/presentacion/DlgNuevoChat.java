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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import negocio.ChatBO;
import negocio.UsuarioBO;
import org.bson.types.ObjectId;
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
        

    public DlgNuevoChat(java.awt.Frame parent, boolean modal, ObjectId idUsuarioLogeado) {
        super(parent, modal);
        initComponents();
        
        this.idUsuarioLogeado = idUsuarioLogeado;
        usuarioBO = new UsuarioBO();
        chatBO = new ChatBO();
        
        
        cargarMetodosIniciales();
    }
    
        // convertir el icono del label a un arreglo de bytes
    
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
            List<UsuarioDTO> listaContactos = usuarioBO.consultarContactosSinChat(idUsuarioLogeado);
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
            chatear();
        };
        int indiceAgregar = 2;
        TableColumnModel modeloColumnas = this.tblContactos.getColumnModel();
        modeloColumnas.getColumn(indiceAgregar).setCellRenderer(new JButtonRenderer("  Chatear  "));
        modeloColumnas.getColumn(indiceAgregar).setCellEditor(new JButtonCellEditor("  Chatear  ",onAgregarClickListener));
    }
    
    // para agregar el chat
    public void chatear() {
        ObjectId idContacto = new ObjectId( (String) (tblContactos.getValueAt(tblContactos.getSelectedRow(), 0)));
        List<ObjectId> listaUsuarios = new ArrayList<>();
        
        listaUsuarios.add(idUsuarioLogeado);
        listaUsuarios.add(idContacto);
        
        
        ChatDTO chat = new ChatDTO(listaUsuarios);
        
        try {
            chatBO.agregar(chat);
            JOptionPane.showMessageDialog(this, "Chat creado exitosamente");
            this.dispose();
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo crear el chat");
        }
        
        
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblContactos = new javax.swing.JTable();
        jLabel19 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        jPanel6.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 620, 290));

        jLabel19.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 51, 102));
        jLabel19.setText("Seleccione un contacto para empezar el chat");
        jPanel6.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 40, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 742, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel19;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblContactos;
    // End of variables declaration//GEN-END:variables
}
