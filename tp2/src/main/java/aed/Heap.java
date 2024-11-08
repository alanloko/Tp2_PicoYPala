package aed;

import java.util.ArrayList;

public class Heap<T> {
    private ArrayList<T> listaHeap;
    private Comparador<T> prioridad;
    private int elementos;

    public Heap(T[] t, Boolean esMaxHeap, Comparador<T> comparador) { // Bool esMaxHeap para saber si el constructor
                                                                      // crea un heapMax o HeapMin
        prioridad = comparador;
        elementos = t.length;
        listaHeap = new ArrayList<T>();
        for (int i = 0; i < elementos; i++) {
            listaHeap.add(t[i]);
        }
        // arranco en el anteultimo nivel, en su ultimo elemento
        heapify(listaHeap.get(padre(elementos - 1)), elementos - 1);
        //
    }

    public void heapify(T actual, int indice) {
        if (indice > 0) {
            siftDown(actual, indice);
            heapify(listaHeap.get(indice - 1), indice - 1);
        }
    }

    public void siftDown(T actual, int indice) {
        int posicionHijoMayor = PosicionHijoMayor(indice);
        T hijo = hijoConPriori(indice);
        if (hijo != null) {
            if (prioridad.comparar(actual, hijo) != 1) {
                listaHeap.set(posicionHijoMayor, actual);
                listaHeap.set(indice, hijo);
                siftDown(actual, posicionHijoMayor);
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
            hijoIzquierdo = listaHeap.get(hijoDer(indice));
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
            hijoIzquierdo = listaHeap.get(hijoDer(indice));
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
        return (i - 1) / 2;
    }

    public int hijoIzq(int i) {
        return (2 * i + 1);
    }

    public int hijoDer(int i) {
        return (2 * i + 2);

    }

    public void AgregarElementos(T[] t) {
        for (int i = 0; i < t.length; i++) {
            listaHeap.add(t[i]);
            siftUp(t[i], listaHeap.size() - 1);
        }
    }

    public void siftUp(T actual, int indice) {
        int posPadre = padre(indice);
        T padre = null;
        if (posPadre > 0) {
            padre = listaHeap.get(posPadre);
            if (1 != prioridad.comparar(actual, padre)) {
                listaHeap.set(posPadre, actual);
                listaHeap.set(indice, padre);
                siftUp(actual, posPadre);
            }
        }
    }

    public int[] listaOrdenada() {
        int[] listaOrdenada = new int[elementos];
        for(int i = 0; i < elementos; i++) {
            Traslado t = (Traslado) this.pop();
            listaOrdenada[i] = t.id;
        }
        return listaOrdenada;
    }

    public void eliminarElemento(int i) {
        if (i < elementos - 1) {
            T obj = listaHeap.get(i);
            listaHeap.set(i, listaHeap.get(elementos - 1));
            listaHeap.remove(elementos - 1);
            siftDown(obj, i);
        } else {
            listaHeap.remove(elementos - 1);
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
        listaHeap.set(0,obj);
        listaHeap.remove(elementos - 1);
        siftDown(obj, 0);
        elementos--;
        return priori;
    }

}
