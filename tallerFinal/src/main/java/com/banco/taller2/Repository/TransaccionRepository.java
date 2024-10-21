package com.banco.taller2.Repository;

import com.banco.taller2.Model.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
    @Query("SELECT t FROM Transaccion t WHERE t.cuenta.numeroCuenta = :numeroCuenta ORDER BY t.fecha DESC")
    List<Transaccion> findByNumeroCuenta(String numeroCuenta);
}
