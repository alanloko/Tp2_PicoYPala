package aed;

import java.util.Comparator;

public class ComparadorDeGanancias implements Comparator<Traslado> {

    @Override
    public int compare(Traslado Traslado1, Traslado Traslado2) {
        return Integer.compare(Traslado1.gananciaNeta, Traslado2.gananciaNeta);
    }
    
}
