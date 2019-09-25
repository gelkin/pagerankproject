package pagerank;

import org.apache.commons.math3.distribution.CauchyDistribution;

import Jama.Matrix;

import java.util.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;


public class Main {

     public List<String> searhQuery(List<String> webNames, Matrix linkMatrix, String query) {
         int topN = Math.min(webNames.size(), 5);  // top 5
         Matrix rankMatrix = pageRank(linkMatrix, 0.5);
         double[] rank = rankMatrix.getColumnPackedCopy();
         List<String> res = new ArrayList<>();
         for (String name : webNames) {
             if (query.equals(name)) {
                 res.add(name);
                 break;
             }
         }
         int[] sortedIndices = IntStream.range(0, rank.length)
                 .boxed().sorted(Comparator.comparingDouble(i -> -rank[i]))
                 .mapToInt(ele -> ele).toArray();
         if (res.size() > 0) {
             for (int i = 0; i < (topN - 1); ++i) {
                 if (!webNames.get(sortedIndices[i]).equals(query)) {
                     res.add(webNames.get(sortedIndices[i]));
                 } else {
                     for (int j = i + 1; j < topN; ++j) {
                         res.add(webNames.get(sortedIndices[j]));
                     }
                     break;
                 }
             }
         } else {
             for (int i = 0; i < topN; ++i) {
                 res.add(webNames.get(sortedIndices[i]));
             }
         }
         return res;
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

    public void run() {
        try (Scanner scan = new Scanner(System.in)) {
            int n = scan.nextInt();
            List<String> names = new ArrayList<>();
            for (int i = 0; i < n; ++i) {
                names.add(scan.next());
            }
            double[][] values = new double[n][n];
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    values[i][j] = scan.nextDouble();
                }
            }
            String query = scan.next();
            Matrix m = new Matrix(values);
            List<String> res = searhQuery(names, m, query);
            for (String x : res) {
                System.out.println(x);
            }
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }
}

