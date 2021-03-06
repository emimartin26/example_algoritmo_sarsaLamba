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
public class Gridworld3 {

    public static void main(String[] args) {
        double GAMMA = 0.5;
        double EPSILON = 0.2;
        double ALFA = 0.1;
        double LAMBDA = 0.8;
        int EPISODIOS = 100000;

        int estadoInicial = 18;
        int ESTADOFINAL = 8;

        int accionInicial = 2;
        int cantidadEstados = 54;
        int ACCIONES = 4;
        String vectorAcciones[] = new String[]{"Arriba", "Derecha", "Abajo", "Izquierda"};

        SarsaLamda algoritmoo = new SarsaLamda(cantidadEstados, ACCIONES, Entorno.entorno3(), Reward.reward3(), EPISODIOS, new int[1]);
        algoritmoo.setALFA(ALFA);
        algoritmoo.setGAMMA(GAMMA);
        algoritmoo.setLAMBDA(LAMBDA);
        algoritmoo.setEPSILON(EPSILON);
        algoritmoo.setESTADOFINAL(ESTADOFINAL);
        algoritmoo.setEstadoInicial(estadoInicial);
        algoritmoo.setAccion(accionInicial);
        algoritmoo.setMapeadorAcciones(vectorAcciones);    
        algoritmoo.entrenar();
        
        algoritmoo.imprimirQ();
        System.out.println("Reward acumulado: " + algoritmoo.auxReward*-1);
        System.out.print("\n");
        Graficador.graficar(algoritmoo);
        Graficador.graficarReward(algoritmoo);
        algoritmoo.soloExplotar();
        
        

    }

}
