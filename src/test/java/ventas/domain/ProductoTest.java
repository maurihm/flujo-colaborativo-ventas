package ventas.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductoTest {

    @Test
    void creaProductoValido() {
        Producto p = new Producto(1L, "Refresco", new Dinero(15.00), 10);

        assertEquals(1L, p.getCodigo());
        assertEquals("Refresco", p.getNombre());
        assertEquals(new Dinero(15.00), p.getPrecio());
        assertEquals(10, p.getExistencia());
    }

    @Test
    void rechazaPrecioNulo() {
        assertThrows(IllegalArgumentException.class, () ->
                new Producto(1L, "Refresco", null, 10)
        );
    }

    @Test void rechazaNombreVacio() {
        Dinero precio = new Dinero(10.00); assertThrows(IllegalArgumentException.class, () -> new Producto(1L, " ", precio, 5));
    }

    @Test void rechazaCodigoNoPositivo() {
        Dinero precio = new Dinero(10.00); assertThrows(IllegalArgumentException.class, () -> new Producto(0L, "Refresco", precio, 5));
    }

    @Test
    void rechazaExistenciaNegativaAlCrear() {
        Dinero precio = new Dinero(10.00);

        assertThrows(IllegalArgumentException.class, () ->
                new Producto(1L, "Refresco", precio, -3)
        );
    }

    @Test
    void descuentaExistenciaValida() {
        Producto p = new Producto(1L, "Refresco", new Dinero(15.00), 10);

        p.descontar(3);

        assertEquals(7, p.getExistencia());
    }

    @Test
    void rechazaDescuentoMayorQueExistencia() {
        Producto p = new Producto(1L, "Refresco", new Dinero(15.00), 10);

        assertThrows(IllegalArgumentException.class, () -> p.descontar(20));
    }

    @Test
    void rechazaCantidadCeroOMenor() {
        Producto p = new Producto(1L, "Refresco", new Dinero(15.00), 10);

        assertThrows(IllegalArgumentException.class, () -> p.descontar(0));
        assertThrows(IllegalArgumentException.class, () -> p.descontar(-5));
    }

    @Test
    void conservaExistenciaDespuesDeUnRechazo() {
        Producto p = new Producto(1L, "Refresco", new Dinero(15.00), 10);

        assertThrows(IllegalArgumentException.class, () -> p.descontar(50));

        assertEquals(10, p.getExistencia());
    }
    
    @Test
    void productoConCategoriaValidaRetornaCategoriaCorrecta() {
        Producto p = new Producto(10L, "Teclado", new Dinero(500.0), 10, Categoria.ELECTRONICA);
        org.junit.jupiter.api.Assertions.assertEquals(Categoria.ELECTRONICA, p.getCategoria());
    }

    @Test
    void productoSinCategoriaLanzaExcepcion() {
    Dinero precio = new Dinero(200.0);
    
    org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
        new Producto(11L, "Mouse", precio, 5, null);
    });
}
}
