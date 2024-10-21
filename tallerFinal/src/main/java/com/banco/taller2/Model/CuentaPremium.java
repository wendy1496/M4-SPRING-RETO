package com.banco.taller2.Model;
import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
public class CuentaPremium extends Cuenta {

    public CuentaPremium() {
        this.setTipoCuenta("Premium");
    }


    @Override
    public void aplicarComision(BigDecimal monto, String tipoOperacion) {

    }
}