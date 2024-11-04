package aed;


public class Comparador {
    private Boolean esMax;
    public Comparador(Boolean esHeapMax) {
        esMax = esHeapMax;
    }
    public Traslado comparar(Traslado Traslado1, Traslado Traslado2) {
        if (esMax) {
            int comparacion = Integer.compare(Traslado1.gananciaNeta, Traslado2.gananciaNeta);
            if(comparacion == 0) {
                if(Integer.compare(Traslado1.id, Traslado2.id) < 0) {
                    return Traslado1;
                } else { 
                    return Traslado2;
                }
            } 
            if(comparacion < 0) {
                return Traslado2;
            } else {
                return Traslado1;
            }
            
            
        } else {
            if(Integer.compare(Traslado1.gananciaNeta, Traslado2.gananciaNeta) < 0) {
                return Traslado1;
            } else {
                return Traslado2;
            }
        }
    }

}
