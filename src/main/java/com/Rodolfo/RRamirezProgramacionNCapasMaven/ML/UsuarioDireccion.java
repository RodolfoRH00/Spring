package com.Rodolfo.RRamirezProgramacionNCapasMaven.ML;

import com.Rodolfo.RRamirezProgramacionNCapasMaven.JPA.*;
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
