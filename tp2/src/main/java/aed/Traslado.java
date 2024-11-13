package aed;

public class Traslado {
    
    int id;
    int origen;
    int destino;
    int gananciaNeta;
    int timestamp;
    int IndexRedituable;
    int IndexAntiguedad;
    
    public Traslado(int id, int origen, int destino, int gananciaNeta, int timestamp){
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.gananciaNeta = gananciaNeta;
        this.timestamp = timestamp;
    }
    public Traslado(int id, int origen, int destino, int gananciaNeta, int timestamp, int Index){
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.gananciaNeta = gananciaNeta;
        this.timestamp = timestamp;
    }

    public boolean compareTo(Traslado traslado){
        return (id == traslado.id && origen == traslado.origen && destino == traslado.destino && gananciaNeta == traslado.gananciaNeta && timestamp == traslado.timestamp && IndexRedituable == traslado.IndexRedituable && IndexAntiguedad == traslado.IndexAntiguedad);
        
    }
}
