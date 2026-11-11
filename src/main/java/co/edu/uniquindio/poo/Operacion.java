package co.edu.uniquindio.poo;

class Operacion {
    private int codigo;
    private EstadoOperacion estado;
 
    public Operacion(int codigo, EstadoOperacion estado) {
        this.codigo = codigo;
        this.estado = estado;
    }
 
    public int getCodigo() {
        return codigo;
    }
 
    public EstadoOperacion getEstado() {
        return estado;
    }
 
    public boolean estaConfirmada() {
        return estado == EstadoOperacion.CONFIRMADA;
    }
 
    @Override
    public String toString() {
        return codigo + " " + estado;
    }
}
