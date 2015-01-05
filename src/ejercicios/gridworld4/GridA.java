/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ejercicios.gridworld4;

import algoritmo.SarsaLamda;
import graficador.Graficador;
import models.Entorno;
import models.Reward;

/**
 *
 * @author emiliano
 */
public class GridA {
    public static void main(String[] args) {
        double GAMMA = 0.5;
        double EPSILON = 0.2;
        double ALFA = 0.1;
        double LAMBDA = 0.8;
        int EPISODIOS = 10000;

        int estadoInicial = 48;
        int ESTADOFINAL = 8;

        int accionInicial = 2;
        int cantidadEstados = 54;
        int ACCIONES = 4;
        String vectorAcciones[] = new String[]{"Arriba", "Derecha", "Abajo", "Izquierda"};

        SarsaLamda algoritmoo = new SarsaLamda(cantidadEstados, ACCIONES, Entorno.entorno4(), Reward.reward4(), EPISODIOS, new int[1]);
        algoritmoo.setALFA(ALFA);
        algoritmoo.setGAMMA(GAMMA);
        algoritmoo.setLAMBDA(LAMBDA);
        algoritmoo.setEPSILON(EPSILON);
        algoritmoo.setESTADOFINAL(ESTADOFINAL);
        algoritmoo.setEstadoInicial(estadoInicial);
        algoritmoo.setAccion(accionInicial);
        algoritmoo.setMapeadorAcciones(vectorAcciones);   
        
        algoritmoo.entrenar4();
        System.out.println("Reward acumulado: " + algoritmoo.auxReward*-1);
        System.out.print("\n");
        algoritmoo.imprimirQ(); 
        Graficador.graficar(algoritmoo);
        Graficador.graficarReward(algoritmoo);       
        algoritmoo.soloExplotar();
       

    }
}
