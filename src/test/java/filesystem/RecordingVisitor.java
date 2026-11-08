package filesystem;

import java.util.ArrayList;
import java.util.List;

class RecordingVisitor implements Visitor {

    private final List<String> calls = new ArrayList<>();

    List<String> getCalls() {
        return calls;
    }

    @Override
    public void visit(FileNode file) {
        calls.add("file:" + file.getName());
    }

    @Override
    public void visit(DirectoryNode directory) {
        calls.add("directory:" + directory.getName());
    }
}
