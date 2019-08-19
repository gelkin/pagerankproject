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
        if (res.length != (36 + 6)) {
            return CheckResult.FALSE(
                    "Your program should contain a matrix and a page rank so 42 numbers"
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

        double[][] matrix = new double[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                double value;
                try {
                    value = Double.parseDouble(res[i * n + j].trim());
                } catch (NumberFormatException ex) {
                    return CheckResult.FALSE(
                            "Your program outputted something which is not a number!"
                    );
                }
                matrix[i][j] = value;
            }
        }

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (Math.abs(L[i][j] - matrix[i][j]) > 1e-3) {
                    return CheckResult.FALSE(
                            "The matrix you outputted is incorrect."
                    );
                }
            }
        }

        double[] pageRank = new double[n];
        for (int i = n * n; i < (n * n + n); ++i) {
            double value;
            try {
                value = Double.parseDouble(res[i].trim());
            } catch (NumberFormatException ex) {
                return CheckResult.FALSE(
                        "Your program outputted something which is not a number!"
                );
            }
            pageRank[i - n * n] = value;
        }

        double[] truePageRank = new double[] {16.000, 5.333, 40.000, 25.333, 0.000, 13.333};
        for (int i = 0; i < n; ++i) {
            if (Math.abs(truePageRank[i] - pageRank[i]) > 1e-3) {
                return CheckResult.FALSE(
                        "The Page Rank you outputted is incorrect."
                );
            }
        }

        return CheckResult.TRUE;
    }
}