package pagerank;

import org.apache.commons.math3.distribution.CauchyDistribution;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.Scanner;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

class MyBarChart extends JFrame {

    public MyBarChart(double[] values) {
        initUI(values);
    }

    private void initUI(double[] values) {

        CategoryDataset dataset = createDataset(values);

        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle("Bar chart");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private CategoryDataset createDataset(double[] values) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < values.length; ++i) {
            dataset.setValue(values[i], "", Integer.valueOf(i + 1));
        }
        return dataset;
    }

    private JFreeChart createChart(CategoryDataset dataset) {

        JFreeChart barChart = ChartFactory.createBarChart(
                "",
                "",
                "PageRank",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);

        return barChart;
    }

}


public class Main {

    public void plotPageRank() {
        Matrix m = generate_internet(100);
        Matrix res = pageRank(m, 0.9);
        double[] values = res.getColumnPackedCopy();
        SwingUtilities.invokeLater(() -> {
            MyBarChart ex = new MyBarChart(values);
            ex.setVisible(true);
        });
    }


    public Matrix generate_internet(int n) {
        double[][] c = new double[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                c[i][j] = j;
            }
        }
        CauchyDistribution d = new CauchyDistribution();
        double[][] cauchy = new double[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                cauchy[i][j] = Math.abs(d.sample() / 2.);
            }
        }
        Matrix cMatrix = new Matrix(c);
        Matrix cTransposed = cMatrix.transpose();
        cMatrix = cMatrix.minus(cTransposed);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                double value = cauchy[i][j] > (Math.abs(cMatrix.get(i, j)) + 1) ? 1. : 0.;
                cMatrix.set(i, j, value);
            }
        }
        double[] colSum = new double[n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                colSum[i] += cMatrix.get(j, i) + 1e-10;
            }
        }
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                cMatrix.set(i, j, (cMatrix.get(i, j) + 1e-10) / colSum[j]);
            }
        }
        return cMatrix;
    }

    public double[] eigenPageRank(Matrix m) {
        EigenvalueDecomposition eigen = m.eig();
        final double [] realPart = eigen.getRealEigenvalues();
        Matrix evectors = eigen.getV();
        int[] sortedIndicesEvalues= IntStream.range(0, realPart.length)
                .boxed().sorted(Comparator.comparingDouble(i -> -realPart[i]))
                .mapToInt(ele -> ele).toArray();
        int colDim = evectors.getColumnDimension();
        double[] flatEvectors = evectors.getColumnPackedCopy();
        double[] principalEvector = Arrays.stream(flatEvectors,
                colDim * sortedIndicesEvalues[0],
                colDim * (sortedIndicesEvalues[0] + 1))
                .toArray();
        double sum = Arrays.stream(principalEvector).sum();
        return DoubleStream.of(principalEvector).map(p -> 100 * p / sum).toArray();
    }



    public Matrix pageRank(Matrix linkMatrix, double d) {
        int n = linkMatrix.getRowDimension();
        double[][] ones = IntStream
                .range(0, n)
                .mapToObj(i -> IntStream.range(0, n)
                        .mapToDouble(j -> 1.)
                        .toArray())
                .toArray(double[][]::new);
        Matrix m = linkMatrix.times(d).plus(new Matrix(ones).times((1 - d) / n));
        double[] r = DoubleStream.generate(() -> 1.).limit(n).toArray();
        r = DoubleStream.of(r).map(p -> 100 * p / n).toArray();
        Matrix b = new Matrix(r, 1); // this is a vector
        Matrix lastC = b.transpose();
        Matrix c = m.times(lastC);
        while (lastC.minus(c).normInf() > 0.01) {
            lastC = c;
            c = m.times(lastC);
        }
        return c;
    }

    public static void main(String[] args) {
        Main obj = new Main();
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        double d = scan.nextDouble();
        double[][] values = new double[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                values[i][j] = scan.nextDouble();
            }
        }
        Matrix m = new Matrix(values);
        Matrix res = obj.pageRank(m, d);
        res.print(0, 3);
    }
}

