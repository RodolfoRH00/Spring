package com.Rodolfo.RRamirezProgramacionNCapasMaven.DAO;

import com.Rodolfo.RRamirezProgramacionNCapasMaven.JPA.Rol;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RolJPADAOImplementation implements IRoldJPADAO {

    @Autowired
    EntityManager entityManager;

    @Override
    public Result GetAll() {
        Result result = new Result();

        try {

            TypedQuery rolesQuery = entityManager.createQuery("FROM Rol ORDER BY IdRol ASC", Rol.class);
            List<Rol> roles = rolesQuery.getResultList();

            if (!roles.isEmpty()) {
                result.objects = new ArrayList<>();
                for (Rol rolJPA : roles) {
                    com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Rol rol = new com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Rol();
                    rol.setIdRol(rolJPA.getIdRol());
                    rol.setNombre(rolJPA.getNombre());
                    result.objects.add(rol);
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
