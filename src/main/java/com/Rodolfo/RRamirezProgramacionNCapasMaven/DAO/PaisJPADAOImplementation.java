package com.Rodolfo.RRamirezProgramacionNCapasMaven.DAO;

import com.Rodolfo.RRamirezProgramacionNCapasMaven.JPA.Pais;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PaisJPADAOImplementation implements IPaisJPADAO{

    @Autowired
    private EntityManager entityManager;
    
    @Override
    public Result GetAll() {
        Result result = new Result();
        try {
            TypedQuery paisQuery = entityManager.createQuery("FROM Pais ORDER BY IdPais ASC", Pais.class);
            List<Pais> paises = paisQuery.getResultList();
            if (!paises.isEmpty()) {
                result.objects = new ArrayList<>();
                for (Pais paisJPA : paises) {
                    com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Pais pais = new com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Pais();
                    pais.setIdPais(paisJPA.getIdPais());
                    pais.setNombrePais(paisJPA.getNombrePais());
                    result.objects.add(pais);
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
