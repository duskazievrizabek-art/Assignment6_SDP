package filesystem;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public class SearchVisitor implements Visitor {

    private final String nameFragment;
    private final long minSize;
    private final Deque<String> currentPath = new ArrayDeque<>();
    private List<String> foundPaths = new ArrayList<>();

    public SearchVisitor(String nameFragment, long minSize) {
        if (nameFragment == null) {
            throw new IllegalArgumentException("Name fragment must not be null");
        }
        if (minSize < 0) {
            throw new IllegalArgumentException("Minimum size must not be negative: " + minSize);
        }
        this.nameFragment = nameFragment;
        this.minSize = minSize;
    }

    public List<String> search(Element root) {
        currentPath.clear();
        foundPaths = new ArrayList<>();
        root.accept(this);
        Collections.sort(foundPaths);
        return List.copyOf(foundPaths);
    }

    @Override
    public void visit(FileNode file) {
        if (matches(file)) {
            foundPaths.add(buildPath(file));
        }
    }

    @Override
    public void visit(DirectoryNode directory) {
        for (Node child : directory.getChildren()) {
            currentPath.addLast(child.getName());
            child.accept(this);
            currentPath.removeLast();
        }
    }

    private boolean matches(FileNode file) {
        return file.getName().contains(nameFragment) && file.getSize() >= minSize;
    }

    private String buildPath(FileNode file) {
        if (currentPath.isEmpty()) {
            return file.getName();
        }
        return String.join("/", currentPath);
    }
}
