package aed;

public class Comparador<T> {
    private Boolean esMax;
    private Boolean HeapCiudades;

    public Comparador(Boolean esHeapMax, Boolean EsHeapDeCiudad) {
        esMax = esHeapMax;
        HeapCiudades = EsHeapDeCiudad;
    }

    public int comparar(T obj1, T obj2) {
        int o1 = 1; // objeto1
        int o2 = 2; // objeto2
        if (!HeapCiudades) {
            Traslado t1 = (Traslado) obj1;
            Traslado t2 = (Traslado) obj2;
            if (esMax) {
                int comparacion = Integer.compare(t1.gananciaNeta, t2.gananciaNeta);
                if (comparacion == 0) {
                    if (Integer.compare(t1.id, t2.id) < 0) {
                        return o1;
                    } else {
                        return o2;
                    }
                }
                if (comparacion < 0) {
                    return o2;
                } else {
                    return o1;
                }

            } else {
                if (Integer.compare(t1.timestamp, t2.timestamp) < 0) {
                    return o1;
                } else {
                    return o2;
                }
            }
        } else {
            Ciudad c1 = (Ciudad) obj1;
            Ciudad c2 = (Ciudad) obj2;
            int comparacion = Integer.compare(c1.Redituabilidad, c2.Redituabilidad);
            if (comparacion == 0) {
                if (Integer.compare(c1.Ciudad, c2.Ciudad) < 0) {
                    return o1;
                } else {
                    return o2;
                }
            } else if (comparacion < 0) {
                return o2;
            }
            return o1;

        }
    }

}
