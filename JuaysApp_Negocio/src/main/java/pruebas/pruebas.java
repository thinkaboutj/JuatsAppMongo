/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebas;

import DTOs.DomicilioDTO;
import DTOs.UsuarioDTO;
import excepciones.NegocioException;
import interfaces.IUsuarioBO;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import negocio.UsuarioBO;

/**
 *
 * @author crazy
 */
public class pruebas {
    
    public static void main (String [] args){
        IUsuarioBO usuarioBO = new UsuarioBO();
        
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        
        try {
            usuarioDTO = usuarioBO.login("xr471112", "asd");
            System.out.println(usuarioDTO.toString());
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        
        
    }
    
}
