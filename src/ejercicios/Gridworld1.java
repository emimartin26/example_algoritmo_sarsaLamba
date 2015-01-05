package ejercicios;

import algoritmo.SarsaLamda;
import graficador.Graficador;
import models.Entorno;
import models.Reward;

/**
 *
 * @author emiliano
 */
public class Gridworld1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        double GAMMA = 0.5;
        double EPSILON = 0.2;
        double ALFA = 0.1;
        double LAMBDA = 0.8;
        int EPISODIOS = 2000;

        int estadoInicial = 0;
        int ESTADOFINAL = 15;

        int accionInicial = 3;
        int cantidadEstados = 16;
        int ACCIONES = 4;

        String vectorAcciones[] = new String[]{"Arriba", "Abajo", "Izquierda", "Derecha"};
        ////////////////////////////////////////////////////////////////////
        SarsaLamda algoritmo = new SarsaLamda(cantidadEstados, ACCIONES, Entorno.entorno1(), Reward.reward1(), EPISODIOS, new int[1]);
        algoritmo.setALFA(ALFA);
        algoritmo.setGAMMA(GAMMA);
        algoritmo.setLAMBDA(LAMBDA);
        algoritmo.setEPSILON(EPSILON);
        algoritmo.setESTADOFINAL(ESTADOFINAL);
        algoritmo.setEstadoInicial(estadoInicial);
        algoritmo.setAccion(accionInicial);
        algoritmo.setMapeadorAcciones(vectorAcciones);

        algoritmo.setObstaculo(1);
        algoritmo.setObstaculo(5);
       // algoritmo.setObstaculo(9);
        algoritmo.setObstaculo(13);


        algoritmo.entrenar();
        System.out.println("Reward acumulado: " + algoritmo.auxReward * -1);
        System.out.print("\n");
        algoritmo.imprimirQ();
        
        Graficador.graficar(algoritmo);
        Graficador.graficarReward(algoritmo);
        algoritmo.soloExplotar();
        

    }

}
