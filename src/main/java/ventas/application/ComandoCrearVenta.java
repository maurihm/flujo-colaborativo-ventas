package ventas.application;

import ventas.domain.Cliente;

public class ComandoCrearVenta {
    private final Cliente cliente;

    public ComandoCrearVenta(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }
}