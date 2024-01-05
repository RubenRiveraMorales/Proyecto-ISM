package modelo.pojo;

public class PromoSucursal {
    
    private Integer idPromocion;
    private Integer idSucursal;
    private String nombreSucursal;
    private String telefono;
    private String codigoPostal;
    private String direccion;
    private String ciudad;
    private String colonia;
    private Integer idEmpresa;

    public PromoSucursal() {
    }

    public PromoSucursal(Integer idPromocion, Integer idSucursal, String nombreSucursal, String telefono, String codigoPostal, String direccion, String ciudad, String colonia, Integer idEmpresa) {
        this.idPromocion = idPromocion;
        this.idSucursal = idSucursal;
        this.nombreSucursal = nombreSucursal;
        this.telefono = telefono;
        this.codigoPostal = codigoPostal;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.colonia = colonia;
        this.idEmpresa = idEmpresa;
    }

    public Integer getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(Integer idPromocion) {
        this.idPromocion = idPromocion;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
}
