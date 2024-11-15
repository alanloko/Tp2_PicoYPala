package aed;

public class Comparador<T> {
    private Boolean esMax;
    private Boolean HeapCiudades;

    public Comparador(Boolean esHeapMax, Boolean EsHeapDeCiudad) {
        esMax = esHeapMax;
        HeapCiudades = EsHeapDeCiudad;
    }

    public int comparar(T obj1, T obj2) { // O(1)
        int o1 = 1; // objeto1
        int o2 = 2; // objeto2
        if (!HeapCiudades) { // O(1)
            Traslado t1 = (Traslado) obj1; // O(1)
            Traslado t2 = (Traslado) obj2; // O(1)
            if (esMax) { // O(1)
                int comparacion = Integer.compare(t1.gananciaNeta, t2.gananciaNeta); // O(1)
                if (comparacion == 0) { // O(1)
                    if (Integer.compare(t1.id, t2.id) < 0) { // O(1)
                        return o1; // O(1)
                    } else {
                        return o2; // O(1)
                    }
                }
                if (comparacion < 0) { // O(1)
                    return o2; // O(1)
                } else {
                    return o1; // O(1)
                }

            } else {
                if (Integer.compare(t1.timestamp, t2.timestamp) < 0) { // O(1)
                    return o1; // O(1)
                } else {
                    return o2; // O(1)
                }
            }
        } else {
            Ciudad c1 = (Ciudad) obj1; // O(1)
            Ciudad c2 = (Ciudad) obj2; // O(1)
            int comparacion = Integer.compare(c1.Redituabilidad, c2.Redituabilidad); // O(1)
            if (comparacion == 0) { // O(1)
                if (Integer.compare(c1.Ciudad, c2.Ciudad) < 0) { // O(1)
                    return o1; // O(1)
                } else {
                    return o2; // O(1)
                }
            } else if (comparacion < 0) { // O(1)
                return o2; // O(1)
            }
            return o1; // O(1)

        }
    }

}
