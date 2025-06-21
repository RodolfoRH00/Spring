package com.Rodolfo.RRamirezProgramacionNCapasMaven.ML;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class Usuario {

    private int IdUsuario;
    
    @Size(min = 3, max = 12, message = "El user name debe contener entre 3 y 12 caracteres alfanumericos")
    @NotEmpty(message = "El user name no puede estar vacio")
    private String Username;
    
    @Size(min = 3, message = "El nombre debe contener mas de un caracter alfabetico")
    @NotEmpty(message = "El nombre no puede estar vacio")
    private String Nombre;
    
    @Size(min = 2, message = "El apellido paterno debe contener mas de un caracter alfanumerico")
    @NotEmpty(message = "El apellido paterno no puede estar vacio")
    private String ApellidoParteno;
    
    @Size(min = 2, message = "El apellido materno debe contener mas de un caracter alfanumerico")
    @NotEmpty(message = "El apellido materno no puede estar vacio")
    private String ApellidoMaterno;
    
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date FechaDeNacimiento;
    
    @NotEmpty(message = "El numero de telefono no puede estar vacio")
    @Pattern(regexp = "^\\d{10}$")
    private String NumeroDeTelefono;
    
    @Pattern(regexp = "/^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$/")
    @NotEmpty(message = "El el correo electronico no puede estar vacio")
    @Email(message = "Correo invalido")
    private String Email;
    
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}:;\"'<>?,./]).{8,}$",
            message = "Tu constrasenia debe tener al menos una mayuscula, una minuscula, 8 caracteres y un caracter especial")
    private String Password;
    
    @NotNull(message = "Debes seleccionar un sexo")
    private char Sexo;
    
    @NotEmpty(message = "El numero de telefono no puede estar vacio")
    @Pattern(regexp = "^\\d{10}$")
    private String Celular;
    
    @NotEmpty
    @Pattern(regexp = "^[A-Z]{4}\\d{6}[A-Z]{6}\\d{2}$")
    private String CURP;
    
    private int IsActivo;
    
    private String Imagen;
    
    @NotNull(message = "Debes seleccionar un rol")
    public Rol Rol;

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellidoParteno() {
        return ApellidoParteno;
    }

    public void setApellidoParteno(String ApellidoParteno) {
        this.ApellidoParteno = ApellidoParteno;
    }

    public String getApellidoMaterno() {
        return ApellidoMaterno;
    }

    public void setApellidoMaterno(String ApellidoMaterno) {
        this.ApellidoMaterno = ApellidoMaterno;
    }

    public Date getFechaDeNacimiento() {
        return FechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Date FechaDeNacimiento) {
        this.FechaDeNacimiento = FechaDeNacimiento;
    }

    public String getNumeroDeTelefono() {
        return NumeroDeTelefono;
    }

    public void setNumeroDeTelefono(String NumeroDeTelefono) {
        this.NumeroDeTelefono = NumeroDeTelefono;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public char getSexo() {
        return Sexo;
    }

    public void setSexo(char Sexo) {
        this.Sexo = Sexo;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String Celular) {
        this.Celular = Celular;
    }

    public String getCURP() {
        return CURP;
    }

    public void setCURP(String CURP) {
        this.CURP = CURP;
    }

    public int getIsActivo() {
        return IsActivo;
    }

    public void setIsActivo(int IsActivo) {
        this.IsActivo = IsActivo;
    }
    
    

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String Imagen) {
        this.Imagen = Imagen;
    }
    
    public Rol getRol() {
        return Rol;
    }

    public void setRol(Rol Rol) {
        this.Rol = Rol;
    }

}
