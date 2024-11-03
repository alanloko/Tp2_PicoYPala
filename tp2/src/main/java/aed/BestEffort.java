package aed;

import java.util.ArrayList;

public class BestEffort {
    //Completar atributos privados
    Heap TrasladosPorAntiguedad;
    Heap TrasladosPorGanancia;
    int CiudadMasRedituable;
    ArrayList<Integer> CiudaddesConMayorGanancia;
    ArrayList<Integer> CiudaddesConMayorPerdida;
    int SumatoriaDeTraslados;
    int CantDeTraslados;


    public BestEffort(int cantCiudades, Traslado[] traslados){
        TrasladosPorAntiguedad.HeapMin(traslados);
        TrasladosPorAntiguedad.HeapMax(traslados);
        SumatoriaDeTraslados = 0;
        CantDeTraslados = 0;
    }

    public void registrarTraslados(Traslado[] traslados){
        TrasladosPorAntiguedad.AgregarElementos(traslados);
        TrasladosPorGanancia.AgregarElementos(traslados);
        CantDeTraslados ++;
    }

    public int[] despacharMasRedituables(int n){
        if(TrasladosPorGanancia.elementos > n) {
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
