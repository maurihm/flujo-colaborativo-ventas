package ventas.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    @Test
    void creaClienteValidoConTelefono() {
        Cliente c = new Cliente(1L, "Juan", "Perez", "2281234567");

        assertEquals(1L, c.getCodigo());
        assertEquals("Juan", c.getNombre());
        assertEquals("Perez", c.getApellido());
        assertEquals("2281234567", c.getTelefono());
    }

    @Test
    void creaClienteValidoSinTelefono() {
        Cliente c = new Cliente(2L, "Ana", "Lopez", null);

        assertEquals("Ana", c.getNombre());
        assertNull(c.getTelefono());
    }

    @Test void rechazaCodigoNoPositivo() {
        assertThrows(IllegalArgumentException.class, () -> new Cliente(0L, "Juan", "Perez", null));
    }

    @Test
    void rechazaNombreVacio() {
        assertThrows(IllegalArgumentException.class, () ->
                new Cliente(1L, " ", "Perez", null)
        );
    }

    @Test
    void rechazaNombreNulo() {
        assertThrows(IllegalArgumentException.class, () ->
                new Cliente(1L, null, "Perez", null)
        );
    }

    @Test
    void rechazaApellidoVacio() {
        assertThrows(IllegalArgumentException.class, () ->
                new Cliente(1L, "Juan", " ", null)
        );
    }

    @Test
    void rechazaApellidoNulo() {
        assertThrows(IllegalArgumentException.class, () ->
                new Cliente(1L, "Juan", null, null)
        );
    }

    @Test
    void getNombreCompletoConcatenaNombreYApellido() {
        Cliente c = new Cliente(1L, "Juan", "Perez", null);

        assertEquals("Juan Perez", c.getNombreCompleto());
    }
}