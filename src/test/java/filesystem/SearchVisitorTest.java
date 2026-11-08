package filesystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class SearchVisitorTest {

    private final DirectoryNode root = SampleTree.create();

    @Test
    void combinesNameAndSizeConditions() {
        SearchVisitor visitor = new SearchVisitor(".txt", 600);

        assertEquals(List.of("docs/report.txt"), visitor.search(root));
    }

    @Test
    void minimumSizeIsInclusive() {
        SearchVisitor visitor = new SearchVisitor("readme", 100);

        assertEquals(List.of("readme.txt"), visitor.search(root));
    }

    @Test
    void filtersBySizeOnlyWhenFragmentIsEmpty() {
        SearchVisitor visitor = new SearchVisitor("", 700);

        assertEquals(
                List.of("docs/archive/backup.zip", "docs/report.txt", "image.png"),
                visitor.search(root));
    }

    @Test
    void returnsSortedRootRelativePaths() {
        SearchVisitor visitor = new SearchVisitor(".txt", 100);

        assertEquals(
                List.of("docs/archive/old_report.txt", "docs/report.txt", "readme.txt"),
                visitor.search(root));
    }

    @Test
    void returnsEmptyListWhenNothingMatches() {
        SearchVisitor visitor = new SearchVisitor(".exe", 0);

        assertTrue(visitor.search(root).isEmpty());
    }

    @Test
    void returnsEmptyListForEmptyDirectory() {
        SearchVisitor visitor = new SearchVisitor("", 0);

        assertTrue(visitor.search(new DirectoryNode("empty")).isEmpty());
    }

    @Test
    void repeatedRunsDoNotMultiplyResultsOrChangeEarlierResults() {
        SearchVisitor visitor = new SearchVisitor(".txt", 600);

        List<String> first = visitor.search(root);
        List<String> second = visitor.search(root);
        visitor.search(new DirectoryNode("empty"));

        assertEquals(List.of("docs/report.txt"), first);
        assertEquals(first, second);
    }
}
