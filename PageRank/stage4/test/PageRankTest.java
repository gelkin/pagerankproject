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
        if (res.length != (7 * 7 + 7 + 7)) {
            return CheckResult.FALSE(
                    "Your program should contain a matrix and two page rank vectors so 63 numbers (7 * 7 + 7 + 7)"
            );
        }
        int n = 7;
        double[][] L2 = {
                {0    , 1./2., 1./3., 0 , 0    , 0    , 0},
                {1./3., 0    , 0    , 0 , 1./2., 0    , 0},
                {1./3., 1./2., 0    , 1., 0    , 1./3., 0},
                {1./3., 0    , 1./3., 0 , 1./2., 1./3., 0},
                {0    , 0    , 0    , 0 , 0    , 0    , 0},
                {0    , 0    , 1./3., 0 , 0    , 0    , 0},
                {0    , 0    , 0    , 0 , 0    , 1./3., 1.}
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
                if (Math.abs(L2[i][j] - matrix[i][j]) > 1e-3) {
                    return CheckResult.FALSE(
                            "The matrix you outputted is incorrect."
                    );
                }
            }
        }

        // page ranks
        double[] pageRankWODamp = new double[] {0.033, 0.012, 0.078, 0.049, 0.000, 0.027, 99.801};
        double[] pageRankWithDamp = new double[] {13.682, 11.209, 22.420, 16.759, 7.143, 10.880, 17.907};

        // w/o damping
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

        for (int i = 0; i < n; ++i) {
            if (Math.abs(pageRankWODamp[i] - pageRank[i]) > 1e-2) {
                return CheckResult.FALSE(
                        "The first Page Rank you outputted is incorrect."
                );
            }
        }

        // with damping
        for (int i = n * n + n; i < (n * n + n + n); ++i) {
            double value;
            try {
                value = Double.parseDouble(res[i].trim());
            } catch (NumberFormatException ex) {
                return CheckResult.FALSE(
                        "Your program outputted something which is not a number!"
                );
            }
            pageRank[i - (n * n + n)] = value;
        }

        for (int i = 0; i < n; ++i) {
            if (Math.abs(pageRankWithDamp[i] - pageRank[i]) > 1e-2) {
                return CheckResult.FALSE(
                        "The second Page Rank you outputted is incorrect."
                );
            }
        }

        return CheckResult.TRUE;
    }
}