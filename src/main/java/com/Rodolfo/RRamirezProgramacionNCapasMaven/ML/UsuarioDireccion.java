/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Rodolfo.RRamirezProgramacionNCapasMaven.ML;

import jakarta.validation.Valid;
import java.util.ArrayList;

public class UsuarioDireccion {

    @Valid
    public Usuario usuarioD;
    
    @Valid
    public Direccion direccionU; 
    public ArrayList<Direccion> Direcciones;

    public Usuario getUsuarioD() {
        return usuarioD;
    }

    public void setUsuarioD(Usuario usuarioD) {
        this.usuarioD = usuarioD;
    }

    public Direccion getDireccionU() {
        return direccionU;
    }

    public void setDireccionU(Direccion direccionU) {
        this.direccionU = direccionU;
    }

    public ArrayList<Direccion> getDirecciones() {
        return Direcciones;
    }

    public void setDirecciones(ArrayList<Direccion> Direcciones) {
        this.Direcciones = Direcciones;
    }
    
    
}
