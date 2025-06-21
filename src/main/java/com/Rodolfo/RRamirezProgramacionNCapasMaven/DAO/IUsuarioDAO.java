package com.Rodolfo.RRamirezProgramacionNCapasMaven.DAO;

import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Result;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Usuario;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.UsuarioDireccion;
import java.util.List;

// Esta clase funciona como un contrato, lo que se defina aqui, tiene que ser implementado en UsuarioDAOImplementatio
public interface IUsuarioDAO {

    Result GetAll();
    Result Add(UsuarioDireccion usuarioDireccion);
    Result GetById(int idUsuario);
    Result Details(int idUsuario);
    Result Update(UsuarioDireccion usuarioDireccion);
    Result Delete(int idUsuario);
    Result Search(Usuario usuario);
    Result IsActivo(int isActivo, int idUsuario);
    Result AddMasivo(List<UsuarioDireccion> usuarioDireccion);
}
