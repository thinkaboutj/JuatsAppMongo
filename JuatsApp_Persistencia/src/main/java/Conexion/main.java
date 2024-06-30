/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import dao.UsuarioDAO;
import entidades.Domicilio;
import entidades.Usuario;
import excepciones.PersistenciaException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author crazy
 */
public class main {
    
    public static void main(String[] args){
        UsuarioDAO dao = new UsuarioDAO();
        
        Usuario usuario = new Usuario();
        usuario.setContrasena("contra123");
        usuario.setSexo("Hombre");
        usuario.setTelefono("+52 4234234");
        usuario.setUsuario("DavidElier1234");    
        usuario.setFechaNacimiento(LocalDate.now());
        
        Domicilio domicilio = new Domicilio();
        domicilio.setCalle("la 200");
        domicilio.setCodigoPostal("85136");
        domicilio.setColonia("montecarlo");
        domicilio.setNumero("4320");
        
        usuario.setDomicilio(domicilio);
        
        try {
            dao.agregar(usuario);
        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
    }
    
}
