package com.Rodolfo.RRamirezProgramacionNCapasMaven.DAO;

import com.Rodolfo.RRamirezProgramacionNCapasMaven.JPA.Municipio;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MunicipioJPADAOImplementation implements IMunicipioJPADAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetByIdEstado(int idEstado) {
        Result result = new Result();
        try {
            TypedQuery<Municipio> municipioQuery = entityManager.createQuery("FROM Municipio WHERE estado.IdEstado = :idestado", Municipio.class);
            municipioQuery.setParameter("idestado", idEstado);

            List<Municipio> municipios = municipioQuery.getResultList();
            result.objects = new ArrayList<>();

            if (!municipios.isEmpty()) {

                for (Municipio municipioJPA : municipios) {
                    com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Municipio municipio = new com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Municipio();
                    municipio.setIdMunicipio(municipioJPA.getIdMunicipio());
                    municipio.setNombreMunicipio(municipioJPA.getNombreMunicipio());
                    result.objects.add(municipio);
                }
                result.correct = true;
            }

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
}
