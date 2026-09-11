package ventas.domain;

// Invariante: el codigo debe ser mayor que cero y nombre, pellido no pueden ser nulos ni vacíos.

public class Cliente {

    private final long codigo;
    private final String nombre;
    private final String apellido;
    private final String telefono;

    public Cliente(long codigo, String nombre, String apellido, String telefono) {
        if (codigo <= 0 || nombre == null || nombre.isBlank()
                || apellido == null || apellido.isBlank()) {
            throw new IllegalArgumentException("Datos de cliente inválidos");
        }
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
    }

    public long getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
}