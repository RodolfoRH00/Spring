package com.Rodolfo.RRamirezProgramacionNCapasMaven.DAO;

import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Municipio;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MunicipioDAOImplementation implements IMunicipioDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetById(int IdEstado) {
        Result result = new Result();

        try {
            result.correct = jdbcTemplate.execute("{CALL MunicipioGetByIdSP(?,?)}", (CallableStatementCallback<Boolean>) callableStatementCallBack -> {

                callableStatementCallBack.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatementCallBack.setInt(2, IdEstado);

                callableStatementCallBack.execute();

                ResultSet resultSet = (ResultSet) callableStatementCallBack.getObject(1);

                result.objects = new ArrayList<>();

                while (resultSet.next()) {
                    Municipio municipio = new Municipio();

                    municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                    municipio.setNombreMunicipio(resultSet.getString("Nombre"));

                    result.objects.add(municipio);

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

}
