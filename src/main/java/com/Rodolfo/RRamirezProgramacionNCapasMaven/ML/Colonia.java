package com.Rodolfo.RRamirezProgramacionNCapasMaven.ML;

/**
 *
 * @author digis
 */
public class Colonia {

    private int IdColonia;
    private String NombreColonia;
    private String CodigoPostal;
    public Municipio municipio;

    public int getIdColonia() {
        return IdColonia;
    }

    public void setIdColonia(int IdColonia) {
        this.IdColonia = IdColonia;
    }

    public String getNombreColonia() {
        return NombreColonia;
    }

    public void setNombreColonia(String NombreColonia) {
        this.NombreColonia = NombreColonia;
    }

    public String getCodigoPostal() {
        return CodigoPostal;
    }

    public void setCodigoPostal(String CodigoPostal) {
        this.CodigoPostal = CodigoPostal;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

}
