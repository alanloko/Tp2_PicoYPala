package aed;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class PicoYPalaTests {

    int cantCiudades;
    Traslado[] listaTraslados;
    ArrayList<Integer> actual;


    @BeforeEach
    void init(){
        //Reiniciamos los valores de las ciudades y traslados antes de cada test
        cantCiudades = 7;
        listaTraslados = new Traslado[] {
                                            new Traslado(1, 2, 3, 100, 10),  //0_0 ++ 0 -- 0 
                                            new Traslado(2, 2, 3, 400, 20), //1_+2000 ++ 2000 -- 0 
                                            new Traslado(3, 5, 6, 500, 50),  //2_-1500 ++ 500 -- 2000 
                                            new Traslado(4, 6, 5, 500, 11),  //3_+1500 ++ 2000 -- 500
                                            new Traslado(5, 3, 2, 1000, 40), //4_0 ++ 0 -- 0 
                                            new Traslado(6, 3, 2, 1000, 41), //5_-2000 ++ 500 -- 2500 
                                            new Traslado(7, 1, 5, 2000, 42) //6_0 ++ 500 -- 500 
                                        };
    }
    
    void assertSetEquals(ArrayList<Integer> s1, ArrayList<Integer> s2) {
        assertEquals(s1.size(), s2.size());
        for (int e1 : s1) {
            boolean encontrado = false;
            for (int e2 : s2) {
                if (e1 == e2) encontrado = true;
            }
            assertTrue(encontrado, "No se encontró el elemento " +  e1 + " en el arreglo " + s2.toString());
        }
    }

    //Tests Heap

    @Test
    void nuevo_maxheap_vacio() {  //creo que este esta bien (lo hicimos en labo)
        Traslado[] listaT  = new Traslado[]{};
        Heap<Traslado> maxheap = new Heap<Traslado>(listaT, true, new Comparador<>(true, false),false);
        Heap<Traslado> minheap = new Heap<Traslado>(listaT, false, new Comparador<>(false, false),false);
        Traslado[] nuevosTraslados = new Traslado[] {
            new Traslado(1, 0, 1, 200, 15),
            new Traslado(2, 0, 1, 100, 40),
            new Traslado(3, 2, 0, 500, 30)
        };
        maxheap.AgregarElementos(nuevosTraslados);
        minheap.AgregarElementos(nuevosTraslados);

        assertEquals(3, maxheap.elementos());

        assertTrue(maxheap.obtener(0).compareTo(nuevosTraslados[2]));
        assertTrue(maxheap.obtener(1).compareTo(nuevosTraslados[1]));
        assertTrue(maxheap.obtener(2).compareTo(nuevosTraslados[0]));
    }
    
    @Test
    void prueba_heapify() { // este lo hicimos en el labo
        Traslado[] listaT = new Traslado[] {
            new Traslado(1, 0, 1, 200, 15),
            new Traslado(2, 0, 1, 100, 40),
            new Traslado(3, 2, 0, 500, 30)
        };
        Heap<Traslado> maxheap = new Heap<Traslado>(listaT, true, new Comparador<>(true, false),false);
        Heap<Traslado> minHeap = new Heap<Traslado>(listaT, false, new Comparador<>(false, false),false);


        assertEquals(3, maxheap.elementos());

        assertTrue(maxheap.obtener(0).compareTo(listaT[2]));
        assertTrue(maxheap.obtener(1).compareTo(listaT[1]));
        assertTrue(maxheap.obtener(2).compareTo(listaT[0]));
        assertTrue(minHeap.obtener(0).compareTo(listaT[0]));
        assertTrue(minHeap.obtener(1).compareTo(listaT[1]));
        assertTrue(minHeap.obtener(2).compareTo(listaT[2]));

    }


    // Tests nuestros
    
    @Test
    void despacharTodoPorGanacia(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);
        sis.despacharMasRedituables(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(1)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(5)), sis.ciudadesConMayorPerdida());
        sis.despacharMasRedituables(1);
        sis.despacharMasRedituables(1);
        sis.despacharMasRedituables(1);
        sis.despacharMasRedituables(1);
        sis.despacharMasRedituables(1);
        sis.despacharMasRedituables(1); // Despachamos todos para ver como queda al final
        assertSetEquals(new ArrayList<>(Arrays.asList(3, 1)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(5)), sis.ciudadesConMayorPerdida());
        assertEquals(1, sis.ciudadConMayorSuperavit()); //ciudad unica
        assertEquals(785, sis.gananciaPromedioPorTraslado()); //ganancia redondeada
    }

    @Test
    void despacharTodoPorTimestamp(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);
        sis.despacharMasAntiguos(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(2)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());
        sis.despacharMasAntiguos(1);
        sis.despacharMasAntiguos(1);
        sis.despacharMasAntiguos(1);
        sis.despacharMasAntiguos(1);
        sis.despacharMasAntiguos(1);
        sis.despacharMasAntiguos(1); // Despachamos todos para ver como queda al final
        assertSetEquals(new ArrayList<>(Arrays.asList(3, 1)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(5)), sis.ciudadesConMayorPerdida());
        assertEquals(1, sis.ciudadConMayorSuperavit()); //ciudad unica
        assertEquals(785, sis.gananciaPromedioPorTraslado()); //ganancia redondeada
    }

    @Test
    void despacharVariado(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);
        sis.despacharMasAntiguos(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(2)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());
        sis.despacharMasRedituables(1);
        sis.despacharMasAntiguos(1);
        sis.despacharMasRedituables(1);
        sis.despacharMasAntiguos(1);
        sis.despacharMasRedituables(1);
        sis.despacharMasAntiguos(1); // Despachamos todos para ver como queda al final
        assertSetEquals(new ArrayList<>(Arrays.asList(3,1)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(5)), sis.ciudadesConMayorPerdida()); 
        assertEquals(1, sis.ciudadConMayorSuperavit()); //ciudad unica
        assertEquals(785, sis.gananciaPromedioPorTraslado()); //ganancia redondeada
    }

    @Test
    void despacharVacio(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);
        sis.despacharMasAntiguos(7);
        sis.despacharMasAntiguos(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(3,1)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(5)), sis.ciudadesConMayorPerdida());
        assertEquals(1, sis.ciudadConMayorSuperavit()); //ciudad unica
        assertEquals(785, sis.gananciaPromedioPorTraslado()); //ganancia redondeada
    }
    
    @Test
    void despacharLleno(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);
        sis.despacharMasRedituables(9);
        assertSetEquals(new ArrayList<>(Arrays.asList(3,1)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(5)), sis.ciudadesConMayorPerdida());
        assertEquals(1, sis.ciudadConMayorSuperavit()); //ciudad unica
        assertEquals(785, sis.gananciaPromedioPorTraslado()); //ganancia redondeada
    }

    @Test
    void registrar(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);
        sis.despacharMasRedituables(1);
        sis.despacharMasRedituables(1);
        sis.despacharMasRedituables(1);
        Traslado[] nuevos = new Traslado[] {
            new Traslado(8, 0, 4, 10001, 55)
        };
        sis.registrarTraslados(nuevos);
        sis.despacharMasRedituables(1);
        sis.despacharMasRedituables(1);
        sis.despacharMasRedituables(1);
        sis.despacharMasRedituables(1);
        sis.despacharMasRedituables(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(0)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(4)), sis.ciudadesConMayorPerdida());
        assertEquals(0, sis.ciudadConMayorSuperavit()); //ciudad unica
        assertEquals(1937, sis.gananciaPromedioPorTraslado()); //ganancia redondeada
    }

    @Test
    void stress(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);
        sis.despacharMasRedituables(2);
        sis.despacharMasAntiguos(3);
        Traslado[] nuevos = new Traslado[] {
            new Traslado(8, 0, 4, 10001, 55),
            new Traslado(9, 0, 1, 40000, 2),
            new Traslado(10, 0, 1, 50000, 3),
            new Traslado(11, 0, 1, 50000, 4),
            new Traslado(12, 1, 0, 150000, 1)
        };
        sis.registrarTraslados(nuevos);
        assertEquals(5, sis.stats.CantDeTraslados);
        assertSetEquals(new ArrayList<>(Arrays.asList(0)), sis.ciudadesConMayorGanancia());     // FALTA CAMBIAR
        assertSetEquals(new ArrayList<>(Arrays.asList(4)), sis.ciudadesConMayorPerdida());      // FALTA CAMBIAR
        assertEquals(0, sis.ciudadConMayorSuperavit());                                         // FALTA CAMBIAR
        assertEquals(1937, sis.gananciaPromedioPorTraslado());                                  // FALTA CAMBIAR
        sis.despacharMasAntiguos(1);
        sis.despacharMasRedituables(3);
        sis.despacharMasRedituables(1);
        sis.despacharMasAntiguos(2);
        assertSetEquals(new ArrayList<>(Arrays.asList(0)), sis.ciudadesConMayorGanancia());     // FALTA CAMBIAR
        assertSetEquals(new ArrayList<>(Arrays.asList(4)), sis.ciudadesConMayorPerdida());      // FALTA CAMBIAR
        assertEquals(0, sis.ciudadConMayorSuperavit());                                         // FALTA CAMBIAR
        assertEquals(1937, sis.gananciaPromedioPorTraslado());                                  // FALTA CAMBIAR
    }













    
    // Tests BestEffort cambiando los números    
    
    @Test
    void despachar_con_mas_ganancia_de_a_uno(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasRedituables(1);
        
        assertSetEquals(new ArrayList<>(Arrays.asList(1)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(5)), sis.ciudadesConMayorPerdida());

        sis.despacharMasRedituables(1);
        sis.despacharMasRedituables(1);

        assertSetEquals(new ArrayList<>(Arrays.asList(3, 1)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(2, 5)), sis.ciudadesConMayorPerdida());
    }
    
    @Test
    void despachar_con_mas_ganancia_de_a_varios(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasRedituables(3);

        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(0, 3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasRedituables(3);

        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());

    }
    
    @Test
    void despachar_mas_viejo_de_a_uno(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);
        
        sis.despacharMasAntiguos(1);

        assertSetEquals(new ArrayList<>(Arrays.asList(0)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(1)), sis.ciudadesConMayorPerdida());

        sis.despacharMasAntiguos(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(4)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasAntiguos(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(0, 4)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 3)), sis.ciudadesConMayorPerdida());
    }
    
    @Test
    void despachar_mas_viejo_de_a_varios(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);
        
        sis.despacharMasAntiguos(3);
        assertSetEquals(new ArrayList<>(Arrays.asList(0, 4)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasAntiguos(3);
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());
        
    }
    
    @Test
    void despachar_mixtos(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasRedituables(3);
        sis.despacharMasAntiguos(3);
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasAntiguos(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());
        
    }
    
    @Test
    void agregar_traslados(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        Traslado[] nuevos = new Traslado[] {
            new Traslado(8, 0, 1, 10001, 5),
            new Traslado(9, 0, 1, 40000, 2),
            new Traslado(10, 0, 1, 50000, 3),
            new Traslado(11, 0, 1, 50000, 4),
            new Traslado(12, 1, 0, 150000, 1)
        };

        sis.registrarTraslados(nuevos);

        sis.despacharMasAntiguos(4);
        assertSetEquals(new ArrayList<>(Arrays.asList(1)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(0)), sis.ciudadesConMayorPerdida());

        sis.despacharMasRedituables(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(0)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(1)), sis.ciudadesConMayorPerdida());

    }
    
    @Test
    void promedio_por_traslado(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasAntiguos(3);
        assertEquals(333, sis.gananciaPromedioPorTraslado());

        sis.despacharMasRedituables(3);
        assertEquals(833, sis.gananciaPromedioPorTraslado());

        Traslado[] nuevos = new Traslado[] {
            new Traslado(8, 1, 2, 1452, 5),
            new Traslado(9, 1, 2, 334, 2),
            new Traslado(10, 1, 2, 24, 3),
            new Traslado(11, 1, 2, 333, 4),
            new Traslado(12, 2, 1, 9000, 1)
        };

        sis.registrarTraslados(nuevos);
        sis.despacharMasRedituables(6);

        assertEquals(1386, sis.gananciaPromedioPorTraslado());
        

    }

    @Test
    void mayor_superavit(){
        Traslado[] nuevos = new Traslado[] {
            new Traslado(1, 3, 4, 1, 7),
            new Traslado(7, 6, 5, 40, 6),
            new Traslado(6, 5, 6, 3, 5),
            new Traslado(2, 2, 1, 41, 4),
            new Traslado(3, 3, 4, 100, 3),
            new Traslado(4, 1, 2, 30, 2),
            new Traslado(5, 2, 1, 90, 1)
        };
        BestEffort sis = new BestEffort(this.cantCiudades, nuevos);

        sis.despacharMasAntiguos(1);
        assertEquals(2, sis.ciudadConMayorSuperavit());

        sis.despacharMasAntiguos(2);
        assertEquals(3, sis.ciudadConMayorSuperavit());

        sis.despacharMasAntiguos(3);
        assertEquals(2, sis.ciudadConMayorSuperavit());

        sis.despacharMasAntiguos(1);
        assertEquals(2, sis.ciudadConMayorSuperavit());

    }
}