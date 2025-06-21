package com.Rodolfo.RRamirezProgramacionNCapasMaven.ML;

/**
 *
 * @author digis
 */
public class Municipio {
    
    private int IdMunicipio;
    private String NombreMunicipio;
    public Estado estado;

    public int getIdMunicipio() {
        return IdMunicipio;
    }

    public void setIdMunicipio(int IdMunicipio) {
        this.IdMunicipio = IdMunicipio;
    }

    public String getNombreMunicipio() {
        return NombreMunicipio;
    }

    public void setNombreMunicipio(String NombreMunicipio) {
        this.NombreMunicipio = NombreMunicipio;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
}
