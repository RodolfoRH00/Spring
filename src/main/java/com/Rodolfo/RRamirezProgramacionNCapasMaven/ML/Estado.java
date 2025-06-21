package com.Rodolfo.RRamirezProgramacionNCapasMaven.ML;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 *
 * @author digis
 */
public class Estado {

    @JsonProperty("IdEstado")
    private int IdEstado;
    @JsonProperty("Nombre")
    private String NombreEstado;
    public Pais pais;

    public int getIdEstado() {
        return IdEstado;
    }

    public void setIdEstado(int IdEstado) {
        this.IdEstado = IdEstado;
    }

    public String getNombreEstado() {
        return NombreEstado;
    }

    public void setNombreEstado(String NombreEstado) {
        this.NombreEstado = NombreEstado;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

}
