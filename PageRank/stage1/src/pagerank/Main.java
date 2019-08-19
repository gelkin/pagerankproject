package pagerank;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class Main {
    double[] eigenPageRank(Matrix m) {
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

    public static void main(String[] args) {
        double[][] L = {
                {0    , 1./2., 1./3., 0 , 0    , 0},
                {1./3., 0    , 0    , 0 , 1./2., 0},
                {1./3., 1./2., 0    , 1., 0    , 1./2.},
                {1./3., 0    , 1./3., 0 , 1./2., 1./2.},
                {0    , 0    , 0    , 0 , 0    , 0},
                {0    , 0    , 1./3., 0 , 0    , 0}
        };
        Matrix m = new Matrix(L);
        m.print(0, 3);
        double[] res = new Main().eigenPageRank(m);
        for (double x : res) {
            System.out.println(new DecimalFormat("#0.000").format(x));
        }
    }
}