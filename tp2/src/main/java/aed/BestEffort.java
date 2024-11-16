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
        TrasladosPorAntiguedad.AgregarElementos(traslados); // O(|T|*log(|T|))
        TrasladosPorGanancia.AgregarElementos(traslados); // O(|T|*log(|T|))
        // // O(|T|*log(|T|) + |T|*log(|T|)) -> O(|T|*log(|T|))
    }

    public int[] despacharMasRedituables(int n) { // O(n*(log(|T|) + log(|C|)))
        if (n > TrasladosPorGanancia.elementos()) { // si n es mayor, no queremos sobrepasarnos de memoria O(1)
            n = TrasladosPorGanancia.elementos();   // asignamos n a la cantidad de traslados totales O(1)
        } 
        int[] despacho = new int[n]; 
        int i = 0;
        while (n > 0 && TrasladosPorGanancia.elementos() > 0) {  // O(n -> n <= |T|)
            Traslado obj = TrasladosPorGanancia.obtener(0); // Obtener el primer elemento
            TrasladosPorGanancia.eliminarElemento(0); // O(log(|T|))
            TrasladosPorAntiguedad.eliminarElemento(obj.IndexAntiguedad); // O(log(|T|))
            modificarCiudades(Ciudades[obj.origen].IndexCiudad, obj.gananciaNeta, true); // O(log(|C|)) Actualizacion de la cola de Prioridad Redituabilidad,
            modificarCiudades(Ciudades[obj.destino].IndexCiudad, obj.gananciaNeta, false); // O(log(|C|)) Segun ganancian y perdida de 2 objetos diferentes
            modificarGanancia(obj.origen); // O(1) Actualización de las listas de mayor ganancia y perdida
            modificarPerdida(obj.destino); // O(1)
            despacho[i] = obj.id; 
            stats.SumatoriaDeTraslados += obj.gananciaNeta; 
            stats.CantDeTraslados++; 
            i++; 
            n--; 
        } // O(1 + 1 + 1 + 1 n*(1 + log(|T| + log(|T|) + log(|C|) + log(|C|) + 1 + 1 + 1 + 1 + 1 + 1))) -> O(n*(log(|T|) + log(|C|)))
        return despacho;
    }

    public int[] despacharMasAntiguos(int n) { // Misma justificaion de complejidad que despacharMasRedituables(n) -> O(n*(log(|T|) + log(|C|)))
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

    public void modificarPerdida(int destino) { // O(1)
        if (stats.CiudaddesConMayorPerdida.size() > 0) { // O(1) - si no es mayor a 0, es la primer ciudad ingresada
            if (Ciudades[destino].perdida > stats.mayorPerdidaActual) { // O(1) - si es mayor, eliminamos la lista y empezamos una nueva con el nuevo valor mas alto
                stats.CiudaddesConMayorPerdida.clear(); // O(1)
                stats.CiudaddesConMayorPerdida.add(Ciudades[destino].Ciudad); // O(1)
                stats.mayorPerdidaActual = Ciudades[destino].perdida; // O(1)
            } else if (Ciudades[destino].perdida == stats.mayorPerdidaActual) { // O(1) - si es igual, la agregamos a la lista
                stats.CiudaddesConMayorPerdida.add(Ciudades[destino].Ciudad); // O(1)
            }
        } else {
            stats.CiudaddesConMayorPerdida.add(destino); // O(1)
            stats.mayorPerdidaActual = Ciudades[destino].perdida; // O(1)
        }
        // O(1 + 1 + 1 + 1 + 1) -> O(1)
    }

    public void modificarGanancia(int origen) {
        if (stats.CiudaddesConMayorGanancia.size() > 0) { // O(1) - si no es mayor a 0, es la primer ciudad ingresada
            if (Ciudades[origen].ganancia > stats.mayorGananciaActual) { // si es mayor, eliminamos la lista y empezamos una nueva con el nuevo valor mas alto
                stats.CiudaddesConMayorGanancia.clear(); // O(1)
                stats.CiudaddesConMayorGanancia.add(Ciudades[origen].Ciudad); // O(1)
                stats.mayorGananciaActual = Ciudades[origen].ganancia; // O(1)
            } else if (Ciudades[origen].ganancia == stats.mayorGananciaActual) { // O(1) - si es igual, la agregamos a la lista
                stats.CiudaddesConMayorGanancia.add(Ciudades[origen].Ciudad); // O(1)
            }
        } else {
            stats.CiudaddesConMayorGanancia.add(origen); // O(1)
            stats.mayorGananciaActual = Ciudades[origen].ganancia; // O(1)
        }
    }

    public int ciudadConMayorSuperavit() { // O(1)
        return Redituabilidad.peak().Ciudad; // O(1)
    }

    public ArrayList<Integer> ciudadesConMayorGanancia() { // O(1)
        return stats.CiudaddesConMayorGanancia; // O(1)
    }

    public ArrayList<Integer> ciudadesConMayorPerdida() { // O(1)
        return stats.CiudaddesConMayorPerdida; // O(1)
    }

    public int gananciaPromedioPorTraslado() { // O(1)
        return stats.SumatoriaDeTraslados / stats.CantDeTraslados; // O(1)
    }

    // aux 
    public void modificarCiudades(int indice, int credito, boolean esGanancia) { // O(log(|C|))
        if (esGanancia) { // O(1) - Si es ganancia, puede ser que sea mayor a su padre, por lo que llamo a siftUp
            Ciudad obj = Redituabilidad.obtener(indice); 
            obj.Redituabilidad += credito; 
            obj.ganancia += credito; 
            Redituabilidad.siftUp(Redituabilidad.obtener(indice), indice); // O(log(|C|))
        } else { // O(1) - Si es perdida, puede ser que sea menor a alguno de sus hijos, por lo que llamo a siftDown
            Ciudad obj = Redituabilidad.obtener(indice); 
            obj.Redituabilidad -= credito; 
            obj.perdida += credito; 
            Redituabilidad.siftDown(Redituabilidad.obtener(indice), indice); // O(log(|C|))
        }
        // O(1 + 1 + 1 + log(|C|)) -> O(log|C|)
    }

}
