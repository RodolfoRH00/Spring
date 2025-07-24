package com.Rodolfo.RRamirezProgramacionNCapasMaven.DAO;

import com.Rodolfo.RRamirezProgramacionNCapasMaven.JPA.Colonia;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.JPA.Direccion;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.JPA.Usuario;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Result;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.UsuarioDireccion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DireccionJPADAOImplementation implements IDireccionJPADAO {

    @Autowired
    EntityManager entityManager;

    @Transactional
    @Override
    public Result Add(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();

        try {
            Direccion direccion = new Direccion();
            direccion.setCalle(usuarioDireccion.direccionU.getCalle());
            direccion.setNumeroInterior(usuarioDireccion.direccionU.getNumeroInterior());
            direccion.setNumeroExterior(usuarioDireccion.direccionU.getNumeroExterior());
            direccion.colonia = new Colonia();
            direccion.colonia.setIdColonia(usuarioDireccion.direccionU.colonia.getIdColonia());
            direccion.usuario = new Usuario();
            direccion.usuario.setIdUsuario(usuarioDireccion.usuarioD.getIdUsuario());

            entityManager.persist(direccion);

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Transactional
    @Override
    public Result Update(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();

        try {
            Direccion direccion = new Direccion();
            direccion.setCalle(usuarioDireccion.direccionU.getCalle());
            direccion.setNumeroInterior(usuarioDireccion.direccionU.getNumeroInterior());
            direccion.setNumeroExterior(usuarioDireccion.direccionU.getNumeroExterior());
            direccion.colonia = new Colonia();
            direccion.colonia.setIdColonia(usuarioDireccion.direccionU.colonia.getIdColonia());
            direccion.usuario = new Usuario();
            direccion.usuario.setIdUsuario(usuarioDireccion.usuarioD.getIdUsuario());
            direccion.setIdDireccion(usuarioDireccion.direccionU.getIdDireccion());

            entityManager.merge(direccion);

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Transactional
    @Override
    public Result Delete(int idDireccion) {
        Result result = new Result();
        try {
            Direccion direccionJPA = entityManager.find(Direccion.class, idDireccion);
            if (direccionJPA != null) {
                entityManager.remove(direccionJPA);
                result.correct = true;
            } else {
                result.correct = false;
            }
        } catch (Exception ex) {
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Override
    public Result GetById(int idDireccion) {
        Result result = new Result();
        try {
            TypedQuery<Direccion> direccionQuery = entityManager.createQuery("FROM Direccion WHERE IdDireccion = :iddireccion", Direccion.class);
            direccionQuery.setParameter("iddireccion", idDireccion);
            Direccion direccionJPA = direccionQuery.getSingleResult();
            if (direccionJPA != null) {
                com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Direccion direccion = new com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Direccion();
                direccion = mapearDireccion(direccionJPA);
                result.object = direccion;
                result.correct = true;
            }

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    private com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Direccion mapearDireccion(Direccion direccionJPA) {
        com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Direccion direccion = new com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Direccion();

        direccion.setIdDireccion(direccionJPA.getIdDireccion());
        direccion.setCalle(direccionJPA.getCalle());
        direccion.setNumeroInterior(direccionJPA.getNumeroInterior());
        direccion.setNumeroExterior(direccionJPA.getNumeroExterior());

        if (direccionJPA.getColonia() != null) {
            direccion.colonia = new com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Colonia();
            direccion.colonia.setIdColonia(direccionJPA.getColonia().getIdColonia());
            direccion.colonia.setNombreColonia(direccionJPA.getColonia().getNombreColonia());
            direccion.colonia.setCodigoPostal(direccionJPA.getColonia().getCodigoPostal());

            if (direccionJPA.getColonia().getMunicipio() != null) {
                direccion.colonia.municipio = new com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Municipio();
                direccion.colonia.municipio.setIdMunicipio(direccionJPA.getColonia().getMunicipio().getIdMunicipio());
                direccion.colonia.municipio.setNombreMunicipio(direccionJPA.getColonia().getMunicipio().getNombreMunicipio());

                if (direccionJPA.getColonia().getMunicipio().getEstado() != null) {
                    direccion.colonia.municipio.estado = new com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Estado();
                    direccion.colonia.municipio.estado.setIdEstado(direccionJPA.getColonia().getMunicipio().getEstado().getIdEstado());
                    direccion.colonia.municipio.estado.setNombreEstado(direccionJPA.getColonia().getMunicipio().getEstado().getNombreEstado());

                    if (direccionJPA.getColonia().getMunicipio().getEstado().getPais() != null) {
                        direccion.colonia.municipio.estado.pais = new com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Pais();
                        direccion.colonia.municipio.estado.pais.setIdPais(
                                direccionJPA.getColonia().getMunicipio().getEstado().getPais().getIdPais());
                        direccion.colonia.municipio.estado.pais.setNombrePais(direccionJPA.getColonia().getMunicipio().getEstado().getPais().getNombrePais());
                    }
                }
            }
        }

        return direccion;
    }
    
    @Override
    public Result GetColoniaByCP(String codigoPostal){
        Result result = new Result();
        try {
            TypedQuery<Direccion> direccionQuery = entityManager.createQuery("FROM Direccion WHERE IdColonia = :idcolonia", Direccion.class);
            List<Direccion> direcciones = direccionQuery.getResultList();
            if (direcciones != null) {
                for (Direccion direccion : direcciones) {
                    com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Colonia colonia = new com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Colonia();
                    colonia.setIdColonia(direccion.colonia.getIdColonia());
                    colonia.setNombreColonia(direccion.colonia.getNombreColonia());
                    colonia.setCodigoPostal(direccion.colonia.getCodigoPostal());
                }
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

}
