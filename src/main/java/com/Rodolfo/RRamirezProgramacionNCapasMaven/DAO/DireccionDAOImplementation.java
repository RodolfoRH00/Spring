package com.Rodolfo.RRamirezProgramacionNCapasMaven.DAO;

import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Colonia;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Direccion;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Estado;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Municipio;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Pais;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Result;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.UsuarioDireccion;
import java.sql.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DireccionDAOImplementation implements IDireccionDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetById(int idDireccion) {
        Result result = new Result();

        try {

            result.correct = jdbcTemplate.execute("{CALL DireccionGetById(?,?)}", (CallableStatementCallback<Boolean>) callableStatementCallBack -> {

                callableStatementCallBack.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatementCallBack.setInt(2, idDireccion);

                callableStatementCallBack.execute();

                ResultSet resultSet = (ResultSet) callableStatementCallBack.getObject(1);

                if (resultSet.next()) {
                    Direccion direccion = new Direccion();

                    direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                    direccion.setCalle(resultSet.getString("Calle"));
                    direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                    direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                    direccion.colonia = new Colonia();
                    direccion.colonia.setIdColonia(resultSet.getInt("IdColonia"));
                    direccion.colonia.setNombreColonia(resultSet.getString("NombreColonia"));
                    direccion.colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                    direccion.colonia.municipio = new Municipio();
                    direccion.colonia.municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                    direccion.colonia.municipio.setNombreMunicipio(resultSet.getString("NombreMunicipio"));
                    direccion.colonia.municipio.estado = new Estado();
                    direccion.colonia.municipio.estado.setIdEstado(resultSet.getInt("IdEstado"));
                    direccion.colonia.municipio.estado.setNombreEstado(resultSet.getString("NombreEstado"));
                    direccion.colonia.municipio.estado.pais = new Pais();
                    direccion.colonia.municipio.estado.pais.setIdPais(resultSet.getInt("IdPais"));
                    direccion.colonia.municipio.estado.pais.setNombrePais(resultSet.getString("NombrePais"));

                    result.object = direccion;
                }

                return true;
            });
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
            result.correct = jdbcTemplate.execute("{CALL DireccionAddSP(?,?,?,?,?)}", (CallableStatementCallback<Boolean>) callableStatementCallBack -> {

                callableStatementCallBack.setString(1, usuarioDireccion.direccionU.getCalle());
                callableStatementCallBack.setString(2, usuarioDireccion.direccionU.getNumeroInterior());
                callableStatementCallBack.setString(3, usuarioDireccion.direccionU.getNumeroExterior());

                callableStatementCallBack.setInt(4, usuarioDireccion.usuarioD.getIdUsuario());
                callableStatementCallBack.setInt(5, usuarioDireccion.direccionU.colonia.getIdColonia());

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
    public Result Update(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();

        try {
            result.correct = jdbcTemplate.execute("{CALL DireccionUpdateSP(?,?,?,?,?,?)}", (CallableStatementCallback<Boolean>) callableStatementCallBack -> {
                
                callableStatementCallBack.setString(1, usuarioDireccion.direccionU.getCalle());
                callableStatementCallBack.setString(2, usuarioDireccion.direccionU.getNumeroExterior());
                callableStatementCallBack.setString(3, usuarioDireccion.direccionU.getNumeroInterior());
                callableStatementCallBack.setInt(4, usuarioDireccion.direccionU.colonia.getIdColonia());
                callableStatementCallBack.setInt(5, usuarioDireccion.usuarioD.getIdUsuario());
                callableStatementCallBack.setInt(6, usuarioDireccion.direccionU.getIdDireccion());
                
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
    public Result Delete(int idDireccion){
        Result result = new Result();
        try{
            result.correct = jdbcTemplate.execute("{CALL DireccionDeleteSP(?)}", (CallableStatementCallback<Boolean>) callableStatementCallBack -> {
                
                callableStatementCallBack.setInt(1, idDireccion);
                
                callableStatementCallBack.executeUpdate();
                
                return true;
            });
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

}
