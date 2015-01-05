/*
 * To change this template, choose Tools | Templates
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
public class Gridworld2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        double GAMMA = 0.5;
        double EPSILON = 0.2;
        double ALFA = 0.1;
        double LAMBDA = 0.8;
        int EPISODIOS = 1000;
        
        int estadoInicial = 30;
        int ESTADOFINAL = 37;
        
        int accionInicial = 3;
        int cantidadEstados = 70;
        int ACCIONES = 9;       
        
        String vectorAcciones[] = new String[]{"Arriba","ArribaDerecha","Derecha","DerechaAbajo","Abajo","AbajoIzquierda","Izquierda","IzquierdaArriba","Me quedo quieto"};

        int vectorViento[] = new int[]{
        0, 0, 0, 1,1,1,2,2,1,0,
        0, 0, 0, 1,1,1,2,2,1,0,
        0, 0, 0, 1,1,1,2,2,1,0,
        0, 0, 0, 1,1,1,2,2,1,0,
        0, 0, 0, 1,1,1,2,2,1,0,
        0, 0, 0, 1,1,1,2,2,1,0,
        0, 0, 0, 1,1,1,2,2,1,0,};
        ////////////////////////////////////////////////////////////////////
        SarsaLamda algoritmo = new SarsaLamda(cantidadEstados,ACCIONES,Entorno.entorno2Viento(),Reward.reward2ConViento(), EPISODIOS,vectorViento);
        algoritmo.setALFA(ALFA);
        algoritmo.setGAMMA(GAMMA);
        algoritmo.setLAMBDA(LAMBDA);
        algoritmo.setEPSILON(EPSILON);
        algoritmo.setESTADOFINAL(ESTADOFINAL);
        algoritmo.setEstadoInicial(estadoInicial);
        algoritmo.setAccion(accionInicial);
        algoritmo.setMapeadorAcciones(vectorAcciones);
       
        algoritmo.setViento(true);
        
        algoritmo.entrenar();
        System.out.println("Reward acumulado: " + algoritmo.auxReward*-1);
        System.out.print("\n");
        
        algoritmo.imprimirQ();
        Graficador.graficar(algoritmo);
        Graficador.graficarReward(algoritmo);
        
        algoritmo.soloExplotar();
        
       

        


    }
     
    

}
