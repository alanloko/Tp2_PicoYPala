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
        prioridad = comparador;
        elementos = t.length;
        listaHeap = new ArrayList<T>();
        MaxHeap = esMaxHeap;
        esHeapCiudad = HeapCiudad;
        if (!esHeapCiudad) {
            for (int i = 0; i < elementos; i++) {
                listaHeap.add(t[i]);
                Traslado obj = (Traslado) t[i];
                if (esMaxHeap) {
                    obj.IndexRedituable = i;
                } else {
                    obj.IndexAntiguedad = i;
                }
            }
        } else {
            for (int i = 0; i < elementos; i++) {
                listaHeap.add(t[i]);
            }
        }
        // arranco en el anteultimo nivel, en su ultimo elemento
        if (elementos != 0) {
            //heapify(listaHeap.get(padre(elementos - 1)), padre(elementos - 1));
            heapify(listaHeap.get(primerElemDelUltimoNivel() - 1), primerElemDelUltimoNivel() - 1);
        }

    }

    public int primerElemDelUltimoNivel() {
        int potencia = 1;
        while(potencia < elementos) {
            potencia *= 2;
        }
        if(potencia == 2) {
            return 1;
        }
        return (potencia / 2) - 1;
    }

    public void heapify(T actual, int indice) {
        if (indice >= 0) {
            siftDown(actual, indice);
            if (indice >= 1) {
                heapify(listaHeap.get(indice - 1), indice - 1);
            }
        }
    }

    public void siftDown(T actual, int indice) {
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
    public void siftUp(T actual, int indice) {
        int posPadre = padre(indice);
        T padre = null;
        if (posPadre >= 0) {
            padre = listaHeap.get(posPadre);
            if (1 == prioridad.comparar(actual, padre)) {
                listaHeap.set(posPadre, actual);
                listaHeap.set(indice, padre);
                swap(actual,padre,indice, posPadre);
                siftUp(actual, posPadre);
            }
        }
    }

    public T hijoConPriori(int indice) {
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
                return hijoDerecho;
            } else {
                return hijoIzquierdo;
            }
        } else if (cantHijos == 1) {
            return hijoIzquierdo;
        }
        return null;
    }

    public int PosicionHijoMayor(int indice) {
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

    public int padre(int i) {
        if (i - 1 < 0) {
            return -1;
        } else {
            return (i - 1) / 2;
        }
    }

    public int hijoIzq(int i) {
        return (2 * i + 1);
    }

    public int hijoDer(int i) {
        return (2 * i + 2);

    }

    public void AgregarElementos(T[] t) {
        for (int i = 0; i < t.length; i++) {
            if (!esHeapCiudad) {
                Traslado obj = (Traslado) t[i];
                if (MaxHeap) {
                    obj.IndexRedituable = elementos + i;
                } else {
                    obj.IndexAntiguedad = elementos + i;
                }
            } 
            listaHeap.add(t[i]);
            siftUp(t[i], listaHeap.size() - 1);
        }
        elementos += t.length;
    }

    public void swap(T actual, T cambio, int indice, int posCambio) {
        if (!esHeapCiudad) {
            if (MaxHeap) {
                Traslado obj1 = (Traslado) actual;
                Traslado obj2 = (Traslado) cambio;
                obj1.IndexRedituable = posCambio;
                obj2.IndexRedituable = indice;
            } else {
                Traslado obj1 = (Traslado) actual;
                Traslado obj2 = (Traslado) cambio;
                obj1.IndexAntiguedad = posCambio;
                obj2.IndexAntiguedad = indice;
            }
        } else {
            Ciudad obj1 = (Ciudad) actual;
            Ciudad obj2 = (Ciudad) cambio;
            obj1.IndexCiudad = posCambio;
            obj2.IndexCiudad = indice;
        }
    }
    
    public void eliminarElemento(int i) {
        if (i < elementos - 1) {
            T obj = listaHeap.get(elementos - 1);
            listaHeap.set(i, obj);
            listaHeap.remove(elementos - 1);
            elementos--;
            siftDown(obj, i);
        } else {
            listaHeap.remove(elementos - 1);
            elementos--;
        }
    }

    public T obtener(int i) {
        return listaHeap.get(i);
    }

    public int elementos() {
        return elementos;
    }

    public T pop() {
        T priori = listaHeap.get(0);
        T obj = listaHeap.get(elementos - 1);
        listaHeap.set(0, obj);
        listaHeap.remove(elementos - 1);
        elementos--;
        siftDown(obj, 0);
        return priori;
    }

    public T peak() {
        return listaHeap.get(0);
    }
}
