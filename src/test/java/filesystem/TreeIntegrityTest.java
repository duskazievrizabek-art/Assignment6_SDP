package filesystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;

class TreeIntegrityTest {

    @Test
    void rejectsNegativeFileSize() {
        assertThrows(IllegalArgumentException.class, () -> new FileNode("a.txt", -1));
    }

    @Test
    void rejectsInvalidNames() {
        assertThrows(IllegalArgumentException.class, () -> new DirectoryNode(" "));
        assertThrows(IllegalArgumentException.class, () -> new FileNode("a/b.txt", 1));
    }

    @Test
    void rejectsDuplicateSiblingNames() {
        DirectoryNode directory = new DirectoryNode("docs");
        directory.add(new FileNode("a.txt", 1));

        assertThrows(IllegalArgumentException.class, () -> directory.add(new FileNode("a.txt", 2)));
    }

    @Test
    void rejectsSecondParent() {
        DirectoryNode first = new DirectoryNode("first");
        DirectoryNode second = new DirectoryNode("second");
        FileNode file = new FileNode("a.txt", 1);
        first.add(file);

        assertThrows(IllegalArgumentException.class, () -> second.add(file));
    }

    @Test
    void rejectsDirectoryAddedToItself() {
        DirectoryNode directory = new DirectoryNode("docs");

        assertThrows(IllegalArgumentException.class, () -> directory.add(directory));
    }

    @Test
    void rejectsCycleThroughDescendant() {
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode child = new DirectoryNode("child");
        DirectoryNode grandChild = new DirectoryNode("grandChild");
        root.add(child);
        child.add(grandChild);

        assertThrows(IllegalArgumentException.class, () -> grandChild.add(root));
    }

    @Test
    void childrenListCannotBeModified() {
        DirectoryNode directory = new DirectoryNode("docs");
        List<Node> children = directory.getChildren();

        assertThrows(UnsupportedOperationException.class, () -> children.add(new FileNode("a.txt", 1)));
    }

    @Test
    void validDeepNestingIsSupported() {
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode current = root;
        for (int level = 1; level <= 5; level++) {
            DirectoryNode next = new DirectoryNode("level" + level);
            current.add(next);
            current = next;
        }
        current.add(new FileNode("deep.txt", 10));

        assertEquals(10, new TotalSizeVisitor().calculateTotalSize(root));
    }
}
