package aed;

import java.util.ArrayList;

public class Estadisticas {
    ArrayList<Integer> CiudaddesConMayorGanancia;
    ArrayList<Integer> CiudaddesConMayorPerdida;
    int SumatoriaDeTraslados;
    int CantDeTraslados;
    int mayorGananciaActual;
    int mayorPerdidaActual;
    
    public Estadisticas() {
        SumatoriaDeTraslados = 0;
        CantDeTraslados = 0;
        CiudaddesConMayorGanancia = new ArrayList<>();
        CiudaddesConMayorPerdida = new ArrayList<>();
        mayorGananciaActual = 0;
        mayorPerdidaActual = 0;
    }
}
