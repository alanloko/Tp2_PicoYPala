package aed;

import java.util.ArrayList;

public class BestEffort {
    // Completar atributos privados
    Heap<Traslado> TrasladosPorAntiguedad;
    Heap<Traslado> TrasladosPorGanancia;
    Heap<Ciudad> Redituabilidad;
    Ciudad[] Ciudades;
    ArrayList<Integer> CiudaddesConMayorGanancia;
    ArrayList<Integer> CiudaddesConMayorPerdida;
    int SumatoriaDeTraslados;
    int CantDeTraslados;
    int mayorGananciaActual;
    int mayorPerdidaActual;

    public BestEffort(int cantCiudades, Traslado[] traslados) {
        Ciudades = new Ciudad[cantCiudades];
        for (int i = 0; i < cantCiudades; i++) {
            Ciudad c = new Ciudad(i, 0, 0, 0);
            Ciudades[i] = c;
        }
        Redituabilidad = new Heap<>(Ciudades, true, new Comparador<>(true, true), true);
        TrasladosPorAntiguedad = new Heap<Traslado>(traslados, false, new Comparador<>(false, false), false);
        TrasladosPorGanancia = new Heap<Traslado>(traslados, true, new Comparador<>(true, false), false);
        SumatoriaDeTraslados = 0;
        CantDeTraslados = 0;
        CiudaddesConMayorGanancia = new ArrayList<>();
        CiudaddesConMayorPerdida = new ArrayList<>();
        mayorGananciaActual = 0;
        mayorPerdidaActual = 0;
    }

    public void registrarTraslados(Traslado[] traslados) {
        TrasladosPorAntiguedad.AgregarElementos(traslados);
        TrasladosPorGanancia.AgregarElementos(traslados);
    }

    public int[] despacharMasRedituables(int n) {
        if (n > TrasladosPorGanancia.elementos()) {
            n = TrasladosPorGanancia.elementos();
        } 
        int[] despacho = new int[n];
        int i = 0;
        while (n > 0 && TrasladosPorGanancia.elementos() > 0) {
            Traslado obj = TrasladosPorGanancia.pop();
            TrasladosPorAntiguedad.eliminarElemento(obj.IndexAntiguedad);
            Redituabilidad.modificarCiudades(Ciudades[obj.origen].IndexCiudad, obj.gananciaNeta, true);
            Redituabilidad.modificarCiudades(Ciudades[obj.destino].IndexCiudad, obj.gananciaNeta, false);
            modificarGanancia(obj.origen);
            modificarPerdida(obj.destino);
            despacho[i] = obj.id;
            SumatoriaDeTraslados += obj.gananciaNeta;
            CantDeTraslados++;
            i++;
            n--;
        }
        return despacho;
    }

    public int[] despacharMasAntiguos(int n) {
        if (n > TrasladosPorAntiguedad.elementos()) {
            n = TrasladosPorAntiguedad.elementos();
        } 
        int[] despacho = new int[n];
        int i = 0;
        while (n > 0 && TrasladosPorAntiguedad.elementos() > 0) {
            Traslado obj = TrasladosPorAntiguedad.pop();
            TrasladosPorGanancia.eliminarElemento(obj.IndexRedituable);
            despacho[i] = obj.id;
            SumatoriaDeTraslados += obj.gananciaNeta;
            CantDeTraslados++;
            Redituabilidad.modificarCiudades(Ciudades[obj.origen].IndexCiudad, obj.gananciaNeta, true);
            Redituabilidad.modificarCiudades(Ciudades[obj.destino].IndexCiudad, obj.gananciaNeta, false);
            modificarGanancia(obj.origen);
            modificarPerdida(obj.destino);
            i++;
            n--;
        }
        return despacho;
    }

    public void modificarPerdida(int destino) {
        if (CiudaddesConMayorPerdida.size() > 0) {
            if (Ciudades[destino].perdida > mayorPerdidaActual) {
                CiudaddesConMayorPerdida.clear();
                CiudaddesConMayorPerdida.add(Ciudades[destino].Ciudad);
                mayorPerdidaActual = Ciudades[destino].perdida;
            } else if (Ciudades[destino].perdida == mayorPerdidaActual) {
                CiudaddesConMayorPerdida.add(Ciudades[destino].Ciudad);
            }
        } else {
            CiudaddesConMayorPerdida.add(destino);
            mayorPerdidaActual = Ciudades[destino].perdida;
        }
    }

    public void modificarGanancia(int origen) {
        if (CiudaddesConMayorGanancia.size() > 0) {
            if (Ciudades[origen].ganancia > mayorGananciaActual) {
                CiudaddesConMayorGanancia.clear();
                CiudaddesConMayorGanancia.add(Ciudades[origen].Ciudad);
                mayorGananciaActual = Ciudades[origen].ganancia;
            } else if (Ciudades[origen].ganancia == mayorGananciaActual) {
                CiudaddesConMayorGanancia.add(Ciudades[origen].Ciudad);
            }
        } else {
            CiudaddesConMayorGanancia.add(origen);
            mayorGananciaActual = Ciudades[origen].ganancia;
        }
    }

    public int ciudadConMayorSuperavit() {
        return Redituabilidad.peak().Ciudad;
    }

    public ArrayList<Integer> ciudadesConMayorGanancia() {
        return CiudaddesConMayorGanancia;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida() {
        return CiudaddesConMayorPerdida;
    }

    public int gananciaPromedioPorTraslado() {
        return SumatoriaDeTraslados / CantDeTraslados;
    }

}
