package ventas.adapters.out.hibernate.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "venta")
public class VentaEntity {
    @Id
    @Column(name = "id", length = 50)
    private String id;

    @Column(name = "cliente_codigo", nullable = false)
    private Long clienteCodigo;

    @Column(name = "cliente_nombre", nullable = false, length = 100)
    private String clienteNombre;

    @Column(name = "cliente_apellido", nullable = false, length = 100)
    private String clienteApellido;

    @Column(name = "cliente_telefono", nullable = false, length = 20)
    private String clienteTelefono;

    @Column(name = "total", nullable = false, precision = 12, scale = 2)
    private BigDecimal total;

    @Column(name = "estado", nullable = false, length = 30)
    private String estado;

    @Column(name = "fecha", nullable = false)
    private Timestamp fecha;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DetalleVentaEntity> detalles = new ArrayList<>();

    public VentaEntity() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Long getClienteCodigo() { return clienteCodigo; }
    public void setClienteCodigo(Long clienteCodigo) { this.clienteCodigo = clienteCodigo; }
    public String getClienteNombre() { return clienteNombre; }
    public void setClienteNombre(String clienteNombre) { this.clienteNombre = clienteNombre; }
    public String getClienteApellido() { return clienteApellido; }
    public void setClienteApellido(String clienteApellido) { this.clienteApellido = clienteApellido; }
    public String getClienteTelefono() { return clienteTelefono; }
    public void setClienteTelefono(String clienteTelefono) { this.clienteTelefono = clienteTelefono; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Timestamp getFecha() { return fecha; }
    public void setFecha(Timestamp fecha) { this.fecha = fecha; }
    public List<DetalleVentaEntity> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleVentaEntity> detalles) { this.detalles = detalles; }
}