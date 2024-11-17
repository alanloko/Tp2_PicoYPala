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
                                            new Traslado(1, 2, 3, 100, 10),  
                                            new Traslado(2, 2, 3, 400, 20), 
                                            new Traslado(3, 5, 6, 500, 50),   
                                            new Traslado(4, 6, 5, 500, 11),  
                                            new Traslado(5, 3, 2, 1000, 40),  
                                            new Traslado(6, 3, 2, 1000, 41),  
                                            new Traslado(7, 1, 5, 2000, 42) 
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
    void nuevo_maxheap_vacio() {  
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
    void prueba_heapify() { 
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
        sis.despacharMasRedituables(1); // Despachamos todos por el heap de ganancia
        assertSetEquals(new ArrayList<>(Arrays.asList(3, 1)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(5)), sis.ciudadesConMayorPerdida());
        assertEquals(1, sis.ciudadConMayorSuperavit());
        assertEquals(785, sis.gananciaPromedioPorTraslado());
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
        sis.despacharMasAntiguos(1); // Despachamos todos por el heap de timestamp
        assertSetEquals(new ArrayList<>(Arrays.asList(3, 1)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(5)), sis.ciudadesConMayorPerdida());
        assertEquals(1, sis.ciudadConMayorSuperavit()); 
        assertEquals(785, sis.gananciaPromedioPorTraslado());
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
        sis.despacharMasAntiguos(1); // Despachamos todos variando entre los heaps de ganancia y timestamp 
        assertSetEquals(new ArrayList<>(Arrays.asList(3,1)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(5)), sis.ciudadesConMayorPerdida()); 
        assertEquals(1, sis.ciudadConMayorSuperavit());
        assertEquals(785, sis.gananciaPromedioPorTraslado());
    }

    @Test
    void despacharVacio(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);
        sis.despacharMasAntiguos(7);
        sis.despacharMasAntiguos(1); // despachamos en un sistema vacio
        assertSetEquals(new ArrayList<>(Arrays.asList(3,1)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(5)), sis.ciudadesConMayorPerdida());
        assertEquals(1, sis.ciudadConMayorSuperavit());
        assertEquals(785, sis.gananciaPromedioPorTraslado());
    }
    
    @Test
    void despacharLleno(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);
        sis.despacharMasRedituables(9); //despachar todos los elementos a la vez
        assertSetEquals(new ArrayList<>(Arrays.asList(3,1)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(5)), sis.ciudadesConMayorPerdida());
        assertEquals(1, sis.ciudadConMayorSuperavit());
        assertEquals(785, sis.gananciaPromedioPorTraslado());
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
        sis.registrarTraslados(nuevos); // registramos una lista de un elemento
        sis.despacharMasRedituables(1);
        sis.despacharMasRedituables(1);
        sis.despacharMasRedituables(1);
        sis.despacharMasRedituables(1);
        sis.despacharMasRedituables(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(0)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(4)), sis.ciudadesConMayorPerdida());
        assertEquals(0, sis.ciudadConMayorSuperavit()); 
        assertEquals(1937, sis.gananciaPromedioPorTraslado()); 
    }

    
    @Test
    void mayorGanancia(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasAntiguos(3); //despachamos la mitad y vemos cuales son las ciudades de mayor ganancia
        assertSetEquals(new ArrayList<>(Arrays.asList(2,6)), sis.ciudadesConMayorGanancia());

        sis.despacharMasRedituables(3); // despachamos el resto y vemos que se actualice bien
        assertSetEquals(new ArrayList<>(Arrays.asList(1,3)), sis.ciudadesConMayorGanancia());

    }

    @Test
    void mayorPerdida(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasAntiguos(3); //despachamos a la mitad y revisamos 
        assertSetEquals(new ArrayList<>(Arrays.asList(3,5)), sis.ciudadesConMayorPerdida());

        sis.despacharMasRedituables(3); // despachamos el resto y vemos que se actualice bien
        assertSetEquals(new ArrayList<>(Arrays.asList(5)), sis.ciudadesConMayorPerdida());

    }



    @Test
    void promedio_por_traslado(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);
        Traslado[] nuevos = new Traslado[] {
            new Traslado(8, 0, 4, 1000, 5)
        };
        sis.registrarTraslados(nuevos);

        sis.despacharMasAntiguos(4); //despachamos la mitad
        assertEquals(500, sis.gananciaPromedioPorTraslado());

        sis.despacharMasRedituables(4); //despachamos todo
        assertEquals(812, sis.gananciaPromedioPorTraslado());

    }

    @Test
    void mayor_superavit(){

        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasRedituables(5);
        assertEquals(1, sis.ciudadConMayorSuperavit()); // Probamos el caso en que las ciudades 1 y 3 están empatadas, y gana la 1 porque tiene id menor.

        Traslado[] nuevos = new Traslado[] {
            new Traslado(8, 0, 4, 2000, 55)
        };
        sis.registrarTraslados(nuevos);

        sis.despacharMasAntiguos(3);
        assertEquals(0, sis.ciudadConMayorSuperavit()); // Probamos el caso en que las ciudades 0 y 1 están empatadas, y gana la 0 porque tiene id menor.


    }

    @Test
    void stress(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);
        sis.despacharMasRedituables(3);
        sis.despacharMasAntiguos(3); //despachamos la mitad
        Traslado[] nuevos = new Traslado[] {
            new Traslado(8, 0, 4, 1001, 55),
            new Traslado(9, 1, 6, 2002, 60),
            new Traslado(10, 0, 1, 2001, 70),
            new Traslado(11, 2, 3, 500, 75),
            new Traslado(12, 1, 0, 780, 80)
        };
        sis.registrarTraslados(nuevos);
        assertEquals(6, sis.stats.CantDeTraslados);
        assertSetEquals(new ArrayList<>(Arrays.asList(1,3)), sis.ciudadesConMayorGanancia()); 
        assertSetEquals(new ArrayList<>(Arrays.asList(5)), sis.ciudadesConMayorPerdida()); 
        assertEquals(1, sis.ciudadConMayorSuperavit());                                        
        assertEquals(833, sis.gananciaPromedioPorTraslado());                                  
        sis.despacharMasAntiguos(1);
        sis.despacharMasRedituables(3);
        sis.despacharMasRedituables(1);
        sis.despacharMasAntiguos(2); // Despachamos todos
        assertSetEquals(new ArrayList<>(Arrays.asList(1)), sis.ciudadesConMayorGanancia());  
        assertSetEquals(new ArrayList<>(Arrays.asList(6)), sis.ciudadesConMayorPerdida());      
        assertEquals(1, sis.ciudadConMayorSuperavit());                                         
        assertEquals(982, sis.gananciaPromedioPorTraslado());                                  
    }

    @Test
    void muchosTraslados(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        // Insertar 500 elementos
        for (Integer i = 8; i <= 500; i++) {
            Traslado[] nuevos = new Traslado[] {
                new Traslado(i, 0, 4, 500, i+100)
            };
            sis.registrarTraslados(nuevos);
        }
        // eliminamos de forma variada
        for (Integer i = 1; i <= 250; i++) {
            sis.despacharMasAntiguos(1);
            sis.despacharMasRedituables(1);
        }
        
        assertSetEquals(new ArrayList<>(Arrays.asList(0)), sis.ciudadesConMayorGanancia());     
        assertSetEquals(new ArrayList<>(Arrays.asList(4)), sis.ciudadesConMayorPerdida());      
        assertEquals(0, sis.ciudadConMayorSuperavit());                                         
        assertEquals(504, sis.gananciaPromedioPorTraslado()); //nos fijamos que se hayan despachado todos los traslados  
    }
}