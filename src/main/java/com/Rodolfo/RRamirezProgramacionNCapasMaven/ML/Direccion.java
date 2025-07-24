package com.Rodolfo.RRamirezProgramacionNCapasMaven.ML;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class Direccion {

    private int IdDireccion;
    
    @Pattern(regexp = "[A-Za-z0-9\\s.-]+?")
    @NotEmpty(message = "La calle no puede estar vacia")
    private String Calle;
    private String NumeroInterior;
    private String NumeroExterior;
    public Colonia colonia;
    public Usuario usuario;

    
    
    
    
//    public int getIdDireccion() {
//        return IdDireccion;
//    }
//
//    public void setIdDireccion(int IdDireccion) {
//        this.IdDireccion = IdDireccion;
//    }
//
//    public String getCalle() {
//        return Calle;
//    }
//
//    public void setCalle(String Calle) {
//        this.Calle = Calle;
//    }
//
//    public String getNumeroInterior() {
//        return NumeroInterior;
//    }
//
//    public void setNumeroInterior(String NumeroInterior) {
//        this.NumeroInterior = NumeroInterior;
//    }
//
//    public String getNumeroExterior() {
//        return NumeroExterior;
//    }
//
//    public void setNumeroExterior(String NumeroExterior) {
//        this.NumeroExterior = NumeroExterior;
//    }
//
//    public Colonia getColonia() {
//        return colonia;
//    }
//
//    public void setColonia(Colonia colonia) {
//        this.colonia = colonia;
//    }
//
//    public Usuario getUsuario() {
//        return usuario;
//    }
//
//    public void setUsuario(Usuario usuario) {
//        this.usuario = usuario;
//    }

    public int getIdDireccion() {
        return IdDireccion;
    }

    public void setIdDireccion(int IdDireccion) {
        this.IdDireccion = IdDireccion;
    }

    public String getCalle() {
        return Calle;
    }

    public void setCalle(String Calle) {
        this.Calle = Calle;
    }

    public String getNumeroInterior() {
        return NumeroInterior;
    }

    public void setNumeroInterior(String NumeroInterior) {
        this.NumeroInterior = NumeroInterior;
    }

    public String getNumeroExterior() {
        return NumeroExterior;
    }

    public void setNumeroExterior(String NumeroExterior) {
        this.NumeroExterior = NumeroExterior;
    }

    public Colonia getColonia() {
        return colonia;
    }

    public void setColonia(Colonia colonia) {
        this.colonia = colonia;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
