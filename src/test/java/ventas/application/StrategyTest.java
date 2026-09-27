package ventas.application;

import org.junit.jupiter.api.Test;
import ventas.domain.Dinero;

import static org.junit.jupiter.api.Assertions.*;

class StrategyTest {

    @Test
    void probarSinDescuento() {
        PoliticaDescuento politica = new SinDescuento();
        Dinero subtotal = new Dinero(100.0);
        
        Dinero resultado = politica.aplicar(subtotal);
        
        assertEquals(100.0, resultado.getMonto(), 0.01);
        assertEquals("Sin descuento", politica.nombre());
    }

    @Test
    void probarDescuentoEstudiante() {
        PoliticaDescuento politica = new DescuentoEstudiante();
        Dinero subtotal = new Dinero(100.0);
        
        Dinero resultado = politica.aplicar(subtotal);
        
        assertEquals(90.0, resultado.getMonto(), 0.01); // 10% de descuento sobre 100
        assertEquals("Descuento Estudiante 10%", politica.nombre());
    }
}