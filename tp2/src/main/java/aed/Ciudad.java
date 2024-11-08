package aed;

public class Ciudad {
    int Ciudad;
    int Redituabilidad;

    public Ciudad(int i, int Ganancia, int Perdida) {
        Ciudad = i;
        Redituabilidad = Ganancia - Perdida;
    }
}
