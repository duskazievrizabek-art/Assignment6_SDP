package filesystem;

public abstract class Node implements Element {

    private final String name;
    private DirectoryNode parent;

    protected Node(String name) {
        if (name == null || name.isBlank() || name.contains("/")) {
            throw new IllegalArgumentException("Invalid name: " + name);
        }
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    DirectoryNode getParent() {
        return parent;
    }

    void setParent(DirectoryNode parent) {
        this.parent = parent;
    }
}
