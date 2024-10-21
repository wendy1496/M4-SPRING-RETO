package com.banco.taller2.Service;
import com.banco.taller2.DTO.TransaccionDTO;
import com.banco.taller2.Model.Cuenta;
import com.banco.taller2.Model.CuentaBasica;
import com.banco.taller2.Model.Transaccion;
import com.banco.taller2.Repository.TransaccionRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransaccionService {

    private final CuentaService cuentaService;
    private final TransaccionRepository transaccionRepository;

    public TransaccionService(CuentaService cuentaService, TransaccionRepository transaccionRepository) {
        this.cuentaService = cuentaService;
        this.transaccionRepository = transaccionRepository;
    }

    public void depositar(String numeroCuenta, BigDecimal monto, String origen) {
        // Verificar que el monto a depositar sea positivo
        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero.");
        }

        // Obtener la cuenta correspondiente al número de cuenta
        Cuenta cuenta = cuentaService.obtenerCuenta(numeroCuenta);
        if (cuenta == null) {
            throw new IllegalArgumentException("Cuenta no encontrada.");
        }

        // Aplicar comisiones si la cuenta es de tipo CuentaBásica
        if (cuenta instanceof CuentaBasica) {
            // Ajustar el monto según el origen
            if ("cajero".equalsIgnoreCase(origen)) {
                monto = monto.subtract(new BigDecimal("2.00"));
            } else if ("otra_cuenta".equalsIgnoreCase(origen)) {
                monto = monto.subtract(new BigDecimal("1.50"));
            }
        }

        // Asegurarse de que el monto ajustado no sea negativo
        if (monto.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El monto ajustado después de las comisiones no puede ser negativo.");
        }

        // Actualizar el saldo de la cuenta
        cuenta.setSaldo(cuenta.getSaldo().add(monto));

        // Guardar la cuenta actualizada
        cuentaService.crearCuenta(cuenta); // Asegúrate de que este método actualice la cuenta

        // Registrar la transacción
        registrarTransaccion(cuenta, "Depósito", monto);
    }

    public void retirar(String numeroCuenta, BigDecimal monto) {
        Cuenta cuenta = cuentaService.obtenerCuenta(numeroCuenta);
        if (cuenta instanceof CuentaBasica) {
            monto = monto.add(new BigDecimal("1.00"));
        }
        if (cuenta.getSaldo().compareTo(monto) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar el retiro.");
        }
        cuenta.setSaldo(cuenta.getSaldo().subtract(monto));
        cuentaService.crearCuenta(cuenta);

        registrarTransaccion(cuenta, "Retiro", monto);
    }

    public void comprarEnPaginaWeb(String numeroCuenta, BigDecimal monto) {
        Cuenta cuenta = cuentaService.obtenerCuenta(numeroCuenta);
        if (cuenta instanceof CuentaBasica) {
            monto = monto.add(new BigDecimal("5.00"));
        }
        if (cuenta.getSaldo().compareTo(monto) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar la compra en la web.");
        }
        cuenta.setSaldo(cuenta.getSaldo().subtract(monto));
        cuentaService.crearCuenta(cuenta);

        registrarTransaccion(cuenta, "Compra en Página Web", monto);
    }

    public List<TransaccionDTO> obtenerHistorial(String numeroCuenta) {
        List<Transaccion> transacciones = transaccionRepository.findByNumeroCuenta(numeroCuenta);
        return transacciones.stream()
                .map(transaccion -> new TransaccionDTO(
                        transaccion.getCuenta().getNumeroCuenta(),
                        transaccion.getMonto(),
                        transaccion.getTipo(),
                        transaccion.getFecha()))
                .collect(Collectors.toList());
    }

    private void registrarTransaccion(Cuenta cuenta, String tipo, BigDecimal monto) {
        Transaccion transaccion = new Transaccion();
        transaccion.setCuenta(cuenta);
        transaccion.setTipo(tipo);
        transaccion.setMonto(monto);
        transaccion.setFecha(LocalDateTime.now());
        transaccionRepository.save(transaccion);
    }
}
