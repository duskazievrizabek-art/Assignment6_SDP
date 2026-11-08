package filesystem;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TotalSizeVisitorTest {

    private final TotalSizeVisitor visitor = new TotalSizeVisitor();

    @Test
    void sumsFilesAcrossAllNestingLevels() {
        assertEquals(6350, visitor.calculateTotalSize(SampleTree.create()));
    }

    @Test
    void emptyDirectoryHasZeroSize() {
        assertEquals(0, visitor.calculateTotalSize(new DirectoryNode("empty")));
    }

    @Test
    void repeatedRunsGiveSameResult() {
        DirectoryNode root = SampleTree.create();

        long first = visitor.calculateTotalSize(root);
        long second = visitor.calculateTotalSize(root);

        assertEquals(first, second);
    }

    @Test
    void runOnAnotherRootDoesNotKeepOldState() {
        visitor.calculateTotalSize(SampleTree.create());

        assertEquals(0, visitor.calculateTotalSize(new DirectoryNode("empty")));
    }
}
