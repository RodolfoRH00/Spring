/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.Rodolfo.RRamirezProgramacionNCapasMaven.DAO;

import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Direccion;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Result;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.UsuarioDireccion;

/**
 *
 * @author digis
 */
public interface IDireccionDAO {
    Result GetById(int idDireccion);
    Result Add(UsuarioDireccion usuarioDireccion);
    Result Update(UsuarioDireccion usuarioDireccion);
    Result Delete(int idDireccion);
}
