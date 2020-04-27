package me.uzxmx.javasistdemo.test;

import org.springframework.core.Ordered;
import org.springframework.test.annotation.DirtiesContext.HierarchyMode;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

public class DirtiesContextBeforeAndAfterClassTestExecutionListener extends AbstractTestExecutionListener {

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public void beforeTestClass(TestContext testContext) throws Exception {
        testContext.markApplicationContextDirty(HierarchyMode.EXHAUSTIVE);
    }

    @Override
    public void afterTestClass(TestContext testContext) throws Exception {
        testContext.markApplicationContextDirty(HierarchyMode.EXHAUSTIVE);
    }
}
