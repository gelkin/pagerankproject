import org.hyperskill.hstest.v5.stage.BaseStageTest;
import org.hyperskill.hstest.v5.testcase.CheckResult;
import org.hyperskill.hstest.v5.testcase.TestCase;
import pagerank.Main;

import java.util.List;

public class PageRankTest extends BaseStageTest {
    public PageRankTest() {
        super(Main.class);
    }

    @Override
    public List<TestCase> generate() {
        return List.of(
                new TestCase()
        );
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        String[] res = reply.trim().split("\\s+");
        if (res.length != (6 + 6 + 6)) {
            return CheckResult.FALSE(
                    "Your program should contain three page rank vectors so 18 numbers"
            );
        }
        int n = 6;
        double[][] L = {
                {0    , 1./2., 1./3., 0 , 0    , 0},
                {1./3., 0    , 0    , 0 , 1./2., 0},
                {1./3., 1./2., 0    , 1., 0    , 1./2.},
                {1./3., 0    , 1./3., 0 , 1./2., 1./2.},
                {0    , 0    , 0    , 0 , 0    , 0},
                {0    , 0    , 1./3., 0 , 0    , 0}
        };

        double[] pageRank1Iter = new double[] {13.889, 13.889, 38.889, 27.778, 0.000, 5.556};
        double[] pageRank100Iters = new double[] {16.000, 5.333, 40.000, 25.333, 0.000, 13.333};
        double[] pageRankUpToPrecision = new double[] {15.998, 5.334, 40.003, 25.334, 0.000, 13.331};

        // 1 iter
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

        for (int i = 0; i < n; ++i) {
            if (Math.abs(pageRank1Iter[i] - pageRank[i]) > 1e-4) {
                return CheckResult.FALSE(
                        "The first Page Rank you outputted is incorrect."
                );
            }
        }

        // 100 iters
        for (int i = n; i < n * 2; ++i) {
            double value;
            try {
                value = Double.parseDouble(res[i].trim());
            } catch (NumberFormatException ex) {
                return CheckResult.FALSE(
                        "Your program outputted something which is not a number!"
                );
            }
            pageRank[i - n] = value;
        }

        for (int i = 0; i < n; ++i) {
            if (Math.abs(pageRank100Iters[i] - pageRank[i]) > 1e-4) {
                return CheckResult.FALSE(
                        "The second Page Rank you outputted is incorrect."
                );
            }
        }

        // up to precision
        for (int i = n * 2; i < n * 3; ++i) {
            double value;
            try {
                value = Double.parseDouble(res[i].trim());
            } catch (NumberFormatException ex) {
                return CheckResult.FALSE(
                        "Your program outputted something which is not a number!"
                );
            }
            pageRank[i - n * 2] = value;
        }

        for (int i = 0; i < n; ++i) {
            if (Math.abs(pageRankUpToPrecision[i] - pageRank[i]) > 1e-2) {
                return CheckResult.FALSE(
                        "The third Page Rank you outputted is incorrect."
                );
            }
        }

        return CheckResult.TRUE;
    }
}