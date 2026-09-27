package ventas.application;

import ventas.domain.Cliente;

public class ComandoCrearVenta {
    private final Cliente cliente;
    private final PoliticaDescuento politica;

    public ComandoCrearVenta(Cliente cliente, PoliticaDescuento politica) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente es obligatorio");
        }
        if (politica == null) {
            throw new IllegalArgumentException("La política de descuento es obligatoria");
        }
        this.cliente = cliente;
        this.politica = politica;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public PoliticaDescuento getPolitica() {
        return politica;
    }
}