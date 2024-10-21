package com.banco.taller2.Service;

import com.banco.taller2.Model.Cuenta;
import com.banco.taller2.Repository.CuentaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;

    public CuentaService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    public Cuenta crearCuenta(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    public Cuenta obtenerCuenta(String numeroCuenta) {
        return cuentaRepository.findById(numeroCuenta)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));
    }

    public List<Cuenta> obtenerTodasLasCuentas() {
        return cuentaRepository.findAll();
    }


    public BigDecimal obtenerSaldo(String numeroCuenta) {
        Cuenta cuenta = obtenerCuenta(numeroCuenta);
        return cuenta.getSaldo();
    }
}
