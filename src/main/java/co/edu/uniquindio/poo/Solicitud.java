package co.edu.uniquindio.poo;

class Solicitud {
    private String codigo;
    private PrioridadSolicitud prioridad;
    private int duracion; // en minutos
 
    public Solicitud(String codigo, PrioridadSolicitud prioridad, int duracion) {
        this.codigo = codigo;
        this.prioridad = prioridad;
        this.duracion = duracion;
    }
 
    public String getCodigo() {
        return codigo;
    }
 
    public PrioridadSolicitud getPrioridad() {
        return prioridad;
    }
 
    public int getDuracion() {
        return duracion;
    }
 
    @Override
    public String toString() {
        return codigo + " " + prioridad + " " + duracion + " min";
    }
}
