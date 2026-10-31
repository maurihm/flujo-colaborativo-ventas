package ventas.adapters.out.hibernate.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "producto")
public class ProductoEntity {
    @Id
    @Column(name = "codigo")
    private Long codigo;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "precio", nullable = false, precision = 12, scale = 2)
    private BigDecimal precio;

    @Column(name = "existencia", nullable = false)
    private Integer existencia;

    @Column(name = "categoria", nullable = false, length = 50)
    private String categoria;

    public ProductoEntity() {}

    public ProductoEntity(Long codigo, String nombre, BigDecimal precio, Integer existencia, String categoria) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.existencia = existencia;
        this.categoria = categoria;
    }

    public Long getCodigo() { return codigo; }
    public void setCodigo(Long codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    public Integer getExistencia() { return existencia; }
    public void setExistencia(Integer existencia) { this.existencia = existencia; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}