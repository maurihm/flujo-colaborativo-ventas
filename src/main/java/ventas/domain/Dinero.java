package ventas.domain;

import java.util.Objects;

// Objeto valor: representa un monto de dinero. Es inmutable y se compara por su
// valor (dos montos iguales se consideran el mismo Dinero), no por identidad.
public final class Dinero {

    public static final Dinero CERO = new Dinero(0);

    private final double monto;

    public Dinero(double monto) {
        if (monto < 0) {
            throw new IllegalArgumentException("El monto no puede ser negativo");
        }
        this.monto = monto;
    }

    public Dinero sumar(Dinero otro) {
        return new Dinero(this.monto + otro.monto);
    }

    public Dinero multiplicar(int factor) {
        return new Dinero(this.monto * factor);
    }

    public double getMonto() {
        return monto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dinero)) return false;
        Dinero dinero = (Dinero) o;
        return Double.compare(monto, dinero.monto) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(monto);
    }

    @Override
    public String toString() {
        return String.valueOf(monto);
    }
}
