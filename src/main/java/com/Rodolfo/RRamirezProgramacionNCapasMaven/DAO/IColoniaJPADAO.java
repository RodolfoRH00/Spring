package com.Rodolfo.RRamirezProgramacionNCapasMaven.DAO;

import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Result;

public interface IColoniaJPADAO {
    Result GetByIdMunicipio(int idMunicipio);
}
