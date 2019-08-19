package pagerank;

import Jama.Matrix;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class Main {

    Matrix iterations_up_to_precision_damping(Matrix m) {
        double[] r = DoubleStream.generate(() -> 1.).limit(7).toArray();
        r = DoubleStream.of(r).map(p -> 100 * p / 7).toArray();
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
        double[][] values = {
                {0    , 1./2., 1./3., 0 , 0    , 0    , 0},
                {1./3., 0    , 0    , 0 , 1./2., 0    , 0},
                {1./3., 1./2., 0    , 1., 0    , 1./3., 0},
                {1./3., 0    , 1./3., 0 , 1./2., 1./3., 0},
                {0    , 0    , 0    , 0 , 0    , 0    , 0},
                {0    , 0    , 1./3., 0 , 0    , 0    , 0},
                {0    , 0    , 0    , 0 , 0    , 1./3., 1.}
        };
        Main obj = new Main();
        Matrix m = new Matrix(values);
        m.print(0, 3);

        // w/o damping
        Matrix res = obj.iterations_up_to_precision_damping(m);
        res.print(0, 3);
        // damping parameter
        double d = 0.5;
        double[][] ones = IntStream
                .range(0, 7)
                .mapToObj(i -> IntStream.range(0, 7)
                        .mapToDouble(j -> 1.)
                        .toArray())
                .toArray(double[][]::new);
        Matrix dMatrix = m.times(d).plus(new Matrix(ones).times((1 - d) / 7));
        res = obj.iterations_up_to_precision_damping(dMatrix);
        res.print(0, 3);
    }
}