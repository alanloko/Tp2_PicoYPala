package aed;

import java.util.ArrayList;

public class HeapMin {
    private ArrayList<Traslado> listaHeap;
    public void Inicializar(Traslado[] ListaTraslados) {
        listaHeap = new ArrayList<Traslado>();
        for (int i = 0; i < ListaTraslados.length; i++) {
            listaHeap.add(ListaTraslados[i]);
        }
        heapify();
    }

    public void heapify() {
        int raiz = 0;
        int actual = raiz;
        while(true /* Condicion Para despues */){
            
        }
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
}

