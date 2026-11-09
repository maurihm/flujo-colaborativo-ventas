package ventas.adapters.in.rest.dto;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class CrearVentaRequestDto {

    @NotBlank(message = "El nombre del cliente no puede estar vacio")
    private String clienteNombre;

    @NotBlank(message = "El email del cliente es obligatorio")
    @Email(message = "El formato de email es invalido")
    private String clienteEmail;

    @NotEmpty(message = "La venta debe contener al menos un item")
    @Valid
    private List<ItemVentaDto> items;

    public CrearVentaRequestDto() {
    }

    public CrearVentaRequestDto(String clienteNombre, String clienteEmail, List<ItemVentaDto> items) {
        this.clienteNombre = clienteNombre;
        this.clienteEmail = clienteEmail;
        this.items = items;
    }

    public String getClienteNombre() { return clienteNombre; }
    public void setClienteNombre(String clienteNombre) { this.clienteNombre = clienteNombre; }

    public String getClienteEmail() { return clienteEmail; }
    public void setClienteEmail(String clienteEmail) { this.clienteEmail = clienteEmail; }

    public List<ItemVentaDto> getItems() { return items; }
    public void setItems(List<ItemVentaDto> items) { this.items = items; }
}
