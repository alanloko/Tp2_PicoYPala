package aed;

import java.util.ArrayList;


public class Heap {
    private ArrayList<Traslado> listaHeap;
    private ComparadorDeGanancias prioridadGanancias;
    private ComparadorDeAntiguedad prioridadAtniguedad;
    public int elementos;
    
    public void HeapMin(Traslado[] ListaTraslados) {
        listaHeap = new ArrayList<Traslado>();
        elementos = ListaTraslados.length;
        for (int i = 0; i < ListaTraslados.length; i++) { // Pasamos la lista a Array redimensionable
            listaHeap.add(ListaTraslados[i]);
        }
        heapifyMin();
    }

    public void HeapMax(Traslado[] ListaTraslados) {
        listaHeap = new ArrayList<Traslado>();
        for (int i = 0; i < ListaTraslados.length; i++) { // Pasamos la lista a Array redimensionable
            listaHeap.add(ListaTraslados[i]);
        }
        heapifyMax();
    }

    public void heapifyMin() {
        int raiz = 0;
        int actual = raiz;
        
    }

    public void heapifyMax() {
        int raiz = 0;
        int actual = raiz;
        
    }
    
    public void swiftDown(int actual) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'swiftDown'");
    }
    
    public Traslado padre(int i) {
        return listaHeap.get((i-1)/2);
    }

    public Traslado hijoIzq(int i) {
        return listaHeap.get(2*1 + 1);
    }

    public Traslado hijoDer(int i) {
        return listaHeap.get(2*1 + 2);

    }

    public void AgregarElementos(Traslado[] t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'AgregarElementos'");
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
}
