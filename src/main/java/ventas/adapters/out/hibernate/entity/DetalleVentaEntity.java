package ventas.adapters.out.hibernate.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "detalle_venta")
public class DetalleVentaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_id", nullable = false)
    private VentaEntity venta;

    @Column(name = "producto_codigo", nullable = false)
    private Long productoCodigo;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", nullable = false, precision = 12, scale = 2)
    private BigDecimal precioUnitario;

    @Column(name = "subtotal", nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;

    public DetalleVentaEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public VentaEntity getVenta() { return venta; }
    public void setVenta(VentaEntity venta) { this.venta = venta; }
    public Long getProductoCodigo() { return productoCodigo; }
    public void setProductoCodigo(Long productoCodigo) { this.productoCodigo = productoCodigo; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}