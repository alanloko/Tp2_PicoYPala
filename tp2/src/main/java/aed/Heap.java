package aed;

import java.util.ArrayList;

public class Heap {
    private ArrayList<Traslado> listaHeap;
    private Comparador prioridad;
    private int elementos;

    public Heap(Traslado[] t, Boolean esMaxHeap) { // Bool esMaxHeap para saber si el constructor crea un heapMax o
                                                   // HeapMin
        prioridad = new Comparador(esMaxHeap);
        elementos = t.length;
        for (int i = 0; i < elementos; i++) {
            listaHeap.add(t[i]);
        }
        // arranco en el anteultimo nivel, en su ultimo elemento
        heapify(listaHeap.get(padre(elementos - 1)), elementos - 1);
    }

    public void heapify(Traslado actual, int indice) {
        if (indice >= 0) {
            siftDown(actual, indice);
            heapify(listaHeap.get(indice - 1), indice - 1);
        }
    }

    public void siftDown(Traslado actual, int indice) {
        int posicionHijoMayor = PosicionHijoMayor(indice);
        Traslado hijo = hijoConPriori(indice);
        if (hijo != null) {
            if (actual != prioridad.comparar(actual, hijo)) {
                listaHeap.set(posicionHijoMayor, actual);
                listaHeap.set(indice, hijo);
                siftDown(actual, posicionHijoMayor);
            }
        }
    }

    public Traslado hijoConPriori(int indice) {
        int cantHijos = 0;
        Traslado hijoDerecho = null;
        Traslado hijoIzquierdo = null;
        if (hijoDer(indice) < elementos) {
            hijoDerecho = listaHeap.get(hijoDer(indice));
            cantHijos++;
        }
        if (hijoIzq(indice) < elementos) {
            hijoIzquierdo = listaHeap.get(hijoDer(indice));
            cantHijos++;
        }
        if (cantHijos == 2) {
            return prioridad.comparar(hijoDerecho, hijoIzquierdo);
        } else if (cantHijos == 1) {
            return hijoIzquierdo;
        }
        return null;
    }

    public int PosicionHijoMayor(int indice) {
        int cantHijos = 0;
        Traslado hijoDerecho = null;
        Traslado hijoIzquierdo = null;
        if (hijoDer(indice) < elementos) {
            hijoDerecho = listaHeap.get(hijoDer(indice));
            cantHijos++;
        }
        if (hijoIzq(indice) < elementos) {
            hijoIzquierdo = listaHeap.get(hijoDer(indice));
            cantHijos++;
        }
        if (cantHijos == 2) {
            if(hijoDerecho == prioridad.comparar(hijoDerecho, hijoIzquierdo)) {
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
        return (2 * 1 + 1);
    }

    public int hijoDer(int i) {
        return (2 * 1 + 2);

    }

    public void AgregarElementos(Traslado[] t) {
        for (int i = 0; i < t.length; i++) {
            listaHeap.add(t[i]);
            siftUp(t[i], listaHeap.size() - 1);
        }
    }
    public void siftUp(Traslado actual, int indice) {
        int posPadre = padre(indice);
        Traslado padre = null;
        if(posPadre > 0) {
            padre = listaHeap.get(posPadre);
            if (padre != prioridad.comparar(actual, padre)) {
                listaHeap.set(posPadre, actual);
                listaHeap.set(indice, padre);
                siftUp(actual, posPadre);
            }
        }
    }
    public int[] listaOrdenada() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listaOrdenada'");
    }

    public void eliminarElemento(int i) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminarElemento'");
    }

    public Traslado obtener(int i) {
        return listaHeap.get(i);
    }

    public int elementos() {
        return elementos;
    }
}
