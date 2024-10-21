package com.banco.taller2.Controller;

import com.banco.taller2.DTO.TransaccionDTO;
import com.banco.taller2.Model.Transaccion;
import com.banco.taller2.Service.TransaccionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transaccion")
public class TransaccionController {

    private final TransaccionService transaccionService;

    public TransaccionController(TransaccionService transaccionService) {
        this.transaccionService = transaccionService;
    }

    @PostMapping("/depositar")
    public ResponseEntity<String> depositar(@RequestBody TransaccionDTO transaccionDTO) {
        try {
            transaccionService.depositar(transaccionDTO.getNumeroCuenta(), transaccionDTO.getMonto(), "DEPOSITO");
            return ResponseEntity.ok("Depósito realizado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al realizar el depósito: " + e.getMessage());
        }
    }

    @PostMapping("/retirar")
    public String retirar(@RequestBody TransaccionDTO transaccionDTO) {
        transaccionService.retirar(transaccionDTO.getNumeroCuenta(), transaccionDTO.getMonto());
        return "Retiro realizado correctamente.";
    }

    @PostMapping("/comprarWeb")
    public String comprarWeb(@RequestBody TransaccionDTO transaccionDTO) {
        transaccionService.comprarEnPaginaWeb(transaccionDTO.getNumeroCuenta(), transaccionDTO.getMonto());
        return "Compra en página web realizada correctamente.";
    }


    @GetMapping("/{numeroCuenta}")
    public ResponseEntity<List<TransaccionDTO>> obtenerHistorial(@PathVariable String numeroCuenta) {
        List<TransaccionDTO> historial = transaccionService.obtenerHistorial(numeroCuenta);
        if (historial.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(historial);
    }
}
