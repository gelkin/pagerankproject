package pagerank;

import Jama.Matrix;

import java.util.stream.DoubleStream;

public class Main {
    Matrix iterate_once(Matrix m) {
        double[] r = DoubleStream.generate(() -> 1.).limit(6).toArray();
        r = DoubleStream.of(r).map(p -> 100 * p / 6).toArray();
        Matrix b = new Matrix(r, 1); // this is a vector
        Matrix c = m.times(b.transpose());
        return c;
    }

    Matrix iterate_n_times(Matrix m, int n) {
        double[] r = DoubleStream.generate(() -> 1.).limit(6).toArray();
        r = DoubleStream.of(r).map(p -> 100 * p / 6).toArray();
        Matrix b = new Matrix(r, 1); // this is a vector
        Matrix c = m.times(b.transpose());
        for (int i = 0; i < (n - 1); ++i) {
            c = m.times(c);
        }
        return c;
    }

    Matrix iterations_up_to_presicion(Matrix m)  {
        double[] r = DoubleStream.generate(() -> 1.).limit(6).toArray();
        r = DoubleStream.of(r).map(p -> 100 * p / 6).toArray();
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
        double[][] L = {
                {0    , 1./2., 1./3., 0 , 0    , 0},
                {1./3., 0    , 0    , 0 , 1./2., 0},
                {1./3., 1./2., 0    , 1., 0    , 1./2.},
                {1./3., 0    , 1./3., 0 , 1./2., 1./2.},
                {0    , 0    , 0    , 0 , 0    , 0},
                {0    , 0    , 1./3., 0 , 0    , 0}
        };
        Matrix m = new Matrix(L);
        Matrix res = obj.iterate_once(m);
        res.print(0, 3);
        res = obj.iterate_n_times(m, 100);
        res.print(0, 3);
        res = obj.iterations_up_to_presicion(m);
        res.print(0, 3);
    }
}