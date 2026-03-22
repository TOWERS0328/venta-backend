package com.ventas.ventas_backend.service;

import com.ventas.ventas_backend.model.Venta;
import com.ventas.ventas_backend.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    public Venta crearVenta(Venta venta) {
        venta.calcularTotal();
        return ventaRepository.save(venta);
    }

    public List<Venta> obtenerTodas() {
        return ventaRepository.findAll();
    }

    public Optional<Venta> obtenerPorId(Long id) {
        return ventaRepository.findById(id);
    }

    public Venta actualizarVenta(Long id, Venta ventaActualizada) {
        Venta ventaExistente = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con id: " + id));

        ventaExistente.setNombre(ventaActualizada.getNombre());
        ventaExistente.setPrecio(ventaActualizada.getPrecio());
        ventaExistente.setCantidad(ventaActualizada.getCantidad());
        ventaExistente.calcularTotal();

        return ventaRepository.save(ventaExistente);
    }

    public void eliminarVenta(Long id) {
        if (!ventaRepository.existsById(id)) {
            throw new RuntimeException("Venta no encontrada con id: " + id);
        }
        ventaRepository.deleteById(id);
    }

    public List<Venta> buscarPorNombre(String nombre) {
        return ventaRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public BigDecimal totalGeneral() {
        BigDecimal total = ventaRepository.sumTotalVentas();
        return total != null ? total : BigDecimal.ZERO;
    }
}