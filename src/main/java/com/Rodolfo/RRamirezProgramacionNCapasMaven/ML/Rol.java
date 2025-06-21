package com.Rodolfo.RRamirezProgramacionNCapasMaven.ML;

/**
 *
 * @author digis
 */
public class Rol {

    private int IdRol;
    private String Nombre;
    private String Activo;
    private String FechaDeCreacion;

    public int getIdRol() {
        return IdRol;
    }

    public void setIdRol(int IdRol) {
        this.IdRol = IdRol;
    }
    
    public String getNombre(){
        return Nombre;
    }
    
    public void setNombre(String Nombre){
        this.Nombre = Nombre;
    }
    
    public String getActivo(){
        return Activo;
    }
    
    public void setActivo(String Activo){
        this.Activo = Activo;
    }
    
    public String getFechaCreacion(){
        return FechaDeCreacion;
    }
    
    public void setFechaCreacion(String FechaCreacion){
        this.FechaDeCreacion = FechaCreacion;
    }
}
