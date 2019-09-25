package pagerank;

import org.apache.commons.math3.distribution.CauchyDistribution;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;

import java.util.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;


public class Main {

    public void plotPageRank() {
        Matrix m = generate_internet(100);
        Matrix res = pageRank(m, 0.5);
        res.print(3, 8);
        double[] values = res.getColumnPackedCopy();
        for (double val : values) {
            System.out.println(val);
        }
    }

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

    /*
5
A B C D E
0.20000000 0.00000000 0.00000000 0.50000000 0.50000000
0.20000000 1.00000000 0.00000000 0.00000000 0.00000000
0.20000000 0.00000000 0.50000000 0.00000000 0.00000000
0.20000000 0.00000000 0.50000000 0.00000000 0.00000000
0.20000000 0.00000000 0.00000000 0.50000000 0.50000000
C

6
A B C D E F
0.000 0.000 0.167 0.167 0.000 0.000
0.500 0.000 0.167 0.167 0.000 0.000
0.500 0.500 0.167 0.167 0.000 0.500
0.000 0.000 0.167 0.167 1.000 0.000
0.000 0.500 0.167 0.167 0.000 0.000
0.000 0.000 0.167 0.167 0.000 0.500
B

===
input
6
A B C D E F
0.000 0.000 0.167 0.167 0.000 0.000
0.500 0.000 0.167 0.167 0.000 0.000
0.500 0.500 0.167 0.167 0.000 0.500
0.000 0.000 0.167 0.167 1.000 0.000
0.000 0.500 0.167 0.167 0.000 0.000
0.000 0.000 0.167 0.167 0.000 0.500
E

output
E
C
D
F
B


input
6
A B C D E F
0.000 0.000 0.167 0.167 0.000 0.000
0.500 0.000 0.167 0.167 0.000 0.000
0.500 0.500 0.167 0.167 0.000 0.500
0.000 0.000 0.167 0.167 1.000 0.000
0.000 0.500 0.167 0.167 0.000 0.000
0.000 0.000 0.167 0.167 0.000 0.500
C

output
C
D
F
E
B


input
4
A B C D
0.250 0.250 0.000 0.000
0.250 0.250 0.000 1.000
0.250 0.250 1.000 0.000
0.250 0.250 0.000 0.000
A

output
A
C
B
D


input
1
A
1
A

output
A

===

3
A B C
0.00000000 0.33333333 0.00000000
1.00000000 0.33333333 0.00000000
0.00000000 0.33333333 1.00000000
B

1
A
1
A

     */
    // todo remove
    public void sandbox() {
//        Matrix m = generate_internet(4);
//        m.print(3, 3);
        try (Scanner scan = new Scanner(System.in)) {
            scan.hasNext();
            int n = scan.nextInt();
            // todo hasNext()
//            System.out.println(n);
            List<String> names = new ArrayList<>();
            for (int i = 0; i < n; ++i) {
                names.add(scan.next());
            }
//            System.out.println(names);
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
        new Main().sandbox();
//        Main obj = new Main();
//        Scanner scan = new Scanner(System.in);
//        int n = scan.nextInt();
//        double d = scan.nextDouble();
//        double[][] values = new double[n][n];
//        for (int i = 0; i < n; ++i) {
//            for (int j = 0; j < n; ++j) {
//                values[i][j] = scan.nextDouble();
//            }
//        }
//        Matrix m = new Matrix(values);
//        Matrix res = obj.pageRank(m, d);
//        res.print(0, 3);
    }
}

