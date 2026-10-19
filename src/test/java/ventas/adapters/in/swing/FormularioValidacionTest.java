package ventas.adapters.in.swing;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FormularioValidacionTest {

    @Test
    void testValidacionFallaConCamposVacios() {
        FormularioProductoPanel form = new FormularioProductoPanel();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, form::validarEntradas);
        assertTrue(ex.getMessage().contains("obligatorios"));
    }
}