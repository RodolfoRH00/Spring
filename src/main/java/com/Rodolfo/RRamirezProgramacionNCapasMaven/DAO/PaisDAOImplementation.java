package com.Rodolfo.RRamirezProgramacionNCapasMaven.DAO;

import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Pais;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PaisDAOImplementation implements IPaisDAO {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetAll() {
        Result result = new Result();

        try {
            result.correct = jdbcTemplate.execute("{CALL PaisGetAllSP(?)}", (CallableStatementCallback<Boolean>) callableStatementCallBack -> {
            
                callableStatementCallBack.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatementCallBack.execute();
                
                ResultSet resultSet = (ResultSet) callableStatementCallBack.getObject(1);
                
                result.objects = new ArrayList<>();
                
                while(resultSet.next()){
                    Pais pais = new Pais();
                    
                    pais.setIdPais(resultSet.getInt("IdPais"));
                    pais.setNombrePais(resultSet.getString("Nombre"));
                    
                    result.objects.add(pais);
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
}
