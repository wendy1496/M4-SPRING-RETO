package com.banco.taller2.Controller;
import com.banco.taller2.DTO.CuentaDTO; // Importar el DTO
import com.banco.taller2.Model.Cuenta;
import com.banco.taller2.Model.CuentaBasica;
import com.banco.taller2.Model.CuentaPremium;
import com.banco.taller2.Service.CuentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/cuenta")
public class CuentaController {

    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @PostMapping("/crear")
    public Cuenta crearCuenta(@RequestBody CuentaDTO cuentaDTO) {
        Cuenta cuenta;

        if ("Básica".equalsIgnoreCase(cuentaDTO.getTipoCuenta())) {
            cuenta = new CuentaBasica();
        } else if ("Premium".equalsIgnoreCase(cuentaDTO.getTipoCuenta())) {
            cuenta = new CuentaPremium();
        } else {
            throw new IllegalArgumentException("Tipo de cuenta no válido: " + cuentaDTO.getTipoCuenta());
        }
        cuenta.setNumeroCuenta(cuentaDTO.getNumeroCuenta());
        cuenta.setSaldo(cuentaDTO.getSaldo());

        return cuentaService.crearCuenta(cuenta);
    }

    @GetMapping("/{numeroCuenta}")
    public Cuenta obtenerCuenta(@PathVariable String numeroCuenta) {
        return cuentaService.obtenerCuenta(numeroCuenta);
    }

    @GetMapping("/all")
    public List<Cuenta> getAll() {
        return cuentaService.obtenerTodasLasCuentas();
    }

    @GetMapping("/{numeroCuenta}/saldo")
    public ResponseEntity<String> obtenerSaldo(@PathVariable String numeroCuenta){
            try {
                BigDecimal saldo = cuentaService.obtenerSaldo(numeroCuenta);
                return ResponseEntity.ok("El saldo de la cuenta es: $" + saldo);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cuenta no encontrada");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
            }
        }
}