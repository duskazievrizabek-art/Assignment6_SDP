package filesystem;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DirectoryNode extends Node {

    private final Map<String, Node> children = new TreeMap<>();

    public DirectoryNode(String name) {
        super(name);
    }

    public void add(Node child) {
        validateChild(child);
        children.put(child.getName(), child);
        child.setParent(this);
    }

    public List<Node> getChildren() {
        return List.copyOf(children.values());
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    private void validateChild(Node child) {
        if (child == null) {
            throw new IllegalArgumentException("Child must not be null");
        }
        rejectCycle(child);
        rejectSecondParent(child);
        rejectDuplicateName(child);
    }

    private void rejectCycle(Node child) {
        DirectoryNode current = this;
        while (current != null) {
            if (current == child) {
                throw new IllegalArgumentException("Cycle detected: " + child.getName());
            }
            current = current.getParent();
        }
    }

    private void rejectSecondParent(Node child) {
        if (child.getParent() != null) {
            throw new IllegalArgumentException("Element already has a parent: " + child.getName());
        }
    }

    private void rejectDuplicateName(Node child) {
        if (children.containsKey(child.getName())) {
            throw new IllegalArgumentException("Duplicate sibling name: " + child.getName());
        }
    }
}
