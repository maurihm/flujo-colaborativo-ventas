package ventas.adapters.in.rest.dto;

import java.math.BigDecimal;

public class VentaResponseDto {

    private String id;
    private String cliente;
    private BigDecimal total;
    private String estado;
    private int detallesCount;

    public VentaResponseDto() {
    }

    public VentaResponseDto(String id, String cliente, BigDecimal total, String estado, int detallesCount) {
        this.id = id;
        this.cliente = cliente;
        this.total = total;
        this.estado = estado;
        this.detallesCount = detallesCount;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public int getDetallesCount() { return detallesCount; }
    public void setDetallesCount(int detallesCount) { this.detallesCount = detallesCount; }
}
