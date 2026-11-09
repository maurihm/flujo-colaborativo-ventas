package ventas.adapters.in.swing;

import ventas.application.ComandoRegistrarProducto;
import ventas.application.RegistrarProductoService;
import ventas.application.VentaFacade;
import ventas.domain.Cliente;
import ventas.domain.Dinero;
import ventas.domain.Producto;
import ventas.domain.Venta;
import javax.swing.*;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class VentaSwingController {
    private final RegistrarProductoService registrarService;
    private final VentaFacade ventaFacade;
    
    private static final String ERROR_TITLE = "Error"; 

    public VentaSwingController(RegistrarProductoService registrarService, VentaFacade ventaFacade) {
        this.registrarService = registrarService;
        this.ventaFacade = ventaFacade;
    }

    public void registrarProductoAsync(FormularioProductoPanel form, ProductoTableModel model, JFrame parent) {
        try {
            form.validarEntradas();
        } catch (IllegalArgumentException ex) {
            mostrarMensaje(parent, ex.getMessage(), "Validación Fallida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ComandoRegistrarProducto cmd = new ComandoRegistrarProducto(
                form.getCodigo(), form.getNombre(), new Dinero(form.getPrecio()),
                form.getStock(), form.getCategoria()
        );

        form.getBtnRegistrar().setEnabled(false);
        SwingWorker<Producto, Void> worker = new SwingWorker<>() {
            @Override
            protected Producto doInBackground() throws Exception {
                return registrarService.registrar(cmd);
            }

            @Override
            protected void done() {
                form.getBtnRegistrar().setEnabled(true);
                try {
                    Producto nuevo = get();
                    model.agregarProducto(nuevo);
                    form.limpiar();
                    mostrarMensaje(parent, "Producto " + nuevo.getNombre() + " registrado!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    mostrarMensaje(parent, "Error al registrar: " + e.getMessage(), ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
                } catch (ExecutionException e) {
                    mostrarMensaje(parent, "Error al registrar: " + e.getMessage(), ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }

    public void procesarVentaAsync(CarritoPanel carrito, JFrame parent) {
        List<ItemCarrito> items = carrito.getItems();
        if (items.isEmpty()) {
            mostrarMensaje(parent, "El carrito está vacío.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Cliente clienteGeneral = new Cliente(1L, "Público", "General", "0000000000");
        List<Producto> prods = new ArrayList<>();
        List<Integer> cants = new ArrayList<>();
        for (ItemCarrito it : items) {
            prods.add(it.getProducto());
            cants.add(it.getCantidad());
        }

        carrito.getBtnConfirmarVenta().setEnabled(false);
        SwingWorker<Venta, Void> worker = new SwingWorker<>() {
            @Override
            protected Venta doInBackground() throws Exception {
                return ventaFacade.procesarVentaCompleta(clienteGeneral, prods, cants);
            }

            @Override
            protected void done() {
                carrito.getBtnConfirmarVenta().setEnabled(true);
                try {
                    Venta venta = get();
                    carrito.vaciar();
                    mostrarMensaje(parent,
                            "¡Venta confirmada exitosamente!\nFolio: " + venta.getId() + "\nTotal: $" + venta.calcularTotal().getMonto(),
                            "Venta Exitosa", JOptionPane.INFORMATION_MESSAGE);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    mostrarMensaje(parent, "Error: " + e.getMessage(), ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
                } catch (ExecutionException e) {
                    mostrarMensaje(parent, "Error: " + e.getMessage(), ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }

    protected void mostrarMensaje(Component parent, String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(parent, mensaje, titulo, tipo);
    }
}
