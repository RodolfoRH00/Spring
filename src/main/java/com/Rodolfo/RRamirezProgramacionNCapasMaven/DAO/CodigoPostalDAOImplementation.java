package com.Rodolfo.RRamirezProgramacionNCapasMaven.DAO;

import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Result;
import java.sql.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;

public class CodigoPostalDAOImplementation implements ICodigoPostalDAO{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Result GetAll(String CodigoPostal){
        Result result = new Result();
        
        try{
            result.correct = jdbcTemplate.execute("{CALL CodigoPostalGetAllSP(?,?)}", (CallableStatementCallback<Boolean>) callableStatementCallBack -> {
                
                callableStatementCallBack.setString(1, CodigoPostal);
                callableStatementCallBack.registerOutParameter(2, java.sql.Types.REF_CURSOR);
                
                callableStatementCallBack.execute();
                
                ResultSet resultSet = (ResultSet) callableStatementCallBack.getObject(2); 
                
                
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
