package com.banco.taller2.Model;

import jakarta.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class CuentaBasica extends Cuenta {

    public CuentaBasica() {
        this.setTipoCuenta("Básica");
    }

    @Override
    public void aplicarComision(BigDecimal monto, String tipoOperacion) {
        switch (tipoOperacion) {
            case "deposito_cajero":
                this.setSaldo(this.getSaldo().subtract(BigDecimal.valueOf(2)));
                break;
            case "retiro_cajero":
                this.setSaldo(this.getSaldo().subtract(BigDecimal.valueOf(1)));
                break;
            case "compra_web":
                this.setSaldo(this.getSaldo().subtract(BigDecimal.valueOf(5)));
                break;
        }
    }
}
