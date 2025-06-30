package com.Rodolfo.RRamirezProgramacionNCapasMaven.DAO;

import com.Rodolfo.RRamirezProgramacionNCapasMaven.JPA.Colonia;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ColoniaJPADAOImplementation implements IColoniaJPADAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetByIdMunicipio(int idMunicipio) {
        Result result = new Result();
        try {
            TypedQuery coloniaQuery = entityManager.createQuery("FROM Colonia WHERE municipio.IdMunicipio = :idmunicipio", Colonia.class);
            coloniaQuery.setParameter("idmunicipio", idMunicipio);

            List<Colonia> colonias = coloniaQuery.getResultList();

            if (!colonias.isEmpty()) {
                result.objects = new ArrayList<>();
                for (Colonia coloniaJPA : colonias) {
                    com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Colonia colonia = new com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Colonia();
                    colonia.setIdColonia(coloniaJPA.getIdColonia());
                    colonia.setNombreColonia(coloniaJPA.getNombreColonia());
                    colonia.setCodigoPostal(coloniaJPA.getCodigoPostal());
                    result.objects.add(colonia);
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
