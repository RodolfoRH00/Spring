package com.Rodolfo.RRamirezProgramacionNCapasMaven.DAO;

import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Result;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Usuario;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.UsuarioDireccion;
import java.util.List;

public interface IUsuarioJPADAO {
    
    Result Add(UsuarioDireccion usuarioDireccion);
    Result Delete(int idUsuario);
    Result Update(UsuarioDireccion usuarioDireccion);
    Result GetAll();
    Result Details(int idUsuario);
    Result IsActivo(int idUsuario, int status);
    Result GetById(int idUsuario);
    Result Add(List<UsuarioDireccion> usuarioDireccion);
    Result DinamicSearch(Usuario usuario);
    Result GetIdByUserName(String userName);
}
