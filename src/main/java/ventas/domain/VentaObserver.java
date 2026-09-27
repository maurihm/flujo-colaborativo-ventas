package ventas.domain;

public interface VentaObserver {
    void alConfirmar(VentaConfirmada evento);
}
