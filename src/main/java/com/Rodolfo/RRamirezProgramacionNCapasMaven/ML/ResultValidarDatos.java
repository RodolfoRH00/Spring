package com.Rodolfo.RRamirezProgramacionNCapasMaven.ML;

import com.Rodolfo.RRamirezProgramacionNCapasMaven.JPA.*;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.*;

public class ResultValidarDatos {
    
    private int Fila;
    private String Texto;
    private String Descripcion;
    
    public ResultValidarDatos(int fila, String texto, String descripcion){
        this.Fila = fila;
        this.Texto = texto;
        this.Descripcion = descripcion;
    }

    public int getFila() {
        return Fila;
    }

    public void setFila(int Fila) {
        this.Fila = Fila;
    }

    public String getTexto() {
        return Texto;
    }

    public void setTexto(String Texto) {
        this.Texto = Texto;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    } 
}
