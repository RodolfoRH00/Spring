package com.Rodolfo.RRamirezProgramacionNCapasMaven.DAO;

import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Colonia;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Direccion;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Estado;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Municipio;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Pais;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Result;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Rol;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Usuario;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.UsuarioDireccion;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository // Clase que maneja la conexion a la base de datos
public class UsuarioDAOImplementation implements IUsuarioDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override // Sobreescritura (polimorfismo)
    public Result GetAll() {

        Result result = new Result();

        try {
            // Proceso para ejecutar el StoredProcedure que devuelve un Entero
            int procesoCorrecto = jdbcTemplate.execute("{CALL UsuarioGetAllSP(?)}", (CallableStatementCallback<Integer>) callableStatementCallback -> {

                int idUsuario = 0;

                callableStatementCallback.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatementCallback.execute();

                ResultSet resultSet = (ResultSet) callableStatementCallback.getObject(1);

                result.objects = new ArrayList<>();

                while (resultSet.next()) {

                    idUsuario = resultSet.getInt("IdUsuario");

                    if (!result.objects.isEmpty() && idUsuario == ((UsuarioDireccion) (result.objects.get(result.objects.size() - 1))).usuarioD.getIdUsuario()) {

                        Direccion direccion = new Direccion();
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                        direccion.colonia = new Colonia();
                        direccion.colonia.setNombreColonia(resultSet.getString("NombreColonia"));
                        direccion.colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                        direccion.colonia.municipio = new Municipio();
                        direccion.colonia.municipio.setNombreMunicipio(resultSet.getString("NombreMunicipio"));
                        direccion.colonia.municipio.estado = new Estado();
                        direccion.colonia.municipio.estado.setNombreEstado(resultSet.getString("NombreEstado"));
                        direccion.colonia.municipio.estado.pais = new Pais();
                        direccion.colonia.municipio.estado.pais.setNombrePais(resultSet.getString("NombrePais"));

                        ((UsuarioDireccion) (result.objects.get(result.objects.size() - 1))).Direcciones.add(direccion);
                    } else {

                        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                        usuarioDireccion.usuarioD = new Usuario();

                        usuarioDireccion.usuarioD.setIdUsuario(resultSet.getInt("IdUsuario"));
                        usuarioDireccion.usuarioD.setUsername(resultSet.getString("UserName"));
                        usuarioDireccion.usuarioD.setNombre(resultSet.getString("NombreUsuario"));
                        usuarioDireccion.usuarioD.setApellidoParteno(resultSet.getString("ApellidoPaterno"));
                        usuarioDireccion.usuarioD.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                        usuarioDireccion.usuarioD.setFechaDeNacimiento(resultSet.getDate("FechaDeNacimiento"));
                        usuarioDireccion.usuarioD.setNumeroDeTelefono(resultSet.getString("NumeroDeTelefono"));
                        usuarioDireccion.usuarioD.setEmail(resultSet.getString("Email"));
                        usuarioDireccion.usuarioD.setPassword(resultSet.getString("Password"));
                        usuarioDireccion.usuarioD.setSexo(resultSet.getString("Sexo").charAt(0));
                        usuarioDireccion.usuarioD.setCelular(resultSet.getString("Celular"));
                        usuarioDireccion.usuarioD.setCURP(resultSet.getString("CURP"));
                        usuarioDireccion.usuarioD.setIsActivo(resultSet.getInt("IsActivo"));
                        usuarioDireccion.usuarioD.Rol = new Rol();
                        usuarioDireccion.usuarioD.Rol.setNombre(resultSet.getString("NombreRol"));
                        usuarioDireccion.usuarioD.Rol.setActivo(resultSet.getString("Activo"));
                        usuarioDireccion.usuarioD.Rol.setFechaCreacion(resultSet.getString("FechaDeCreacion"));

                        usuarioDireccion.Direcciones = new ArrayList<>();
                        Direccion direccion = new Direccion();

                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                        direccion.colonia = new Colonia();
                        direccion.colonia.setNombreColonia(resultSet.getString("NombreColonia"));
                        direccion.colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                        direccion.colonia.municipio = new Municipio();
                        direccion.colonia.municipio.setNombreMunicipio(resultSet.getString("NombreMunicipio"));
                        direccion.colonia.municipio.estado = new Estado();
                        direccion.colonia.municipio.estado.setNombreEstado(resultSet.getString("NombreEstado"));
                        direccion.colonia.municipio.estado.pais = new Pais();
                        direccion.colonia.municipio.estado.pais.setNombrePais(resultSet.getString("NombrePais"));

                        usuarioDireccion.Direcciones.add(direccion);
                        result.objects.add(usuarioDireccion);
                    }

                }

                return 1;
            });

            result.correct = procesoCorrecto == 1;

        } catch (DataAccessException ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result Add(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();

        try {
            jdbcTemplate.execute("{CALL UsuarioAddSP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Integer>) callableStatementCallBack -> {

                callableStatementCallBack.setString(1, usuarioDireccion.usuarioD.getUsername());
                callableStatementCallBack.setString(2, usuarioDireccion.usuarioD.getNombre());
                callableStatementCallBack.setString(3, usuarioDireccion.usuarioD.getApellidoParteno());
                callableStatementCallBack.setString(4, usuarioDireccion.usuarioD.getApellidoMaterno());
                callableStatementCallBack.setString(5, usuarioDireccion.usuarioD.getEmail());
                callableStatementCallBack.setString(6, usuarioDireccion.usuarioD.getPassword());
                callableStatementCallBack.setDate(7, new java.sql.Date(usuarioDireccion.usuarioD.getFechaDeNacimiento().getTime()));
                callableStatementCallBack.setString(8, String.valueOf(usuarioDireccion.usuarioD.getSexo()));
                callableStatementCallBack.setString(9, usuarioDireccion.usuarioD.getNumeroDeTelefono());
                callableStatementCallBack.setString(10, usuarioDireccion.usuarioD.getCelular());
                callableStatementCallBack.setString(11, usuarioDireccion.usuarioD.getCURP());
                callableStatementCallBack.setString(12, usuarioDireccion.usuarioD.getImagen());
                callableStatementCallBack.setInt(13, usuarioDireccion.usuarioD.Rol.getIdRol());
                callableStatementCallBack.setString(14, usuarioDireccion.direccionU.getCalle());
                callableStatementCallBack.setString(15, usuarioDireccion.direccionU.getNumeroInterior());
                callableStatementCallBack.setString(16, usuarioDireccion.direccionU.getNumeroExterior());
                callableStatementCallBack.setInt(17, usuarioDireccion.direccionU.colonia.getIdColonia());

                int rowsAffected = callableStatementCallBack.executeUpdate();

                result.correct = rowsAffected > 0;
                return 1;
            });

        } catch (DataAccessException ex) {
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
            result.correct = jdbcTemplate.execute("{CALL UsuarioGetByIdSP(?, ?)}", (CallableStatementCallback<Boolean>) callableStatement -> {

                callableStatement.registerOutParameter(2, java.sql.Types.REF_CURSOR);
                callableStatement.setInt(1, idUsuario);

                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);

                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.Direcciones = new ArrayList<>();

                if (resultSet.next()) {
                    usuarioDireccion.usuarioD = new Usuario();
                    usuarioDireccion.usuarioD.setIdUsuario(resultSet.getInt("IdUsuario"));
                    usuarioDireccion.usuarioD.setUsername(resultSet.getString("UserName"));
                    usuarioDireccion.usuarioD.setNombre(resultSet.getString("NombreUsuario"));
                    usuarioDireccion.usuarioD.setApellidoParteno(resultSet.getString("ApellidoPaterno"));
                    usuarioDireccion.usuarioD.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                    usuarioDireccion.usuarioD.setFechaDeNacimiento(resultSet.getDate("FechaDeNacimiento"));
                    usuarioDireccion.usuarioD.setNumeroDeTelefono(resultSet.getString("NumeroDeTelefono"));
                    usuarioDireccion.usuarioD.setEmail(resultSet.getString("Email"));
                    usuarioDireccion.usuarioD.setPassword(resultSet.getString("Password"));
                    usuarioDireccion.usuarioD.setSexo(resultSet.getString("Sexo").charAt(0));
                    usuarioDireccion.usuarioD.setCelular(resultSet.getString("Celular"));
                    usuarioDireccion.usuarioD.setCURP(resultSet.getString("Curp"));
                    usuarioDireccion.usuarioD.setIsActivo(resultSet.getInt("IsActivo"));
                    usuarioDireccion.usuarioD.setImagen(resultSet.getString("Imagen"));
                    usuarioDireccion.usuarioD.Rol = new Rol();
                    usuarioDireccion.usuarioD.Rol.setIdRol(resultSet.getInt("IdRol"));
                    usuarioDireccion.usuarioD.Rol.setNombre(resultSet.getString("NombreRol"));
                    usuarioDireccion.usuarioD.Rol.setActivo(resultSet.getString("Activo"));
                    usuarioDireccion.usuarioD.Rol.setFechaCreacion(resultSet.getString("FechaDeCreacion"));

                    // Dirección (puede haber varias)
                    do {
                        Direccion direccion = new Direccion();
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                        direccion.colonia = new Colonia();
                        direccion.colonia.setNombreColonia(resultSet.getString("NombreColonia"));
                        direccion.colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                        direccion.colonia.municipio = new Municipio();
                        direccion.colonia.municipio.setNombreMunicipio(resultSet.getString("NombreMunicipio"));
                        direccion.colonia.municipio.estado = new Estado();
                        direccion.colonia.municipio.estado.setNombreEstado(resultSet.getString("NombreEstado"));
                        direccion.colonia.municipio.estado.pais = new Pais();
                        direccion.colonia.municipio.estado.pais.setNombrePais(resultSet.getString("NombrePais"));

                        usuarioDireccion.Direcciones.add(direccion);
                    } while (resultSet.next());
                }

                result.object = usuarioDireccion;
                return true;
            });

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

            result.correct = jdbcTemplate.execute("{CALL UsuarioDetails(?,?,?)}", (CallableStatementCallback<Boolean>) callableStatementCallBack -> {

                callableStatementCallBack.registerOutParameter(1, Types.REF_CURSOR);
                callableStatementCallBack.registerOutParameter(2, Types.REF_CURSOR);
                callableStatementCallBack.setInt(3, idUsuario);

                callableStatementCallBack.execute();

                ResultSet resultSetUsuario = (ResultSet) callableStatementCallBack.getObject(1);
                ResultSet resultSetDireccion = (ResultSet) callableStatementCallBack.getObject(2);

                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();

                if (resultSetUsuario.next()) {
                    usuarioDireccion.usuarioD = new Usuario();

                    usuarioDireccion.usuarioD.setIdUsuario(resultSetUsuario.getInt("IdUsuario"));
                    usuarioDireccion.usuarioD.setUsername(resultSetUsuario.getString("UserName"));
                    usuarioDireccion.usuarioD.setNombre(resultSetUsuario.getString("NombreUsuario"));
                    usuarioDireccion.usuarioD.setApellidoParteno(resultSetUsuario.getString("ApellidoPaterno"));
                    usuarioDireccion.usuarioD.setApellidoMaterno(resultSetUsuario.getString("ApellidoMaterno"));
                    usuarioDireccion.usuarioD.setFechaDeNacimiento(resultSetUsuario.getDate("FechaDeNacimiento"));
                    usuarioDireccion.usuarioD.setNumeroDeTelefono(resultSetUsuario.getString("NumeroDeTelefono"));
                    usuarioDireccion.usuarioD.setEmail(resultSetUsuario.getString("Email"));
                    usuarioDireccion.usuarioD.setPassword(resultSetUsuario.getString("Password"));
                    usuarioDireccion.usuarioD.setSexo(resultSetUsuario.getString("Sexo").charAt(0));
                    usuarioDireccion.usuarioD.setCelular(resultSetUsuario.getString("Celular"));
                    usuarioDireccion.usuarioD.setCURP(resultSetUsuario.getString("CURP"));
                    usuarioDireccion.usuarioD.setImagen(resultSetUsuario.getString("Imagen"));
                    usuarioDireccion.usuarioD.Rol = new Rol();
                    usuarioDireccion.usuarioD.Rol.setNombre(resultSetUsuario.getString("NombreRol"));
                    usuarioDireccion.usuarioD.Rol.setActivo(resultSetUsuario.getString("Activo"));
                    usuarioDireccion.usuarioD.Rol.setFechaCreacion(resultSetUsuario.getString("FechaDeCreacion"));
                }
                usuarioDireccion.Direcciones = new ArrayList<>();
                while (resultSetDireccion.next()) {
                    Direccion direccion = new Direccion();

                    direccion.setIdDireccion(resultSetDireccion.getInt("IdDireccion"));
                    direccion.setCalle(resultSetDireccion.getString("Calle"));
                    direccion.setNumeroInterior(resultSetDireccion.getString("NumeroInterior"));
                    direccion.setNumeroExterior(resultSetDireccion.getString("NumeroExterior"));
                    direccion.colonia = new Colonia();
                    direccion.colonia.setNombreColonia(resultSetDireccion.getString("NombreColonia"));
                    direccion.colonia.setCodigoPostal(resultSetDireccion.getString("CodigoPostal"));
                    direccion.colonia.municipio = new Municipio();
                    direccion.colonia.municipio.setNombreMunicipio(resultSetDireccion.getString("NombreMunicipio"));
                    direccion.colonia.municipio.estado = new Estado();
                    direccion.colonia.municipio.estado.setNombreEstado(resultSetDireccion.getString("NombreEstado"));
                    direccion.colonia.municipio.estado.pais = new Pais();
                    direccion.colonia.municipio.estado.pais.setNombrePais(resultSetDireccion.getString("NombrePais"));
                    usuarioDireccion.Direcciones.add(direccion);
                }

                result.object = usuarioDireccion;

                return true;
            });

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result Update(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();

        try {
            result.correct = jdbcTemplate.execute("{CALL UsuarioUpdateSP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Boolean>) callableStatementCallBack -> {

                callableStatementCallBack.setString(1, usuarioDireccion.usuarioD.getUsername());
                callableStatementCallBack.setString(2, usuarioDireccion.usuarioD.getNombre());
                callableStatementCallBack.setString(3, usuarioDireccion.usuarioD.getApellidoParteno());
                callableStatementCallBack.setString(4, usuarioDireccion.usuarioD.getApellidoMaterno());
                callableStatementCallBack.setString(5, usuarioDireccion.usuarioD.getEmail());
                callableStatementCallBack.setString(6, usuarioDireccion.usuarioD.getPassword());
                callableStatementCallBack.setDate(7, new java.sql.Date(usuarioDireccion.usuarioD.getFechaDeNacimiento().getTime()));
                callableStatementCallBack.setString(8, String.valueOf(usuarioDireccion.usuarioD.getSexo()));
                callableStatementCallBack.setString(9, usuarioDireccion.usuarioD.getNumeroDeTelefono());
                callableStatementCallBack.setString(10, usuarioDireccion.usuarioD.getCelular());
                callableStatementCallBack.setString(11, usuarioDireccion.usuarioD.getCURP());
                callableStatementCallBack.setInt(12, usuarioDireccion.usuarioD.getIsActivo());
                callableStatementCallBack.setString(13, usuarioDireccion.usuarioD.getImagen());
                callableStatementCallBack.setInt(14, usuarioDireccion.usuarioD.Rol.getIdRol());
                callableStatementCallBack.setInt(15, usuarioDireccion.usuarioD.getIdUsuario());

                int rowsAffected = callableStatementCallBack.executeUpdate();

                result.correct = rowsAffected > 0;

                return true;
            });
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result Delete(int idUsuario) {
        Result result = new Result();

        try {
            result.correct = jdbcTemplate.execute("{CALL UsuarioDeleteSP(?)}", (CallableStatementCallback<Boolean>) callableStatementCallBack -> {

                callableStatementCallBack.setInt(1, idUsuario);

                callableStatementCallBack.executeUpdate();

                return true;
            });
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result Search(Usuario usuario) {
        Result result = new Result();

        try {
            result.correct = jdbcTemplate.execute("{CALL BusquedaDinamicaSP(?,?,?,?,?,?)}", (CallableStatementCallback<Boolean>) callableStatementCallBack -> {

                callableStatementCallBack.registerOutParameter(1, Types.REF_CURSOR);
                callableStatementCallBack.setString(2, usuario.getNombre());
                callableStatementCallBack.setString(3, usuario.getApellidoParteno());
                callableStatementCallBack.setString(4, usuario.getApellidoMaterno());
                callableStatementCallBack.setInt(5, usuario.Rol.getIdRol());
                callableStatementCallBack.setInt(6, usuario.getIsActivo());

                callableStatementCallBack.execute();

                ResultSet resultSet = (ResultSet) callableStatementCallBack.getObject(1);

                result.objects = new ArrayList<>();

                while (resultSet.next()) {

                    int idUsuario = resultSet.getInt("IdUsuario");

                    if (!result.objects.isEmpty() && idUsuario == ((UsuarioDireccion) (result.objects.get(result.objects.size() - 1))).usuarioD.getIdUsuario()) {

                        Direccion direccion = new Direccion();
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                        direccion.colonia = new Colonia();
                        direccion.colonia.setNombreColonia(resultSet.getString("NombreColonia"));
                        direccion.colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                        direccion.colonia.municipio = new Municipio();
                        direccion.colonia.municipio.setNombreMunicipio(resultSet.getString("NombreMunicipio"));
                        direccion.colonia.municipio.estado = new Estado();
                        direccion.colonia.municipio.estado.setNombreEstado(resultSet.getString("NombreEstado"));
                        direccion.colonia.municipio.estado.pais = new Pais();
                        direccion.colonia.municipio.estado.pais.setNombrePais(resultSet.getString("NombrePais"));

                        ((UsuarioDireccion) (result.objects.get(result.objects.size() - 1))).Direcciones.add(direccion);
                    } else {

                        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                        usuarioDireccion.usuarioD = new Usuario();

                        usuarioDireccion.usuarioD.setIdUsuario(resultSet.getInt("IdUsuario"));
                        usuarioDireccion.usuarioD.setUsername(resultSet.getString("UserName"));
                        usuarioDireccion.usuarioD.setNombre(resultSet.getString("NombreUsuario"));
                        usuarioDireccion.usuarioD.setApellidoParteno(resultSet.getString("ApellidoPaterno"));
                        usuarioDireccion.usuarioD.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                        usuarioDireccion.usuarioD.setFechaDeNacimiento(resultSet.getDate("FechaDeNacimiento"));
                        usuarioDireccion.usuarioD.setNumeroDeTelefono(resultSet.getString("NumeroDeTelefono"));
                        usuarioDireccion.usuarioD.setEmail(resultSet.getString("Email"));
                        usuarioDireccion.usuarioD.setPassword(resultSet.getString("Password"));
                        usuarioDireccion.usuarioD.setSexo(resultSet.getString("Sexo").charAt(0));
                        usuarioDireccion.usuarioD.setCelular(resultSet.getString("Celular"));
                        usuarioDireccion.usuarioD.setCURP(resultSet.getString("CURP"));
                        usuarioDireccion.usuarioD.setIsActivo(resultSet.getInt("IsActivo"));
                        usuarioDireccion.usuarioD.Rol = new Rol();
                        usuarioDireccion.usuarioD.Rol.setNombre(resultSet.getString("NombreRol"));
                        usuarioDireccion.usuarioD.Rol.setActivo(resultSet.getString("Activo"));
                        usuarioDireccion.usuarioD.Rol.setFechaCreacion(resultSet.getString("FechaDeCreacion"));

                        usuarioDireccion.Direcciones = new ArrayList<>();
                        Direccion direccion = new Direccion();

                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                        direccion.colonia = new Colonia();
                        direccion.colonia.setNombreColonia(resultSet.getString("NombreColonia"));
                        direccion.colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                        direccion.colonia.municipio = new Municipio();
                        direccion.colonia.municipio.setNombreMunicipio(resultSet.getString("NombreMunicipio"));
                        direccion.colonia.municipio.estado = new Estado();
                        direccion.colonia.municipio.estado.setNombreEstado(resultSet.getString("NombreEstado"));
                        direccion.colonia.municipio.estado.pais = new Pais();
                        direccion.colonia.municipio.estado.pais.setNombrePais(resultSet.getString("NombrePais"));

                        usuarioDireccion.Direcciones.add(direccion);
                        result.objects.add(usuarioDireccion);
                    }

                }

                return true;
            });
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result IsActivo(int idUsuario, int status) {
        Result result = new Result();
        try {
            result.correct = jdbcTemplate.execute("{CALL IsActivoSP(?,?)}", (CallableStatementCallback<Boolean>) callableStatementCallBack -> {

                callableStatementCallBack.setInt(1, status);
                callableStatementCallBack.setInt(2, idUsuario);

                callableStatementCallBack.executeUpdate();

                return true;
            });
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Override
    public Result AddMasivo(List<UsuarioDireccion> usuarioDireccionList) {
        Result result = new Result();

        for (int i = 0; i < usuarioDireccionList.size(); i++) {
            UsuarioDireccion usuarioDireccion = usuarioDireccionList.get(i);
            try {
                 result.correct = jdbcTemplate.execute("{CALL UsuarioAddSP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Boolean>) callableStatementCallBack -> {

                    callableStatementCallBack.setString(1, usuarioDireccion.usuarioD.getUsername());
                    callableStatementCallBack.setString(2, usuarioDireccion.usuarioD.getNombre());
                    callableStatementCallBack.setString(3, usuarioDireccion.usuarioD.getApellidoParteno());
                    callableStatementCallBack.setString(4, usuarioDireccion.usuarioD.getApellidoMaterno());
                    callableStatementCallBack.setString(5, usuarioDireccion.usuarioD.getEmail());
                    callableStatementCallBack.setString(6, usuarioDireccion.usuarioD.getPassword());
                    callableStatementCallBack.setDate(7, new java.sql.Date(usuarioDireccion.usuarioD.getFechaDeNacimiento().getTime()));
                    callableStatementCallBack.setString(8, String.valueOf(usuarioDireccion.usuarioD.getSexo()));
                    callableStatementCallBack.setString(9, usuarioDireccion.usuarioD.getNumeroDeTelefono());
                    callableStatementCallBack.setString(10, usuarioDireccion.usuarioD.getCelular());
                    callableStatementCallBack.setString(11, usuarioDireccion.usuarioD.getCURP());
                    callableStatementCallBack.setString(12, usuarioDireccion.usuarioD.getImagen());
                    callableStatementCallBack.setInt(13, usuarioDireccion.usuarioD.Rol.getIdRol());
                    callableStatementCallBack.setString(14, usuarioDireccion.direccionU.getCalle());
                    callableStatementCallBack.setString(15, usuarioDireccion.direccionU.getNumeroInterior());
                    callableStatementCallBack.setString(16, usuarioDireccion.direccionU.getNumeroExterior());
                    callableStatementCallBack.setInt(17, usuarioDireccion.direccionU.colonia.getIdColonia());

                    int rowsAffected = callableStatementCallBack.executeUpdate();

                    result.correct = rowsAffected > 0;
                    
                    return true;
                });
            } catch (Exception ex) {
                result.correct = false;
                result.errorMessage = ex.getLocalizedMessage();
                result.ex = ex;
            }
        }

        return result;
    }

}
