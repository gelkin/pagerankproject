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
        return CheckResult.TRUE;
    }
}