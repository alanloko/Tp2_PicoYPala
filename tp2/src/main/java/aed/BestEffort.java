package aed;

import java.util.ArrayList;

public class BestEffort {
    //Completar atributos privados
    Heap TrasladosPorAntiguedad;
    Heap TrasladosPorGanancia;
    Heap Redituabilidad;
    ArrayList<Integer> PosicionEnAntiguedad;
    ArrayList<Integer> PosicionEnGanancia;
    int[] GananciaDeCiudades;
    int[] PerdidaDeCiudades;
    ArrayList<Integer> CiudaddesConMayorGanancia;
    ArrayList<Integer> CiudaddesConMayorPerdida;
    int SumatoriaDeTraslados;
    int CantDeTraslados;

    public BestEffort(int cantCiudades, Traslado[] traslados){
        PosicionEnGanancia = new ArrayList<>();
        PosicionEnAntiguedad = new ArrayList<>();
        GananciaDeCiudades = new int[cantCiudades];
        PerdidaDeCiudades = new int[cantCiudades];
        TrasladosPorAntiguedad = new Heap(traslados, false);
        TrasladosPorGanancia = new Heap(traslados,true);
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
        return Redituabilidad.pop();
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
