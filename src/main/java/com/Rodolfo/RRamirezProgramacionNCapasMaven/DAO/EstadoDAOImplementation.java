package com.Rodolfo.RRamirezProgramacionNCapasMaven.DAO;

import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Estado;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EstadoDAOImplementation implements IEstadoDAO {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Result GetAll(int idPais){
        
        Result result = new Result();
        
        try{
            result.correct = jdbcTemplate.execute("{CALL EstadoGetAllSP(?,?)}", (CallableStatementCallback<Boolean>) callalbleStatementCallBack -> {
            
                callalbleStatementCallBack.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callalbleStatementCallBack.setInt(2, idPais);
                callalbleStatementCallBack.execute();
                
                ResultSet resultSet = (ResultSet) callalbleStatementCallBack.getObject(1);
                
                result.objects = new ArrayList<>();
                while(resultSet.next()){
                    Estado estado = new Estado();
                    
                    estado.setIdEstado(resultSet.getInt("IdEstado"));
                    estado.setNombreEstado(resultSet.getString("Nombre"));
                    
                    result.objects.add(estado);
                } 
                result.correct = true;
                return true;
            });
        }catch(DataAccessException ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }
}
