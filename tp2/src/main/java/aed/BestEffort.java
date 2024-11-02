package aed;

import java.util.ArrayList;

public class BestEffort {
    //Completar atributos privados
    HeapMin TrasladosPorAntiguedad;
    HeapMax TrasladosPorGanancia;
    int CiudadMasRedituable;
    ArrayList<Integer> CiudaddesConMayorGanancia;
    ArrayList<Integer> CiudaddesConMayorPerdida;
    int SumatoriaDeTraslados;
    int CantDeTraslados;


    public BestEffort(int cantCiudades, Traslado[] traslados){
        // Implementar
    }

    public void registrarTraslados(Traslado[] traslados){
        // Implementar
    }

    public int[] despacharMasRedituables(int n){
        // Implementar
        return null;
    }

    public int[] despacharMasAntiguos(int n){
        // Implementar
        return null;
    }

    public int ciudadConMayorSuperavit(){
        return CiudadMasRedituable;
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){
        return CiudaddesConMayorGanancia;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){
        return CiudaddesConMayorPerdida;
    }

    public int gananciaPromedioPorTraslado(){
        return SumatoriaDeTraslados / CantDeTraslados;
    }
    
}
