package ventas.adapters.in.rest.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ProductoRequestDto {

    @NotNull(message = "El codigo es obligatorio")
    @Min(value = 1, message = "El codigo debe ser un numero positivo")
    private Long codigo;

    @NotBlank(message = "El nombre no puede estar vacio")
    private String nombre;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a cero")
    private BigDecimal precio;

    @NotNull(message = "La existencia es obligatoria")
    @Min(value = 0, message = "La existencia no puede ser negativa")
    private Integer existencia;

    public ProductoRequestDto() {
    }

    public ProductoRequestDto(Long codigo, String nombre, BigDecimal precio, Integer existencia) {
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
