package filesystem;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class StatisticsVisitorTest {

    private final StatisticsVisitor visitor = new StatisticsVisitor();

    @Test
    void countsFilesDirectoriesAndDepthForNestedTree() {
        visitor.collect(SampleTree.create());

        assertEquals(6, visitor.getFileCount());
        assertEquals(4, visitor.getDirectoryCount());
        assertEquals(3, visitor.getMaxDepth());
    }

    @Test
    void emptyRootDirectoryCountsItself() {
        visitor.collect(new DirectoryNode("empty"));

        assertEquals(0, visitor.getFileCount());
        assertEquals(1, visitor.getDirectoryCount());
        assertEquals(0, visitor.getMaxDepth());
    }

    @Test
    void repeatedRunsDoNotAccumulateState() {
        DirectoryNode root = SampleTree.create();

        visitor.collect(root);
        visitor.collect(root);

        assertEquals(6, visitor.getFileCount());
        assertEquals(4, visitor.getDirectoryCount());
        assertEquals(3, visitor.getMaxDepth());
    }
}
