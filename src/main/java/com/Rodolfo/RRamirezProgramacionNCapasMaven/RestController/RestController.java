package com.Rodolfo.RRamirezProgramacionNCapasMaven.RestController;

import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/demoapi")
public class RestController {

    @GetMapping("/holaMundo")
    public ResponseEntity DemoApi() {
        return ResponseEntity.ok().body("Hola Mundo");
    }

    @GetMapping("/sumaDeDosNumeros")
    public ResponseEntity SumaDeDosNumero(@RequestParam int numeroUno, @RequestParam int numeroDos) {
        int resultado = numeroUno + numeroDos;
        return ResponseEntity.ok().body("Suma: " + resultado);
    }

    @PostMapping("/sumaDeNValores")
    public ResponseEntity<String> SumaDeNValores(@RequestBody List<Integer> numeros) {
        int resultado = numeros.stream().mapToInt(Integer::intValue).sum();
        return ResponseEntity.ok("Resultado: " + resultado);
    }

    int[] lista = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};

    @PatchMapping("/actualizarNumero")
    public ResponseEntity ActualizarValor(@RequestBody Map<String, Integer> valores) {

        int posicion = valores.get("key");
        int valorNuevo = valores.get("value");
        if (posicion < 0 || posicion > lista.length - 1) {
            return ResponseEntity.badRequest().body("Posicion fuera del rango (0 - " + lista.length + ")");
        }
        if (valorNuevo == lista[posicion]) {
            return ResponseEntity.badRequest().body("El valor nuevo no puede ser el mismo");
        }

        lista[posicion] = valorNuevo;

        return ResponseEntity.ok(lista);
    }

    @GetMapping
    public ResponseEntity<int[]> obtenerLista() {
        return ResponseEntity.ok(lista);
    }
}
