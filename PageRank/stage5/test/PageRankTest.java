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
                new TestCase().setInput("6\nA B C D E F\n0.000 0.000 0.167 0.167 0.000 0.000\n0.500 0.000 0.167 0.167 0.000 0.000\n0.500 0.500 0.167 0.167 0.000 0.500\n0.000 0.000 0.167 0.167 1.000 0.000\n0.000 0.500 0.167 0.167 0.000 0.000\n0.000 0.000 0.167 0.167 0.000 0.500\nE")
                        .setAttach(
                        new Attach("Doesn't return correct result on a network of 6 nodes.", "E\n" + "C\n" + "D\n" + "F\n" + "B")),
                new TestCase().setInput("6\nA B C D E F\n0.000 0.000 0.167 0.167 0.000 0.000\n0.500 0.000 0.167 0.167 0.000 0.000\n0.500 0.500 0.167 0.167 0.000 0.500\n0.000 0.000 0.167 0.167 1.000 0.000\n0.000 0.500 0.167 0.167 0.000 0.000\n0.000 0.000 0.167 0.167 0.000 0.500\nC")
                        .setAttach(new Attach("Doesn't return correct result on a network of 6 nodes.", "C\n" + "D\n" + "F\n" + "E\n" + "B")),
                new TestCase().setInput("4\nA B C D\n0.250 0.250 0.000 0.000\n0.250 0.250 0.000 1.000\n0.250 0.250 1.000 0.000\n0.250 0.250 0.000 0.000\nA")
                        .setAttach(new Attach("Doesn't return correct result on a network of 4 nodes.", "A\n" + "C\n" + "B\n" + "D")),
                new TestCase().setInput("1\nA\n1\nA")
                        .setAttach(new Attach("Doesn't return correct result on a network of 1 node.", "A"))
                );
    }

    @Override
    public CheckResult check(String reply, Attach attach) {
        String[] trueRes = attach.ans.trim().split("\\s+");
        int n = trueRes.length;
        String[] res = reply.trim().split("\\s+");
        if (res.length != n) {
            return CheckResult.FALSE(
                    "Your program should print " + n + " websites names line by line."
            );
        }
        for (int i = 0; i < n; ++i) {
            if (!res[i].equals(trueRes[i])) {
                return CheckResult.FALSE(attach.feedback);
            }
        }
        return CheckResult.TRUE;
    }
}