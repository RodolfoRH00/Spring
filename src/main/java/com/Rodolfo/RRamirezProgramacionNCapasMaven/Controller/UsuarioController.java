package com.Rodolfo.RRamirezProgramacionNCapasMaven.Controller;

import com.Rodolfo.RRamirezProgramacionNCapasMaven.DAO.ColoniaDAOImplementation;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.DAO.DireccionDAOImplementation;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.DAO.EstadoDAOImplementation;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.DAO.MunicipioDAOImplementation;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.DAO.PaisDAOImplementation;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.DAO.RolDAOImplementation;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.DAO.UsuarioDAOImplementation;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Colonia;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Direccion;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Result;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.ResultValidarDatos;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Rol;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Usuario;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.UsuarioDireccion;
import jakarta.validation.Valid;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.Cell;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired // Inyeccion de dependencia
    private UsuarioDAOImplementation usuarioDAOImplementatio;
    @Autowired
    private RolDAOImplementation rolDAOImplementation;
    @Autowired
    private PaisDAOImplementation paisDAOImplementation;
    @Autowired
    private EstadoDAOImplementation estadoDAOImplementation;
    @Autowired
    private MunicipioDAOImplementation municipioDAOImplementation;
    @Autowired
    private ColoniaDAOImplementation coloniaDAOImplementation;
    @Autowired
    private DireccionDAOImplementation direccionDAOImplementation;

    @GetMapping // maneja solicitudes GET
    public String Usuario(Model model) {

        Result result = usuarioDAOImplementatio.GetAll();

        if (result.correct) {
            model.addAttribute("usuariosDireccion", result.objects);
            model.addAttribute("roles", rolDAOImplementation.GetAll().objects);
            Usuario usuario = new Usuario();
            usuario.Rol = new Rol();
            model.addAttribute("usuario", usuario);
        }
        return "Usuario";
    }

    @GetMapping("form/{idUsuario}")
    public String ShowFormulario(Model model, @PathVariable int idUsuario) {
        if (idUsuario <= 0) {
            model.addAttribute("paises", paisDAOImplementation.GetAll().objects);
            model.addAttribute("roles", rolDAOImplementation.GetAll().objects);
            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion.usuarioD = new Usuario();
            usuarioDireccion.direccionU = new Direccion();
            model.addAttribute("usuarioDireccion", usuarioDireccion);
            return "formUsuario";

        } else {
            model.addAttribute("usuariosDireccion", usuarioDAOImplementatio.Details(idUsuario).object);
            return "UsuarioDetails";
        }
    }

    @PostMapping("form") // maneja solicitudes POST
    public String SubmitFormulario(@Valid @ModelAttribute UsuarioDireccion usuarioDireccion,
            BindingResult bindingResult, @RequestParam MultipartFile imagenFile,
            Model model) {

//        if (bindingResult.hasErrors()) {
//            model.addAttribute("usuarioDireccion", usuarioDireccion);
//            return "formusuario"; // retornar IdDireccion y IdUsuario
//        }
        try {
            if (!imagenFile.isEmpty()) {
                byte[] imagenBytes = imagenFile.getBytes();
                String imgBase64 = Base64.getEncoder().encodeToString(imagenBytes);
                usuarioDireccion.usuarioD.setImagen(imgBase64);
            }
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        if (usuarioDireccion.usuarioD.getIdUsuario() == 0 && usuarioDireccion.direccionU.getIdDireccion() == 0) {
            Result result = usuarioDAOImplementatio.Add(usuarioDireccion);
            if (result.correct) {
                return "redirect:/usuario";
            } else {
                return "/usuario";
            }

        } else if (usuarioDireccion.direccionU.getIdDireccion() == 0 && usuarioDireccion.usuarioD.getIdUsuario() > 0) {
            Result result = direccionDAOImplementation.Add(usuarioDireccion);
            if (result.correct) {
                return "redirect:/usuario";
            } else {
                return "/usuario";
            }
        } else if (usuarioDireccion.usuarioD.getIdUsuario() > 0 && usuarioDireccion.direccionU.getIdDireccion() > 0) {
            Result result = direccionDAOImplementation.Update(usuarioDireccion);
            if (result.correct) {
                return "redirect:/usuario";
            } else {
                return "redirect:/usuario";
            }
        } else {
            Result result = usuarioDAOImplementatio.Update(usuarioDireccion);
            if (result.correct) {
                return "redirect:/usuario";
            } else {
                return "/usuario";
            }
        }
    }

    @PostMapping
    public String UsuarioBusqueda(@ModelAttribute Usuario usuario, Model model) {

        Result result = usuarioDAOImplementatio.Search(usuario);
        model.addAttribute("usuario", new Usuario());

        model.addAttribute("usuariosDireccion", result.objects);
        model.addAttribute("roles", rolDAOImplementation.GetAll().objects);

        return "Usuario";
    }

    @GetMapping("formEdit")
    public String Edit(@RequestParam int idUsuario, @RequestParam(required = false) Integer idDireccion, Model model) {

        if (idDireccion == null) { // editar usuario
            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion = (UsuarioDireccion) usuarioDAOImplementatio.GetById(idUsuario).object;
            usuarioDireccion.direccionU = new Direccion();
            usuarioDireccion.direccionU.setIdDireccion(-1);
            model.addAttribute("usuarioDireccion", usuarioDireccion);
            model.addAttribute("roles", rolDAOImplementation.GetAll().objects);
        } else if (idDireccion == 0) { // agreagar direccion
            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion.usuarioD = new Usuario();
            usuarioDireccion.usuarioD.setIdUsuario(idUsuario);
            usuarioDireccion.direccionU = new Direccion();
            usuarioDireccion.direccionU.setIdDireccion(0);
            model.addAttribute("usuarioDireccion", usuarioDireccion);
            model.addAttribute("paises", paisDAOImplementation.GetAll().objects);
        } else { // editar direccion
            Direccion direccion = (Direccion) direccionDAOImplementation.GetById(idDireccion).object;
            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion.usuarioD = new Usuario();
            usuarioDireccion.usuarioD.setIdUsuario(idUsuario);
            usuarioDireccion.direccionU = direccion;
            model.addAttribute("usuarioDireccion", usuarioDireccion);
            model.addAttribute("direccion", direccion);
            model.addAttribute("paises", paisDAOImplementation.GetAll().objects);
            model.addAttribute("estados", estadoDAOImplementation.GetAll(direccion.colonia.municipio.estado.pais.getIdPais()).objects);
            model.addAttribute("municipios", municipioDAOImplementation.GetById(direccion.colonia.municipio.getIdMunicipio()).objects);
            model.addAttribute("colonias", coloniaDAOImplementation.GetById(direccion.colonia.getIdColonia()).objects);
        }
        return "formUsuario";
    }

    @GetMapping("CargaMasiva")
    public String CargaMasiva() {
        return "CargaMasiva";
    }

    @PostMapping("CargaMasiva")
    public String CargaMasiva(@RequestParam MultipartFile archivo, Model model) throws IOException {
        if (archivo != null && !archivo.isEmpty()) {
            String extension = archivo.getOriginalFilename().split("\\.")[1];

            // Procesar archivo desde MultipartFile antes de transferTo
            List<UsuarioDireccion> usuariosDireccion = new ArrayList<>();
            if (extension.equalsIgnoreCase("txt")) {
                usuariosDireccion = LecturaArchivoTXT(archivo);
            } else if (extension.equalsIgnoreCase("xlsx")) {
                usuariosDireccion = LecturaArchivoXLSX(archivo);
            }

            // Validar datos
            List<ResultValidarDatos> errores = ValidarDatos(usuariosDireccion);
            if (!errores.isEmpty()) {
                model.addAttribute("errores", errores);
                return "CargaMasiva";
            }

            String root = System.getProperty("user.dir");
            String path = "src/main/resources/archivos";
            String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            String nombreArchivo = fecha + "_" + archivo.getOriginalFilename();
            File destino = new File(root + "/" + path + "/" + nombreArchivo);
            destino.getParentFile().mkdirs();
            archivo.transferTo(destino);
        }
        return "redirect:/usuario";
    }

    public List<UsuarioDireccion> LecturaArchivoXLSX(MultipartFile archivo) {
        List<UsuarioDireccion> listaUsuarios = new ArrayList<>();

        try (InputStream inputStream = archivo.getInputStream(); Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet primeraHoja = workbook.getSheetAt(0);
            Iterator<Row> filas = primeraHoja.iterator();

            boolean primeraFila = true;

            while (filas.hasNext()) {
                Row filaActual = filas.next();
                if (primeraFila) {
                    primeraFila = false;
                    continue;
                }

                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                Usuario usuario = new Usuario();
                Direccion direccion = new Direccion();
                Colonia colonia = new Colonia();
                Rol rol = new Rol();

                // Asignación de campos desde las celdas
                usuario.setUsername(getStringValue(filaActual.getCell(0)));
                usuario.setNombre(getStringValue(filaActual.getCell(1)));
                usuario.setApellidoParteno(getStringValue(filaActual.getCell(2)));
                usuario.setApellidoMaterno(getStringValue(filaActual.getCell(3)));
                usuario.setEmail(getStringValue(filaActual.getCell(4)));
                usuario.setPassword(getStringValue(filaActual.getCell(5)));
                usuario.setNumeroDeTelefono(getStringValue(filaActual.getCell(6)));
                usuario.setCelular(getStringValue(filaActual.getCell(7)));
                usuario.setCURP(getStringValue(filaActual.getCell(8)));

                // Fecha de nacimiento

                usuario.setFechaDeNacimiento(filaActual.getCell(9).getDateCellValue());

                // Sexo
                String sexo = getStringValue(filaActual.getCell(10));
                if (sexo != null && !sexo.isEmpty()) {
                    usuario.setSexo(sexo.charAt(0));
                }

                usuario.setImagen(getStringValue(filaActual.getCell(11)));

                // Rol
                Cell rolCell = filaActual.getCell(12);
                if (rolCell != null && rolCell.getCellType() == CellType.NUMERIC) {
                    rol.setIdRol((int) rolCell.getNumericCellValue());
                    usuario.setRol(rol);
                }

                direccion.setCalle(getStringValue(filaActual.getCell(13)));
                direccion.setNumeroInterior(getStringValue(filaActual.getCell(14)));
                direccion.setNumeroExterior(getStringValue(filaActual.getCell(15)));

                // Colonia
                Cell coloniaCell = filaActual.getCell(16);
                if (coloniaCell != null && coloniaCell.getCellType() == CellType.NUMERIC) {
                    colonia.setIdColonia((int) coloniaCell.getNumericCellValue());
                    direccion.setColonia(colonia);
                }

                // Setear objetos
                usuarioDireccion.setUsuarioD(usuario);
                usuarioDireccion.setDireccionU(direccion);
                listaUsuarios.add(usuarioDireccion);
            }
            
            

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return listaUsuarios;
    }

    private String getStringValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        try {
            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue().trim();
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        return new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
                    } else {
                        double num = cell.getNumericCellValue();
                        return (num % 1 == 0) ? String.valueOf((long) num) : String.valueOf(num);
                    }
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                case FORMULA:
                    return cell.toString().trim(); // Devuelve el valor evaluado
                case BLANK:
                default:
                    return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    public List<UsuarioDireccion> LecturaArchivoTXT(MultipartFile archivo) {

        List<UsuarioDireccion> usuariosDireccion = new ArrayList<>();

        try (InputStream inputStream = archivo.getInputStream(); BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));) {

            bufferedReader.readLine();
            String linea = "";
            while ((linea = bufferedReader.readLine()) != null) {
                String[] datos = linea.split("\\|");

                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.usuarioD = new Usuario();
                usuarioDireccion.usuarioD.setUsername(datos[0]);
                usuarioDireccion.usuarioD.setNombre(datos[1]);
                usuarioDireccion.usuarioD.setApellidoParteno(datos[2]);
                usuarioDireccion.usuarioD.setApellidoMaterno(datos[3]);
                usuarioDireccion.usuarioD.setEmail(datos[4]);
                usuarioDireccion.usuarioD.setPassword(datos[5]);
                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                Date fecha = formato.parse(datos[6]);
                usuarioDireccion.usuarioD.setFechaDeNacimiento(fecha);
                if (datos[7] != null && !datos[7].isEmpty()) {
                    usuarioDireccion.usuarioD.setSexo(datos[7].charAt(0));
                } else {
                    usuarioDireccion.usuarioD.setSexo('N'); // Por ejemplo: 'N' para "No especificado"
                }

                usuarioDireccion.usuarioD.setNumeroDeTelefono(datos[8]);
                usuarioDireccion.usuarioD.setCelular(datos[9]);
                usuarioDireccion.usuarioD.setCURP(datos[10]);
                usuarioDireccion.usuarioD.setImagen(datos[11]);

                Rol rol = new Rol();
                rol.setIdRol(Integer.parseInt(datos[12]));
                usuarioDireccion.usuarioD.setRol(rol);

                Direccion direccion = new Direccion();
                direccion.setCalle(datos[13]);
                direccion.setNumeroInterior(datos[14]);
                direccion.setNumeroExterior(datos[15]);

                Colonia colonia = new Colonia();
                colonia.setIdColonia(Integer.parseInt(datos[16]));
                direccion.setColonia(colonia);

                usuariosDireccion.add(usuarioDireccion);
                usuarioDireccion.setDireccionU(direccion);

            }

        } catch (Exception ex) {
            usuariosDireccion = null;
        }
        return usuariosDireccion;
    }

    private List<ResultValidarDatos> ValidarDatos(List<UsuarioDireccion> alumnos) {
        List<ResultValidarDatos> listaErrores = new ArrayList<>();
        int fila = 1;

        if (alumnos == null) {
            listaErrores.add(new ResultValidarDatos(0, "Lista inexistente", "Lista inexistente"));
        } else if (alumnos.isEmpty()) {
            listaErrores.add(new ResultValidarDatos(0, "Lista vacía", "Lista vacía"));
        } else {
            for (UsuarioDireccion usuariodireccion : alumnos) {

                // Validación de UserName
                if (usuariodireccion.usuarioD.getUsername() == null || usuariodireccion.usuarioD.getUsername().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, "UserName", "Campo obligatorio"));
                }

                // Validación de Nombre
                if (usuariodireccion.usuarioD.getNombre() == null || usuariodireccion.usuarioD.getNombre().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, "Nombre", "Campo obligatorio"));
                }

                // Validación de Apellido Paterno
                if (usuariodireccion.usuarioD.getApellidoParteno() == null || usuariodireccion.usuarioD.getApellidoParteno().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, "Apellido Paterno", "Campo obligatorio"));
                }

                // Validación de Apellido Materno
                if (usuariodireccion.usuarioD.getApellidoMaterno() == null || usuariodireccion.usuarioD.getApellidoMaterno().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, "Apellido Materno", "Campo obligatorio"));
                }

                // Validación de Email
                if (usuariodireccion.usuarioD.getEmail() == null || usuariodireccion.usuarioD.getEmail().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, "Email", "Campo obligatorio"));
                } else if (!isValidEmail(usuariodireccion.usuarioD.getEmail())) {
                    listaErrores.add(new ResultValidarDatos(fila, "Email", "Formato de email inválido"));
                }

                // Validación de Password
                if (usuariodireccion.usuarioD.getPassword() == null || usuariodireccion.usuarioD.getPassword().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, "Password", "Campo obligatorio"));
                } else if (usuariodireccion.usuarioD.getPassword().length() < 6) {
                    listaErrores.add(new ResultValidarDatos(fila, "Password", "Debe tener al menos 6 caracteres"));
                }

                // Validación de Número de Teléfono
                if (!isValidPhoneNumber(usuariodireccion.usuarioD.getNumeroDeTelefono())) {
                    listaErrores.add(new ResultValidarDatos(fila, "Número de Teléfono", "Debe contener 10 dígitos numéricos"));
                }

                // Validación de Celular
                if (usuariodireccion.usuarioD.getCelular() == null || usuariodireccion.usuarioD.getCelular().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, "Celular", "Campo obligatorio"));
                } else if (usuariodireccion.usuarioD.getCelular().length() != 10) {
                    listaErrores.add(new ResultValidarDatos(fila, "Celular", "Debe tener 10 dígitos"));
                }

                // Validación de CURP
                if (usuariodireccion.usuarioD.getCURP() == null || usuariodireccion.usuarioD.getCURP().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, "CURP", "Campo obligatorio"));
                } else if (!isValidCURP(usuariodireccion.usuarioD.getCURP())) {
                    listaErrores.add(new ResultValidarDatos(fila, "CURP", "Formato de CURP inválido"));
                }

                // Validación de Fecha de Nacimiento
                if (usuariodireccion.usuarioD.getFechaDeNacimiento() == null) {
                    listaErrores.add(new ResultValidarDatos(fila, "Fecha de Nacimiento", "Campo obligatorio"));
                }

                // Validación de Sexo
                char sexo = usuariodireccion.usuarioD.getSexo();
                if (sexo != 'H' && sexo != 'M') {
                    listaErrores.add(new ResultValidarDatos(fila, "Sexo", "Valor inválido. Debe ser 'H' o 'M'"));
                }

                // Validación de Imagen
                if (usuariodireccion.usuarioD.getImagen() == null || usuariodireccion.usuarioD.getImagen().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, "Imagen", "Campo obligatorio"));
                }

                // Validación de Dirección
                if (usuariodireccion.direccionU == null || usuariodireccion.direccionU.getCalle() == null || usuariodireccion.direccionU.getCalle().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, "Calle", "Campo obligatorio"));
                }
                if (usuariodireccion.direccionU.getNumeroInterior() == null || usuariodireccion.direccionU.getNumeroInterior().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, "Número Interior", "Campo obligatorio"));
                }
                if (usuariodireccion.direccionU.getNumeroExterior() == null || usuariodireccion.direccionU.getNumeroExterior().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, "Número Exterior", "Campo obligatorio"));
                }

                // Validación de Colonia
                if (usuariodireccion.direccionU.colonia == null || usuariodireccion.direccionU.colonia.getIdColonia() == 0) {
                    listaErrores.add(new ResultValidarDatos(fila, "Colonia", "Campo obligatorio"));
                }
                fila++;
            }
        }

        return listaErrores;
    }

// Método para validar el formato del email
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }

    private boolean isValidPhoneNumber(String numero) {
        return numero.matches("^\\d{10}$");
    }

    private boolean isValidCURP(String curp) {
        String curpRegex = "^[A-Z]{4}[0-9]{6}[A-Z]{6}[0-9A-Z]{2}$";
        return curp.toUpperCase().matches(curpRegex);
    }

    @GetMapping("deleteUsuario")
    public String DeleteUsuario(@RequestParam int idUsuario) {

        Result result = usuarioDAOImplementatio.Delete(idUsuario);
        if (result.correct) {
            return "redirect:/usuario";
        } else {
            return "redirect:/usuario";
        }

    }

    @GetMapping("deleteDireccion")
    public String DeleteDireccion(@RequestParam int idDireccion) {

        Result result = direccionDAOImplementation.Delete(idDireccion);
        if (result.correct) {
            return "redirect:/usuario";
        } else {
            return "redirect:/UsuarioDetails";
        }

    }

    @GetMapping("/estados/{idPais}")
    @ResponseBody
    public Result Estado(@PathVariable("idPais") int Idpais) {

        return estadoDAOImplementation.GetAll(Idpais);
    }

    @GetMapping("/estados/municipios/{idEstado}")
    @ResponseBody
    public Result Municipios(@PathVariable("idEstado") int IdEstado) {
        return municipioDAOImplementation.GetById(IdEstado);
    }

    @GetMapping("/estados/municipios/colonias/{idMunicipio}")
    @ResponseBody
    public Result Colonias(@PathVariable("idMunicipio") int IdMunicipio) {
        return coloniaDAOImplementation.GetById(IdMunicipio);
    }

    @GetMapping("/isActivo")
    @ResponseBody
    public boolean isActivo(@RequestParam int idUsuario, @RequestParam int status) {
        Result result = usuarioDAOImplementatio.IsActivo(idUsuario, status);
        return result.correct;
    }
}
