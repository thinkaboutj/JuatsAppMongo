package DTOs;

/**
 * Clase que representa un domicilioDTO en la aplicación. Un domicilio tiene una
 * dirección compuesta por calle, número, colonia y código postal. También tiene
 * un identificador único.
 *
 * @author Jesus Medina (╹ڡ╹ ) ID:00000247527
 */
public class DomicilioDTO {

    private String calle;
    private String numero;
    private String colonia;
    private String codigoPostal;

    public DomicilioDTO() {
    }

    public DomicilioDTO(String calle, String numero, String colonia, String codigoPostal) {
        this.calle = calle;
        this.numero = numero;
        this.colonia = colonia;
        this.codigoPostal = codigoPostal;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    @Override
    public String toString() {
        return "DomicilioDTO{" + "calle=" + calle + ", numero=" + numero + ", colonia=" + colonia + ", codigoPostal=" + codigoPostal + '}';
    }
    
    

}
