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

    Matrix automate(Matrix m) {
        double[] r = DoubleStream.generate(() -> 1.).limit(6).toArray();
        r = DoubleStream.of(r).map(p -> 100 * p / 6).toArray();
        Matrix b = new Matrix(r, 1); // this is a vector
        Matrix c = m.times(b.transpose());
        for (int i = 0; i < 100; ++i) {
            c = m.times(c);
        }
        c.print(8, 3);
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}