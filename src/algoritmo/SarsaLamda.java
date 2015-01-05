/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;
import models.Entorno;
import models.Reward;

/**
 *
 * @author emiliano
 */
public class SarsaLamda {

    private int Q_SIZE;
    private int ACCIONES;
    private double GAMMA;
    private double EPSILON;
    private double ALFA;
    private double LAMBDA;
    private int EPISODIOS;
    private int ESTADOFINAL;
    private int estadoActual;
    private int estadoInicial;
    private int accion;
    private int R[][];
    private double RUNO[][];
    private int ENTORNO[][];
    // Creo la matriz q
    private double q[][] = new double[Q_SIZE][ACCIONES];
    private double e[][] = new double[Q_SIZE][ACCIONES];
    private Random random;
    private double vectorCont[];
    private double vectorAcu[];
    private int viento[];
    private boolean HAYVIENTO;
    private String[] AccionesString;

    private int i = 0;

    private double reward;
    public double rewardAcumulado;
    public double auxReward;


    /*
     * q_size = cantidad de estados
     * acciones = cantidad de acciones
     * entorno = matriz entorno. (dado un estado y una accion me dice el siguiente estado)
     * r = matriz reward.(dado un estado y una accion me dice cual es el reward)
     * */
    public SarsaLamda(int q_size, int acciones, int[][] entorno, int[][] r, int episodios, int[] viento) {
        this.q = new double[q_size][acciones];
        this.e = new double[q_size][acciones];
        this.ACCIONES = acciones;
        this.Q_SIZE = q_size;
        this.ENTORNO = entorno;
        this.R = r;
        this.EPISODIOS = episodios;
        this.vectorCont = new double[episodios];
        this.vectorAcu = new double[episodios];
        this.viento = viento;
        this.HAYVIENTO = false;
    }

    public void entrenar() {
        inicializar();
        reward = 0;
        auxReward = 0;
        for (i = 0; i < EPISODIOS; i++) {
            estadoActual = estadoInicial;
            int cont = 0;
            rewardAcumulado = 0;
            do {
                if (cont == 0) {
                    estadoActual = ENTORNO[estadoActual][accion];
                }

                tomarAccion();
                if (HAYVIENTO) {
                    //System.out.println("Entro");
                    this.efectoViento();
                }
                cont++;
                rewardAcumulado = rewardAcumulado + reward;
              
            } while (estadoActual != ESTADOFINAL);
            vectorCont[i] = cont;
            auxReward = auxReward + rewardAcumulado;

            if (i == 0) {
                vectorAcu[i] = 0;
            } else {
                vectorAcu[i] = (auxReward / i);
            }
              //System.out.println("Estate: "+ i);
        }
    }
// Girdworld 4 - A

    public void entrenar4() {
        inicializar();
        reward = 0;
        auxReward = 0;
        for (i = 0; i < EPISODIOS; i++) {
            estadoActual = estadoInicial;
            int cont = 0;
            rewardAcumulado = 0;
            do {
                if (cont == 0) {
                    estadoActual = ENTORNO[estadoActual][accion];
                }
                tomarAccion();
                cont++;
                rewardAcumulado = rewardAcumulado + reward;
            } while (estadoActual != ESTADOFINAL);
            vectorCont[i] = cont;
            auxReward = auxReward + rewardAcumulado;

            if (i == 0) {
                vectorAcu[i] = 0;
            } else {
                vectorAcu[i] = (auxReward / i) * -1;
            }

            if (i == 1000) {
                this.ENTORNO = Entorno.entorno4Modificado();
                this.R = Reward.reward4Modificado();

            }

        }
    }
// Girdworld 4 - B

    public void entrenar5() {
        inicializar();
        reward = 0;
        auxReward = 0;
        for (i = 0; i < EPISODIOS; i++) {
            estadoActual = estadoInicial;
            int cont = 0;
            rewardAcumulado = 0;
            do {
                if (cont == 0) {
                    estadoActual = ENTORNO[estadoActual][accion];
                }

                tomarAccion();
                cont++;
                rewardAcumulado = rewardAcumulado + reward;
            } while (estadoActual != ESTADOFINAL);
            vectorCont[i] = cont;
            auxReward = auxReward + rewardAcumulado;

            if (i == 0) {
                vectorAcu[i] = 0;
            } else {
                vectorAcu[i] = (auxReward / i) * -1;
            }

            if (i == 1000) {
                this.ENTORNO = Entorno.entorno5();
                this.R = Reward.reward5();
                //this.EPSILON = 0.0;

            }

        }
    }

    public void entrenarUNO() {
        inicializar();
        reward = 0;
        auxReward = 0;
        for (i = 0; i < EPISODIOS; i++) {
            estadoActual = estadoInicial;
            int cont = 0;
            int contDos = 0;
            rewardAcumulado = 0;
            do {
                if (cont == 0) {
                    estadoActual = ENTORNO[estadoActual][accion];
                }

                tomarAccionUno();
                cont++;
                contDos++;
                rewardAcumulado = rewardAcumulado + reward;
            } while (contDos != 1000);
            vectorCont[i] = cont;
            auxReward = auxReward + rewardAcumulado;

            if (i == 0) {
                vectorAcu[i] = 0;
            } else {
                vectorAcu[i] = (auxReward / i) * -1;
            }
        }
    }

    public void tomarAccion() {
        random = new Random();
        double valor = random.nextDouble();

        if (valor < EPSILON) {
            this.explorar();

        } else {

            this.explotar();

        }

    }

    public void tomarAccionUno() {
        random = new Random();
        double valor = random.nextDouble();

        if (valor < EPSILON) {
            this.explorarUNO();

        } else {

            this.explotarUNO();

        }

    }

    public void explorar() {
        int estadoSiguiente = ENTORNO[estadoActual][accion];
        reward = R[estadoActual][accion];
       
        int mejorAccion = mejorAccion(estadoSiguiente);
        double qAnterior = q[estadoActual][accion];

        double delta = reward + GAMMA * q[estadoSiguiente][mejorAccion] - qAnterior;
        e[estadoActual][accion] = e[estadoActual][accion] + 1;

        for (int i = 0; i < Q_SIZE; i++) {
            for (int j = 0; j < ACCIONES; j++) {
                q[estadoActual][accion] = q[estadoActual][accion] + ALFA * delta * e[estadoActual][accion];
                e[estadoActual][accion] = GAMMA * LAMBDA * e[estadoActual][accion];
            }

        }
        estadoActual = estadoSiguiente;
        accion = getAccionPosible(ACCIONES);

    }

    public void explorarUNO() {
        int estadoSiguiente = ENTORNO[estadoActual][accion];
        double reward = RUNO[estadoActual][accion];

        //  
        int mejorAccion = mejorAccion(estadoSiguiente);
        double qAnterior = q[estadoActual][accion];

        double delta = reward + GAMMA * q[estadoSiguiente][mejorAccion] - qAnterior;
        e[estadoActual][accion] = e[estadoActual][accion] + 1;

        for (int i = 0; i < Q_SIZE; i++) {
            for (int j = 0; j < ACCIONES; j++) {
                q[estadoActual][accion] = q[estadoActual][accion] + ALFA * delta * e[estadoActual][accion];
                e[estadoActual][accion] = GAMMA * LAMBDA * e[estadoActual][accion];
            }

        }
        estadoActual = estadoSiguiente;
        accion = getAccionPosible(ACCIONES);

    }

    public int getAccionPosible(int accionesPosibles) {
        int acc = new Random().nextInt(accionesPosibles);
        return acc;
    }

    public void explotar() {
        int mejor = this.mejorAccion(estadoActual);
        estadoActual = ENTORNO[estadoActual][mejor];
        reward = R[estadoActual][accion];
        accion = mejor;

    }

    public void explotarUNO() {
        int mejor = this.mejorAccion(estadoActual);
        estadoActual = ENTORNO[estadoActual][mejor];
        reward = RUNO[estadoActual][accion];
        accion = mejor;

    }

    public int mejorAccion(int estado) {
        double valores[] = new double[ACCIONES];
        //   System.out.println(ACCIONES);
        //cargo todos los valores que tiene q para el estado en el que estoy
        for (int i = 0; i < ACCIONES; i++) {
            double valor = q[estado][i];
            valores[i] = valor;
        }

        double mayor = valores[0];
        for (int i = 0; i < ACCIONES; i++) {
            if (valores[i] > mayor) {
                mayor = valores[i];

            }
        }

        List<Integer> mejoresAcciones;
        mejoresAcciones = new ArrayList<Integer>();

        for (int i = 0; i < ACCIONES; i++) {
            int cont = 0;
            if (mayor == valores[i]) {
                mejoresAcciones.add(i);
            }
        }

        int rango = 0;
        rango = mejoresAcciones.size();
        int m;

        if (rango == 1) {
            m = mejoresAcciones.get(0);
        } else {

            int azar = new Random().nextInt(rango);
            m = mejoresAcciones.get(azar);
        }
        return m;
    }

    //Inicializo la matriz q en 0. 
    public void inicializar() {
        for (int i = 0; i < Q_SIZE; i++) {
            for (int j = 0; j < ACCIONES; j++) {
                q[i][j] = 0;
                e[i][j] = 0;
            } // j
        } // i

    }

    public double[] getVector() {
        return vectorCont;
    }

    public double[] getVectorAcu() {
        return vectorAcu;
    }

    public void setVectorAcu(double[] vectorAcu) {
        this.vectorAcu = vectorAcu;
    }

    public int getEpisodios() {
        return EPISODIOS;
    }

    public int getAccion() {
        return accion;
    }

    public void setAccion(int accion) {
        this.accion = accion;
    }

    public double[][] getQ() {
        return q;
    }

    public void setQ(double[][] q) {
        this.q = q;
    }

    public double[][] getE() {
        return e;
    }

    public void setE(double[][] e) {
        this.e = e;
    }

    public int getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoInicial(int estado) {
        this.estadoInicial = estado;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public double[] getVectorCont() {
        return vectorCont;
    }

    public void setVectorCont(double[] vectorCont) {
        this.vectorCont = vectorCont;
    }

    public int[][] getR() {
        return R;
    }

    public void setR(int[][] R) {
        this.R = R;
    }

    public int getQ_SIZE() {
        return Q_SIZE;
    }

    public void setQ_SIZE(int Q_SIZE) {
        this.Q_SIZE = Q_SIZE;
    }

    public int getACCIONES() {
        return ACCIONES;
    }

    public void setACCIONES(int ACCIONES) {
        this.ACCIONES = ACCIONES;
    }

    public double getGAMMA() {
        return GAMMA;
    }

    public void setGAMMA(double GAMMA) {
        this.GAMMA = GAMMA;
    }

    public double getEPSILON() {
        return EPSILON;
    }

    public void setEPSILON(double EPSILON) {
        this.EPSILON = EPSILON;
    }

    public double getALFA() {
        return ALFA;
    }

    public void setALFA(double ALFA) {
        this.ALFA = ALFA;
    }

    public double getLAMBDA() {
        return LAMBDA;
    }

    public void setLAMBDA(double LAMBDA) {
        this.LAMBDA = LAMBDA;
    }

    public int getEPISODIOS() {
        return EPISODIOS;
    }

    public void setEPISODIOS(int EPISODIOS) {
        this.EPISODIOS = EPISODIOS;
    }

    public int getESTADOFINAL() {
        return ESTADOFINAL;
    }

    public void setESTADOFINAL(int ESTADOFINAL) {
        this.ESTADOFINAL = ESTADOFINAL;
    }

    public int[][] getENTORNO() {
        return ENTORNO;
    }

    public void setENTORNO(int[][] ENTORNO) {
        this.ENTORNO = ENTORNO;
    }

    public void setObstaculo(int estadoObstaculo) {
        for (int i = 0; i < Q_SIZE; i++) {
            for (int j = 0; j < ACCIONES; j++) {
                if (ENTORNO[i][j] == estadoObstaculo) {
                    R[i][j] = -1;
                    ENTORNO[i][j] = i;
                }
            }
        }
    }

    public void removeObstaculo(int estadoLibre) {
        for (int i = 0; i < Q_SIZE; i++) {
            for (int j = 0; j < ACCIONES; j++) {
                if (ENTORNO[i][j] == estadoLibre) {
                    R[i][j] = 0;
                    ENTORNO[i][j] = estadoLibre;
                }
            }
        }
    }

    public void setViento(boolean b) {
        this.HAYVIENTO = b;
    }

    public void soloExplotar() {
        estadoActual = estadoInicial;
        // System.out.println("MEjor: " + this.AccionesString[this.mejorAccion(30)]);
        do {
            System.out.println("Estado Actual: " + estadoActual);
            // System.out.println("Accion: " + this.AccionesString[mejor]);
            // System.out.println("Accion: " + mejor);         
            int mejor = this.mejorAccion(estadoActual);
            System.out.println("Accion: " + this.AccionesString[mejor]);
            estadoActual = ENTORNO[estadoActual][mejor];
            System.out.println("Estado Siguiente: " + estadoActual);
            System.out.print("\n");
        } while (estadoActual != ESTADOFINAL);
    }

    public void imprimirQ() {
        System.out.println("Matriz q:");
        for (int h = 0; h < Q_SIZE; h++) {
            System.out.println("q" + h);
            for (int j = 0; j < ACCIONES; j++) {
                // double d = q[h][j];                
                //String t = String.valueOf(d) ;
                System.out.print(q[h][j] + ",\t");
            } // j
            System.out.print("\n");
        } // i
        System.out.print("\n");
    }

    public void imprimirR() {
        System.out.println("Matriz reward:");
        for (int h = 0; h < Q_SIZE; h++) {
            System.out.println("q" + h);
            for (int j = 0; j < ACCIONES; j++) {
                System.out.print(R[h][j] + ",\t");
            } // j
            System.out.print("\n");
        } // i
        System.out.print("\n");
    }

    public void imprimirEntorno() {
        System.out.println("Matriz Entorno:");
        for (int h = 0; h < Q_SIZE; h++) {
            System.out.println("q" + h);
            for (int j = 0; j < ACCIONES; j++) {
                System.out.print(ENTORNO[h][j] + ",\t");
            } // j
            System.out.print("\n");
        } // i
        System.out.print("\n");
    }

    public void imprimirE() {
        System.out.println("Matriz e:");
        for (int h = 0; h < Q_SIZE; h++) {
            System.out.println("q" + h);
            for (int j = 0; j < ACCIONES; j++) {
                System.out.print(e[h][j] + ",\t");
            } // j
            System.out.print("\n");
        } // i
        System.out.print("\n");
    }

    public void setMapeadorAcciones(String[] vectorAcciones) {
        this.AccionesString = vectorAcciones;
    }

//    public void imprimirRewardAcumulado() {
//        JOptionPane.showMessageDialog(null, "Reward acumulado: " + rewardAcumulado);
//    }
    public void setRUNO(double[][] RUNO) {
        this.RUNO = RUNO;
    }

    public void efectoViento() {
        int accionArriba = 0;
        int cantidadMovimientos = this.viento[estadoActual];
        //System.out.println("Viento: " + cantidadMovimientos);
        for (int i = 0; i < cantidadMovimientos; i++) {
            estadoActual = ENTORNO[estadoActual][accionArriba];
        }
    }

    public void efectoVientoEstocastico() {
        int accionArriba = 0;
        //genero un numero entre 0 y 3 (excluido el 3 )
        int cantidadMovimientos = (int) (Math.random() * 3 + 0);

        for (int i = 0; i < cantidadMovimientos; i++) {
            estadoActual = ENTORNO[estadoActual][accionArriba];
        }
    }

}
