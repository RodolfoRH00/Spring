package com.Rodolfo.RRamirezProgramacionNCapasMaven.DAO;

import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Colonia;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ColoniaDAOImplementation implements IColoniaDAO{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Result GetById(int IdMunicipio){
        Result result = new Result();
        
        try{
            
            result.correct = jdbcTemplate.execute("{CALL ColoniaGetByIdSP(?,?)}", (CallableStatementCallback<Boolean>) callablestatementCallback -> {
                
                callablestatementCallback.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callablestatementCallback.setInt(2, IdMunicipio);
                callablestatementCallback.execute();
                
                ResultSet resultSet = (ResultSet) callablestatementCallback.getObject(1);
                
                result.objects = new ArrayList<>();
                
                while(resultSet.next()){
                    Colonia colonia = new Colonia();
                    
                    colonia.setIdColonia(resultSet.getInt("IdColonia"));
                    colonia.setNombreColonia(resultSet.getString("Nombre"));
                    colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                    
                    result.objects.add(colonia);
                }
                
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
