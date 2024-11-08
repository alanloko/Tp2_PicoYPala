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
        Heap<Ciudad> Redituabilidad = new Heap<>(null, true, new Comparador<>(true,true));
        GananciaDeCiudades = new int[cantCiudades];
        PerdidaDeCiudades = new int[cantCiudades];
        TrasladosPorAntiguedad = new Heap<Traslado>(traslados, false, new Comparador<>(true,false));
        TrasladosPorGanancia = new Heap<Traslado>(traslados,true,new Comparador<>(true,false));
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
        ArrayList<Integer> despacho = new ArrayList<Integer>();
        int i = 0;
        Traslado t;
        while(i <= n) {
            t = TrasladosPorGanancia.obtener(i);
            SumatoriaDeTraslados += t.gananciaNeta;
            despacho.add(t.id);
            TrasladosPorGanancia.eliminarElemento(i);
            TrasladosPorAntiguedad.eliminarElemento(i);
            i++;
        }
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
