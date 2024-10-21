package com.banco.taller2.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransaccionDTO {
    private String numeroCuenta;
    private BigDecimal monto;
    private String tipoTransaccion;
    private LocalDateTime fecha;

    // Constructor
    public TransaccionDTO(String numeroCuenta, BigDecimal monto, String tipo, LocalDateTime fecha) {
        this.numeroCuenta = numeroCuenta;
        this.monto = monto;
        this.tipoTransaccion = tipo;
        this.fecha = fecha;
    }

    // Getters y setters
    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }


    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
