/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicios;

import algoritmo.SarsaLamda;
import graficador.Graficador;
import models.Entorno;
import models.Reward;

/**
 *
 * @author emiliano
 */
public class Uno {

    public static void main(String[] args) {
        double GAMMA = 0.5;
        double EPSILON = 0.2;
        double ALFA = 0.1;
        double LAMBDA = 0.8;
        int EPISODIOS = 2000;

        int estadoInicial = 0;
        int ESTADOFINAL = 0;

        int accionInicial = 1;
        int cantidadEstados = 2;
        int ACCIONES = 2;

        String vectorAcciones[] = new String[]{"Climb", "Rest"};

        ////////////////////////////////////////////////////////////////////
        SarsaLamda algoritmo = new SarsaLamda(cantidadEstados, ACCIONES, Entorno.entornoUNO(), new int[1][1], EPISODIOS, new int[1]);
        algoritmo.setRUNO(Reward.rewardUNO());
        algoritmo.setALFA(ALFA);
        algoritmo.setGAMMA(GAMMA);
        algoritmo.setLAMBDA(LAMBDA);
        algoritmo.setEPSILON(EPSILON);
        algoritmo.setESTADOFINAL(ESTADOFINAL);
        algoritmo.setEstadoInicial(estadoInicial);
        algoritmo.setAccion(accionInicial);
        algoritmo.setMapeadorAcciones(vectorAcciones);

        algoritmo.entrenarUNO();
        System.out.println("Reward acumulado: " + algoritmo.auxReward * -1);
        System.out.print("\n");
        algoritmo.imprimirQ();
        
        Graficador.graficar(algoritmo);
        Graficador.graficar(algoritmo);
        Graficador.graficarReward(algoritmo);

    }

}
