package ventas.adapters.in.swing;

import ventas.application.ComandoRegistrarProducto;
import ventas.application.RegistrarProductoService;
import ventas.application.VentaFacade;
import ventas.domain.Cliente;
import ventas.domain.Dinero;
import ventas.domain.Producto;
import ventas.domain.Venta;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class VentaSwingController {
    private final RegistrarProductoService registrarService;
    private final VentaFacade ventaFacade;

    public VentaSwingController(RegistrarProductoService registrarService, VentaFacade ventaFacade) {
        this.registrarService = registrarService;
        this.ventaFacade = ventaFacade;
    }

    public void registrarProductoAsync(FormularioProductoPanel form, ProductoTableModel model, JFrame parent) {
        try {
            form.validarEntradas();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(parent, ex.getMessage(), "Validación Fallida", JOptionPane.WARNING_MESSAGE);
            return;
        }
        form.getBtnRegistrar().setEnabled(false);
        SwingWorker<Producto, Void> worker = new SwingWorker<>() {
            @Override
            protected Producto doInBackground() throws Exception {
                Thread.sleep(300); // simulación leve de latencia fuera del EDT
                ComandoRegistrarProducto cmd = new ComandoRegistrarProducto(
                        form.getCodigo(), form.getNombre(), new Dinero(form.getPrecio()),
                        form.getStock(), form.getCategoria()
                );
                return registrarService.registrar(cmd);
            }

            @Override
            protected void done() {
                form.getBtnRegistrar().setEnabled(true);
                try {
                    Producto nuevo = get();
                    model.agregarProducto(nuevo);
                    form.limpiar();
                    JOptionPane.showMessageDialog(parent, "Producto " + nuevo.getNombre() + " registrado!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (InterruptedException | ExecutionException e) {
                    JOptionPane.showMessageDialog(parent, "Error al registrar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }

    public void procesarVentaAsync(CarritoPanel carrito, JFrame parent) {
        List<ItemCarrito> items = carrito.getItems();
        if (items.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "El carrito está vacío.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        carrito.getBtnConfirmarVenta().setEnabled(false);
        SwingWorker<Venta, Void> worker = new SwingWorker<>() {
            @Override
            protected Venta doInBackground() throws Exception {
                Thread.sleep(400); // trabajo en segundo plano fuera del EDT
                Cliente clienteGeneral = new Cliente(1L, "Público", "General", "0000000000");
                List<Producto> prods = new ArrayList<>();
                List<Integer> cants = new ArrayList<>();
                for (ItemCarrito it : items) {
                    prods.add(it.getProducto());
                    cants.add(it.getCantidad());
                }
                return ventaFacade.procesarVentaCompleta(clienteGeneral, prods, cants);
            }

            @Override
            protected void done() {
                carrito.getBtnConfirmarVenta().setEnabled(true);
                try {
                    Venta venta = get();
                    carrito.vaciar();
                    JOptionPane.showMessageDialog(parent,
                            "¡Venta confirmada exitosamente!\nFolio: " + venta.getId() + "\nTotal: $" + venta.calcularTotal().getMonto(),
                            "Venta Exitosa", JOptionPane.INFORMATION_MESSAGE);
                } catch (InterruptedException | ExecutionException e) {
                    JOptionPane.showMessageDialog(parent, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }
}