package aed;

import java.util.ArrayList;

public class BestEffort {
    //Completar atributos privados
    Heap<Traslado> TrasladosPorAntiguedad;
    Heap<Traslado> TrasladosPorGanancia;
    Heap<Ciudad> Redituabilidad;
    int[] GananciaDeCiudades;
    int[] PerdidaDeCiudades;
    ArrayList<Integer> CiudaddesConMayorGanancia;
    ArrayList<Integer> CiudaddesConMayorPerdida;
    int SumatoriaDeTraslados;
    int CantDeTraslados;

    public BestEffort(int cantCiudades, Traslado[] traslados){
        Ciudad[] c = new Ciudad[0];
        Heap<Ciudad> Redituabilidad = new Heap<>(c, true, new Comparador<>(true,true),  true);
        GananciaDeCiudades = new int[cantCiudades];
        PerdidaDeCiudades = new int[cantCiudades];
        TrasladosPorAntiguedad = new Heap<Traslado>(traslados, false, new Comparador<>(true,false), false);
        TrasladosPorGanancia = new Heap<Traslado>(traslados,true,new Comparador<>(true,false), false);
        SumatoriaDeTraslados = 0;
        CantDeTraslados = 0;
    }

    public void registrarTraslados(Traslado[] traslados){
        TrasladosPorAntiguedad.AgregarElementos(traslados);
        TrasladosPorGanancia.AgregarElementos(traslados);
        CantDeTraslados ++;
    }

    public int[] despacharMasRedituables(int n){
        if(TrasladosPorGanancia.elementos() > n) {
            return TrasladosPorGanancia.listaOrdenada();
        }
        int[] despacho = new int[n];
        int i = 0;
        Traslado t;
        
        return null;
    }

    public int[] despacharMasAntiguos(int n){
        // Implementar
        return null;
    }

    public int ciudadConMayorSuperavit(){
        return Redituabilidad.pop().Redituabilidad;
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
