package ventas.adapters.in.rest.dto;

import java.math.BigDecimal;

public class ProductoResponseDto {

    private Long codigo;
    private String nombre;
    private BigDecimal precio;
    private Integer existencia;

    public ProductoResponseDto() {
    }

    public ProductoResponseDto(Long codigo, String nombre, BigDecimal precio, Integer existencia) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.existencia = existencia;
    }

    public Long getCodigo() { return codigo; }
    public void setCodigo(Long codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public Integer getExistencia() { return existencia; }
    public void setExistencia(Integer existencia) { this.existencia = existencia; }
}
