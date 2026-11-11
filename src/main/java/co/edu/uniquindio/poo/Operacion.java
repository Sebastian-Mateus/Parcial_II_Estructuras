package co.edu.uniquindio.poo;

public class Operacion {
    private int codigo;
    private EstadoOperacion estado;
 
    public Operacion(int codigo, EstadoOperacion estado) {
        this.codigo = codigo;
        this.estado = estado;
    }
 
    public boolean estaConfirmada() {
        return estado == EstadoOperacion.CONFIRMADA;
    }
 
    @Override
    public String toString() {
        return codigo + " " + estado;
    }
}
