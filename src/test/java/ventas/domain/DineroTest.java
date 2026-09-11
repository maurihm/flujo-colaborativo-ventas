package ventas.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DineroTest {

    @Test
    void creaDineroValido() {
        Dinero d = new Dinero(15.00);

        assertEquals(15.00, d.getMonto());
    }

    @Test
    void rechazaMontoNegativo() {
        assertThrows(IllegalArgumentException.class, () -> new Dinero(-5.00));
    }

    @Test
    void dosDinerosConElMismoMontoSonIguales() {
        assertEquals(new Dinero(15.00), new Dinero(15.00));
    }

    @Test
    void sumaDosMontos() {
        Dinero resultado = new Dinero(10.00).sumar(new Dinero(5.00));

        assertEquals(new Dinero(15.00), resultado);
    }

    @Test
    void multiplicaPorUnFactor() {
        Dinero resultado = new Dinero(10.00).multiplicar(3);

        assertEquals(new Dinero(30.00), resultado);
    }
}
