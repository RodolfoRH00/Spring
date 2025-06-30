package com.Rodolfo.RRamirezProgramacionNCapasMaven.DAO;

import com.Rodolfo.RRamirezProgramacionNCapasMaven.JPA.Estado;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EstadoJPADAOImplementation implements IEstadoJPADAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetByIdPais(int idPais) {
        Result result = new Result();
        try {
            TypedQuery<Estado> estadosQuery = entityManager.createQuery("FROM Estado WHERE pais.IdPais = :idpais", Estado.class);
            estadosQuery.setParameter("idpais", idPais);

            List<Estado> estados = estadosQuery.getResultList();
            result.objects = new ArrayList<>();
            if (!estados.isEmpty() && estados != null) {
                for (Estado estadoJPA : estados) {
                    com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Estado estado = new com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Estado();
                    estado.setIdEstado(estadoJPA.getIdEstado());
                    estado.setNombreEstado(estadoJPA.getNombreEstado());
                    result.objects.add(estado);
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
