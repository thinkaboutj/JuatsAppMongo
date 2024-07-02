/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilerias;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * clase para poder crear un renderizador de imagenes en el jtable
 * @author crazy
 */
public class ImageRenderer extends DefaultTableCellRenderer {
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        // Asegurarse de que el valor es un Icon
        if (value instanceof Icon) {
            label.setIcon((Icon) value);
            label.setText(null); // No mostrar texto si hay un Icono
        } else {
            label.setText("Imagen no disponible");
        }
        
        label.setHorizontalAlignment(JLabel.CENTER); // Centrar el contenido en la celda
        
        return label;
    }
}
    