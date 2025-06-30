package com.Rodolfo.RRamirezProgramacionNCapasMaven.DAO;

import com.Rodolfo.RRamirezProgramacionNCapasMaven.JPA.Colonia;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.JPA.Direccion;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.JPA.Rol;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Result;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.JPA.Usuario;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.UsuarioDireccion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioJPADAOImplementation implements IUsuarioJPADAO {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    @Override
    public Result Add(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();

        try {
            Usuario usuarioJPA = new Usuario();
            usuarioJPA.setUsername(usuarioDireccion.usuarioD.getUsername());
            usuarioJPA.setNombre(usuarioDireccion.usuarioD.getNombre());
            usuarioJPA.setApellidoParteno(usuarioDireccion.usuarioD.getApellidoParteno());
            usuarioJPA.setApellidoMaterno(usuarioDireccion.usuarioD.getApellidoMaterno());
            usuarioJPA.setFechaDeNacimiento(usuarioDireccion.usuarioD.getFechaDeNacimiento());
            usuarioJPA.setNumeroDeTelefono(usuarioDireccion.usuarioD.getNumeroDeTelefono());
            usuarioJPA.setEmail(usuarioDireccion.usuarioD.getEmail());
            usuarioJPA.setPassword(usuarioDireccion.usuarioD.getPassword());
            usuarioJPA.setSexo(usuarioDireccion.usuarioD.getSexo());
            usuarioJPA.setCelular(usuarioDireccion.usuarioD.getCelular());
            usuarioJPA.setCURP(usuarioDireccion.usuarioD.getCURP());
            usuarioJPA.setIsActivo(usuarioDireccion.usuarioD.getIsActivo());
            usuarioJPA.setImagen(usuarioDireccion.usuarioD.getImagen());

            usuarioJPA.Rol = new Rol();
            usuarioJPA.Rol.setIdRol(usuarioDireccion.usuarioD.Rol.getIdRol());

            entityManager.persist(usuarioJPA);
            entityManager.flush();

            Direccion direccion = new Direccion();

            direccion.setCalle(usuarioDireccion.direccionU.getCalle());
            direccion.setNumeroInterior(usuarioDireccion.direccionU.getNumeroInterior());
            direccion.setNumeroExterior(usuarioDireccion.direccionU.getNumeroExterior());

            direccion.colonia = new Colonia();
            direccion.colonia.setIdColonia(usuarioDireccion.direccionU.colonia.getIdColonia());

            direccion.setUsuario(usuarioJPA);

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
    public Result Delete(int IdUsuario) {
        Result result = new Result();

        try {
            Usuario usuarioJPA = entityManager.find(Usuario.class, IdUsuario);
            if (usuarioJPA != null) {
                entityManager.remove(usuarioJPA);
                result.correct = true;
            } else {
                result.correct = false;
                result.errorMessage = "Usuario no encontrado";
            }
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

            Usuario usuarioJPA = entityManager.find(Usuario.class, usuarioDireccion.usuarioD.getIdUsuario());
            if (usuarioJPA != null) {

                usuarioJPA.setUsername(usuarioDireccion.usuarioD.getUsername());
                usuarioJPA.setNombre(usuarioDireccion.usuarioD.getNombre());
                usuarioJPA.setApellidoParteno(usuarioDireccion.usuarioD.getApellidoParteno());
                usuarioJPA.setApellidoMaterno(usuarioDireccion.usuarioD.getApellidoMaterno());
                usuarioJPA.setFechaDeNacimiento(usuarioDireccion.usuarioD.getFechaDeNacimiento());
                usuarioJPA.setNumeroDeTelefono(usuarioDireccion.usuarioD.getNumeroDeTelefono());
                usuarioJPA.setEmail(usuarioDireccion.usuarioD.getEmail());
                usuarioJPA.setPassword(usuarioDireccion.usuarioD.getPassword());
                usuarioJPA.setSexo(usuarioDireccion.usuarioD.getSexo());
                usuarioJPA.setCelular(usuarioDireccion.usuarioD.getCelular());
                usuarioJPA.setCURP(usuarioDireccion.usuarioD.getCURP());
                usuarioJPA.setIsActivo(usuarioDireccion.usuarioD.getIsActivo());
                usuarioJPA.setImagen(usuarioDireccion.usuarioD.getImagen());

                usuarioJPA.Rol = new Rol();
                usuarioJPA.Rol.setIdRol(usuarioDireccion.usuarioD.Rol.getIdRol());
                entityManager.merge(usuarioJPA);
                result.correct = true;
            } else {
                result.correct = false;
                result.errorMessage = "Usuario no encontrado";
            }

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result GetAll() {
        Result result = new Result();
        try {
            result.objects = new ArrayList<>();

            TypedQuery<Usuario> usuariosQuery = entityManager.createQuery("FROM Usuario ORDER BY IdUsuario ASC", Usuario.class);
            List<Usuario> usuarios = usuariosQuery.getResultList();

            for (Usuario usuarioJPA : usuarios) {
                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.usuarioD = mapearUsuarioJPA(usuarioJPA);

                TypedQuery<Direccion> direccionesQuery = entityManager.createQuery("FROM Direccion d WHERE d.usuario.IdUsuario = :idusuario", Direccion.class);
                direccionesQuery.setParameter("idusuario", usuarioJPA.getIdUsuario());

                List<Direccion> direccionesJPA = direccionesQuery.getResultList();

                if (!direccionesJPA.isEmpty()) {
                    usuarioDireccion.Direcciones = new ArrayList<>();
                    for (Direccion direccionJPA : direccionesJPA) {
                        usuarioDireccion.Direcciones.add(mapearDireccionJPA(direccionJPA));
                    }
                }

                result.objects.add(usuarioDireccion);
            }

            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
        }
        return result;
    }

    @Override
    public Result Details(int idUsuario) {
        Result result = new Result();

        try {
            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();

            TypedQuery<Usuario> usuarioGetByIdQuery = entityManager.createQuery(
                    "FROM Usuario WHERE IdUsuario = :idUsuario", Usuario.class);
            usuarioGetByIdQuery.setParameter("idUsuario", idUsuario);

            Usuario usuarioJPA = usuarioGetByIdQuery.getSingleResult();

            if (usuarioJPA != null) {

                com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Usuario usuarioML = mapearUsuarioJPA(usuarioJPA);
                usuarioDireccion.usuarioD = usuarioML;

                TypedQuery<Direccion> direccionesQuery = entityManager.createQuery(
                        "FROM Direccion WHERE usuario.IdUsuario = :idusuario", Direccion.class);
                direccionesQuery.setParameter("idusuario", idUsuario);
                List<Direccion> direcciones = direccionesQuery.getResultList();

                if (!direcciones.isEmpty()) {
//                    List<com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.UsuarioDireccion> direccionesML = new ArrayList<>();
                    usuarioDireccion.Direcciones = new ArrayList<>();

                    for (Direccion direccion : direcciones) {
                        com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Direccion direccionUsuario = mapearDireccionJPA(direccion);
//                        direccionesML.add(direccionUsuario);

                        usuarioDireccion.Direcciones.add(direccionUsuario);
                    }

                }

                result.object = usuarioDireccion;
                result.correct = true;

            } else {
                result.correct = false;
                result.errorMessage = "Usuario no encontrado";
            }

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Transactional
    @Override
    public Result IsActivo(int idUsuario, int status) {
        Result result = new Result();
        try {
            Usuario usuarioJPA = new Usuario();
            usuarioJPA.setIdUsuario(idUsuario);
            usuarioJPA.setIsActivo(status);
            entityManager.persist(usuarioJPA);
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Override
    public Result GetById(int idUsuario) {
        Result result = new Result();
        try {
            TypedQuery<Usuario> usuarioQuery = entityManager.createQuery("From Usuario WHERE IdUsuario = :idusuario", Usuario.class);
            usuarioQuery.setParameter("idusuario", idUsuario);
            Usuario usuarioJPA = usuarioQuery.getSingleResult();
            if (usuarioJPA != null) {
                com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Usuario usuario = new com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Usuario();
                usuario = mapearUsuarioJPA(usuarioJPA);
                result.object = usuario;
                result.correct = true;
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Transactional
    @Override
    public Result Add(List<UsuarioDireccion> usuarioDireccionList) {
        Result result = new Result();

        try {
            for (UsuarioDireccion usuarioDireccionML : usuarioDireccionList) {

                Usuario usuarioJPA = mapearUsuario(usuarioDireccionML.usuarioD);
                entityManager.persist(usuarioJPA);

                if (usuarioDireccionML.direccionU != null) {
                    for (com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Direccion direccionML : usuarioDireccionML.Direcciones) {
                        Direccion direccionJPA = mapearDireccion(direccionML);

                        direccionJPA.setUsuario(usuarioJPA);

                        entityManager.persist(direccionJPA);
                    }
                }
            }

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    private com.Rodolfo.RRamirezProgramacionNCapasMaven.JPA.Usuario mapearUsuario(com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Usuario usuarioML) {
        com.Rodolfo.RRamirezProgramacionNCapasMaven.JPA.Usuario usuario = new com.Rodolfo.RRamirezProgramacionNCapasMaven.JPA.Usuario();

        usuario.setIdUsuario(usuarioML.getIdUsuario());
        usuario.setUsername(usuarioML.getUsername());
        usuario.setNombre(usuarioML.getNombre());
        usuario.setApellidoParteno(usuarioML.getApellidoParteno());
        usuario.setApellidoMaterno(usuarioML.getApellidoMaterno());
        usuario.setEmail(usuarioML.getEmail());
        usuario.setPassword(usuarioML.getPassword());
        usuario.setCelular(usuarioML.getCelular());
        usuario.setNumeroDeTelefono(usuarioML.getNumeroDeTelefono());
        usuario.setFechaDeNacimiento(usuarioML.getFechaDeNacimiento());
        usuario.setSexo(usuarioML.getSexo());
        usuario.setCURP(usuarioML.getCURP());
        usuario.setIsActivo(usuarioML.getIsActivo());
        usuario.setImagen(usuarioML.getImagen());

        usuario.Rol = new com.Rodolfo.RRamirezProgramacionNCapasMaven.JPA.Rol();
        if (usuarioML.getRol() != null) {
            usuario.Rol.setIdRol(usuarioML.getRol().getIdRol());
            usuario.Rol.setNombre(usuarioML.getRol().getNombre());
        }

        return usuario;
    }

    private com.Rodolfo.RRamirezProgramacionNCapasMaven.JPA.Direccion mapearDireccion(com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Direccion direccionML) {
        com.Rodolfo.RRamirezProgramacionNCapasMaven.JPA.Direccion direccion = new com.Rodolfo.RRamirezProgramacionNCapasMaven.JPA.Direccion();

        direccion.setCalle(direccionML.getCalle());
        direccion.setNumeroInterior(direccionML.getNumeroInterior());
        direccion.setNumeroExterior(direccionML.getNumeroExterior());

        if (direccionML.getColonia() != null) {
            direccion.colonia = new com.Rodolfo.RRamirezProgramacionNCapasMaven.JPA.Colonia();
            direccion.colonia.setIdColonia(direccionML.getColonia().getIdColonia());
            direccion.colonia.setNombreColonia(direccionML.getColonia().getNombreColonia());
            direccion.colonia.setCodigoPostal(direccionML.getColonia().getCodigoPostal());

            if (direccionML.getColonia().getMunicipio() != null) {
                direccion.colonia.municipio = new com.Rodolfo.RRamirezProgramacionNCapasMaven.JPA.Municipio();
                direccion.colonia.municipio.setIdMunicipio(direccionML.getColonia().getMunicipio().getIdMunicipio());
                direccion.colonia.municipio.setNombreMunicipio(direccionML.getColonia().getMunicipio().getNombreMunicipio());

                if (direccionML.getColonia().getMunicipio().getEstado() != null) {
                    direccion.colonia.municipio.estado = new com.Rodolfo.RRamirezProgramacionNCapasMaven.JPA.Estado();
                    direccion.colonia.municipio.estado.setIdEstado(direccionML.getColonia().getMunicipio().getEstado().getIdEstado());
                    direccion.colonia.municipio.estado.setNombreEstado(direccionML.getColonia().getMunicipio().getEstado().getNombreEstado());

                    if (direccionML.getColonia().getMunicipio().getEstado().getPais() != null) {
                        direccion.colonia.municipio.estado.pais = new com.Rodolfo.RRamirezProgramacionNCapasMaven.JPA.Pais();
                        direccion.colonia.municipio.estado.pais.setIdPais(
                                direccionML.getColonia().getMunicipio().getEstado().getPais().getIdPais());
                        direccion.colonia.municipio.estado.pais.setNombrePais(direccionML.getColonia().getMunicipio().getEstado().getPais().getNombrePais());
                    }
                }
            }
        }

        return direccion;
    }

    private com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Usuario mapearUsuarioJPA(com.Rodolfo.RRamirezProgramacionNCapasMaven.JPA.Usuario usuarioJPA) {
        com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Usuario usuario = new com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Usuario();

        usuario.setIdUsuario(usuarioJPA.getIdUsuario());
        usuario.setUsername(usuarioJPA.getUsername());
        usuario.setNombre(usuarioJPA.getNombre());
        usuario.setApellidoParteno(usuarioJPA.getApellidoParteno());
        usuario.setApellidoMaterno(usuarioJPA.getApellidoMaterno());
        usuario.setEmail(usuarioJPA.getEmail());
        usuario.setPassword(usuarioJPA.getPassword());
        usuario.setCelular(usuarioJPA.getCelular());
        usuario.setNumeroDeTelefono(usuarioJPA.getNumeroDeTelefono());
        usuario.setFechaDeNacimiento(usuarioJPA.getFechaDeNacimiento());
        usuario.setSexo(usuarioJPA.getSexo());
        usuario.setCURP(usuarioJPA.getCURP());
        usuario.setIsActivo(usuarioJPA.getIsActivo());
        usuario.setImagen(usuarioJPA.getImagen());

        usuario.Rol = new com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Rol();
        if (usuarioJPA.getRol() != null) {
            usuario.Rol.setIdRol(usuarioJPA.getRol().getIdRol());
            usuario.Rol.setNombre(usuarioJPA.getRol().getNombre());
        }

        return usuario;
    }

    private com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Direccion mapearDireccionJPA(com.Rodolfo.RRamirezProgramacionNCapasMaven.JPA.Direccion direccionJPA) {
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
    public Result DinamicSearch(com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Usuario usuario) {
        Result result = new Result();
        result.objects = new ArrayList<>();

        try {
            StringBuilder jpql = new StringBuilder("FROM Usuario u WHERE 1=1");

            if (usuario.getNombre() != null && !usuario.getNombre().isEmpty()) {
                jpql.append(" AND LOWER(u.Nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))");
            }

            if (usuario.getApellidoParteno()!= null && !usuario.getApellidoParteno().isEmpty()) {
                jpql.append(" AND LOWER(u.ApellidoPaterno) LIKE LOWER(CONCAT('%', :apellidoPaterno, '%'))");
            }

            if (usuario.getApellidoMaterno() != null && !usuario.getApellidoMaterno().isEmpty()) {
                jpql.append(" AND LOWER(u.ApellidoMaterno) LIKE LOWER(CONCAT('%', :apellidoMaterno, '%'))");
            }

            jpql.append(" AND u.IsActivo = :isActivo");

            if (usuario.getRol() != null && usuario.getRol().getIdRol() != 0) {
                jpql.append(" AND u.Rol.IdRol = :idRol");
            }

            TypedQuery<Usuario> query = entityManager.createQuery(jpql.toString(), Usuario.class);

            if (usuario.getNombre() != null && !usuario.getNombre().isEmpty()) {
                query.setParameter("nombre", usuario.getNombre());
            }

            if (usuario.getApellidoParteno()!= null && !usuario.getApellidoParteno().isEmpty()) {
                query.setParameter("apellidoPaterno", usuario.getApellidoParteno());
            }

            if (usuario.getApellidoMaterno() != null && !usuario.getApellidoMaterno().isEmpty()) {
                query.setParameter("apellidoMaterno", usuario.getApellidoMaterno());
            }

            query.setParameter("isActivo", usuario.getIsActivo());

            if (usuario.getRol() != null && usuario.getRol().getIdRol() != 0) {
                query.setParameter("idRol", usuario.getRol().getIdRol());
            }

            List<Usuario> usuarios = query.getResultList();

            for (Usuario usuarioJPA : usuarios) {
                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.usuarioD = mapearUsuarioJPA(usuarioJPA);

                TypedQuery<Direccion> direccionesQuery = entityManager.createQuery(
                        "FROM Direccion d WHERE d.usuario.IdUsuario = :idusuario", Direccion.class);
                direccionesQuery.setParameter("idusuario", usuarioJPA.getIdUsuario());

                List<Direccion> direccionesJPA = direccionesQuery.getResultList();

                if (!direccionesJPA.isEmpty()) {
                    usuarioDireccion.Direcciones = new ArrayList<>();
                    for (Direccion direccionJPA : direccionesJPA) {
                        usuarioDireccion.Direcciones.add(mapearDireccionJPA(direccionJPA));
                    }
                }

                result.objects.add(usuarioDireccion);
            }

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
        }

        return result;
    }

}
