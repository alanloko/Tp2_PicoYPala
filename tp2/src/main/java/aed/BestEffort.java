package aed;

import java.util.ArrayList;

public class BestEffort {
    // Completar atributos privados
    Heap<Traslado> TrasladosPorAntiguedad;
    Heap<Traslado> TrasladosPorGanancia;
    Heap<Ciudad> Redituabilidad;
    Ciudad[] Ciudades;
    Estadisticas stats;

    public BestEffort(int cantCiudades, Traslado[] traslados) {
        // Inicializamos las ciudades, todas con ganancia y perdida en 0
        Ciudades = new Ciudad[cantCiudades];
        for (int i = 0; i < cantCiudades; i++) { // O(|C|)
            Ciudad c = new Ciudad(i, 0, 0, 0);
            Ciudades[i] = c;
        }
        // Ya con la lista de Ciudades y traslados, inicializamos los Heaps
        Redituabilidad = new Heap<>(Ciudades, true, new Comparador<>(true, true), true); // O(|C|)
        TrasladosPorAntiguedad = new Heap<Traslado>(traslados, false, new Comparador<>(false, false), false); // O(|T|)
        TrasladosPorGanancia = new Heap<Traslado>(traslados, true, new Comparador<>(true, false), false); // O(|T|)
        // Inicialización de Estadisticas
        stats = new Estadisticas(); // O(1)
        // O(|C| + |C| + |T| + |T| + 1) -> O(|C| + |T|)
    }

    public void registrarTraslados(Traslado[] traslados) {
        TrasladosPorAntiguedad.AgregarElementos(traslados); //
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
            stats.SumatoriaDeTraslados += obj.gananciaNeta;
            stats.CantDeTraslados++;
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
            stats.SumatoriaDeTraslados += obj.gananciaNeta;
            stats.CantDeTraslados++;
            i++;
            n--;
        }
        return despacho;
    }

    public void modificarPerdida(int destino) {
        if (stats.CiudaddesConMayorPerdida.size() > 0) { // si no es mayor a 0, es la primer ciudad ingresada
            if (Ciudades[destino].perdida > stats.mayorPerdidaActual) { // si es mayor, eliminamos la lista y empezamos una nueva con el nuevo valor mas alto
                stats.CiudaddesConMayorPerdida.clear();
                stats.CiudaddesConMayorPerdida.add(Ciudades[destino].Ciudad);
                stats.mayorPerdidaActual = Ciudades[destino].perdida;
            } else if (Ciudades[destino].perdida == stats.mayorPerdidaActual) { // si es igual, la agregamos a la lista
                stats.CiudaddesConMayorPerdida.add(Ciudades[destino].Ciudad);
            }
        } else {
            stats.CiudaddesConMayorPerdida.add(destino);
            stats.mayorPerdidaActual = Ciudades[destino].perdida;
        }
    }

    public void modificarGanancia(int origen) {
        if (stats.CiudaddesConMayorGanancia.size() > 0) { // si no es mayor a 0, es la primer ciudad ingresada
            if (Ciudades[origen].ganancia > stats.mayorGananciaActual) { // si es mayor, eliminamos la lista y empezamos una nueva con el nuevo valor mas alto
                stats.CiudaddesConMayorGanancia.clear();
                stats.CiudaddesConMayorGanancia.add(Ciudades[origen].Ciudad);
                stats.mayorGananciaActual = Ciudades[origen].ganancia;
            } else if (Ciudades[origen].ganancia == stats.mayorGananciaActual) { // si es igual, la agregamos a la lista
                stats.CiudaddesConMayorGanancia.add(Ciudades[origen].Ciudad);
            }
        } else {
            stats.CiudaddesConMayorGanancia.add(origen);
            stats.mayorGananciaActual = Ciudades[origen].ganancia;
        }
    }

    public int ciudadConMayorSuperavit() {
        return Redituabilidad.peak().Ciudad;
    }

    public ArrayList<Integer> ciudadesConMayorGanancia() {
        return stats.CiudaddesConMayorGanancia;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida() {
        return stats.CiudaddesConMayorPerdida;
    }

    public int gananciaPromedioPorTraslado() {
        return stats.SumatoriaDeTraslados / stats.CantDeTraslados;
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
