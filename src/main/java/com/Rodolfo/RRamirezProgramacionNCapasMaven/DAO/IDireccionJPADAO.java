package com.Rodolfo.RRamirezProgramacionNCapasMaven.DAO;

import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Result;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.UsuarioDireccion;

public interface IDireccionJPADAO {
    Result Add(UsuarioDireccion usuarioDireccion);
    Result Update(UsuarioDireccion usuarioDireccion);
    Result Delete(int idDireccion);
    Result GetById(int idDireccion);
    Result GetColoniaByCP(String codigoPostal);
}
