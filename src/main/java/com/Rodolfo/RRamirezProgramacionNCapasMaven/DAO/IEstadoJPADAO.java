package com.Rodolfo.RRamirezProgramacionNCapasMaven.DAO;

import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Result;

public interface IEstadoJPADAO {
    Result GetByIdPais(int idPais);
}
