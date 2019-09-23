import Jama.Matrix;
import org.hyperskill.hstest.v5.stage.BaseStageTest;
import org.hyperskill.hstest.v5.testcase.CheckResult;
import org.hyperskill.hstest.v5.testcase.TestCase;
import pagerank.Main;

import java.util.List;

class Attach {
    String feedback;
    String ans;
    Attach(String feedback, String ans) {
        this.feedback = feedback;
        this.ans = ans;
    }
}

public class PageRankTest extends BaseStageTest<Attach> {
    public PageRankTest() {
        super(Main.class);
    }

    @Override
    public List<TestCase<Attach>> generate() {
        return List.of(
                new TestCase().setInput("3 0.15\n0.333 0.000 0.500\n0.333 1.000 0.000\n0.333 0.000 0.500").setAttach(
                        new Attach("Doesn't return correct result on a network of 3 nodes.", "32.375 35.231 32.375")),
                new TestCase().setInput("10 0.5\n0.100 0.100 0.000 0.100 0.500 0.000 0.500 0.000 0.100 0.000\n0.100 0.100 0.000 0.100 0.000 0.000 0.000 0.000 0.100 0.000\n0.100 0.100 1.000 0.100 0.000 0.000 0.500 0.500 0.100 0.000\n0.100 0.100 0.000 0.100 0.000 0.000 0.000 0.000 0.100 0.000\n0.100 0.100 0.000 0.100 0.500 0.000 0.000 0.000 0.100 0.000\n0.100 0.100 0.000 0.100 0.000 0.500 0.000 0.000 0.100 0.000\n0.100 0.100 0.000 0.100 0.000 0.500 0.000 0.000 0.100 0.000\n0.100 0.100 0.000 0.100 0.000 0.000 0.000 0.500 0.100 1.000\n0.100 0.100 0.000 0.100 0.000 0.000 0.000 0.000 0.100 0.000\n0.100 0.100 0.000 0.100 0.000 0.000 0.000 0.000 0.100 0.000\n")
                        .setAttach(new Attach("Doesn't return correct result on a network of 10 nodes.", "10.870 6.522 23.910 6.522 8.696 8.696 8.696 13.044 6.522 6.522")),
                new TestCase().setInput("20 0.6\n0.050 0.000 0.000 0.000 0.250 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.050 0.050 0.000 0.050 0.000 0.050 0.050 0.000\n0.050 1.000 1.000 0.000 0.000 0.000 0.000 0.250 0.000 0.000 0.000 0.000 0.050 0.050 0.000 0.050 0.000 0.050 0.050 0.000\n0.050 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.050 0.050 0.000 0.050 0.000 0.050 0.050 0.000\n0.050 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.050 0.050 0.000 0.050 0.000 0.050 0.050 0.000\n0.050 0.000 0.000 0.000 0.000 1.000 0.000 0.000 0.000 0.000 0.000 0.000 0.050 0.050 0.000 0.050 0.000 0.050 0.050 0.000\n0.050 0.000 0.000 1.000 0.250 0.000 0.500 0.000 0.000 0.000 0.000 0.000 0.050 0.050 0.000 0.050 0.000 0.050 0.050 0.000\n0.050 0.000 0.000 0.000 0.000 0.000 0.500 0.000 0.500 0.000 0.000 0.000 0.050 0.050 0.000 0.050 0.000 0.050 0.050 0.000\n0.050 0.000 0.000 0.000 0.250 0.000 0.000 0.250 0.000 0.000 0.333 0.000 0.050 0.050 0.000 0.050 0.000 0.050 0.050 0.000\n0.050 0.000 0.000 0.000 0.000 0.000 0.000 0.250 0.000 0.500 0.000 0.500 0.050 0.050 0.000 0.050 0.000 0.050 0.050 0.000\n0.050 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.050 0.050 0.000 0.050 0.000 0.050 0.050 0.000\n0.050 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.500 0.333 0.000 0.050 0.050 0.000 0.050 0.000 0.050 0.050 0.000\n0.050 0.000 0.000 0.000 0.000 0.000 0.000 0.250 0.500 0.000 0.000 0.000 0.050 0.050 0.000 0.050 0.000 0.050 0.050 0.000\n0.050 0.000 0.000 0.000 0.250 0.000 0.000 0.000 0.000 0.000 0.333 0.000 0.050 0.050 0.000 0.050 0.000 0.050 0.050 0.000\n0.050 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.500 0.050 0.050 0.000 0.050 0.000 0.050 0.050 0.000\n0.050 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.050 0.050 0.000 0.050 1.000 0.050 0.050 0.000\n0.050 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.050 0.050 1.000 0.050 0.000 0.050 0.050 0.000\n0.050 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.050 0.050 0.000 0.050 0.000 0.050 0.050 1.000\n0.050 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.050 0.050 0.000 0.050 0.000 0.050 0.050 0.000\n0.050 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.050 0.050 0.000 0.050 0.000 0.050 0.050 0.000\n0.050 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.000 0.050 0.050 0.000 0.050 0.000 0.050 0.050 0.000")
                        .setAttach(new Attach("Doesn't return correct result on a network which has more than 10 nodes.", "3.798 12.954 2.725 2.725 7.148 7.369 6.454 5.509 5.973 2.725 4.428 5.344 4.682 4.329 5.342 5.931 4.360 2.725 2.725 2.725"))
                );
    }

    @Override
    public CheckResult check(String reply, Attach attach) {
        String[] trueRes = attach.ans.trim().split("\\s+");
        int n = trueRes.length;
        String[] res = reply.trim().split("\\s+");
        if (res.length != n) {
            return CheckResult.FALSE(
                    "Your program should contain a page rank vector of size " + n + "."
            );
        }
        double[] pageRank = new double[n];
        for (int i = 0; i < n; ++i) {
            double value;
            try {
                value = Double.parseDouble(res[i].trim());
            } catch (NumberFormatException ex) {
                return CheckResult.FALSE(
                        "Your program outputted something which is not a number!"
                );
            }
            pageRank[i] = value;
        }

        double[] pageRankTrue = new double[n];
        for (int i = 0; i < n; ++i) {
            double value = Double.parseDouble(trueRes[i].trim());
            pageRankTrue[i] = value;
        }

        Matrix mPageRank = new Matrix(pageRank, 1); // this is a vector
        Matrix mPageRankTrue = new Matrix(pageRankTrue, 1); // this is a vector
        if (mPageRank.minus(mPageRankTrue).normInf() > 0.01) {
            return CheckResult.FALSE(attach.feedback);
        }
        return CheckResult.TRUE;
    }
}