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
        // Inicializamos las ciudades, todas con ganancia y perdida en 0
        Ciudades = new Ciudad[cantCiudades];
        for (int i = 0; i < cantCiudades; i++) {
            Ciudad c = new Ciudad(i, 0, 0, 0);
            Ciudades[i] = c;
        }
        // Ya con la lista de Ciudades y traslados, inicializamos los Heaps
        Redituabilidad = new Heap<>(Ciudades, true, new Comparador<>(true, true), true);
        TrasladosPorAntiguedad = new Heap<Traslado>(traslados, false, new Comparador<>(false, false), false);
        TrasladosPorGanancia = new Heap<Traslado>(traslados, true, new Comparador<>(true, false), false);
        // Variables Varias necesarias para el llamado de ciertas funciones
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
        if (n > TrasladosPorGanancia.elementos()) { // si n es mayor, no queremos sobrepasarnos de memoria
            n = TrasladosPorGanancia.elementos();   // asignamos n a la cantidad de traslados totales
        } 
        int[] despacho = new int[n];
        int i = 0;
        while (n > 0 && TrasladosPorGanancia.elementos() > 0) {
            Traslado obj = TrasladosPorGanancia.obtener(0);
            TrasladosPorGanancia.eliminarElemento(0);
            TrasladosPorAntiguedad.eliminarElemento(obj.IndexAntiguedad);
            modificarCiudades(Ciudades[obj.origen].IndexCiudad, obj.gananciaNeta, true); // Actualizacion de la cola de Prioridad Redituabilidad,
            modificarCiudades(Ciudades[obj.destino].IndexCiudad, obj.gananciaNeta, false); // Segun ganancian y perdida de 2 objetos diferentes
            modificarGanancia(obj.origen); // Actualización de las listas de mayor ganancia y perdida
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
        if (n > TrasladosPorAntiguedad.elementos()) { // si n es mayor, no queremos sobrepasarnos de memoria
            n = TrasladosPorAntiguedad.elementos();   // asignamos n a la cantidad de traslados totales
        } 
        int[] despacho = new int[n];
        int i = 0;
        while (n > 0 && TrasladosPorAntiguedad.elementos() > 0) {
            Traslado obj = TrasladosPorAntiguedad.obtener(0);
            TrasladosPorAntiguedad.eliminarElemento(0);
            TrasladosPorGanancia.eliminarElemento(obj.IndexRedituable);
            modificarCiudades(Ciudades[obj.origen].IndexCiudad, obj.gananciaNeta, true); // Actualizacion de la cola de Prioridad Redituabilidad,
            modificarCiudades(Ciudades[obj.destino].IndexCiudad, obj.gananciaNeta, false); // Segun ganancia y perdida de 2 objetos diferentes
            modificarGanancia(obj.origen); // Actualización de las listas de mayor ganancia y perdida
            modificarPerdida(obj.destino);
            despacho[i] = obj.id;
            SumatoriaDeTraslados += obj.gananciaNeta;
            CantDeTraslados++;
            i++;
            n--;
        }
        return despacho;
    }

    public void modificarPerdida(int destino) {
        if (CiudaddesConMayorPerdida.size() > 0) { // si no es mayor a 0, es la primer ciudad ingresada
            if (Ciudades[destino].perdida > mayorPerdidaActual) { // si es mayor, eliminamos la lista y empezamos una nueva con el nuevo valor mas alto
                CiudaddesConMayorPerdida.clear();
                CiudaddesConMayorPerdida.add(Ciudades[destino].Ciudad);
                mayorPerdidaActual = Ciudades[destino].perdida;
            } else if (Ciudades[destino].perdida == mayorPerdidaActual) { // si es igual, la agregamos a la lista
                CiudaddesConMayorPerdida.add(Ciudades[destino].Ciudad);
            }
        } else {
            CiudaddesConMayorPerdida.add(destino);
            mayorPerdidaActual = Ciudades[destino].perdida;
        }
    }

    public void modificarGanancia(int origen) {
        if (CiudaddesConMayorGanancia.size() > 0) { // si no es mayor a 0, es la primer ciudad ingresada
            if (Ciudades[origen].ganancia > mayorGananciaActual) { // si es mayor, eliminamos la lista y empezamos una nueva con el nuevo valor mas alto
                CiudaddesConMayorGanancia.clear();
                CiudaddesConMayorGanancia.add(Ciudades[origen].Ciudad);
                mayorGananciaActual = Ciudades[origen].ganancia;
            } else if (Ciudades[origen].ganancia == mayorGananciaActual) { // si es igual, la agregamos a la lista
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

    // aux 
    public void modificarCiudades(int indice, int credito, boolean esGanancia) {
        if (esGanancia) { // Si es ganancia, puede ser que sea mayor a su padre, por lo que llamo a siftUp
            Ciudad obj = Redituabilidad.obtener(indice);
            obj.Redituabilidad += credito;
            obj.ganancia += credito;
            Redituabilidad.siftUp(Redituabilidad.obtener(indice), indice);
        } else { // Si es perdida, puede ser que sea menor a alguno de sus hijos, por lo que llamo a siftDown
            Ciudad obj = Redituabilidad.obtener(indice);
            obj.Redituabilidad -= credito;
            obj.perdida += credito;
            Redituabilidad.siftDown(Redituabilidad.obtener(indice), indice);
        }
    }

}
