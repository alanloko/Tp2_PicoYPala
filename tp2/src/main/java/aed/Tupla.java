package aed;

public class Tupla {
    private int elemento1;
    private int elemento2;

    public Tupla(int valor1, int valor2){
        elemento1 = valor1;
        elemento2 = valor2;
    }
    public int ganancia() {
        return elemento1;
    }
    public int perdida() {
        return elemento2;
    }
    public void modificarGanancia(int a) {
        elemento1 = a;
    }
    public void modificarPerdida(int b) {
        elemento2 = b;
    }
}
