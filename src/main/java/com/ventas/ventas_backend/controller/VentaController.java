package com.ventas.ventas_backend.controller;

import com.ventas.ventas_backend.model.Venta;
import com.ventas.ventas_backend.service.VentaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "*")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<Venta>> obtenerTodas() {
        return ResponseEntity.ok(ventaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venta> obtenerPorId(@PathVariable Long id) {
        return ventaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Venta> crearVenta(@Valid @RequestBody Venta venta) {
        Venta nuevaVenta = ventaService.crearVenta(venta);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaVenta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Venta> actualizarVenta(
            @PathVariable Long id,
            @Valid @RequestBody Venta venta) {
        try {
            Venta actualizada = ventaService.actualizarVenta(id, venta);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminarVenta(@PathVariable Long id) {
        try {
            ventaService.eliminarVenta(id);
            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Venta eliminada correctamente");
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Venta>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(ventaService.buscarPorNombre(nombre));
    }

    @GetMapping("/total")
    public ResponseEntity<Map<String, BigDecimal>> totalGeneral() {
        Map<String, BigDecimal> respuesta = new HashMap<>();
        respuesta.put("totalGeneral", ventaService.totalGeneral());
        return ResponseEntity.ok(respuesta);
    }
}