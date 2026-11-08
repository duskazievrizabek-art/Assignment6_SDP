package filesystem;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class DoubleDispatchTest {

    @Test
    void fileReachesFileVisitMethod() {
        RecordingVisitor visitor = new RecordingVisitor();

        new FileNode("a.txt", 1).accept(visitor);

        assertEquals(List.of("file:a.txt"), visitor.getCalls());
    }

    @Test
    void directoryReachesDirectoryVisitMethod() {
        RecordingVisitor visitor = new RecordingVisitor();

        new DirectoryNode("docs").accept(visitor);

        assertEquals(List.of("directory:docs"), visitor.getCalls());
    }
}
