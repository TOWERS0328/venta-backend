package com.ventas.ventas_backend.repository;

import com.ventas.ventas_backend.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    List<Venta> findByNombreContainingIgnoreCase(String nombre);

    @Query("SELECT SUM(v.total) FROM Venta v")
    BigDecimal sumTotalVentas();
}