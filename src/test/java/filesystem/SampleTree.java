package filesystem;

final class SampleTree {

    private SampleTree() {
    }

    static DirectoryNode create() {
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode docs = new DirectoryNode("docs");
        DirectoryNode archive = new DirectoryNode("archive");

        root.add(new FileNode("readme.txt", 100));
        root.add(new FileNode("image.png", 3000));
        root.add(docs);
        root.add(new DirectoryNode("empty"));

        docs.add(new FileNode("report.txt", 2000));
        docs.add(new FileNode("notes.txt", 50));
        docs.add(archive);

        archive.add(new FileNode("old_report.txt", 500));
        archive.add(new FileNode("backup.zip", 700));
        return root;
    }
}
