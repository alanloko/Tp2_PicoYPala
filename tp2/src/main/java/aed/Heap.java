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
            heapify(listaHeap.get(padre(elementos - 1)), elementos - 1);
        }

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
                if (!esHeapCiudad) {
                    if (MaxHeap) {
                        Traslado obj1 = (Traslado) actual;
                        Traslado obj2 = (Traslado) hijo;
                        obj1.IndexRedituable = posicionHijoMayor;
                        obj2.IndexRedituable = indice;
                    } else {
                        Traslado obj1 = (Traslado) actual;
                        Traslado obj2 = (Traslado) hijo;
                        obj1.IndexAntiguedad = posicionHijoMayor;
                        obj2.IndexAntiguedad = indice;
                    }
                    siftDown(actual, posicionHijoMayor);
                } else {
                    Ciudad obj1 = (Ciudad) actual;
                    Ciudad obj2 = (Ciudad) hijo;
                    obj1.IndexCiudad = posicionHijoMayor;
                    obj2.IndexCiudad = indice;
                }
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

    public void siftUp(T actual, int indice) {
        int posPadre = padre(indice);
        T padre = null;
        if (posPadre >= 0) {
            padre = listaHeap.get(posPadre);
            if (1 == prioridad.comparar(actual, padre)) {
                listaHeap.set(posPadre, actual);
                listaHeap.set(indice, padre);
                if (!esHeapCiudad) {
                    if (MaxHeap) {
                        Traslado obj1 = (Traslado) actual;
                        Traslado obj2 = (Traslado) padre;
                        obj1.IndexRedituable = posPadre;
                        obj2.IndexRedituable = indice;
                    } else {
                        Traslado obj1 = (Traslado) actual;
                        Traslado obj2 = (Traslado) padre;
                        obj1.IndexAntiguedad = posPadre;
                        obj2.IndexAntiguedad = indice;
                    }
                } else {
                    Ciudad obj1 = (Ciudad) actual;
                    Ciudad obj2 = (Ciudad) padre;
                    obj1.IndexCiudad = posPadre;
                    obj2.IndexCiudad = indice;
                }
                siftUp(actual, posPadre);
            }
        }
    }

    public int[] listaOrdenada() {
        int[] listaOrdenada = new int[elementos];
        int i = 0;
        while (elementos != 0) {
            Traslado t = (Traslado) this.pop();
            listaOrdenada[i] = t.id;
            i++;
            elementos--;
        }
        return listaOrdenada;
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

    public void modificarCiudades(int indice, int credito, boolean esGanancia) {
        if (esGanancia) {
            Ciudad obj = (Ciudad) listaHeap.get(indice);
            obj.Redituabilidad += credito;
            obj.ganancia += credito;
            siftUp(listaHeap.get(indice), indice);
        } else {
            Ciudad obj = (Ciudad) listaHeap.get(indice);
            obj.Redituabilidad -= credito;
            obj.perdida += credito;
            siftDown(listaHeap.get(indice), indice);
        }
    }

    public T peak() {
        return listaHeap.get(0);
    }
}
