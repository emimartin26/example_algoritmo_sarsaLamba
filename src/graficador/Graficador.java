/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graficador;

/*
 * Autor: Roberto Perez
 * Potosi - Bolivia.
 */
import algoritmo.SarsaLamda;
import java.awt.Dimension;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class Graficador extends ApplicationFrame {

    public Graficador(double[] x, double[] y, String titulo, String eje) {
        super(titulo);
        XYDataset paresDeDatos = generarDatos(x, y);
        JFreeChart diagrama = crearDiagrama(paresDeDatos, eje);
        ChartPanel chartPanel = new ChartPanel(diagrama);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        setContentPane(chartPanel);
    }

    private XYDataset generarDatos(double[] x, double[] y) {
        //le pasamos una funcion generadora f(x)
        XYSeries datos = new XYSeries("Linea Funcion");
        for (int i = 0; i < x.length; i++) {
            datos.add(x[i], y[i]);
        }

        XYSeriesCollection conjuntoDatos = new XYSeriesCollection();
        conjuntoDatos.addSeries(datos);

        return conjuntoDatos;
    }

    private JFreeChart crearDiagrama(XYDataset conjuntoDatos, String eje) {
        JFreeChart diag = ChartFactory.createXYLineChart(
                "Graficador", //Titulo Grafica
                "EPISODIOS", // Leyenda eje X
                eje, // Leyenda eje Y
                conjuntoDatos, // Los datos
                PlotOrientation.VERTICAL, //orientacion
                false, // ver titulo de linea
                false, //tooltips
                false //URL
        );
        return diag;
    }

    //aqui definimos la funcion que desees, en esta caso la f(x) = 4sen(x)
    private double f(double x) {
        return 4 * Math.sin(x);
    }

    public static void graficar(SarsaLamda q) {
        double vector[] = new double[q.getEpisodios()];
        for (int i = 0; i < vector.length; i++) {
            vector[i] = i;
        }

        double acumulador = 0.0;
        double res = 0.0;
        double vectorRes[] = new double[q.getVector().length];
        int contador = 0;
        for (int i = 0; i < q.getVector().length; i++) {
            contador++;
            acumulador = acumulador + q.getVector()[i];
            res = acumulador / contador;
            vectorRes[i] = res;
        }
        ////////////////////////////////////////////////////////////////////

        Graficador miGraficador = new Graficador(vector, vectorRes, "Grafica Acumulador Pasos Por Episodio", "Pasos Acumulados");
        miGraficador.pack();
        RefineryUtilities.centerFrameOnScreen(miGraficador);
        miGraficador.setVisible(true);
    }

    public static void graficarReward(SarsaLamda q) {
        double vector[] = new double[q.getEpisodios()];
        for (int i = 0; i < vector.length; i++) {
            vector[i] = i;
        }

        ////////////////////////////////////////////////////////////////////

        Graficador miGraficador = new Graficador(vector, q.getVectorAcu(), "Grafica Acumulador Reward Por Episodio","Reward Acumulado");
        miGraficador.pack();
        RefineryUtilities.centerFrameOnScreen(miGraficador);
        miGraficador.setVisible(true);
    }

}
