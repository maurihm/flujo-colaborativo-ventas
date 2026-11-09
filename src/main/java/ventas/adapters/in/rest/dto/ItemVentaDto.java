package ventas.adapters.in.rest.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ItemVentaDto {

    @NotNull(message = "El codigo del producto es obligatorio")
    private Long codigoProducto;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser de al menos 1 unidad")
    private Integer cantidad;

    public ItemVentaDto() {
    }

    public ItemVentaDto(Long codigoProducto, Integer cantidad) {
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
    }

    public Long getCodigoProducto() { return codigoProducto; }
    public void setCodigoProducto(Long codigoProducto) { this.codigoProducto = codigoProducto; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
}
