package aed;

import java.util.ArrayList;

public class Heap<T> {
    private ArrayList<T> listaHeap;
    private Comparador<T> prioridad;
    private int elementos;
    private Boolean MaxHeap;
    private Boolean esHeapCiudad;

    public Heap(T[] t, Boolean esMaxHeap, Comparador<T> comparador, Boolean HeapCiudad) { // Bool esMaxHeap para saber
                                                                                          // si el constructor
        // crea un heapMax o HeapMin
        prioridad = comparador; // O(1)
        elementos = t.length; // O(1)
        listaHeap = new ArrayList<T>(); // O(1)
        MaxHeap = esMaxHeap; // O(1)
        esHeapCiudad = HeapCiudad; // O(1)
        if (!esHeapCiudad) { // O(1)
            for (int i = 0; i < elementos; i++) { // O(|T|) Si es un Heap de traslados, asigno los indices iniciales dentro de la Clase Traslado para usar como Handlers
                listaHeap.add(t[i]); // O(1)
                Traslado obj = (Traslado) t[i]; // O(1)
                if (esMaxHeap) { // O(1)
                    obj.IndexRedituable = i; // O(1)
                } else {
                    obj.IndexAntiguedad = i; // O(1)
                }
            } // Si es Traslados -> O(|T|)
        } else {
            for (int i = 0; i < elementos; i++) { // O(|C|)
                listaHeap.add(t[i]); // O(1)
            }
        }
        // arranco en el anteultimo nivel, en su ultimo elemento
        if (elementos != 0) { // evito un caso Borde
            // arranco en el anteultimo nivel, en su ultimo elemento, recorriendo en total n/2 elementos
            heapify(listaHeap.get(primerElemDelUltimoNivel() - 1), primerElemDelUltimoNivel() - 1); // Ciudades: O(|C|) - Traslados(O(|T|))
        }

    }

    public int primerElemDelUltimoNivel() { // O(log(elementos)) -> Traslados: O(log(|T|)) - Ciudades: O(log(|C|))
        int potencia = 1;
        while(potencia < elementos) { // potencia de 2 hasta pasarme por una potencia
            potencia *= 2;
        }
        if(potencia == 2) { // manejo de caso borde
            return 1;
        }
        return (potencia / 2) - 1; // le "saco" la ultima potencia ya que me paso de largo en el while, le resto uno por la posicion
    }

    public void heapify(T actual, int indice) { // O(n), segun la demostracion formal, n = |T| o |C|
        if (indice >= 0) {
            siftDown(actual, indice); // A lo sumo Log(n) - caso general O(1) por demostracion formal
            if (indice >= 1) { // O(1)
                heapify(listaHeap.get(indice - 1), indice - 1); // O(n - 1)
            }
        }
        // O(n/2 + (n/2 - 1) + (n/ - 2) ... + 1) -> O(n)
    }

    public void siftDown(T actual, int indice) { // O(log(n)) - misma justificacion que siftUp
        int posicionHijoMayor = PosicionHijoMayor(indice);
        T hijo = hijoConPriori(indice);
        if (hijo != null) {
            if (prioridad.comparar(actual, hijo) != 1) {
                listaHeap.set(posicionHijoMayor, actual);
                listaHeap.set(indice, hijo);
                swap(actual,hijo,indice,posicionHijoMayor);
                siftDown(actual, posicionHijoMayor);

            }
        }
    }
    public void siftUp(T actual, int indice) { // O(log(n))
        int posPadre = padre(indice); // O(1)
        T padre = null; // O(1)
        if (posPadre >= 0) { // O(1) 
            padre = listaHeap.get(posPadre); // O(1)
            if (1 == prioridad.comparar(actual, padre)) { // O(1)
                listaHeap.set(posPadre, actual); // O(1)
                listaHeap.set(indice, padre); // O(1)
                swap(actual,padre,indice, posPadre); // O(1)
                siftUp(actual, posPadre); // O(log(n)) - A lo sumo recorre la altura del arbol = log(n)
            }
        }
        // O(1 + 1 + 1 + 1 + 1 + log(n)) -> O(log(n))
    }

    public T hijoConPriori(int indice) {
        int cantHijos = 0; // O(1)
        T hijoDerecho = null; // O(1)
        T hijoIzquierdo = null; // O(1)
        if (hijoDer(indice) < elementos) { // O(1)
            hijoDerecho = listaHeap.get(hijoDer(indice)); // O(1)
            cantHijos++; // O(1)
        }
        if (hijoIzq(indice) < elementos) { // O(1)
            hijoIzquierdo = listaHeap.get(hijoIzq(indice)); // O(1)
            cantHijos++; // O(1)
        }
        if (cantHijos == 2) { // O(1)
            if (prioridad.comparar(hijoDerecho, hijoIzquierdo) == 1) { // O(1)
                return hijoDerecho; // O(1)
            } else {
                return hijoIzquierdo; // O(1)
            }
        } else if (cantHijos == 1) { // O(1)
            return hijoIzquierdo; // O(1)
        }
        return null; // O(1) -> O(1)
    }

    public int PosicionHijoMayor(int indice) { // mismas operaciones que Hijo con Priori, diferente retorno, -> O(1)
        int cantHijos = 0;
        T hijoDerecho = null;
        T hijoIzquierdo = null;
        if (hijoDer(indice) < elementos) {
            hijoDerecho = listaHeap.get(hijoDer(indice));
            cantHijos++;
        }
        if (hijoIzq(indice) < elementos) {
            hijoIzquierdo = listaHeap.get(hijoIzq(indice));
            cantHijos++;
        }
        if (cantHijos == 2) {
            if (prioridad.comparar(hijoDerecho, hijoIzquierdo) == 1) {
                return hijoDer(indice);
            } else {
                return hijoIzq(indice);
            }
        } else {
            return hijoIzq(indice);
        }
    }

    public int padre(int i) { // O(1)
        if (i - 1 < 0) {
            return -1;
        } else {
            return (i - 1) / 2;
        }
    }

    public int hijoIzq(int i) { // O(1)
        return (2 * i + 1);
    }

    public int hijoDer(int i) { // O(1)
        return (2 * i + 2);

    }

    public void AgregarElementos(T[] t) { // O(|T|*log(|T))
        for (int i = 0; i < t.length; i++) { // O(n) -> como no lo uso para ciudades, esto va a ser siempre O(|T|)
            if (!esHeapCiudad) { // O(1)
                Traslado obj = (Traslado) t[i]; // O(1)
                if (MaxHeap) { // O(1)
                    obj.IndexRedituable = elementos + i; // O(1)
                } else {
                    obj.IndexAntiguedad = elementos + i; // O(1)
                }
            } 
            listaHeap.add(t[i]); // O(1)
            siftUp(t[i], listaHeap.size() - 1); // a lo sumo O(log(|T|))
        }
        elementos += t.length; // O(1)
        // O(|T|*(1 + 1 + 1 + 1 log(|T|))) -> O(|T|*log(|T))
    }

    public void swap(T actual, T cambio, int indice, int posCambio) { // O(1)
        if (!esHeapCiudad) { // Si es un Heap sobre traslados, le cambio los indices de sus handlers
            if (MaxHeap) { // si es maxHeap, se que es redituable
                Traslado obj1 = (Traslado) actual; // O(1)
                Traslado obj2 = (Traslado) cambio; // O(1)
                obj1.IndexRedituable = posCambio; // O(1)
                obj2.IndexRedituable = indice; // O(1)
            } else { // Si es minHeap, se que es de timestamp
                Traslado obj1 = (Traslado) actual; // O(1)
                Traslado obj2 = (Traslado) cambio; // O(1)
                obj1.IndexAntiguedad = posCambio; // O(1)
                obj2.IndexAntiguedad = indice; // O(1)
            }
        } else { // Si es un heap de ciudades, le cambio los indices de su handler
            Ciudad obj1 = (Ciudad) actual; // O(1)
            Ciudad obj2 = (Ciudad) cambio; // O(1) 
            obj1.IndexCiudad = posCambio; // O(1)
            obj2.IndexCiudad = indice; // O(1)
        }
    }
    
    public void eliminarElemento(int i) {
        if (i < elementos - 1) {  // O(1)
            T obj = listaHeap.get(elementos - 1); // O(1)
            swap(obtener(i), listaHeap.get(elementos - 1), i, elementos - 1); // O(1) - le cambio la posicion e indice a los elementos
            listaHeap.set(i, obj); // O(1) - lo llevo a la ultima posicion para eliminarlo sin problemas
            listaHeap.remove(elementos - 1); // O(1)
            elementos--; // O(1)
            siftDown(obj, i); // O(log(n)) - "reacomodo" el elemento que cambie de posicion para que el heap cumpla con sus propiedades
        } else {
            listaHeap.remove(elementos - 1); // O(1)
            elementos--; // O(1)
        }
    }

    public T obtener(int i) {
        return listaHeap.get(i); // O(1)
    }

    public int elementos() {
        return elementos; // O(1)
    }

    public T peak() {
        return listaHeap.get(0); // O(1)
    }
}
